package com.example.itspacequizcommon.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user",schema = "public")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name should be not empty")
    private String name;
    @NotEmpty(message = "surname should be not empty")
    private String surname;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty(message = "password should be not empty")
    private String password;
    @Enumerated(EnumType.STRING)

    @Column(name = "user_type")
    private UserType userType;

}
