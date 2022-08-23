package com.bside.sidefriends.family.domain;

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
    private List<User> users = new ArrayList<>();

    // 가족 그룹 삭제
    public void delete() {
        this.isDeleted = true;
    }

    public void addUser(User user) {
        users.add(user);
        user.setFamily(this);
    }

    public long getFamilySize() {
        return this.users.stream()
                .filter(user -> !user.isDeleted())
                .count();
    }

    public List<User> getMemberList() {
        return this.users.stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
    }

    public void deleteUser(User user) {
        users.remove(user);
        user.setFamily(null);
    }
}
