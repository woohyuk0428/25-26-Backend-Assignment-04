package com.gdg.convenience.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String role;
}
