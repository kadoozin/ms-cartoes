package com.kadoozin.mscartoes.database.model;

import com.kadoozin.mscartoes.database.enums.BandeiraCartao;
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
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeiraCartao;

    @Column(nullable = false)
    private BigDecimal rendaMinima;

    @Column
    private BigDecimal rendaMaxima;

    @Column(nullable = false)
    private BigDecimal limiteBasico;
}
