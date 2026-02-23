package com.cryptointel.api.risk.rules;

import com.cryptointel.api.entity.RiskScore;
import com.cryptointel.api.risk.RiskRule;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VestingRiskRule implements RiskRule {

    @Override
    public String getName() {
        return "Vesting Schedule Risk";
    }

    @Override
    public RiskScore evaluate(Map<String, Object> data) {
        String summary = (String) data.getOrDefault("vesting_schedule_summary", "");
        int points = 0;
        String reasoning = "Vesting looks standard or not found.";

        if (summary.toLowerCase().contains("no vesting") || summary.toLowerCase().contains("immediate unlock")) {
            points = 50;
            reasoning = "High risk: No vesting or immediate unlock detected.";
        } else if (summary.toLowerCase().contains("short") || summary.toLowerCase().contains("year")) {
            points = 20;
            reasoning = "Moderate risk: Short vesting period detected.";
        }

        return RiskScore.builder()
                .ruleName(getName())
                .pointsAdded(points)
                .reasoning(reasoning)
                .build();
    }
}
