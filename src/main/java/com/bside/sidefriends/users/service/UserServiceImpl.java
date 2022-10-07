package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.exception.*;
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
    @Transactional(rollbackFor = Exception.class)
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto){

        User userEntity = User.builder()
                .name(createUserRequestDto.getName())
                .email(createUserRequestDto.getEmail())
                .role(User.Role.ROLE_USER) // 회원 가입 시 회원 권한 기본값 ROLE_USER
                .provider(createUserRequestDto.getProvider())
                .providerId(createUserRequestDto.getProviderId())
                .username(createUserRequestDto.getProvider() + "_" + createUserRequestDto.getProviderId())
                .isDeleted(false) // 회원 가입 시 회원 삭제 여부 false
                .build();

        userRepository.save(userEntity);

        return new CreateUserResponseDto(
                userEntity.getUserId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getMainPetId(),
                userEntity.getImageUrlInfo()
        );
    }

    @Override
    public FindUserByUserIdResponseDto findUserByUserId(String username) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(UserNotFoundException::new);

        return new FindUserByUserIdResponseDto(
                findUser.getUserId(),
                findUser.getName(),
                findUser.getEmail(),
                findUser.getMainPetId(),
                findUser.getRole(),
                findUser.getFamilyIdInfo(),
                findUser.getImageUrlInfo()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ModifyUserResponseDto modifyUser(String username, ModifyUserRequestDto modifyUserRequestDto) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(UserNotFoundException::new);

        if (modifyUserRequestDto.getName().equals(findUser.getName())) {
            throw new UserInfoNotChangedException(
                    String.format("변경하려는 이름 %s가 기존 이름 %s와 동일합니다.", modifyUserRequestDto.getName(), findUser.getName()));
        }

        findUser.modify(modifyUserRequestDto);
        userRepository.save(findUser);

        return new ModifyUserResponseDto(
                findUser.getUserId(),
                findUser.getName(),
                findUser.getMainPetId(),
                findUser.getRole(),
                findUser.getEmail(),
                findUser.getImageUrlInfo()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeleteUserResponseDto deleteUser(String username) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(UserNotFoundException::new);

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LeaveFamilyResponseDto leaveFamily(String username) {

        User findUser = userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(UserNotFoundException::new);

        if (findUser.getFamilyIdInfo() == null) {
            throw new UserHasNoFamilyException();
        }

        if (findUser.getRole().equals(User.Role.ROLE_MANAGER)) {
            throw new UserAlreadyManagerException();
        }

        findUser.leaveFamily();
        userRepository.save(findUser);

        return new LeaveFamilyResponseDto(
                findUser.getUserId(),
                findUser.getName(),
                findUser.getEmail(),
                findUser.getRole(),
                findUser.getMainPetId(),
                findUser.getImageUrlInfo(),
                findUser.getFamilyIdInfo()
        );
    }
}
