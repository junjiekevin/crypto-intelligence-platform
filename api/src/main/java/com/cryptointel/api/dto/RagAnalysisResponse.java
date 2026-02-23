package com.cryptointel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RagAnalysisResponse {
    private String token_name;
    private String total_supply;
    private String inflation_rate;
    private String vesting_schedule_summary;
    private String staking_rewards;
    private String utility_description;
    private String governance_model;
    private boolean deflationary;
    private List<String> identified_risks;
    private double computed_risk_score;
}
