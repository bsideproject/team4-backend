package com.bside.sidefriends.diary.domain;

import com.bside.sidefriends.pet.domain.Pet;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    private Long writerId;

    @Column(length = 140) // TODO: 140자 영문 한글 기준 확인 필요. IR.
    private String contents;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
