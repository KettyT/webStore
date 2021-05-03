package ru.tiutikova.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tiutikova.UserException;
import ru.tiutikova.dto.ResultDto;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UserException.class})
    public ResponseEntity handleException(UserException ex, WebRequest request) {
        ResultDto resultDto = new ResultDto(false, ex.getMessage());

        return handleExceptionInternal(ex, resultDto,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
