package com.example.fooballobs.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.fooballobs.actuator.FootballCustomEndpoint;
import com.example.fooballobs.service.FileLoader;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;

@Configuration
public class FootballConfiguration {

    @Value("${football.folder}")
    private String folder;

    @Bean
    public FileLoader fileLoader() throws IOException {
        return new FileLoader(folder);
    }

    @Bean
    public FootballCustomEndpoint footballCustomEndpoint(FileLoader fileLoader) {
        return new FootballCustomEndpoint(fileLoader);
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

}