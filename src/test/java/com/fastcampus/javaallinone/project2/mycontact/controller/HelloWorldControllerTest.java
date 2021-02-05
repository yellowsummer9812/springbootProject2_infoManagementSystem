package com.fastcampus.javaallinone.project2.mycontact.controller;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


@SpringBootTest
class HelloWorldControllerTest {

    @Autowired // spring context에서 Bean을 주입하겠다는 뜻
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

    @Test
    void helloWorld(){
        //System.out.println("test");
        System.out.println(helloWorldController.helloWorld());

        assertThat(helloWorldController.helloWorld(),equalTo("HelloWorld"));
    }

    @Test
    void mockMvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build(); // MockMvc 세팅

        mockMvc.perform(// 실제 동작하도록 요청
                MockMvcRequestBuilders.get("/api/helloWorld")) // get 메소드로 /api/helloWorld 호출
        .andDo(MockMvcResultHandlers.print()) // request와 response 정보 출력
        .andExpect(MockMvcResultMatchers.status().isOk()) // status가 ok인지 예측
        .andExpect(MockMvcResultMatchers.content().string("HelloWorld")) // response의 내용이  "HelloWorld" 인지 CHECK
        ;
    }
}