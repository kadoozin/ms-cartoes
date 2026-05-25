package com.kadoozin.mscartoes.service;

import com.kadoozin.mscartoes.database.model.ClienteCartao;
import com.kadoozin.mscartoes.database.repository.ClienteCartaoRepository;
import com.kadoozin.mscartoes.dto.request.ClienteCartaoRequest;
import com.kadoozin.mscartoes.dto.response.ClienteCartaoResponse;
import com.kadoozin.mscartoes.mapper.ClienteCartaoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteCartaoServiceTest {

    @Mock
    private ClienteCartaoRepository clienteCartaoRepository;

    @Mock
    private ClienteCartaoMapper clienteCartaoMapper;

    @InjectMocks
    private ClienteCartaoService clienteCartaoService;

    @Test
    void deveNormalizarCpfComMascaraAntesDeConsultarRepositorio() {
        ClienteCartaoRequest request = new ClienteCartaoRequest("529.982.247-25");
        when(clienteCartaoRepository.findByCpfNormalizado("52998224725")).thenReturn(List.of());

        List<ClienteCartaoResponse> response = clienteCartaoService.listarCartoesPorCpf(request);

        verify(clienteCartaoRepository).findByCpfNormalizado("52998224725");
        assertEquals(List.of(), response);
    }

    @Test
    void deveConsultarRepositorioComCpfSemMascaraQuandoCpfJaEstiverNormalizado() {
        ClienteCartao entity = new ClienteCartao();
        ClienteCartaoResponse expected = new ClienteCartaoResponse("Cartao Platinum", "VISA", new BigDecimal("5000.00"));
        ClienteCartaoRequest request = new ClienteCartaoRequest("52998224725");

        when(clienteCartaoRepository.findByCpfNormalizado("52998224725")).thenReturn(List.of(entity));
        when(clienteCartaoMapper.toResponse(entity)).thenReturn(expected);

        List<ClienteCartaoResponse> response = clienteCartaoService.listarCartoesPorCpf(request);

        verify(clienteCartaoRepository).findByCpfNormalizado("52998224725");
        assertEquals(1, response.size());
        assertEquals(expected, response.getFirst());
    }
}
