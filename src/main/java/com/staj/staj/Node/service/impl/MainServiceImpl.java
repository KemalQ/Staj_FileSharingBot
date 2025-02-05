package com.staj.staj.Node.service.impl;

import com.staj.staj.Node.service.ProducerService;
import com.staj.staj.Node.dao.RawDataDAO;
import com.staj.staj.Node.entity.RawData;
import com.staj.staj.Node.service.MainService;
import com.staj.staj.common_jpa.dao.AppUserDAO;
import com.staj.staj.common_jpa.entity.AppUser;
import com.staj.staj.common_jpa.entity.enums.UserState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.staj.staj.common_jpa.entity.enums.UserState.BASIC_STATE;

@Service
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService, AppUserDAO appUserDAO) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var textMessage = update.getMessage();//достаем из телеграм. пользователя из входящего Update
        var telegramUser = textMessage.getFrom();
        var appUser = findOrSaveAppUser(telegramUser);

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from NODE, this is text message");
        producerService.producerAnswer(sendMessage);
    }
    private AppUser findOrSaveAppUser(User telegramUser){//поиск пользователя в бд, имеет PrimaryKey и связан с сессией Hibernate
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
        if(persistentAppUser==null){//если польз. нет сохраняем его
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    //TODO изменить значение по умолчанию после добавления регистрации
                    .isActive(true)
                    .state(BASIC_STATE).build();
            return appUserDAO.save(transientAppUser);
        }
        return persistentAppUser;
    }
    private void saveRawData(Update update) {//storing to hash collection
        RawData rawData = RawData.builder().event(update).build();//из за неправильного импорта в RawData .event не распознается
        rawDataDAO.save(rawData);//storing to DB and setting id
    }
}
