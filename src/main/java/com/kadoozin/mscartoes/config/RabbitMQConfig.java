package com.kadoozin.mscartoes.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${mq.exchange}")
    private String exchange;

    @Value("${mq.queues.emissao-cartoes}")
    private String queueEmissaoCartoes;

    @Value("${rabbitmq.exchange.cliente}")
    private String clienteExchange;

    @Value("${rabbitmq.queue.cliente-criado}")
    private String queueClienteCriado;

    @Value("${rabbitmq.routing-key.cliente-criado}")
    private String routingKeyClienteCriado;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange cartoesExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queueEmissaoCartoes() {
        return new Queue(queueEmissaoCartoes, true);
    }

    @Bean
    public Binding bindingEmissaoCartoes() {
        return BindingBuilder
                .bind(queueEmissaoCartoes())
                .to(cartoesExchange())
                .with(queueEmissaoCartoes);
    }

    @Bean
    public DirectExchange clienteExchange() {
        return new DirectExchange(clienteExchange, true, false);
    }

    @Bean
    public Queue queueClienteCriado() {
        return new Queue(queueClienteCriado, true);
    }

    @Bean
    public Binding bindingClienteCriado() {
        return BindingBuilder
                .bind(queueClienteCriado())
                .to(clienteExchange())
                .with(routingKeyClienteCriado);
    }
}
