package com.fastcampus.javaallinone.project2.mycontact.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(value = "api/helloWorld") // http GET 요청에 응답하는 메소드
    public String helloWorld(){
        return "HelloWorld";
    }
}
