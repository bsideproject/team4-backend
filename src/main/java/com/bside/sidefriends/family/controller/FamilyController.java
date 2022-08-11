package com.bside.sidefriends.family.controller;

import com.bside.sidefriends.common.annotation.SideFriendsController;
import com.bside.sidefriends.family.service.FamilyService;
import com.bside.sidefriends.family.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SideFriendsController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/family")
    ResponseEntity<CreateFamilyReponseDto> createFamily(@Valid @RequestBody CreateFamilyRequestDto createFamilyRequestDto) {

        CreateFamilyReponseDto createFamilyReponseDto = familyService.createFamily(createFamilyRequestDto);

        return ResponseEntity.ok().body(createFamilyReponseDto);
    }

    @GetMapping("family/{familyId}")
    ResponseEntity<FindFamilyMembersByFamilyIdResponseDto> findFamily(@PathVariable("familyId") Long familyId) {

        FindFamilyMembersByFamilyIdResponseDto findFamilyMembersByFamilyIdResponseDto
                = familyService.findFamilyMembersByFamilyId(familyId);

        return ResponseEntity.ok().body(findFamilyMembersByFamilyIdResponseDto);
    }

    @DeleteMapping("family/{familyId}")
    ResponseEntity<DeleteFamilyResponseDto> deleteFamily(@PathVariable("familyId") Long familyId) {

        DeleteFamilyResponseDto deleteFamilyResponseDto = familyService.deleteFamily(familyId);

        return ResponseEntity.ok().body(deleteFamilyResponseDto);

    }

    @PostMapping("family/{familyId}/members")
    ResponseEntity<AddFamilyMemberResponseDto> addFamilyMember(@PathVariable("familyId") Long familyId,
                                                               @Valid @RequestBody AddFamilyMemberRequestDto addFamilyMemberRequestDto) {

        AddFamilyMemberResponseDto addFamilyMemberResponseDto
                = familyService.addFamilyMember(familyId, addFamilyMemberRequestDto);

        return ResponseEntity.ok().body(addFamilyMemberResponseDto);
    }

    @DeleteMapping("family/{familyId}/members")
    ResponseEntity<DeleteFamilyMemberResponseDto> deleteFamilyMember(@PathVariable("familyId") Long familyId,
                                                                     @Valid @RequestBody DeleteFamilyMemberRequestDto deleteFamilyMemberRequestDto) {
        DeleteFamilyMemberResponseDto deleteFamilyMemberResponseDto
                = familyService.deleteFamilyMember(familyId, deleteFamilyMemberRequestDto);

        return ResponseEntity.ok().body(deleteFamilyMemberResponseDto);
    }

    @PutMapping("family/{familyId}/manager")
    ResponseEntity<ChangeFamilyManagerResponseDto> changeFamilyManager(@PathVariable("familyId") Long familyId,
                                                                       @Valid @RequestBody ChangeFamilyManagerRequestDto changeFamilyManagerRequestDto) {
        ChangeFamilyManagerResponseDto changeFamilyManagerResponseDto
                = familyService.changeFamilyManager(familyId, changeFamilyManagerRequestDto);

        return ResponseEntity.ok().body(changeFamilyManagerResponseDto);
    }
}
