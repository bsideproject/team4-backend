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
import java.util.Optional;
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
    public FindFamilyMembersResponseDto findFamilyMembers(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (user.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        Long familyId = user.getFamilyId();
        // TODO: 가족 그룹 조회 불가능 이유 상세화
        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);
        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                        .map(getFamilyMemberInfo)
                        .collect(Collectors.toList());

        return new FindFamilyMembersResponseDto(
                findFamily.getFamilyId(),
                familyMemberList.size(),
                familyMemberList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteFamilyResponseDto deleteFamily(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (user.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        Long familyId = user.getFamilyId();
        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);
        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        // 그룹장 이외의 인원이 있는 경우 삭제 불가능
        if (findFamily.getFamilySize() > 1) {
            throw new FamilyDeleteFailException("가족 그룹에 그룹장 1명만 존재해야 삭제할 수 있습니다.");
        }

        findFamily.getUsers().forEach(User::leaveFamily);
        findFamily.delete();
        familyRepository.save(findFamily);

        return new DeleteFamilyResponseDto(
                findFamily.getFamilyId(),
                findFamily.isDeleted()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddFamilyMemberResponseDto addFamilyMember(String username, AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (user.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        Long familyId = user.getFamilyId();
        if (familyId == null) {
            /**
             * TODO: 가족 그룹 없을 때 생성 필요
             * - 해당 유저를 가족 그룹장으로 변경
             * - 해당 유저의 공유 펫 선택되었는지 확인
             * - 해당 유저에 대한 가족 그룹 생성
             * - 가족 그룹 id 변경
             */
        }

        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);
        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        // 가족 그룹 구성원 정원 초과
        if (findFamily.getFamilySize() >= 4) {
            throw new FamilyLimitExceededException();
        }

        // 새로 추가할 구성원
        User newUser = userRepository.findByUserId(addFamilyMemberRequestDto.getAddMemberId())
                .orElseThrow(() -> new FamilyMemberNotFoundException(new UserNotFoundException()));

        // 새로 추가할 구성원이 이미 삭제된 사용자인 경우
        if (newUser.isDeleted()) {
            throw new FamilyMemberNotFoundException(new UserAlreadyDeletedException());
        }

        // 새로 추가할 구성원이 이미 가족 그룹에 속해 있는 경우
        if (userHasFamily.test(newUser)) {
            throw new UserHasFamilyException();
        }

        findFamily.addUser(newUser);
        familyRepository.save(findFamily);

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new AddFamilyMemberResponseDto(
                findFamily.getFamilyId(),
                familyMemberList.size(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteFamilyMemberResponseDto deleteFamilyMember(String username, DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (user.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        Long familyId = user.getFamilyId();
        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);
        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

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

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new DeleteFamilyMemberResponseDto(
                findFamily.getFamilyId(),
                familyMemberList.size(),
                familyMemberList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeFamilyManagerResponseDto changeFamilyManager(String username, ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto) {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (user.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        Long familyId = user.getFamilyId();
        Family findFamily = familyRepository.findByFamilyId(familyId).orElseThrow(FamilyNotFoundException::new);
        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        User nextManagerUser = userRepository.findByUserId(changeFamilyManagerRequestDto.getNextManagerId())
                .orElseThrow(UserNotFoundException::new);
        if (nextManagerUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        if (!userInFamily.test(user, familyId)) {
            throw new UserHasFamilyException("기존 그룹장이 해당 가족 그룹에 속해 있지 않습니다.");
        }

        if (!userInFamily.test(nextManagerUser, familyId)) {
            throw new UserHasFamilyException("그룹장으로 위임하려는 사용자가 해당 가족 그룹에 속해 있지 않습니다.");
        }

        if (nextManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new UserAlreadyManagerException("그룹장으로 위임하려는 사용자가 이미 그룹장 권한을 가지고 있습니다.");
        }

        user.changeRole(User.Role.ROLE_USER);
        nextManagerUser.changeRole(User.Role.ROLE_MANAGER);
        userRepository.save(user);
        userRepository.save(nextManagerUser);

        List<FamilyMember> familyMemberList = findFamily.getMemberList().stream()
                .map(getFamilyMemberInfo)
                .collect(Collectors.toList());

        return new ChangeFamilyManagerResponseDto(
                findFamily.getFamilyId(),
                nextManagerUser.getUserId(),
                familyMemberList.size(),
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
            user -> new FamilyMember(user.getUserId(), user.getName(), user.getRole(), user.getImageUrl());

}