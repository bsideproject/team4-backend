package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.family.error.exception.*;
import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.*;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiPredicate;
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
    public CreateFamilyReponseDto createFamily(CreateFamilyRequestDto createFamilyRequestDto) {

        Long groupManagerId = createFamilyRequestDto.getGroupManagerId();

        User managerUser = userRepository.findByUserId(groupManagerId)
                .orElseThrow(() -> new FamilyManagerNotFoundException(new UserNotFoundException()));

        if (managerUser.isDeleted()) {
            throw new FamilyManagerNotFoundException(new UserAlreadyDeletedException());
        }

        if (userHasFamily.test(managerUser)) {
            throw new UserHasFamilyException();
        }

        // TODO: 공유 펫 선택

        // 가족 생성
        Family family = new Family();
        managerUser.changeRole(User.Role.ROLE_MANAGER); // 가족 그룹장 권한 부여
        family.addUser(managerUser);
        family.setDeleted(false);
        familyRepository.save(family);

        return new CreateFamilyReponseDto(
                family.getFamilyId(),
                managerUser.getUserId()
        );
    }

    @Override
    public FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyId(Long familyId) {

        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);

        List<FindFamilyMembersByFamilyIdResponseDto.FamilyMember> familyMemberList =
                findFamily.getMemberList().stream()
                .map(user -> new FindFamilyMembersByFamilyIdResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()))
                .collect(Collectors.toList());

        return new FindFamilyMembersByFamilyIdResponseDto(familyMemberList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddFamilyMemberResponseDto addFamilyMember(Long familyId, AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);

        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        if (findFamily.getFamilySize() >= 4) {
            throw new FamilyLimitExceededException();
        }

        User newUser = userRepository.findByUserId(addFamilyMemberRequestDto.getAddMemberId())
                .orElseThrow(() -> new FamilyMemberNotFoundException(new UserNotFoundException()));

        if (newUser.isDeleted()) {
            throw new FamilyMemberNotFoundException(new UserAlreadyDeletedException());
        }

        if (userHasFamily.test(newUser)) {
            throw new UserHasFamilyException();
        }

        findFamily.addUser(newUser);
        familyRepository.save(findFamily);

        List<AddFamilyMemberResponseDto.FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(user -> new AddFamilyMemberResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()))
                .collect(Collectors.toList());

        return new AddFamilyMemberResponseDto(
                findFamily.getFamilyId(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteFamilyMemberResponseDto deleteFamilyMember(Long familyId, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {

        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);

        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        // TODO: 그룹장 권한 확인

        User existUser = userRepository.findByUserId(deleteFamilyMemberRequestDto.getDeleteMemberId())
                .orElseThrow(() -> new FamilyMemberNotFoundException(new UserNotFoundException()));

        if (existUser.isDeleted()) {
            throw new FamilyMemberNotFoundException(new UserAlreadyDeletedException());
        }

        if (!userInFamily.test(existUser, familyId)) {
            throw new FamilyMemberDeleteFailException("사용자가 해당 가족 그룹에 속해 있지 않습니다.");
        }

        findFamily.deleteUser(existUser);
        familyRepository.save(findFamily);

        List<DeleteFamilyMemberResponseDto.FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(user -> new DeleteFamilyMemberResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()))
                .collect(Collectors.toList());

        return new DeleteFamilyMemberResponseDto(
                findFamily.getFamilyId(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteFamilyResponseDto deleteFamily(Long familyId) {

        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);

        // TODO: 그룹장 권한 확인
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

        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);

        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        User prevManagerUser = userRepository.findByUserId(changeFamilyManagerRequestDto.getPrevManagerId())
                .orElseThrow(UserNotFoundException::new);

        if (prevManagerUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        User nextManagerUser = userRepository.findByUserId(changeFamilyManagerRequestDto.getNextManagerId())
                .orElseThrow(UserNotFoundException::new);

        if (nextManagerUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

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

        List<ChangeFamilyManagerResponseDto.FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(user -> new ChangeFamilyManagerResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()))
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

}