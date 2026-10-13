package com.example.project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private Integer user_id;

    @NotEmpty
    @Size(min = 4, max = 10)
    private String username;

    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String phoneNumber;
}
