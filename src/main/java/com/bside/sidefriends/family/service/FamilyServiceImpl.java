package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.family.exception.*;
import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.exception.*;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FamilyServiceImpl implements FamilyService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateFamilyResponseDto createFamily(String memberUsername, CreateFamilyRequestDto createFamilyRequestDto) {

        Long groupManagerId = createFamilyRequestDto.getGroupManagerId();
        User managerUser = userRepository.findByUserIdAndIsDeletedFalse(groupManagerId)
                .orElseThrow(() -> new FamilyManagerNotFoundException(new UserNotFoundException()));

        User memberUser = userRepository.findByUsernameAndIsDeletedFalse(memberUsername)
                .orElseThrow(() -> new FamilyMemberNotFoundException(new UserNotFoundException()));

        if (memberUser.getUserId().equals(managerUser.getUserId())) {
            throw new FamilyManagerRequiredException("가족 그룹장과 가족 그룹원은 동일할 수 없습니다.");
        }

        if (userHasFamily.test(managerUser)) {
            throw new UserHasFamilyException();
        }

        // 가족 생성
        Family family = new Family();

        // 가족 그룹장 추가
        managerUser.changeRole(User.Role.ROLE_MANAGER);
        family.addUser(managerUser);

        // 가족 구성원 추가
        family.addUser(memberUser);
        family.setDeleted(false);
        familyRepository.save(family);

        // 사용자 정보 변경
        userRepository.save(managerUser);
        userRepository.save(memberUser);

        // 생성된 가족 구성원 리스트
        List<FamilyMember> familyMemberList = family.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new CreateFamilyResponseDto(
                family.getFamilyId(),
                managerUser.getUserId(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddFamilyMemberResponseDto addFamilyMember(String memberUsername, AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        User memberUser = userRepository.findByUsernameAndIsDeletedFalse(memberUsername)
                .orElseThrow(() -> new FamilyMemberNotFoundException(new UserNotFoundException()));

        Long groupManagerId = addFamilyMemberRequestDto.getGroupManagerId();
        User managerUser = userRepository.findByUserIdAndIsDeletedFalse(groupManagerId)
                .orElseThrow(() -> new FamilyManagerNotFoundException(new UserNotFoundException()));

        if (!managerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException("가족 그룹 구성원 추가는 그룹장만이 할 수 있습니다.");
        }

        Long familyId = managerUser.getFamilyIdInfo();

        // TODO: 서버 에러로 변경. 그룹장에게만 노출되는 API를 타고 들어 왔으므로 null이면 안 됨. IR
        Family findFamily = familyRepository.findByFamilyIdAndIsDeletedFalse(familyId).orElseThrow(FamilyNotFoundException::new);

        if (findFamily.getFamilySize() >= 4) {
            throw new FamilyLimitExceededException();
        }

        if (userHasFamily.test(memberUser)) {
            throw new UserHasFamilyException();
        }

        // 가족 구성원 추가
        findFamily.addUser(memberUser);
        familyRepository.save(findFamily);

        // 사용자 정보 변경
        userRepository.save(memberUser);

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new AddFamilyMemberResponseDto(
                findFamily.getFamilyId(),
                managerUser.getUserId(),
                familyMemberList
        );
    }

    @Override
    public FindFamilyMembersResponseDto findFamilyMembers(Long familyId) {

        Family findFamily = familyRepository.findByFamilyIdAndIsDeletedFalse(familyId).orElseThrow(FamilyNotFoundException::new);

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                        .map(getFamilyMemberInfo)
                        .collect(Collectors.toList());

        return new FindFamilyMembersResponseDto(
                findFamily.getFamilySize(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteFamilyMemberResponseDto deleteFamilyMember(Long familyId, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {

        Family findFamily = familyRepository.findByFamilyIdAndIsDeletedFalse(familyId).orElseThrow(FamilyNotFoundException::new);

        User existUser = userRepository.findByUserIdAndIsDeletedFalse(deleteFamilyMemberRequestDto.getDeleteMemberId())
                .orElseThrow(() -> new FamilyMemberNotFoundException(new UserNotFoundException()));

        if (existUser.isDeleted()) {
            throw new FamilyMemberNotFoundException(new UserNotFoundException());
        }

        if (!userInFamily.test(existUser, familyId)) {
            throw new FamilyMemberDeleteFailException("사용자가 해당 가족 그룹에 속해 있지 않습니다.");
        }

        findFamily.deleteUser(existUser);
        familyRepository.save(findFamily);

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new DeleteFamilyMemberResponseDto(
                findFamily.getFamilyId(),
                findFamily.getFamilySize(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteFamilyResponseDto deleteFamily(Long familyId) {

        Family findFamily = familyRepository.findByFamilyIdAndIsDeletedFalse(familyId).orElseThrow(FamilyNotFoundException::new);

        if (findFamily.getFamilySize() > 1) {
            throw new FamilyDeleteFailException("가족 그룹 구성원이 2명 이상입니다.");
        }

        findFamily.delete();
        findFamily.getUsers().forEach(User::leaveFamily);
        familyRepository.save(findFamily);

        return new DeleteFamilyResponseDto(
                findFamily.getFamilyId(),
                findFamily.isDeleted()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeFamilyManagerResponseDto changeFamilyManager(Long familyId, ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto) {

        Family findFamily = familyRepository.findByFamilyIdAndIsDeletedFalse(familyId).orElseThrow(FamilyNotFoundException::new);

        User prevManagerUser = userRepository.findByUserIdAndIsDeletedFalse(changeFamilyManagerRequestDto.getPrevManagerId())
                .orElseThrow(UserNotFoundException::new);

        User nextManagerUser = userRepository.findByUserIdAndIsDeletedFalse(changeFamilyManagerRequestDto.getNextManagerId())
                .orElseThrow(UserNotFoundException::new);

        if (!userInFamily.test(prevManagerUser, familyId)) {
            throw new UserHasFamilyException("기존 그룹장이 해당 가족 그룹에 속해 있지 않습니다.");
        }

        if (!userInFamily.test(nextManagerUser, familyId)) {
            throw new UserHasFamilyException("그룹장으로 위임하려는 사용자가 해당 가족 그룹에 속해 있지 않습니다.");
        }

        // TODO: 가족 그룹장 권한 확인 굳이 여기서 해야 할까?
        if (!prevManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException("기존 그룹장이 그룹장 권한을 가지고 있지 않습니다.");
        }

        // TODO: 애초에 위에서 걸러지지 않을까?
        if (nextManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException(new UserAlreadyManagerException("그룹장으로 위임하려는 사용자가 이미 그룹장 권한을 가지고 있습니다."));
        }

        prevManagerUser.changeRole(User.Role.ROLE_USER);
        nextManagerUser.changeRole(User.Role.ROLE_MANAGER);
        userRepository.save(prevManagerUser);
        userRepository.save(nextManagerUser);

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new ChangeFamilyManagerResponseDto(
                findFamily.getFamilyId(),
                nextManagerUser.getUserId(),
                familyMemberList
        );
    }

    // 가족 그룹에 속한 사용자인지 확인
    private static final Predicate<User> userHasFamily =
            user -> user.getFamily() != null && user.getFamily().getFamilyId() != null;

    // 해당 가족 그룹에 속한 사용자인지 확인
    private static final BiPredicate<User, Long> userInFamily =
            (user, familyId) -> user.getFamily() != null && user.getFamily().getFamilyId().equals(familyId);

    // 가족 그룹 구성원 정보 변환
    private Function<User, FamilyMember> getFamilyMemberInfo =
            user -> new FamilyMember(user.getUserId(), user.getName(), user.getRole(), user.getImageUrlInfo());

}