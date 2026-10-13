package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    private Integer id;

    @Column(columnDefinition = "varchar(255) not null unique")
    private String accountNumber;

    @Column(columnDefinition = "double")
    private Double balance;

    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

}
