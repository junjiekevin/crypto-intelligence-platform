package com.cryptointel.api.service;

import com.cryptointel.api.client.RagClient;
import com.cryptointel.api.dto.RagAnalysisResponse;
import com.cryptointel.api.entity.AnalysisResult;
import com.cryptointel.api.entity.Document;
import com.cryptointel.api.repository.AnalysisResultRepository;
import com.cryptointel.api.repository.DocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisOrchestrator {

    private final RagClient ragClient;
    private final AnalysisResultRepository analysisResultRepository;
    private final DocumentRepository documentRepository;
    private final ScoringService scoringService;
    private final ObjectMapper objectMapper;

    @Transactional
    public AnalysisResult runFullAnalysis(UUID documentId, MultipartFile file) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        try {
            // 1. Prepare file for RAG
            Resource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            // 2. Call RAG service (Blocking for simplicity in this flow)
            RagAnalysisResponse ragResponse = ragClient.analyzeDocument(resource).block();

            if (ragResponse == null) {
                throw new RuntimeException("RAG service returned empty response");
            }

            // 3. Create initial AnalysisResult
            AnalysisResult result = AnalysisResult.builder()
                    .token(document.getToken())
                    .document(document)
                    .modelVersion("gpt-4o-mini-v1")
                    .structuredOutput(objectMapper.writeValueAsString(ragResponse))
                    .build();

            AnalysisResult savedResult = analysisResultRepository.save(result);

            // 4. Trigger Scoring Engine
            return scoringService.scoreAnalysis(savedResult.getId());

        } catch (IOException e) {
            throw new RuntimeException("Failed to read document for analysis: " + e.getMessage());
        } catch (Exception e) {
            log.error("Analysis orchestration failed", e);
            throw new RuntimeException("Analysis failed: " + e.getMessage());
        }
    }
}
