package com.nix.module.module3.jdbs.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account extends AbstractEntity {
    private Long userId;
    private Long balance;

    public Account() {
    }
}
