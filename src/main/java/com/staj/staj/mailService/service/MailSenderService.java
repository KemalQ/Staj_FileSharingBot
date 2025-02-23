package com.staj.staj.mailService.service;

import com.staj.staj.commonUtils.dto.MailParams;

public interface MailSenderService {
    void send(MailParams mailParams);
}
