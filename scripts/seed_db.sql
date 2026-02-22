-- seed_db.sql
-- Seed data for Crypto Intelligence Platform

-- Insert sample tokens
INSERT INTO tokens (id, name, symbol) VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Ethereum', 'ETH'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Solana', 'SOL'),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Uniswap', 'UNI');

-- Insert sample documents
INSERT INTO documents (id, token_id, filename, content_hash, upload_status) VALUES
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ethereum-whitepaper.pdf', 'hash_eth_123', 'PROCESSED'),
('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'solana-whitepaper.pdf', 'hash_sol_456', 'PROCESSED');

-- Insert sample analysis results
INSERT INTO analysis_results (id, token_id, document_id, raw_llm_output, structured_output, risk_score, model_version) VALUES
('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '{"raw": "data"}', '{"structured": "data"}', 15.5, 'gpt-4o'),
('00eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '{"raw": "data"}', '{"structured": "data"}', 25.0, 'gpt-4o');

-- Insert sample risk scores
INSERT INTO risk_scores (analysis_id, rule_name, points_added, reasoning) VALUES
('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'decentralization_check', 0, 'Highly decentralized network.'),
('00eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'inflation_check', 10, 'Inflation rate slightly above threshold.');
