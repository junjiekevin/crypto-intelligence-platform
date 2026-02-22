# AGENT INSTRUCTIONS

## 1. Overview

You are an expert software engineer and system architect. Your goal is to build a **Crypto Intelligence Platform** that analyzes tokenomics from whitepapers and provides deterministic risk scoring.

**CRUCIAL** Ensure .venv is activated before starting any work.

## 2. Project Structure

The project follows a monorepo structure with the following top-level directories:

- `api/`: Spring Boot (Java) backend
- `rag-service/`: Python RAG service (LangChain/FastAPI)
- `frontend/`: React frontend
- `k8s/`: Kubernetes deployment manifests
- `docs/`: Project documentation

## 3. Development Workflow

Before starting any work, always:
1. Read `docs/CURRENT_STATE.md` to understand what's already done
2. Read `docs/engineering_log.md` for recent changes and decisions
3. Update `docs/CURRENT_STATE.md` after completing work
4. Add an entry to `docs/engineering_log.md` summarizing what you did (detailed enough for an LLM agent or new engineer to understand what was done and continue building)

`docs/IMPLEMENTATION_PLAN.md` is the single source of truth for what needs to be built.

## 4. Key Principles

- **Deterministic Risk Scoring**: Risk rules must be implemented in Java (Spring Boot) as deterministic logic, not in the RAG service
- **YAML Configuration**: Risk rules should be configurable via YAML files, not hardcoded
- **Separation of Concerns**: RAG service handles text extraction and embedding; API handles business logic and risk scoring
- **Documentation**: Keep `docs/CURRENT_STATE.md` and `docs/engineering_log.md` updated after each work session

## 5. Mandatory Close Out 

After completing phases, you must update `docs/CURRENT_STATE.md` and `docs/engineering_log.md` to reflect the changes made. 