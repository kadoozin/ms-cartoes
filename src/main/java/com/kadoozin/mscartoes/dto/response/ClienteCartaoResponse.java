package com.kadoozin.mscartoes.dto.response;

import java.math.BigDecimal;

public record ClienteCartaoResponse(
        String nome,
        String bandeira,
        BigDecimal limite
) {
}
