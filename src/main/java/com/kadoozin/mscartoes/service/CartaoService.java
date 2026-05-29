package com.kadoozin.mscartoes.service;

import com.kadoozin.mscartoes.database.enums.BandeiraCartao;
import com.kadoozin.mscartoes.database.model.Cartao;
import com.kadoozin.mscartoes.database.repository.CartaoRepository;
import com.kadoozin.mscartoes.dto.request.CartaoRequest;
import com.kadoozin.mscartoes.dto.response.CartaoResponse;
import com.kadoozin.mscartoes.exception.RegraDeNegocioException;
import com.kadoozin.mscartoes.exception.ResourceNotFoundException;
import com.kadoozin.mscartoes.mapper.CartaoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Validated
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final CartaoMapper cartaoMapper;
    private static final Map<BandeiraCartao, BigDecimal> RENDA_MINIMA_POR_BANDEIRA = Map.of(
            BandeiraCartao.ELO, BigDecimal.valueOf(1000),
            BandeiraCartao.VISA, BigDecimal.valueOf(2500),
            BandeiraCartao.MASTERCARD, BigDecimal.valueOf(5000),
            BandeiraCartao.AMERICAN_EXPRESS, BigDecimal.valueOf(40000),
            BandeiraCartao.HIPERCARD, BigDecimal.valueOf(1000)
    );

    private static final Map<BandeiraCartao, BigDecimal> LIMITE_BASICO_POR_BANDEIRA = Map.of(
            BandeiraCartao.ELO, BigDecimal.valueOf(500),
            BandeiraCartao.VISA, BigDecimal.valueOf(2500),
            BandeiraCartao.MASTERCARD, BigDecimal.valueOf(5000),
            BandeiraCartao.AMERICAN_EXPRESS, BigDecimal.valueOf(40000),
            BandeiraCartao.HIPERCARD, BigDecimal.valueOf(500)
    );


    @Transactional
    public CartaoResponse save(@Valid @NotNull CartaoRequest request) {
        var bandeira = request.bandeiraCartao();
        var rendaMinimaBandeira = getRendaMinimaByBandeira(bandeira);
        var limiteBasicoBandeira = getLimiteBasicoByBandeira(bandeira);

        Cartao cartao = cartaoMapper.toEntity(request);
        cartao.setRendaMinima(rendaMinimaBandeira);
        cartao.setLimiteBasico(limiteBasicoBandeira);
        return cartaoMapper.toResponse(cartaoRepository.save(cartao));
    }

    @Transactional(readOnly = true)
    public List<CartaoResponse> getCartoesElegiveisPorRenda(@Positive long rendaCliente) {
        var rendaClienteBigDecimal = BigDecimal.valueOf(rendaCliente);
        return cartaoRepository.findByRendaMinimaLessThanEqual(rendaClienteBigDecimal)
                .stream()
                .filter(cartao -> rendaClienteBigDecimal.compareTo(getRendaMinimaByBandeira(cartao.getBandeiraCartao())) >= 0)
                .map(cartaoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CartaoResponse> findAll() {
        return cartaoRepository.findAll()
                .stream()
                .map(cartaoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CartaoResponse getById(@Positive @NotNull Integer cartaoId) {
        return cartaoRepository.findById(cartaoId)
                .map(cartaoMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cartao nao encontrado para id: " + cartaoId));
    }

    private BigDecimal getRendaMinimaByBandeira(BandeiraCartao bandeiraCartao) {
        var rendaMinima = RENDA_MINIMA_POR_BANDEIRA.get(bandeiraCartao);
        if (rendaMinima == null) {
            throw new RegraDeNegocioException("Bandeira de cartao sem regra de renda minima: " + bandeiraCartao);
        }
        return rendaMinima;
    }

    private BigDecimal getLimiteBasicoByBandeira(BandeiraCartao bandeiraCartao) {
        var limiteBasico = LIMITE_BASICO_POR_BANDEIRA.get(bandeiraCartao);
        if (limiteBasico == null) {
            throw new RegraDeNegocioException("Bandeira de cartao sem regra de limite basico: " + bandeiraCartao);
        }
        return limiteBasico;
    }

}
