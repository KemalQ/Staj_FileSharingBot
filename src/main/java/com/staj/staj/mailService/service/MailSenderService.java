package com.staj.staj.mailService.service;

import com.staj.staj.mailService.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
