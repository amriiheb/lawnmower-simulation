package com.mowitnow.mower;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MowerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MowerApplication.class, args);
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
