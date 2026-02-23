package com.cryptointel.api.service;

import com.cryptointel.api.entity.AnalysisResult;
import com.cryptointel.api.repository.AnalysisResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisResultRepository analysisResultRepository;

    @Transactional(readOnly = true)
    public List<AnalysisResult> getAnalysisHistory(UUID tokenId) {
        return analysisResultRepository.findByTokenIdOrderByCreatedAtDesc(tokenId);
    }
}
