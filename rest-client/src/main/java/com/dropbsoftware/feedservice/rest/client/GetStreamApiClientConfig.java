package com.dropbsoftware.feedservice.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.getstream.client.StreamClient;
import io.getstream.client.apache.StreamClientImpl;
import io.getstream.client.config.ClientConfiguration;

@Configuration
public class GetStreamApiClientConfig {
    @Value("${getstream.api.key}")
    private String apiKey;
    @Value("${getstream.api.secret}")
    private String apiSecret;

    @Bean
    public StreamClient streamClient() {
        return new StreamClientImpl(new ClientConfiguration(), apiKey, apiSecret);
    }
}
