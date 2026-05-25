package com.kadoozin.mscartoes.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteCartaoRequest(
        @NotBlank(message = "cpf e obrigatorio")
        @CPF(message = "cpf invalido")
        String cpf
) {
    public String cpfNormalizado() {
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }
}
