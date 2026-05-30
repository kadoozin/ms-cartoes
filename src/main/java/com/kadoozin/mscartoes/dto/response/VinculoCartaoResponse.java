package com.kadoozin.mscartoes.dto.response;

public record VinculoCartaoResponse(
        String nomeCliente,
        String cpf,
        String endereco,
        String nomeCartao,
        String bandeira
) {}
