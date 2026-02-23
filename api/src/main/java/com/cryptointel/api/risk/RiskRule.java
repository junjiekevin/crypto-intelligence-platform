package com.cryptointel.api.risk;

import com.cryptointel.api.entity.RiskScore;
import java.util.Map;

public interface RiskRule {
    String getName();
    RiskScore evaluate(Map<String, Object> tokenomicsData);
}
