package com.kadoozin.mscartoes.dto.request;

import com.kadoozin.mscartoes.database.enums.BandeiraCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartaoRequest(
        @NotBlank String nome,
        @NotNull BandeiraCartao bandeiraCartao
) {}
