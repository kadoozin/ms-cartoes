package com.kadoozin.mscartoes.dto.request;

import java.math.BigDecimal;

public record DadosSolicitacaoEmissaoCartao(
        Long idCartao,
        String cpf,
        String endereco,
        BigDecimal limiteAprovado
) {
}
