package com.staj.staj.restService.service.impl;

import com.staj.staj.commonUtils.utils.CryptoTool;
import com.staj.staj.common_jpa.dao.AppDocumentDAO;
import com.staj.staj.common_jpa.dao.AppPhotoDAO;
import com.staj.staj.common_jpa.entity.AppDocument;
import com.staj.staj.common_jpa.entity.AppPhoto;
import com.staj.staj.common_jpa.entity.BinaryContent;
import com.staj.staj.restService.service.FileServiceRest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j//добавил логирование
@Service//создаст из класса спринговый bean
public class FileServiceImplRest implements FileServiceRest {
    private final AppDocumentDAO appDocumentDAO;
    private final AppPhotoDAO appPhotoDAO;
    private final CryptoTool cryptoTool;

    public FileServiceImplRest(AppDocumentDAO appDocumentDAO, AppPhotoDAO appPhotoDAO, CryptoTool cryptoTool) {
        this.appDocumentDAO = appDocumentDAO;
        this.appPhotoDAO = appPhotoDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public AppDocument getDocument(Long id) {//парсим строку в лонг, по лонгу находим док
        return appDocumentDAO.findById(id).orElse(null);//и несанкц доступа к докам. других пользв.
    }

    @Override
    public AppPhoto getPhoto(Long id) {//парсим строку в лонг, по лонгу находим фото
        //TODO добавить дешифрование хеш строки
//        var id = cryptoTool.idOf(hash);//чтобы не было самовольного указывания id,
//        if (id == null){
//            return null;
//        }
        return appPhotoDAO.findById(id).orElse(null);//парсим строку в лонг, по лонгу находим фото
    }

    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try{
            //TODO добавить генерацию имени временного файла
            File temp = File.createTempFile("tempFile", ".bin");
            temp.deleteOnExit();//автоудаление после завершения работы JVM
            // Записываем содержимое бинарного объекта в созданный временный файл
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        } catch (IOException e) {
            log.error("IOException in FileSystemResource: " +e);
            return null;
        }
    }
}
