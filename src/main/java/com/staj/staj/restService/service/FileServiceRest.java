package com.staj.staj.restService.service;

import com.staj.staj.common_jpa.entity.AppDocument;
import com.staj.staj.common_jpa.entity.AppPhoto;
import com.staj.staj.common_jpa.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileServiceRest {
    AppDocument getDocument(String id);//для получения документа
    AppPhoto getPhoto(String id);//для получения фото
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);//для преобразования массива byte
    //в объект FileSystemResource, для передачи контента в телеге в теле ответа
}
