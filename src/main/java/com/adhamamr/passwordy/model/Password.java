package com.adhamamr.passwordy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passwords")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password_value")
    private String value;

    public Password() {}

    public Password(String value) {
        this.value = value;
    }

}
