package com.newtonk.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Created by newtonk on 2017/6/20.
 */
@Getter@Setter
public class NewTonkException extends RuntimeException{
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message ;
    public NewTonkException(){
        super();
    }

    public NewTonkException(String messge){
        super(messge);
        this.message = messge;
    }


    public NewTonkException(String message,HttpStatus httpStatus){
        super(message);
        this.message =message;
        this.status = httpStatus;
    }
}
