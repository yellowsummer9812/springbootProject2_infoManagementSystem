package com.fastcampus.javaallinone.project2.mycontact.controller;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class HelloWorldControllerTest {

    @Autowired // spring context에서 Bean을 주입하겠다는 뜻
    private HelloWorldController helloWorldController;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();
    }

    @Test
    void helloWorld() throws Exception {
        //mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build(); // MockMvc 세팅

        mockMvc.perform(// 실제 동작하도록 요청
                MockMvcRequestBuilders.get("/api/helloWorld")) // get 메소드로 /api/helloWorld 호출
        //.andDo(print()) // request와 response 정보 출력
        .andExpect(status().isOk()) // status가 ok인지 예측
        .andExpect(MockMvcResultMatchers.content().string("HelloWorld")) // response의 내용이  "HelloWorld" 인지 CHECK
        ;
    }

    @Test
    void helloException() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloException"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("알 수 없는 서버 오류가 발생했습니다."));
    }
}