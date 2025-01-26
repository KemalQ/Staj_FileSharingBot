package com.staj.staj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String userName;
    @Value("${telegram.bot.token}")
    private String tokenName;
    private UpdateController updateController;

    @PostConstruct
    public void init(){
        updateController.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return userName;
    }
    @Override
    public String getBotToken(){
        return tokenName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message originalMessage = update.getMessage();
        log.info("recieved message: {}", originalMessage.getText());

        var response = new SendMessage();
        response.setChatId(originalMessage.getChatId().toString());
        response.setText("Hello from bot");
        sendAnswerMessage(response);
    }
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try{
                execute(message);
            } catch (TelegramApiException e){
                log.error(String.valueOf(e));
            }
        }
    }
}
