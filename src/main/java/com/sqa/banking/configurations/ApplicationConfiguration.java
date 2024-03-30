package com.sqa.banking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sqa.banking.generators.TimestampGenerator;

@Configuration
@EnableJpaRepositories
public class ApplicationConfiguration {
    
    @Bean
    public TimestampGenerator timestampGenerator() {
        return new TimestampGenerator();
    }
}
