package com.staj.staj.controller;

import com.staj.staj.service.UpdateProducer;
import com.staj.staj.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.staj.staj.module.RabbitQueue.*;


@Component
@Slf4j

public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private  UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }
    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.getMessage() != null){
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is:  {}" , update);
        }
    }
    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        if (message.getText() != null){
            processTextMessage(update);
        } else if (message.getDocument() != null) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        }else{
            setUnsupportedMessageTypeView(update);
        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Unsupported message type!");
        setView(sendMessage);
    }
    private void setFileReceived(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "File received! Loadind...");
        setView(sendMessage);
    }
    private void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        setFileReceived(update);
    }
    private void processDocMessage(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
    }
    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }


}
