package com.bside.sidefriends.users.service;

import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import com.bside.sidefriends.users.service.dto.CreateUserRequestDto;
import com.bside.sidefriends.users.service.dto.CreateUserResponseDto;
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

        return new CreateUserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
