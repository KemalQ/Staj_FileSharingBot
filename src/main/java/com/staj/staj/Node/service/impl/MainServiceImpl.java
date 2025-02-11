package com.staj.staj.Node.service.impl;

import com.staj.staj.Node.service.ProducerService;
import com.staj.staj.Node.dao.RawDataDAO;
import com.staj.staj.Node.entity.RawData;
import com.staj.staj.Node.service.MainService;
import com.staj.staj.common_jpa.dao.AppUserDAO;
import com.staj.staj.common_jpa.entity.AppUser;
import com.staj.staj.common_jpa.entity.enums.UserState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.staj.staj.Node.service.enums.ServiceCommands.*;
import static com.staj.staj.common_jpa.entity.enums.UserState.BASIC_STATE;
import static com.staj.staj.common_jpa.entity.enums.UserState.WAIR_FOR_EMAIL_STATE;

@Service
@Slf4j
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
        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getState();
        var text = update.getMessage().getText();//достаем из телеграм. пользователя из входящего Update
        var output = "";

        if (CANCEL.equals(text)){
            output = cancelProcess(appUser);
        }
        else if(BASIC_STATE.equals(userState)){
            output = processServiceCommand(appUser, text);
        }
        else if(WAIR_FOR_EMAIL_STATE.equals(userState)){
            //TODO add email processing
        }
        else {
            log.error("Unknown user state: " + userState);
            output = "Unknown error! Enter /cancel and try again!";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)) {
            return;
        }
        //TODO добавить сохранения документа
        var answer = "Документ успешно загружен! ссыдка для скачивания: http://test.ru/get-doc//777";
        sendAnswer(answer, chatId);
    }

    private boolean isNotAllowToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getState();
        if (!appUser.getIsActive()) {
            var error = "Зарегистрируйтесь или активируйте свою учетную запись для загрузки контента";
            sendAnswer(error, chatId);
            return true;
        } else if (!BASIC_STATE.equals(userState)){
            var error = "Отмените текущую команду с помощью /cancel для отправки файлов";
            sendAnswer(error, chatId);
            return true;
        }
        return false;
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)) {
            return;
        }
        //TODO добавить сохранения фото
        var answer = "Фото успешно загружено! ссылка для скачивания: http://test.ru/get-photo//777";
        sendAnswer(answer, chatId);
    }

    private String processServiceCommand(AppUser appUser, String cmd){
        if (REGISTRATION.equals(cmd)){
            //TODO add registration
            return "temporary unavailable";
        } else if (HELP.equals(cmd)){
            return help();
        } else if (START.equals(cmd)) {
            return "Welcome! Enter /help to see list of available commands";
        } else return "Unknown command. Enter /help to see list of available commands";
    }

    private String help(){
        return "List of available commands:\n"
                + "/cancel - cancel execution of the current command\n"
                + "/registration - user registration";
    }

    private String cancelProcess(AppUser appUser){
        appUser.setState(BASIC_STATE);
        appUserDAO.save(appUser);
        return "Command cancelled!";
    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);//"Hello from NODE, this is text message"
        producerService.producerAnswer(sendMessage);
    }

    private AppUser findOrSaveAppUser(Update update){//поиск пользователя в бд, имеет PrimaryKey и связан с сессией Hibernate
        User telegramUser = update.getMessage().getFrom();
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
