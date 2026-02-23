package com.cryptointel.api.service;

import com.cryptointel.api.dto.DocumentResponse;
import com.cryptointel.api.entity.Document;
import com.cryptointel.api.entity.Token;
import com.cryptointel.api.repository.DocumentRepository;
import com.cryptointel.api.repository.TokenRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TokenRepository tokenRepository;
    private final AnalysisOrchestrator analysisOrchestrator;

    public DocumentService(DocumentRepository documentRepository, TokenRepository tokenRepository, @Lazy AnalysisOrchestrator analysisOrchestrator) {
        this.documentRepository = documentRepository;
        this.tokenRepository = tokenRepository;
        this.analysisOrchestrator = analysisOrchestrator;
    }

    @Transactional
    public DocumentResponse uploadDocument(UUID tokenId, MultipartFile file) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found with id: " + tokenId));

        // In a real app, we would save the file to S3/Local storage and compute hash
        // For now, we just save metadata as per Task 4 intent
        Document document = Document.builder()
                .token(token)
                .filename(file.getOriginalFilename())
                .uploadStatus("PENDING")
                .contentHash("STUB_HASH") // Would be real hash in production
                .build();

        Document saved = documentRepository.save(document);

        // Trigger Orchestrator
        analysisOrchestrator.runFullAnalysis(saved.getId(), file);
        
        return DocumentResponse.builder()
                .id(saved.getId())
                .filename(saved.getFilename())
                .uploadStatus(saved.getUploadStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
