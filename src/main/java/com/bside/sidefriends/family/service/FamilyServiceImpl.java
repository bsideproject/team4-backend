package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.domain.Family;
import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.family.service.dto.*;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;


    @Override
    public CreateFamilyReponseDto createFamily(CreateFamilyRequestDto createFamilyRequestDto) {

        User mangerUser = userRepository.findByUserId(createFamilyRequestDto.getGroupManagerId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));

        if (mangerUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        if (mangerUser.getFamily() != null && mangerUser.getFamily().getFamilyId() != null) {
            throw new IllegalStateException("이미 가족 그룹이 존재하는 사용자입니다.");
        }

        // TODO: 공유 펫 선택

        // 가족 생성
        Family family = new Family();
        mangerUser.changeRole(User.Role.ROLE_MANAGER); // 가족 그룹장 권한 부여
        family.addUser(mangerUser);
        family.setDeleted(false); // TODO: 가족 삭제 여부
        familyRepository.save(family);

        return new CreateFamilyReponseDto(
                family.getFamilyId(),
                mangerUser.getUserId() // TODO: 엔티티 안에서 연관관계?
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
}
