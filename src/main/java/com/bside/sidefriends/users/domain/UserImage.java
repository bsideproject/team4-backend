package com.bside.sidefriends.users.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Setter
    private String imageUrl;

    /**
     * TODO: 이미지 id 구현 방안
     * - 이미지 id 반환
     * - 이미지 경로 + 이미지 id로 받아 오기(로컬 서버)
     * - 이미지 id로 이미지 받아 오기(오브젝트 스토리지)
     * - 이미지 경로 자체를 저장하기(오브젝트 스토리지)
     * 이미지 id: 이미지 서버 내에 이미지 오브젝트 스토리지 저장 경로가 노출되지 않도록 하기 위함
     */
//    @Column(nullable = false)
//    @Setter
//    private String imageId;

    @Column(nullable = false)
    @Setter
    private String imageName; // 클라이언트 파일 시스템에 저장된 이미지 이름

    @Builder
    public UserImage(Long userImageId, User user, LocalDateTime createdAt, LocalDateTime updatedAt,
                     String imageUrl, String imageName) {
        this.userImageId = userImageId;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }
}
