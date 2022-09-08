package com.bside.sidefriends.family.domain;

import com.bside.sidefriends.pet.domain.Pet;
import com.bside.sidefriends.users.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class Family {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long familyId;

    // TODO: 엔티티 세터 괜찮은가
    @NotNull @Setter
    private boolean isDeleted;

    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    private final List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    private final List<Pet> pets = new ArrayList<>();

    // 가족 그룹 삭제
    public void delete() {
        this.isDeleted = true;
    }

    // 가족 그룹 구성원 추가
    public void addUser(User user) {
        users.add(user);
        user.setFamily(this);
    }

    // 가족 그룹 구성원 인원 수
    public long getFamilySize() {
        return this.users.stream()
                .filter(user -> !user.isDeleted())
                .count();
    }

    // 가족 그룹 구성원 리스트
    public List<User> getMemberList() {
        return this.users.stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
    }

    // 가족 그룹 구성원 삭제
    public void deleteUser(User user) {
        users.remove(user);
        user.setFamily(null);
    }

    // 가족 공유펫 등록
    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setFamily(this);
    }
}
