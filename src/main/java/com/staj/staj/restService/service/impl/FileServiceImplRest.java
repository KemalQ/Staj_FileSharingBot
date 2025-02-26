package com.staj.staj.restService.service.impl;

import com.staj.staj.commonUtils.utils.CryptoTool;
import com.staj.staj.common_jpa.dao.AppDocumentDAO;
import com.staj.staj.common_jpa.dao.AppPhotoDAO;
import com.staj.staj.common_jpa.entity.AppDocument;
import com.staj.staj.common_jpa.entity.AppPhoto;
import com.staj.staj.restService.service.FileServiceRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
