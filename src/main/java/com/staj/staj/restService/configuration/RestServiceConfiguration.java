package com.staj.staj.restService.configuration;

import com.staj.staj.commonUtils.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestServiceConfiguration {//реализовать при микросервисной архитектуре
//    @Value("${salt}")
//    private String salt;
//    @Bean
//    public CryptoTool getCryptoToolRest() {
//        return new CryptoTool(salt);
//    }
}
