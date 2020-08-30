package com.nix.module.module3.jdbs.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends AbstractEntity {
    private String fullName;
    private String email;
    private String phone;
}
