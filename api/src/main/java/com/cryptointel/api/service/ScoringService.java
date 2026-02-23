package com.cryptointel.api.service;

import com.cryptointel.api.entity.AnalysisResult;
import com.cryptointel.api.repository.AnalysisResultRepository;
import com.cryptointel.api.risk.RiskScoringEngine;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScoringService {

    private final AnalysisResultRepository analysisResultRepository;
    private final RiskScoringEngine riskScoringEngine;
    private final ObjectMapper objectMapper;

    @Transactional
    public AnalysisResult scoreAnalysis(UUID analysisId) {
        AnalysisResult result = analysisResultRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        try {
            // Convert structured JSON string to Map
            Map<String, Object> data = objectMapper.readValue(
                    result.getStructuredOutput(),
                    new TypeReference<Map<String, Object>>() {}
            );

            // Execute scoring
            riskScoringEngine.calculateTotalRisk(result, data);

            return analysisResultRepository.save(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to score analysis: " + e.getMessage());
        }
    }
}
