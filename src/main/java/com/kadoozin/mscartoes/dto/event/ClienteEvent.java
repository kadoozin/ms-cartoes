package com.kadoozin.mscartoes.dto.event;

public record ClienteEvent(
        String action,
        String cpf,
        String nome,
        Integer idade
) {
}
