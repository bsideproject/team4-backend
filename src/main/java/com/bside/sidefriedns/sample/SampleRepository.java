package com.bside.sidefriedns.sample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleMember, String> {

}
