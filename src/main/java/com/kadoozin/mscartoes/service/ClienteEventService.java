package com.kadoozin.mscartoes.service;

import com.kadoozin.mscartoes.dto.event.ClienteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteEventService {

    public void processarClienteEvent(ClienteEvent evento) {
        log.info("Processando evento de cliente - Action: {}, CPF: {}, Nome: {}, Idade: {}",
                evento.action(), evento.cpf(), evento.nome(), evento.idade());
    }
}
