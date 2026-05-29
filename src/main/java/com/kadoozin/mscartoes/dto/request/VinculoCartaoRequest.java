package com.kadoozin.mscartoes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record VinculoCartaoRequest(
        @NotBlank(message = "cpf e obrigatorio")
        @CPF(message = "cpf invalido")
        String cpf,

        @NotNull(message = "limite aprovado e obrigatorio")
        BigDecimal limiteAprovado
) {
    public String cpfNormalizado() {
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }
}
