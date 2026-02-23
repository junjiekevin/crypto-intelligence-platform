package com.cryptointel.api.risk.rules;

import com.cryptointel.api.entity.RiskScore;
import com.cryptointel.api.risk.RiskRule;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InflationRiskRule implements RiskRule {

    @Override
    public String getName() {
        return "Inflation Rate Risk";
    }

    @Override
    public RiskScore evaluate(Map<String, Object> data) {
        String rate = (String) data.getOrDefault("inflation_rate", "0%");
        int points = 0;
        String reasoning = "Inflation rate is low or standard.";

        // Simple parsing logic for prototype
        try {
            double value = Double.parseDouble(rate.replaceAll("[^0-9.]", ""));
            if (value > 20) {
                points = 40;
                reasoning = "Critical risk: Extremely high inflation rate (>20%).";
            } else if (value > 10) {
                points = 20;
                reasoning = "High risk: High inflation rate (>10%).";
            }
        } catch (Exception e) {
            reasoning = "Could not parse inflation rate reliably. Defaulting to low risk.";
        }

        return RiskScore.builder()
                .ruleName(getName())
                .pointsAdded(points)
                .reasoning(reasoning)
                .build();
    }
}
