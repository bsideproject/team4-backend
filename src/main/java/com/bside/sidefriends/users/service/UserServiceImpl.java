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

    /**
     * 회원가입
     * @param userCreateRequestDto 회원가입 요청 DTO
     * @return
     * @throws IllegalStateException
     */
    @Override
    @Transactional
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto) throws IllegalStateException{

        // TODO: 예외 처리 분리
        if (validateIfExistsByEmail(userCreateRequestDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        User userEntity = User.builder()
                .name(userCreateRequestDto.getName())
                .nickname(userCreateRequestDto.getNickname())
                .phoneNumber(userCreateRequestDto.getPhoneNumber())
                .email(userCreateRequestDto.getEmail())
                .role(userCreateRequestDto.isFamilyLeader() ?
                        User.Role.ROLE_MANAGER : User.Role.ROLE_USER)
                .provider(userCreateRequestDto.getProvider())
                .providerId(userCreateRequestDto.getProviderId())
                .build();

        userRepository.save(userEntity);

        return new UserCreateResponseDto(
                userEntity.getId(), userEntity.getCreatedAt(), userEntity.getUpdatedAt()
        );
    }

    /**
     * 이메일 주소로 이미 존재하는 회원인지 검사
     * @param userEmail 존재 여부를 검사할 회원의 이메일
     * @return 회원 존재 여부
     * IR
     */
    @Override
    public boolean validateIfExistsByEmail(String userEmail) {

        return userRepository.findByEmail(userEmail).isPresent();
    }
}
