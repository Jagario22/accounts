package com.nix.module.module3.jdbs.entity.operation;


import com.nix.module.module3.jdbs.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Operation {

    private Long amount;

    private Long accountId;

    private Instant timestamp;

    public Operation() {
    }
}
