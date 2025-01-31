package com.staj.staj.Node.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NodeRabbitConfiguration {//должен назв RabbitConfiguration
//    @Bean
//    public MessageConverter jsonMessageConverter(){//раскоментировать в модуле Node
//        return new Jackson2JsonMessageConverter();
//    }
}
