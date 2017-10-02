package com.joe.dating.common;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MultipartException.class,FileUploadBase.FileSizeLimitExceededException.class,java.lang.IllegalStateException.class})
    ResponseEntity handleFileException(HttpServletRequest request, Throwable e) {
        if(e.getCause() instanceof IllegalStateException && e.getCause().getCause().getClass().getSimpleName().equals(FileUploadBase.SizeLimitExceededException.class.getSimpleName())) {
            return new ResponseEntity(HttpStatus.PAYLOAD_TOO_LARGE);
        }
        throw new RuntimeException(e);
    }

    @ExceptionHandler({NotFoundException.class})
    ResponseEntity NotFoundException(WebRequest request, Exception e) {
        String bodyOfResponse = "Not Found";
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
