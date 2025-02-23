package com.staj.staj.restService.controller;

import com.staj.staj.restService.service.FileServiceRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/file")
@RestController//чтобы спринг не искал в ресурсах шаблон страницы view
public class FileController {
    //localhost:8080/file/get-doc?id=3
    //localhost:8080/file/get-photo?id=1
    private final FileServiceRest fileService;

    public FileController(FileServiceRest fileService) {
        this.fileService = fileService;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get-doc")
    public ResponseEntity<?> getDoc(@RequestParam("id") Long id){
        //TODO для формирования badRequest добавить ControllerAdvice
        var doc = fileService.getDocument(id);
        if (doc == null) {//400 ответ если возникла ощибка в id
            return ResponseEntity.badRequest().build();
        }
        var binaryContent = doc.getBinaryContent();
        var fileSystemResource = fileService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getMimeType()))//браузер из потока byte созд. файл с нужным расширением
                .header("Content-Disposition", "attachment; filename=" + doc.getDocName())
                //header-указывает как воспринимать получ инф., attachment-чтобы бр скачал файл
                .body(fileSystemResource);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-photo")
    public ResponseEntity<?> getPhoto(@RequestParam("id") Long id){
        //TODO для формирования badRequest добавить ControllerAdvice
        var photo = fileService.getPhoto(id);
        if (photo == null) {
            return ResponseEntity.badRequest().build();
        }
        var binaryContent = photo.getBinaryContent();
        var fileSystemResource = fileService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-Disposition", "attachment;")//telega не хранит имя фото?
                .body(fileSystemResource);
    }
}
