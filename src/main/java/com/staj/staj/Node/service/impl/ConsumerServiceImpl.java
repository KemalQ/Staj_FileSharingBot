package com.staj.staj.Node.service.impl;

import com.staj.staj.Node.service.ConsumerService;
import com.staj.staj.Node.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.staj.staj.module.RabbitQueue.*;

@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    private final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues= TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdate(Update update) {
        log.debug("NODE: Text message is received");

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from NODE");
        producerService.producerAnswer(sendMessage);
    }

    @Override
    @RabbitListener(queues= DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdate(Update update) {
        log.debug("NODE: Doc message is received");
    }

    @Override
    @RabbitListener(queues= PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdate(Update update) {
        log.debug("NODE: Photo message is received");
    }
}
