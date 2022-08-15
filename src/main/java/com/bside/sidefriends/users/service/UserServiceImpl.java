package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.UserAlreadyDeletedException;
import com.bside.sidefriends.users.error.exception.UserAlreadyExistsException;
import com.bside.sidefriends.users.error.exception.UserInfoNotChangedException;
import com.bside.sidefriends.users.error.exception.UserNotFoundException;
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

        if (userRepository.findByProviderAndProviderId(
                userCreateRequestDto.getProvider(), userCreateRequestDto.getProviderId())
                .isPresent()) {
            throw new UserAlreadyExistsException();
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

        userRepository.save(userEntity);

        return new CreateUserResponseDto(
                userEntity.getUserId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.isDeleted(),
                userEntity.getRole()
        );
    }

    @Override
    public FindUserByUserIdResponseDto findUserByUserId(Long userId) {

        User findUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        if (findUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        return new FindUserByUserIdResponseDto(
                findUser.getUserId(),
                findUser.getName(),
                findUser.getEmail(),
                findUser.getMainPetId(),
                findUser.getRole(),
                findUser.isDeleted(),
                findUser.getFamily() == null ? null : findUser.getFamily().getFamilyId()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyUserResponseDto modifyUser(Long userId, ModifyUserRequestDto modifyUserRequestDto) {

        User findUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        if (findUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        if (modifyUserRequestDto.getName().equals(findUser.getName())) {
//            throw new UserInfoNotChangedException(String.format("변경하려는 이름 %s가 기존과 동일합니다", modifyUserRequestDto.getName()));
            throw new UserInfoNotChangedException();
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
