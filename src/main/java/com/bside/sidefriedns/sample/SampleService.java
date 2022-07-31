package com.bside.sidefriedns.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public List<SampleMember> getMembers() {
        return sampleRepository.findAll();
    }
}
