package com.cryptointel.api.repository;

import com.cryptointel.api.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, UUID> {
    List<AnalysisResult> findByTokenIdOrderByCreatedAtDesc(UUID tokenId);
    Optional<AnalysisResult> findByDocumentId(UUID documentId);
}
