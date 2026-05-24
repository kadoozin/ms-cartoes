package com.kadoozin.mscartoes.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kadoozin.mscartoes.database.enums.BandeiraCartao;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartaoResponse(
        Integer cartaoId,
        String nome,
        BandeiraCartao bandeiraCartao,
        BigDecimal rendaMinima,
        BigDecimal rendaMaxima,
        BigDecimal limiteBasico
) {
}
