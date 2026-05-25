package com.kadoozin.mscartoes.controller;

import com.kadoozin.mscartoes.dto.request.CartaoRequest;
import com.kadoozin.mscartoes.dto.request.ClienteCartaoRequest;
import com.kadoozin.mscartoes.dto.response.CartaoResponse;
import com.kadoozin.mscartoes.dto.response.ClienteCartaoResponse;
import com.kadoozin.mscartoes.service.CartaoService;
import com.kadoozin.mscartoes.service.ClienteCartaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
@Slf4j
public class CartoesController {
    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;


    @PostMapping
    public ResponseEntity<CartaoResponse> save(@Valid @RequestBody CartaoRequest cartaoRequest) {
        log.info("Solicitacao recebida para cadastro de cartao: {}", cartaoRequest.nome());
        CartaoResponse response = cartaoService.save(cartaoRequest);
        URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.cartaoId())
                .toUri();
        log.info("Cartao cadastrado com sucesso: {}", response.nome());
        return ResponseEntity.created(headerLocation).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoResponse> getById(@PathVariable("id") Integer id) {
        log.info("Solicitacao recebida para consulta de cartao por id: {}", id);
        CartaoResponse cartao = cartaoService.getById(id);
        return ResponseEntity.ok(cartao);
    }

    @GetMapping("/elegiveis")
    public ResponseEntity<List<CartaoResponse>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        log.info("Solicitacao recebida para consulta de cartoes com renda ate: {}", renda);
        List<CartaoResponse> cartoes = cartaoService.getCartoesElegiveisPorRenda(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ClienteCartaoResponse>> getCartoesByCpf(
            @Valid ClienteCartaoRequest request
    ) {
        log.info("Solicitacao recebida para consulta de cartoes por cpf");
        var cartoes = clienteCartaoService.listarCartoesPorCpf(request);
        return ResponseEntity.ok(cartoes);
    }

}
