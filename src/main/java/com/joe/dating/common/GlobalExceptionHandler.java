package com.joe.dating.common;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MultipartException.class,FileUploadBase.FileSizeLimitExceededException.class,java.lang.IllegalStateException.class})
    ResponseEntity handleFileException(HttpServletRequest request, Throwable e) {
        if(e.getCause() instanceof IllegalStateException && e.getCause().getCause().getClass().getSimpleName().equals(FileUploadBase.SizeLimitExceededException.class.getSimpleName())) {
            return new ResponseEntity(HttpStatus.PAYLOAD_TOO_LARGE);
        }
        throw new RuntimeException(e);
    }
}
