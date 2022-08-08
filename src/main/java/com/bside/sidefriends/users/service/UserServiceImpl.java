package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.dto.UserCreateRequestDto;
import com.bside.sidefriends.users.service.dto.UserCreateResponseDto;
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
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto) throws IllegalStateException{

        // TODO: 예외 처리 분리
        if (checkIfExistsByEmail(userCreateRequestDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        User userEntity = User.builder()
                .name(userCreateRequestDto.getName())
                .nickname(userCreateRequestDto.getNickname())
                .email(userCreateRequestDto.getEmail())
                .role(userCreateRequestDto.isFamilyLeader() ?
                        User.Role.ROLE_MANAGER : User.Role.ROLE_USER)
                .provider(userCreateRequestDto.getProvider())
                .providerId(userCreateRequestDto.getProviderId())
                .build();

        User user = userRepository.save(userEntity);

        return new UserCreateResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Override
    public boolean checkIfExistsByEmail(String userEmail) {

        return userRepository.existsByEmail(userEmail);
    }
}
