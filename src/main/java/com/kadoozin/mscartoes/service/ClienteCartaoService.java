package com.kadoozin.mscartoes.service;

import com.kadoozin.mscartoes.database.repository.ClienteCartaoRepository;
import com.kadoozin.mscartoes.dto.request.ClienteCartaoRequest;
import com.kadoozin.mscartoes.dto.response.ClienteCartaoResponse;
import com.kadoozin.mscartoes.mapper.ClienteCartaoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ClienteCartaoService {
    private final ClienteCartaoRepository clienteCartaoRepository;
    private final ClienteCartaoMapper clienteCartaoMapper;

    @Transactional(readOnly = true)
    public List<ClienteCartaoResponse> listarCartoesPorCpf(@Valid @NotNull ClienteCartaoRequest request) {
        return clienteCartaoRepository.findByCpf(request.cpf())
                .stream()
                .map(clienteCartaoMapper::toResponse)
                .toList();
    }
}
