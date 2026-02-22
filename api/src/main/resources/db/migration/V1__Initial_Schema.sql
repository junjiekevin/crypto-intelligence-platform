-- V1__Initial_Schema.sql
-- Initial schema for Crypto Intelligence Platform

-- Create extensions if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tokens Table
CREATE TABLE tokens (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    symbol VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Documents Table
CREATE TABLE documents (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token_id UUID NOT NULL REFERENCES tokens(id) ON DELETE CASCADE,
    filename VARCHAR(255) NOT NULL,
    content_hash VARCHAR(64),
    upload_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Analysis Results Table
CREATE TABLE analysis_results (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    token_id UUID NOT NULL REFERENCES tokens(id) ON DELETE CASCADE,
    document_id UUID NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
    raw_llm_output JSONB,
    structured_output JSONB,
    risk_score DECIMAL(5, 2),
    model_version VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Risk Scores Table (Rule-level breakdown)
CREATE TABLE risk_scores (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    analysis_id UUID NOT NULL REFERENCES analysis_results(id) ON DELETE CASCADE,
    rule_name VARCHAR(255) NOT NULL,
    points_added INTEGER NOT NULL,
    reasoning TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_tokens_symbol ON tokens(symbol);
CREATE INDEX idx_documents_token_id ON documents(token_id);
CREATE INDEX idx_analysis_results_token_id ON analysis_results(token_id);
CREATE INDEX idx_risk_scores_analysis_id ON risk_scores(analysis_id);
