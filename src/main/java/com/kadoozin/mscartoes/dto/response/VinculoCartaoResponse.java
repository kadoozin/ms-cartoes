package com.kadoozin.mscartoes.dto.response;

import java.math.BigDecimal;

public record VinculoCartaoResponse(
        String nomeCliente,
        String cpf,
        String endereco,
        String nomeCartao,
        BigDecimal limiteAprovado
) {}
