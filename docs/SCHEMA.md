# Database Schema — Crypto Intelligence Platform

This document describes the relational database schema used to store token metadata, whitepaper analysis results, and risk scores.

## Entity Relationship Diagram

```mermaid
erDiagram
    TOKENS ||--o{ DOCUMENTS : has
    TOKENS ||--o{ ANALYSIS_RESULTS : has
    DOCUMENTS ||--o{ ANALYSIS_RESULTS : produces
    ANALYSIS_RESULTS ||--o{ RISK_SCORES : contains

    TOKENS {
        uuid id PK
        string name
        string symbol
        timestamp created_at
        timestamp updated_at
    }
    DOCUMENTS {
        uuid id PK
        uuid token_id FK
        string filename
        string content_hash
        string upload_status
        timestamp created_at
    }
    ANALYSIS_RESULTS {
        uuid id PK
        uuid token_id FK
        uuid document_id FK
        jsonb raw_llm_output
        jsonb structured_output
        decimal risk_score
        string model_version
        timestamp created_at
    }
    RISK_SCORES {
        uuid id PK
        uuid analysis_id FK
        string rule_name
        int points_added
        string reasoning
        timestamp created_at
    }
```

## Table Definitions

### `tokens`
Stores the high-level information for each crypto token analyzed by the platform.

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key. |
| `name` | VARCHAR | Full name of the token (e.g., "Ethereum"). |
| `symbol` | VARCHAR | Ticker symbol (e.g., "ETH"). |
| `created_at` | TIMESTAMP | Creation timestamp. |
| `updated_at` | TIMESTAMP | Last update timestamp. |

### `documents`
Stores metadata for whitepapers or other documents uploaded for analysis.

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key. |
| `token_id` | UUID | Foreign key to `tokens`. |
| `filename` | VARCHAR | Original filename. |
| `content_hash` | VARCHAR | SHA-256 hash of the content to prevent duplicates. |
| `upload_status` | VARCHAR | Status of the upload (e.g., PENDING, PROCESSED). |
| `created_at` | TIMESTAMP | Upload timestamp. |

### `analysis_results`
Stores the structured output from the RAG service and the aggregate risk score.

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key. |
| `token_id` | UUID | Foreign key to `tokens`. |
| `document_id` | UUID | Foreign key to `documents`. |
| `raw_llm_output` | JSONB | The raw JSON string returned by the LLM. |
| `structured_output` | JSONB | Post-processed/validated JSON output. |
| `risk_score` | DECIMAL | Aggregate risk score calculated by the platform (0-100). |
| `model_version` | VARCHAR | Version of the LLM/RAG pipeline used. |
| `created_at` | TIMESTAMP | Analysis timestamp. |

### `risk_scores`
Stores per-rule breakdown of the risk score for transparency and auditability.

| Column | Type | Description |
|---|---|---|
| `id` | UUID | Primary key. |
| `analysis_id` | UUID | Foreign key to `analysis_results`. |
| `rule_name` | VARCHAR | Name of the risk rule triggered. |
| `points_added` | INTEGER | Points added to the raw risk score by this rule. |
| `reasoning` | TEXT | Explanation of why the rule was triggered. |
| `created_at` | TIMESTAMP | Score calculation timestamp. |
