from pydantic import BaseModel, Field
from typing import List

class TokenomicsOutput(BaseModel):
    token_name: str = Field(..., description="Name of the token")
    total_supply: str = Field(..., description="Total supply of the token")
    inflation_rate: str = Field(..., description="Annual inflation rate")
    vesting_schedule_summary: str = Field(..., description="Summary of the vesting schedule")
    staking_rewards: str = Field(..., description="Description of staking rewards")
    utility_description: str = Field(..., description="Description of token utility")
    governance_model: str = Field(..., description="Description of the governance model")
    deflationary: bool = Field(..., description="Whether the token is deflationary")
    identified_risks: List[str] = Field(default_factory=list, description="List of identified risks")
    computed_risk_score: float = Field(0.0, description="Placeholder risk score (to be calculated by risk engine)")
