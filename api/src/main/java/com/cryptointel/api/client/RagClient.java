package com.cryptointel.api.client;

import com.cryptointel.api.dto.RagAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RagClient {

    private final WebClient webClient;

    public RagClient(WebClient.Builder webClientBuilder, @Value("${app.rag-service.url:http://localhost:8000}") String ragUrl) {
        this.webClient = webClientBuilder.baseUrl(ragUrl).build();
    }

    public Mono<RagAnalysisResponse> analyzeDocument(Resource resource) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", resource);
        MultiValueMap<String, HttpEntity<?>> multipartBody = builder.build();

        return webClient.post()
                .uri("/api/v1/analyze")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBody))
                .retrieve()
                .bodyToMono(RagAnalysisResponse.class);
    }
}
