package com.staj.staj.restService.controller;

import com.staj.staj.commonUtils.utils.CryptoTool;
import com.staj.staj.restService.service.FileServiceRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequestMapping("/file")
@RestController//чтобы спринг не искал в ресурсах шаблон страницы view
public class FileController {
    //localhost:8080/file/get-doc?id=3
    //localhost:8080/file/get-photo?id=1
    private final FileServiceRest fileService;
    private final CryptoTool cryptoTool;

    public FileController(FileServiceRest fileService, CryptoTool cryptoTool) {
        this.fileService = fileService;
        this.cryptoTool = cryptoTool;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get-doc")
    public void getDoc(@RequestParam("id") String idValue, HttpServletResponse response){
        //TODO для формирования badRequest добавить ControllerAdvice
        Long id = cryptoTool.idOf(idValue);
        var doc = fileService.getDocument(id);
        if (doc == null) {//400 ответ если возникла ощибка в id
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType(MediaType.parseMediaType(doc.getMimeType()).toString());
        response.setHeader("Content-disposition", "attachment; filename" + doc.getDocName());
        response.setStatus(HttpServletResponse.SC_OK);

        var binaryContent = doc.getBinaryContent();

        try{
            var out = response.getOutputStream();
            out.write(binaryContent.getFileAsArrayOfBytes());
            out.close();
        } catch (IOException e) {
            log.error("Error occurred: ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-photo")
    public void getPhoto(@RequestParam("id") String idValue, HttpServletResponse response){
        //TODO для формирования badRequest добавить ControllerAdvice
        Long id = cryptoTool.idOf(idValue);
        var photo = fileService.getPhoto(id);
        if (photo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType(MediaType.IMAGE_JPEG.toString());
        response.setHeader("Content-disposition", "attachment;");
        response.setStatus(HttpServletResponse.SC_OK);

        var binaryContent = photo.getBinaryContent();
        try{
            var out = response.getOutputStream();
            out.write(binaryContent.getFileAsArrayOfBytes());
            out.close();
        } catch (IOException e) {
            log.error("Error occurred: ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
