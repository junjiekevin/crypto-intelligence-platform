package com.cryptointel.api.risk.rules;

import com.cryptointel.api.entity.RiskScore;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VestingRiskRuleTest {

    private final VestingRiskRule rule = new VestingRiskRule();

    @Test
    void evaluate_HighRisk() {
        Map<String, Object> data = Map.of("vesting_schedule_summary", "Immediate unlock for team.");
        RiskScore score = rule.evaluate(data);
        assertEquals(50, score.getPointsAdded());
        assertTrue(score.getReasoning().contains("High risk"));
    }

    @Test
    void evaluate_ModerateRisk() {
        Map<String, Object> data = Map.of("vesting_schedule_summary", "1 year linear vesting.");
        RiskScore score = rule.evaluate(data);
        assertEquals(20, score.getPointsAdded());
    }

    @Test
    void evaluate_LowRisk() {
        Map<String, Object> data = Map.of("vesting_schedule_summary", "Standard 4-year vesting with cliff.");
        RiskScore score = rule.evaluate(data);
        assertEquals(0, score.getPointsAdded());
    }
}
