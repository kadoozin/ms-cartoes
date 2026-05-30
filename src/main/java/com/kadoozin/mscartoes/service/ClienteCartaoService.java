package com.kadoozin.mscartoes.service;

import com.kadoozin.mscartoes.client.ClienteClient;
import com.kadoozin.mscartoes.database.model.ClienteCartao;
import com.kadoozin.mscartoes.database.repository.CartaoRepository;
import com.kadoozin.mscartoes.database.repository.ClienteCartaoRepository;
import com.kadoozin.mscartoes.dto.request.ClienteCartaoRequest;
import com.kadoozin.mscartoes.dto.request.VinculoCartaoRequest;
import com.kadoozin.mscartoes.dto.response.ClienteCartaoResponse;
import com.kadoozin.mscartoes.dto.response.VinculoCartaoResponse;
import com.kadoozin.mscartoes.exception.ResourceNotFoundException;
import com.kadoozin.mscartoes.mapper.ClienteCartaoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoMapper clienteCartaoMapper;
    private final ClienteClient clienteClient;

    @Transactional(readOnly = true)
    public List<ClienteCartaoResponse> listarCartoesPorCpf(@Valid @NotNull ClienteCartaoRequest request) {
        return clienteCartaoRepository.findByCpfNormalizado(request.cpfNormalizado())
                .stream()
                .map(clienteCartaoMapper::toResponse)
                .toList();
    }

    @Transactional
    public VinculoCartaoResponse vincularCartaoAoCliente(
            @Positive @NotNull Integer cartaoId,
            @Valid @NotNull VinculoCartaoRequest request
    ) {
        var cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartao nao encontrado para id: " + cartaoId));

        var cpfNormalizado = request.cpfNormalizado();

        var dadosCliente = clienteClient.getClienteByCpf(cpfNormalizado);

        var clienteCartao = new ClienteCartao();
        clienteCartao.setCpf(cpfNormalizado);
        clienteCartao.setCartao(cartao);
        clienteCartao.setLimite(cartao.getLimiteBasico());
        clienteCartaoRepository.save(clienteCartao);

        return new VinculoCartaoResponse(
                dadosCliente.nome(),
                cpfNormalizado,
                dadosCliente.endereco(),
                cartao.getNome(),
                cartao.getBandeiraCartao().name()
        );
    }
}
