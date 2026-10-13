package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    private Integer id;

//    @Column(columnDefinition = "varchar(50) not null unique")
//    private String username;
//
//    @Column(columnDefinition = "varchar(255)")
//    private String password;
//
//    @Column(columnDefinition = "varchar(50) not null")
//    private String name;
//
//    @Column(columnDefinition = "varchar(50) unique")
//    private String email;

    @Column(columnDefinition = "varchar(50) not null")
    private String position;
    @Column(columnDefinition = "int not null")
    private Integer salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

}
