package com.staj.staj.Node.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProducerService {
    void producerAnswer(SendMessage sendMessage);
}
