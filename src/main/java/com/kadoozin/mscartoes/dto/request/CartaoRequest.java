package com.kadoozin.mscartoes.dto.request;

import com.kadoozin.mscartoes.database.enums.BandeiraCartao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CartaoRequest(
        @NotBlank String nome,
        @NotNull BandeiraCartao bandeiraCartao,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal rendaMaxima,
        @NotNull BigDecimal limiteBasico
) {}
