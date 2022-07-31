package com.bside.sidefriedns.sample;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "SAMPLE_MEMBER")
public class SampleMember {

    @Id
    @Column(name = "MEMBER_NO")
    private String memberNo;

    @Column(name = "MEMBER_NAME")
    private String memberName;

}
