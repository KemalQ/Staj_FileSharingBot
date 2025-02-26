package com.staj.staj.restService.service;

import com.staj.staj.common_jpa.entity.AppDocument;
import com.staj.staj.common_jpa.entity.AppPhoto;
import com.staj.staj.common_jpa.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileServiceRest {
    AppDocument getDocument(Long id);//для получения документа
    AppPhoto getPhoto(Long id);//для получения фото
}
