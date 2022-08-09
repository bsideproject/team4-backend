package com.bside.sidefriends.family.service;

import com.bside.sidefriends.family.repository.FamilyRepository;
import com.bside.sidefriends.family.service.dto.CreateFamilyRequestDto;
import com.bside.sidefriends.users.domain.User;
import com.bside.sidefriends.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FamilyServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired FamilyRepository familyRepository;

    FamilyService familyService = new FamilyServiceImpl(userRepository, familyRepository);


    @BeforeEach
    void beforeEach() {
        User user = new User(
                "sirzzang",
                "sirzzang@naver.com",
                null,
                "sirzzang",
                User.Role.ROLE_USER,
                "google",
                "123123123",
                null,
                null,
                false
                );

        User manager = new User(
                "sir0123",
                "sir0123@naver.com",
                null,
                "sir0123",
                User.Role.ROLE_MANAGER,
                "google",
                "123",
                null,
                null,
                false
        );


        userRepository.save(user);
        userRepository.save(manager);
    }

    @Test
    void 가족_생성_테스트() {

        // given
        CreateFamilyRequestDto createFamilyRequestDto = new CreateFamilyRequestDto(1L);

        // when
        familyService.createFamily(createFamilyRequestDto);

        // then
        System.out.println(familyRepository.findByFamilyId(1L).get().getUsers());

    }

}