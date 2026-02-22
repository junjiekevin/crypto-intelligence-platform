
# 🚀 Crypto Intelligence Platform

A distributed AI-powered crypto tokenomics analysis platform built with **Spring Boot**, **Python (LangChain)**, **PostgreSQL**, **Docker**, and **Kubernetes**.

---

## 🧠 Overview

Crypto projects publish whitepapers and tokenomics documents that are dense, unstructured, and difficult to analyze systematically.

This platform converts unstructured crypto documentation into:

- Structured token intelligence
- Deterministic risk scores
- Comparable tokenomics metrics
- Persistent, queryable analysis results

Unlike basic LLM chat systems, this project separates AI inference from business logic using a distributed microservices architecture deployed on Kubernetes.

---

## 🎯 Key Objectives

This project was designed to:

- Demonstrate real-world RAG architecture
- Implement structured LLM output validation
- Combine AI inference with deterministic financial modeling
- Build a polyglot microservice system (Java + Python)
- Deploy containerized services on Kubernetes
- Showcase scalable AI system design

---

## 🏗 System Architecture

```
                     ┌─────────────────────┐
                     │     Frontend        │
                     └──────────┬──────────┘
                                │
                                ▼
                  ┌─────────────────────────┐
                  │  Spring Boot API Layer  │
                  │  (Business Logic)       │
                  └──────────┬──────────────┘
                             │ REST
                             ▼
                ┌───────────────────────────┐
                │ Python RAG Microservice   │
                │ (LangChain + Embeddings)  │
                └──────────┬────────────────┘
                           │
                           ▼
              ┌──────────────────────────────┐
              │ Vector Store (FAISS/Chroma)  │
              └──────────────────────────────┘
                           │
                           ▼
                    LLM Provider
                           │
                           ▼
                 PostgreSQL (Metadata)
```

---

## 🧩 Core Components

### 1️⃣ Spring Boot API (Java)

**Responsibilities:**

- Document upload endpoint  
- Token metadata management  
- Persistent storage of structured results  
- Risk score persistence  
- Multi-token comparison API  
- Communication with AI microservice via REST  

**Technologies:**

- Spring Boot  
- Spring Web  
- Spring Data JPA  
- PostgreSQL  

---

### 2️⃣ Python AI Service (RAG Engine)

**Responsibilities:**

- Document ingestion  
- Text chunking  
- Embedding generation  
- Vector storage  
- Retrieval pipeline  
- Structured tokenomics extraction  
- Risk signal identification  

**Technologies:**

- Python  
- LangChain  
- FAISS / Chroma  
- Pydantic (structured schema validation)  

---

### 3️⃣ Risk Scoring Engine

Hybrid logic layer that:

- Applies deterministic financial risk rules  
- Combines LLM-extracted tokenomics with rule-based scoring  
- Produces normalized risk score  

**Example logic:**

- Inflation > 10% → +2 risk  
- Major unlock within 6 months → +3 risk  
- No governance transparency → +1 risk  
- Unlimited supply → +3 risk  

This ensures AI outputs are grounded in deterministic evaluation.

---

## 📊 Structured Output Schema

The AI service extracts structured token intelligence:

```json
{
  "token_name": "",
  "total_supply": "",
  "inflation_rate": "",
  "vesting_schedule_summary": "",
  "staking_rewards": "",
  "utility_description": "",
  "governance_model": "",
  "deflationary": false,
  "identified_risks": [],
  "computed_risk_score": 0
}
```

Using strict schema validation ensures:

- Deterministic storage  
- Reliable comparisons  
- Reduced hallucination risk  

---

## ☸ Kubernetes Deployment

Each component runs as an independent containerized service.

### Deployed Services:

- spring-api  
- rag-service  
- postgres  
- (Optional) frontend  

### Kubernetes Resources Used:

- Deployments  
- Services  
- PersistentVolumeClaims  
- ConfigMaps  
- Secrets  
- Horizontal Pod Autoscaler (for AI service)  

### Why Kubernetes?

- AI inference is stateless and horizontally scalable  
- Business logic and inference are isolated  
- Resource limits prevent runaway LLM costs  
- Production-style orchestration  

---

## 🐳 Containerization

Each microservice includes:

- Independent Dockerfile  
- Optimized build stages  
- Environment variable configuration  
- Health check endpoints  

Local development is supported via Docker Compose.

---

## 🔍 Example Workflow

1. Upload token whitepaper via Spring Boot API.  
2. API forwards document to Python RAG service.  
3. RAG service:
   - Chunks document  
   - Generates embeddings  
   - Stores vectors  
   - Extracts structured tokenomics  
4. Risk engine computes deterministic risk score.  
5. Structured output returned to Spring Boot.  
6. Results persisted in PostgreSQL.  
7. User queries token comparison endpoint.  

---

## 🧠 Why This Is Not Just a Chatbot

This system:

- Separates inference from business logic  
- Enforces structured LLM output  
- Applies deterministic financial rules  
- Persists analysis results  
- Supports cross-token comparisons  
- Is horizontally scalable  

This mirrors real-world AI SaaS architectures.

---

## 🚀 Future Enhancements

- Live market data integration (CoinGecko API)  
- Fully diluted valuation calculations  
- Historical unlock tracking  
- Redis caching layer  
- Async task queue (e.g., Kafka or RabbitMQ)  
- Authentication & user roles  
- CI/CD pipeline  

---

## 🛠 Local Setup

### 1. Clone repository

```bash
git clone https://github.com/junjiekevin/crypto-intelligence-platform.git
cd crypto-intelligence-platform
```

### 2. Start services locally

```bash
docker-compose up --build
```

### 3. Access API

Spring Boot API:

```
http://localhost:8080
```

Python RAG service:

```
http://localhost:8000
```

---

## 📦 Kubernetes Deployment

```bash
kubectl apply -f k8s/
```

Verify pods:

```bash
kubectl get pods
```

Scale AI service:

```bash
kubectl scale deployment rag-service --replicas=3
```

---

## 🧠 Technical Highlights

- Retrieval-Augmented Generation (RAG)  
- Structured LLM output enforcement  
- Hybrid AI + deterministic rule modeling  
- Microservices architecture  
- Polyglot stack (Java + Python)  
- Containerization & orchestration  
- Scalable inference service design  

---
