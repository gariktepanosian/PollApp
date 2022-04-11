package com.epam.pollWebApp.model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private int id;
    @NotBlank
    private String first_name;
    @NotBlank
    @Email(message = "Email isn't validate")
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String address;
    @NotBlank
    private String contact;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String poll_result;
}
