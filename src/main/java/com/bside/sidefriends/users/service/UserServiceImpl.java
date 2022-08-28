package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.error.exception.*;
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

        Optional<User> findUser = userRepository.findByProviderAndProviderId(
                userCreateRequestDto.getProvider(), userCreateRequestDto.getProviderId());

        if (findUser.isPresent() && !findUser.get().isDeleted()) {
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
                findUser.getFamily() == null ? null : findUser.getFamily().getFamilyId(),
                findUser.getUserImage() == null ? null : findUser.getUserImage().getImageUrl()
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
            throw new UserInfoNotChangedException(
                    String.format("변경하려는 이름 %s가 기존 이름 %s와 동일합니다.", modifyUserRequestDto.getName(), findUser.getName()));
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

        User findUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        if (findUser.isDeleted()) {
            throw new UserAlreadyDeletedException();
        }

        if (findUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new UserDeleteFailException(new UserAlreadyManagerException());
        }

        findUser.delete();
        userRepository.save(findUser);

        return new DeleteUserResponseDto(
                findUser.getUserId(),
                findUser.isDeleted()
        );
    }

}
