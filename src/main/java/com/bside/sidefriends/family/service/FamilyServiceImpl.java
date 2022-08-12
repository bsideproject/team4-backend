package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.users.domain.User;
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
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));

        if (managerUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        if (managerUser.getFamily() != null && managerUser.getFamily().getFamilyId() != null) {
            throw new IllegalStateException("이미 가족 그룹이 존재하는 사용자입니다.");
        }

        // TODO: 공유 펫 선택

        // 가족 생성
        Family family = new Family();
        managerUser.changeRole(User.Role.ROLE_MANAGER); // 가족 그룹장 권한 부여
        family.addUser(managerUser);
        family.setDeleted(false); // TODO: 가족 삭제 여부
        familyRepository.save(family);

        return new CreateFamilyReponseDto(
                family.getFamilyId(),
                managerUser.getUserId() // TODO: 엔티티 안에서 연관관계?
        );
    }

    @Override
    public FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyId(Long familyId) {
        // TODO: Dto 안에 리스트?
        List<User> userList = userRepository.findAllByFamilyFamilyId(familyId);

        List<FindFamilyMembersByFamilyIdResponseDto.FamilyMember> familyMemberList = userList.stream()
                .filter(user -> !user.isDeleted())
                .map(user -> new FindFamilyMembersByFamilyIdResponseDto.FamilyMember(
                        user.getUserId(), user.getName(), user.getRole()))
                .collect(Collectors.toList());

        return new FindFamilyMembersByFamilyIdResponseDto(familyMemberList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddFamilyMemberResponseDto addFamilyMember(Long familyId, AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        Family findFamily = familyRepository.findByFamilyId(familyId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 가족 그룹입니다."));

        if (findFamily.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 가족 그룹입니다.");
        }

        if (findFamily.getFamilySize() >= 4) {
            throw new IllegalStateException("가족 그룹 정원을 초과했습니다.");
        }

        User newUser = userRepository.findByUserId(addFamilyMemberRequestDto.getAddMemberId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));

        if (newUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        if (newUser.getFamily() != null && newUser.getFamily().getFamilyId() != null) {
            throw new IllegalStateException("사용자가 이미 속해 있는 가족 그룹이 있습니다.");
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
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 가족 그룹입니다."));

        if (findFamily.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 가족 그룹입니다.");
        }

        User existUser = userRepository.findByUserId(deleteFamilyMemberRequestDto.getDeleteMemberId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));

        if (existUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        if (existUser.getFamily() == null) {
            throw new IllegalStateException("사용자가 아무 가족 그룹에 속해 있지 않습니다.");
        } else if (existUser.getFamily() != null && !Objects.equals(existUser.getFamily().getFamilyId(), familyId)) {
            throw new IllegalStateException("사용자가 해당 가족 그룹에 속해 있지 않습니다.");
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
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 가족 그룹입니다."));

        if (findFamily.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 가족 그룹입니다.");
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
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 가족 그룹입니다."));

        if (findFamily.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 가족 그룹입니다.");
        }

        User prevManagerUser = userRepository.findByUserId(changeFamilyManagerRequestDto.getPrevManagerId())
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (prevManagerUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        User nextManagerUser = userRepository.findByUserId(changeFamilyManagerRequestDto.getNextManagerId())
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (nextManagerUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        // 권한 변경 관련 예외 처리

        if (prevManagerUser.getFamily() == null) {
            throw new IllegalStateException("가족 그룹에 속해 있지 않은 사용자입니다.");
        }

        if (prevManagerUser.getFamily() != null && !prevManagerUser.getFamily().getFamilyId().equals(familyId)) {
            throw new IllegalStateException("사용자가 변경 대상 가족 그룹의 구성원이 아닙니다.");
        }

        if (!prevManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new IllegalStateException("사용자가 가족 그룹의 그룹장이 아닙니다.");
        }

        if (nextManagerUser.getFamily() == null) {
            throw new IllegalStateException("가족 그룹에 속해 있지 않은 사용자입니다.");
        }

        if (nextManagerUser.getFamily() != null && !nextManagerUser.getFamily().getFamilyId().equals(familyId)) {
            throw new IllegalStateException("사용자가 변경 대상 가족 그룹의 구성원이 아닙니다.");
        }

        if (nextManagerUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new IllegalStateException("사용자가 이미 가족 그룹의 그룹장입니다.");
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