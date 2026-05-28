package com.kadoozin.mscartoes.service;

import com.kadoozin.mscartoes.database.model.ClienteCartao;
import com.kadoozin.mscartoes.database.repository.CartaoRepository;
import com.kadoozin.mscartoes.database.repository.ClienteCartaoRepository;
import com.kadoozin.mscartoes.dto.request.DadosSolicitacaoEmissaoCartao;
import com.kadoozin.mscartoes.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoService {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @Transactional
    public void processarSolicitacao(DadosSolicitacaoEmissaoCartao dados){
        log.info("Processando emissao do cartao...");
        log.info("CPF: {}, ID Cartao: {}, Limite Aprovado: {}",
                dados.cpf(), dados.idCartao(), dados.limiteAprovado());

        var cartao = cartaoRepository.findById(dados.idCartao().intValue())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cartao nao encontrado para id: " + dados.idCartao()));

        var cpfNormalizado = dados.cpf().replaceAll("\\D", "");

        var clienteCartao = new ClienteCartao();
        clienteCartao.setCpf(cpfNormalizado);
        clienteCartao.setCartao(cartao);
        clienteCartao.setLimite(dados.limiteAprovado());

        clienteCartaoRepository.save(clienteCartao);

        log.info("Cartao emitido com sucesso para o CPF: {}", cpfNormalizado);
    }
}
