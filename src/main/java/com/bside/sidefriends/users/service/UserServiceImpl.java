package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreateUserResponseDto createUser(CreateUserRequestDto userCreateRequestDto) throws IllegalStateException{

        // TODO: provider, providerId 이미 있으면?

        User userEntity = User.builder()
                .name(userCreateRequestDto.getName())
                .email(userCreateRequestDto.getEmail())
                .role(User.Role.ROLE_USER) // 회원 가입 시 회원 권한 기본값 ROLE_USER
                .provider(userCreateRequestDto.getProvider())
                .providerId(userCreateRequestDto.getProviderId())
                .isDeleted(false) // 회원 가입 시 회원 삭제 여부 false
                .build();

        User user = userRepository.save(userEntity);

        return new CreateUserResponseDto(
                user.getId(),
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
                findUser.getId(),
                findUser.getName(),
                findUser.getNickname(),
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

        findUser.modify(modifyUserRequestDto);
        userRepository.save(findUser);

        return new ModifyUserResponseDto(
                findUser.getId(),
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

        findUser.delete();
        userRepository.save(findUser);

        return new DeleteUserResponseDto(
                findUser.getId(),
                findUser.isDeleted()
        );
    }

}
