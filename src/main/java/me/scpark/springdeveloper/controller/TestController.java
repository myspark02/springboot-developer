package me.scpark.springdeveloper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("test")
    public ResponseEntity<String> test() {
        ResponseEntity<String> response = new ResponseEntity<>("Hello World", HttpStatus.OK);
        return response;
    }
}
