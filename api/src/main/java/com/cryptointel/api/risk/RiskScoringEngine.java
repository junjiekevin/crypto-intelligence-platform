package com.cryptointel.api.risk;

import com.cryptointel.api.entity.AnalysisResult;
import com.cryptointel.api.entity.RiskScore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RiskScoringEngine {

    private final List<RiskRule> rules;

    public void calculateTotalRisk(AnalysisResult result, Map<String, Object> tokenomicsData) {
        int totalPoints = 0;
        List<RiskScore> scores = new ArrayList<>();

        for (RiskRule rule : rules) {
            RiskScore score = rule.evaluate(tokenomicsData);
            score.setAnalysisResult(result);
            scores.add(score);
            totalPoints += score.getPointsAdded();
        }

        // Normalize or cap score at 100
        BigDecimal finalScore = BigDecimal.valueOf(Math.min(totalPoints, 100));
        result.setRiskScore(finalScore);
        result.setRiskScores(scores);
    }
}
