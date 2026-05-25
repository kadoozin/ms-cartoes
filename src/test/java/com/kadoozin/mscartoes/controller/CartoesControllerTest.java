package com.kadoozin.mscartoes.controller;

import com.kadoozin.mscartoes.dto.response.ClienteCartaoResponse;
import com.kadoozin.mscartoes.exception.ApiExceptionHandler;
import com.kadoozin.mscartoes.service.CartaoService;
import com.kadoozin.mscartoes.service.ClienteCartaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartoesController.class)
@Import(ApiExceptionHandler.class)
class CartoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartaoService cartaoService;

    @MockitoBean
    private ClienteCartaoService clienteCartaoService;

    @Test
    void deveAceitarCpfValidoSemMascara() throws Exception {
        List<ClienteCartaoResponse> response = List.of(
                new ClienteCartaoResponse("Cartao Essencial", "MASTERCARD", new BigDecimal("1500.00"))
        );
        when(clienteCartaoService.listarCartoesPorCpf(any())).thenReturn(response);

        mockMvc.perform(get("/cartoes/cliente")
                        .param("cpf", "52998224725"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Cartao Essencial"));
    }

    @Test
    void deveAceitarCpfValidoComMascara() throws Exception {
        when(clienteCartaoService.listarCartoesPorCpf(
                argThat(request -> "529.982.247-25".equals(request.cpf())))).thenReturn(List.of());

        mockMvc.perform(get("/cartoes/cliente")
                        .param("cpf", "529.982.247-25"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveRetornar400QuandoCpfInvalido() throws Exception {
        mockMvc.perform(get("/cartoes/cliente")
                        .param("cpf", "11111111111"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(clienteCartaoService);
    }

    @Test
    void deveRetornar200ComListaVaziaQuandoCpfValidoSemCartoes() throws Exception {
        when(clienteCartaoService.listarCartoesPorCpf(any())).thenReturn(List.of());

        mockMvc.perform(get("/cartoes/cliente")
                        .param("cpf", "168.995.350-09"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void devePreservarContratoDoPayloadNoEndpointCartoesPorCliente() throws Exception {
        List<ClienteCartaoResponse> response = List.of(
                new ClienteCartaoResponse("Cartao Platinum", "VISA", new BigDecimal("5000.00"))
        );
        when(clienteCartaoService.listarCartoesPorCpf(any())).thenReturn(response);

        mockMvc.perform(get("/cartoes/cliente")
                        .param("cpf", "52998224725"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Cartao Platinum"))
                .andExpect(jsonPath("$[0].bandeira").value("VISA"))
                .andExpect(jsonPath("$[0].limite").value(5000.00));
    }
}
