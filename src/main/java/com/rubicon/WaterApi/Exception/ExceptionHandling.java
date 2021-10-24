package com.rubicon.WaterApi.Exception;

import com.rubicon.WaterApi.constants.ExceptionConstants;
import com.rubicon.WaterApi.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(OrderAlreadyPlacedException.class)
    public ResponseEntity<HttpResponse> orderAlreadyPlacedException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, ExceptionConstants.ORDER_ALREADY_PALCED);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<HttpResponse> orderNotFoundException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST, ExceptionConstants.ORDER_DONT_EXIST);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),httpStatus);
    }
}
