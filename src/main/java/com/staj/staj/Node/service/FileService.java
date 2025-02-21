package com.staj.staj.Node.service;

import com.staj.staj.Node.service.enums.LinkType;
import com.staj.staj.common_jpa.entity.AppDocument;
import com.staj.staj.common_jpa.entity.AppPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
    String generateLink(Long docId, LinkType linkType);
}
