from langchain_core.prompts import ChatPromptTemplate

TOKENOMICS_EXTRACTION_SYSTEM_PROMPT = """
You are a senior crypto tokenomics analyst. Your goal is to extract structured tokenomics data from the provided document chunks.
Extract as much detail as possible for each field. If a piece of information is missing, state "Not found".
Ensure the output is valid JSON matching the requested schema.

Schema description:
- token_name: The full name of the token.
- total_supply: Total/Max supply including units (e.g., "1,000,000,000 ETH").
- inflation_rate: Annual inflation or minting rate.
- vesting_schedule_summary: Brief summary of how tokens are unlocked over time.
- staking_rewards: Description of rewards for stakers.
- utility_description: What the token is used for (fees, governance, etc.).
- governance_model: How decisions are made (DAO, centralized, etc.).
- deflationary: Boolean true/false if there is a burn mechanism.
- identified_risks: List of specific risks mentioned (e.g., "High insider allocation").
"""

TOKENOMICS_EXTRACTION_USER_PROMPT = """
Context from whitepaper:
---
{context}
---

Extract the tokenomics data for the token described in the context above.
"""

def get_extraction_prompt():
    return ChatPromptTemplate.from_messages([
        ("system", TOKENOMICS_EXTRACTION_SYSTEM_PROMPT),
        ("user", TOKENOMICS_EXTRACTION_USER_PROMPT),
    ])
