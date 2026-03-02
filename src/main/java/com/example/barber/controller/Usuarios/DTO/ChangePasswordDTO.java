package com.example.barber.controller.Usuarios.DTO;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
}
