package com.kadoozin.mscartoes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteCartaoRequest(
        @NotBlank(message = "cpf e obrigatorio")
        @Pattern(regexp = "\\d{11}", message = "cpf deve conter 11 digitos numericos")
        String cpf
) {
}
