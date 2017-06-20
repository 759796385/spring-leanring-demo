package com.newtonk.controller;

import com.newtonk.exception.NewTonkException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by newtonk on 2017/6/20.
 */
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {


    @GetMapping(value = "")
    public String excep() throws Exception{
        throw new IOException("I want to throw exception");
    }

    @GetMapping(value = "/my")
    public String exception2(){
        throw new NewTonkException("my exception");
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(NewTonkException ex) {
        // prepare responseEntity
        return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), ex.getStatus());
    }
}
