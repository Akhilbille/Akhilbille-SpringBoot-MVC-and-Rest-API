package com.codewithmosh.store.dtos;

import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
}
