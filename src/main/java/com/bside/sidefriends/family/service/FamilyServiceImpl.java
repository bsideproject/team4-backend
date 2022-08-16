package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.family.error.exception.FamilyAlreadyDeletedException;
import com.bside.sidefriends.family.error.exception.FamilyLimitExceededException;
import com.bside.sidefriends.family.error.exception.FamilyManagerRequiredException;
import com.bside.sidefriends.family.error.exception.FamilyNotFoundException;
import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.*;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

        User managerUser = userRepository.findByUserId(createFamilyRequestDto.getGroupManagerId())
                .orElseThrow(UserNotFoundException::new);

        if (managerUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        if (managerUser.getFamily() != null && managerUser.getFamily().getFamilyId() != null) {
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
                managerUser.getUserId() // TODO: 엔티티 안에서 연관관계?
        );
    }

    @Override
    public FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyId(Long familyId) {

        Family findFamily = familyRepository.findByFamilyId(familyId)
                .orElseThrow(FamilyNotFoundException::new);

        List<User> userList = findFamily.getActiveMemberList();

        List<FindFamilyMembersByFamilyIdResponseDto.FamilyMember> familyMemberList = userList.stream()
                .map(user -> new FindFamilyMembersByFamilyIdResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()
                ))
                .collect(Collectors.toList());

        return new FindFamilyMembersByFamilyIdResponseDto(familyMemberList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddFamilyMemberResponseDto addFamilyMember(Long familyId, AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        Family findFamily = familyRepository.findByFamilyId(familyId)
                .orElseThrow(FamilyNotFoundException::new);

        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        if (findFamily.getFamilySize() >= 4) {
            throw new FamilyLimitExceededException();
        }

        User newUser = userRepository.findByUserId(addFamilyMemberRequestDto.getAddMemberId())
                .orElseThrow(UserNotFoundException::new);

        if (newUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        if (newUser.getFamily() != null && newUser.getFamily().getFamilyId() != null) {
            throw new UserHasFamilyException();
        }

        findFamily.addUser(newUser);
        familyRepository.save(findFamily);

        List<AddFamilyMemberResponseDto.FamilyMember> familyMemberList = findFamily.getActiveMemberList().stream()
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

        Family findFamily = familyRepository.findByFamilyId(familyId)
                .orElseThrow(FamilyNotFoundException::new);

        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        User existUser = userRepository.findByUserId(deleteFamilyMemberRequestDto.getDeleteMemberId())
                .orElseThrow(UserNotFoundException::new);

        if (existUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        if (existUser.getFamily() == null) {
            throw new UserHasNoFamilyException();
        } else if (existUser.getFamily() != null && !Objects.equals(existUser.getFamily().getFamilyId(), familyId)) {
            throw new UserHasWrongFamilyException();
        }

        findFamily.deleteUser(existUser);
        familyRepository.save(findFamily);

        List<DeleteFamilyMemberResponseDto.FamilyMember> familyMemberList = findFamily.getActiveMemberList().stream()
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

        Family findFamily = familyRepository.findByFamilyId(familyId)
                .orElseThrow(FamilyNotFoundException::new);

        if (findFamily.isDeleted()) {
            throw new FamilyAlreadyDeletedException();
        }

        // TODO: 그룹장 권한 확인
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

        Family findFamily = familyRepository.findByFamilyId(familyId)
                .orElseThrow(FamilyNotFoundException::new);

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

        // 권한 변경 관련 예외 처리

        if (prevManagerUser.getFamily() == null) {
            throw new UserHasNoFamilyException();
        } else if (prevManagerUser.getFamily() != null && !prevManagerUser.getFamily().getFamilyId().equals(familyId)) {
            throw new UserHasWrongFamilyException();
        }

        if (!prevManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new FamilyManagerRequiredException();
        }

        if (nextManagerUser.getFamily() == null) {
            throw new UserHasNoFamilyException();
        } else if (nextManagerUser.getFamily() != null && !nextManagerUser.getFamily().getFamilyId().equals(familyId)) {
            throw new UserHasWrongFamilyException();
        }

        if (nextManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new UserAlreadyManagerException();
        }

        // TODO: 가족 그룹장 권한 확인
        prevManagerUser.changeRole(User.Role.ROLE_USER);
        nextManagerUser.changeRole(User.Role.ROLE_MANAGER);
        userRepository.save(prevManagerUser);
        userRepository.save(nextManagerUser);

        List<ChangeFamilyManagerResponseDto.FamilyMember> familyMemberList = findFamily.getActiveMemberList().stream()
                .map(user -> new ChangeFamilyManagerResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()
                ))
                .collect(Collectors.toList());

        return new ChangeFamilyManagerResponseDto(
                findFamily.getFamilyId(),
                nextManagerUser.getUserId(),
                familyMemberList
        );
    }
}
