package com.staj.staj.service.impl;

import com.staj.staj.controller.UpdateController;
import com.staj.staj.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.staj.staj.module.RabbitQueue.ANSWER_MESSAGE;

@Service
public class AnswerServiceImpl implements AnswerConsumer {
    private final UpdateController updateController;

    public AnswerServiceImpl(UpdateController updateController) {
        this.updateController = updateController;
    }


    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
