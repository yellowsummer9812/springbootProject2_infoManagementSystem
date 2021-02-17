package com.fastcampus.javaallinone.project2.mycontact.configuration;


import com.fastcampus.javaallinone.project2.mycontact.configuration.serializer.BirthdaySerializer;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

// 해당 serializer를 springboot에 mapping
@Configuration
public class JsonConfig {

    // 우리가 만든 ObjectMapper를 주입하는 용도
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    // 우리가 customizing하는 부분
    // 우리가 만든 serializer를 등록
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule());
        objectMapper.registerModule(new JavaTimeModule()); // LocalDate를 간단하게 변경할 수 있게 해주는 모듈, [1991,8,15] 이런 형식

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 1991-08-15 이런 형식

        return objectMapper;
    }

    // Module에 우리가 만든 serializer를 mapping
    static class BirthdayModule extends SimpleModule {
        BirthdayModule(){
            super(); // SimpleModule의 생성자 호출
            addSerializer(Birthday.class, new BirthdaySerializer());
        }
    }
}
