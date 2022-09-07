package com.bside.sidefriends.users.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
@AllArgsConstructor
public class GetUserImageResponseDto {

    @URL
    String userImageUrl;
}
