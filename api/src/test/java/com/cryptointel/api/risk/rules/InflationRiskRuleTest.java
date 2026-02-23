package com.cryptointel.api.risk.rules;

import com.cryptointel.api.entity.RiskScore;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InflationRiskRuleTest {

    private final InflationRiskRule rule = new InflationRiskRule();

    @Test
    void evaluate_CriticalRisk() {
        Map<String, Object> data = Map.of("inflation_rate", "25%");
        RiskScore score = rule.evaluate(data);
        assertEquals(40, score.getPointsAdded());
    }

    @Test
    void evaluate_HighRisk() {
        Map<String, Object> data = Map.of("inflation_rate", "12 percent");
        RiskScore score = rule.evaluate(data);
        assertEquals(20, score.getPointsAdded());
    }

    @Test
    void evaluate_LowRisk() {
        Map<String, Object> data = Map.of("inflation_rate", "2%");
        RiskScore score = rule.evaluate(data);
        assertEquals(0, score.getPointsAdded());
    }
}
