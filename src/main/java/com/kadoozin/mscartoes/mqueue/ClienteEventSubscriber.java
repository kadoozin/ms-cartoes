package com.kadoozin.mscartoes.mqueue;

import com.kadoozin.mscartoes.dto.event.ClienteEvent;
import com.kadoozin.mscartoes.service.ClienteEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteEventSubscriber {

    private final ClienteEventService service;

    @RabbitListener(queues = "${rabbitmq.queue.cliente-criado}")
    public void receberClienteEvent(@Payload ClienteEvent evento) {
        log.info("Evento de cliente recebido da fila: {}", evento.cpf());
        service.processarClienteEvent(evento);
    }
}
