package com.staj.staj.Node.service.impl;

import com.staj.staj.Node.service.AppUserService;
import com.staj.staj.commonUtils.dto.MailParams;
import com.staj.staj.commonUtils.utils.CryptoTool;
import com.staj.staj.common_jpa.dao.AppUserDAO;
import com.staj.staj.common_jpa.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static com.staj.staj.common_jpa.entity.enums.UserState.BASIC_STATE;
import static com.staj.staj.common_jpa.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;

@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;
    @Value("${service.mail.uri}")
    private String mailServiceUri;

    public AppUserServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public String registerUser(AppUser appUser) {
        if (appUser.getIsActive()){
            return "Вы уже зарегистрированы!";
        } else if (appUser.getEmail() != null){
            return "Вам на почту было отправлено письмо. "
                    + "Перейдите по ссылке в письме для пожтверждения регистрации.";
        }
        appUser.setState(WAIT_FOR_EMAIL_STATE);
        appUserDAO.save(appUser);
        return "Введите, пожалуйта ваш email:";
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);//созд InternetAddress
            emailAddress.validate();//проверка на соответствие эл адр правильному формату
        } catch (AddressException e) {
            return "Введите пожалуйста конкретный email. Для отмены команды введите /cancel";
        }
        var optional = appUserDAO.findByEmail(email);//если нет optional=null
        if (optional.isEmpty()) {//если польз. не найден по эл почте в бд
            appUser.setEmail(email);//TODO установка эл почты  setEmail здесь это setter из AppUser
            appUser.setState(BASIC_STATE);
            appUser = appUserDAO.save(appUser);

            var cryptoUserId = cryptoTool.hashOf(appUser.getId());//TODO ошибка возможно изза возвр знач из хеша
            var response = sendRequestToMailService(cryptoUserId, email);
            if (response.getStatusCode() != HttpStatus.OK) {
                var msg = String.format("Отправка эл. письма на почту %s не удалась.", email);
                log.error(msg);
                appUser.setEmail(null);
                appUserDAO.save(appUser);
                return msg;
            }
            return "Вам на почту было отправлено письмо."
                    + " Перейдите по ссылке в письме для подстверждения регистрации";
        }
        else {
            return "Этот email уже используется. Введите корректный email."
                    + " Для отмены команды введите /cancel";
        }
    }
    private ResponseEntity<String> sendRequestToMailService(String cryptoUserId, String email) {
        //отправка HTTP POST запроса к сервису почтовых рассылок
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .emailTo(email)
                .build();
        var request = new HttpEntity<>(mailParams, headers);
        return restTemplate.exchange(mailServiceUri, HttpMethod.POST, request, String.class);//TODO возм. ощибка в mailServiceUri
    }
}
