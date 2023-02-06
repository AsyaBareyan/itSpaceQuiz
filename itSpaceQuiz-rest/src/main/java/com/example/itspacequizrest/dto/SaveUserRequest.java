package com.example.itspacequizrest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequest {
    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "first name must not be less than two characters ")
    private String name;
    @NotNull(message = "surname cannot be null")
    @Size(min = 2, message = "first name must not be less than two characters ")
    private String surname;
    @NotNull(message = "email cannot be null")
    @Email
    private String email;
    @NotNull(message = "Password cannot be null")
    //    @Size(min = 8, max = 16, message = "password can be equal or grater than 8 characters and less than 16 characters")
    private String password;

}
