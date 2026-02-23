package com.cryptointel.api.repository;

import com.cryptointel.api.entity.RiskScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RiskScoreRepository extends JpaRepository<RiskScore, UUID> {
    List<RiskScore> findByAnalysisResultId(UUID analysisId);
}
