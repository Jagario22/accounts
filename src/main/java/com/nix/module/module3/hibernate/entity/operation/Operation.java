package com.nix.module.module3.hibernate.entity.operation;


import com.nix.module.module3.hibernate.entity.AbstractEntity;
import com.nix.module.module3.hibernate.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("CASE WHEN amount > 0 THEN 'income' WHEN amount > 0 ELSE 'expense' END")
@Table(name = "operations")
public class Operation extends AbstractEntity {

    @Column(nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private Instant timestamp;

    public Operation() {
    }
}
