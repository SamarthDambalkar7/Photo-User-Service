package com.userservice.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder getWebClientBuilder() {

        int bufferSize = 100 * 1024 * 1024; // 10 MB
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(bufferSize));

    }

}
