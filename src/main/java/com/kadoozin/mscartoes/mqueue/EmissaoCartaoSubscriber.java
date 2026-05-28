package com.kadoozin.mscartoes.mqueue;

import com.kadoozin.mscartoes.dto.request.DadosSolicitacaoEmissaoCartao;
import com.kadoozin.mscartoes.service.EmissaoCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    private final EmissaoCartaoService service;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissaoCartao(
            @Payload DadosSolicitacaoEmissaoCartao dados) {

        service.processarSolicitacao(dados);
    }
}
