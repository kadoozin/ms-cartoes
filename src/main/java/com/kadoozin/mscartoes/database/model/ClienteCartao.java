package com.kadoozin.mscartoes.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    @Column(nullable = false)
    private BigDecimal limite;
}
