package com.myapps;

import com.myapps.domain.MsgSrc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobTrackerApplication.class, args);
    }

    @Bean
    public MsgSrc msgSrc(MessageSource messageSource){
        return new MsgSrc(messageSource);
    }
}
