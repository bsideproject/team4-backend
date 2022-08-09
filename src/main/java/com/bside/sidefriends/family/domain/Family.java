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
@Setter
public class Family {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long familyId;

    @NotNull
    private boolean isDeleted;

    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setFamily(this);
    }

    public long getFamilySize() {
        return this.users.stream()
                .filter(user -> !user.isDeleted())
                .count();
    }

    public List<User> getActiveMemberList() {
        return this.users.stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
    }

    public void deleteUser(User user) {
        users.remove(user);
        user.setFamily(null);
    }
}
