package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateUserResponseDto createUser(CreateUserRequestDto userCreateRequestDto) throws IllegalStateException{

        if (userRepository.findByProviderAndProviderId(
                userCreateRequestDto.getProvider(), userCreateRequestDto.getProviderId())
                .isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        }

        User userEntity = User.builder()
                .name(userCreateRequestDto.getName())
                .email(userCreateRequestDto.getEmail())
                .role(User.Role.ROLE_USER) // 회원 가입 시 회원 권한 기본값 ROLE_USER
                .provider(userCreateRequestDto.getProvider())
                .providerId(userCreateRequestDto.getProviderId())
                .username(userCreateRequestDto.getProvider() + "_" + userCreateRequestDto.getProviderId())
                .isDeleted(false) // 회원 가입 시 회원 삭제 여부 false
                .build();

        User user = userRepository.save(userEntity);

        return new CreateUserResponseDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.isDeleted()
        );
    }

    @Override
    public FindUserByUserIdResponseDto findUserByUserId(Long userId) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (findUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        return new FindUserByUserIdResponseDto(
                findUser.getUserId(),
                findUser.getName(),
                findUser.getEmail(),
                findUser.getMainPetId(),
                findUser.getRole()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyUserResponseDto modifyUser(Long userId, ModifyUserRequestDto modifyUserRequestDto) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (findUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        if (modifyUserRequestDto.getName().equals(findUser.getName())) {
            throw new IllegalStateException("변경하려는 이름이 동일합니다.");
        }

        findUser.modify(modifyUserRequestDto);
        userRepository.save(findUser);

        return new ModifyUserResponseDto(
                findUser.getUserId(),
                findUser.getName(),
                findUser.getEmail()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteUserResponseDto deleteUser(Long userId) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        if (findUser.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }

        // 그룹장인 경우 탈퇴 불가
        if (findUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new IllegalStateException("가족 그룹장은 탈퇴할 수 없습니다.");
        }



        findUser.delete();
        userRepository.save(findUser);

        return new DeleteUserResponseDto(
                findUser.getUserId(),
                findUser.isDeleted()
        );
    }

}
