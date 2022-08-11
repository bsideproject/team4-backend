package com.bside.sidefriends.users.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class UserImage {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userImageId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public UserImage(Long userImageId, User user, LocalDateTime createdAt, LocalDateTime updatedAt, String imageUrl) {
        this.userImageId = userImageId;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
    }
}
