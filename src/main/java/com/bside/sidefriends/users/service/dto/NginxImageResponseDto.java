package com.bside.sidefriends.users.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class NginxImageResponseDto {
    private int code;
    private String message;

    @Nullable
    private FileInfo data;

    @Getter
    public static class FileInfo {

        @JsonProperty("file_id")
        private String fileId;

        @JsonProperty("file_name")
        private String fileName;
    }
}
