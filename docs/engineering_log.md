# 📝 Engineering Log — Crypto Intelligence Platform

> **Purpose:** Append-only development log. Each entry records what was done, key decisions, issues encountered, and next steps. Entries are ordered newest-first for quick scanning.
>
> **Convention:** Each entry follows the format below. Never delete or modify previous entries.

---

## Entry Format

```
### YYYY-MM-DD — [Title]

**Phase:** [Active phase]  
**Author:** [Name or "LLM Agent"]  
**Duration:** [Approx time spent]

#### What Was Done
- Bullet points of completed work

#### Key Decisions
- Important technical or architectural decisions and reasoning

#### Issues / Blockers
- Problems encountered and how they were resolved (or if still open)

#### Next Steps
- What to pick up next session
```

---

## Log Entries

---

### 2026-02-23 — Python Environment Hardening & Dependency Upgrade

**Phase:** Phase 3 (Python RAG Service)  
**Author:** LLM Agent  
**Duration:** 15 min

#### What Was Done
- Upgraded RAG service dependencies to modern versions:
    - **LangChain:** Upgraded from 0.1.x to **0.3.0**.
    - **FastAPI:** Upgraded to **0.115.0**.
    - **Pydantic:** Upgraded to **2.9.0**.
- Resolved legacy dependency conflicts (e.g., `pydantic_v1`) by moving to a unified v0.3 stack.
- Verified runtime health by import-testing the service within the root `.venv`.
- Confirmed that "Module Not Found" warnings in the IDE were due to missing subpackages (`langchain-text-splitters`) which are now installed.

#### Key Decisions
- **Absolute Imports:** Reverted to absolute `app.` imports for FastAPI controllers to ensure compatibility with standard `uvicorn` entry points and better service discovery.

#### next steps
- Proceed to **Phase 6: Frontend Development**.

---

### 2026-02-23 — Phase 5: Task 9 Completion - Integration Orchestration

**Phase:** Phase 5 (Integration)  
**Author:** LLM Agent  
**Duration:** 25 min

#### What Was Done
- Implemented `RagClient` using Spring WebFlux `WebClient` for non-blocking communication with the Python service.
- Created `AnalysisOrchestrator` to manage the lifecycle of a token analysis:
    - Receive file from API
    - Forward to RAG service for extraction
    - Persist results
    - Trigger `RiskScoringEngine` for final assessment.
- Updated `DocumentService` to trigger the orchestrator upon successful upload.
- Verified system compilation using local JDK 21 and Gradle 8.14.4 within the `.venv` shell.

#### Key Decisions
- **Manual Tooling Setup:** Installed JDK 21 and Gradle on the host to bypass Docker performance bottlenecks and Testcontainers networking issues on Windows.
- **Lazy Initialization:** Used `@Lazy` injection in `DocumentService` for the `AnalysisOrchestrator` to support circular dependencies if future expansion requires bidirectional lookups.

#### Issues / Blockers
- **IDE Syntax Warnings:** Acknowledged IDE import errors in Python; verified these do not impact runtime as packages are present in `.venv`.

#### Next Steps
- Begin **Phase 6**: Build the React-based Frontend.

---

### 2026-02-23 — Phase 4: Task 8 Completion - Deterministic Risk Scoring

**Phase:** Phase 4 (Risk Engine)  
**Author:** LLM Agent  
**Duration:** 20 min

#### What Was Done
- Implemented `RiskScoringEngine` in Java to aggregate rule-level scores.
- Created modular `RiskRule` interface and implemented `VestingRiskRule` and `InflationRiskRule`.
- Implemented `ScoringService` to handle `AnalysisResult` persistence and scoring orchestration.
- Verified all risk rules via JUnit 5 unit tests using local JDK 21 and Gradle wrapper.
- Ensured `.venv` was active during verification as per user request.

#### Key Decisions
- **Rule-based Architecture:** Used a composite pattern for risk rules to allow easy addition of new heuristics (e.g., liquidity, governance).
- **Local Verification:** Switched to local JDK/Gradle to overcome Testcontainers/Docker limitations on host, significantly speeding up the feedback loop.

#### Issues / Blockers
- None.

#### Next Steps
- Begin **Phase 5**: Implement Token Analysis Orchestration (Task 9) to link API and RAG.

---

### 2026-02-23 — Phase 3: Task 7 Completion - RAG Pipeline Implementation

**Phase:** Phase 3 (Python RAG Service)  
**Author:** LLM Agent  
**Duration:** 25 min

#### What Was Done
- Implemented modular RAG pipeline components:
    - `DocumentIngestor`: PDF/TXT text extraction via `pypdf`.
    - `DocumentChunker`: LangChain `RecursiveCharacterTextSplitter`.
    - `DocumentEmbedder`: OpenAI `text-embedding-3-small` with FAISS.
    - `DocumentRetriever`: Similarity search for relevant context.
    - `TokenomicsExtractor`: Structured output extraction via GPT-4o-mini.
    - `RagOrchestrator`: End-to-end pipeline wiring.
- Integrated pipeline into FastAPI `analyze` endpoint with temporary file handling.
- Verified local runtime readiness in `.venv`.

#### Key Decisions
- **Structured Output:** Leveraged LangChain's `with_structured_output` for deterministic JSON extraction.
- **Transient Vector Stores:** Opted for document-scoped transient FAISS indexes for the initial version to simplify state management.

#### Issues / Blockers
- **IDE Import Warnings:** The IDE reports missing imports for `langchain` and `fastapi`, but these are verified as installed and working in the `.venv` runtime.

#### Next Steps
- Begin **Phase 4**: Implement Risk Scoring Logic in Java (Task 8).

---

### 2026-02-23 — Phase 3: Task 6 Completion - Python RAG Service Initialization

**Phase:** Phase 3 (Python RAG Service)  
**Author:** LLM Agent  
**Duration:** 15 min

#### What Was Done
- Initialized FastAPI application structure in `rag-service/app/`.
- Defined `TokenomicsOutput` Pydantic schema for structured AI extraction.
- Implemented `main.py` with health checks and analysis stubs.
- Updated `requirements.txt` with specific versions for LangChain, FastAPI, and Pydantic.
- Updated `Dockerfile` for multi-stage Python builds with non-root support.
- Verified service locally in `.venv` (Health check OK, Analysis stub OK).

#### Key Decisions
- **Pydantic V2:** Standardized on Pydantic V2 for faster validation and better LangChain integration.
- **faiss-cpu versioning:** Relaxed `faiss-cpu` to `>=1.8.0` to ensure compatibility with the host architecture while maintaining performance.

#### Issues / Blockers
- None.

#### Next Steps
- Begin **Task 7**: Implement the document processing pipeline (ingestion, chunking, embedding, extraction).

---

### 2026-02-23 — Phase 2: Task 5 Completion - Unit Testing

**Phase:** Phase 2 (Spring Boot API)  
**Author:** LLM Agent  
**Duration:** 15 min

#### What Was Done
- Implemented `TokenServiceTest` utilizing JUnit 5 and Mockito to verify service layer logic.
- Created `TokenRepositoryIT` for integration testing with Testcontainers PostgreSQL.
- Verified unit tests successfully pass within a Dockerized Gradle environment.
- Completed all Phase 2 tasks from `LIST.md`.

#### Key Decisions
- **Unit Testing Priority:** Focused on unit testing for service-level verification to decouple from infrastructure dependencies during the initial build phase.

#### Issues / Blockers
- **Testcontainers in Docker:** Encountered expected challenges running nested Docker containers (Testcontainers) in a transient CI-like environment; pivoted to verifying unit tests to maintain progress.

#### Next Steps
- Begin **Phase 3**: Initialize Python RAG service (Task 6).

---

### 2026-02-23 — Phase 2: Task 4 Completion - REST API & Service Layer

**Phase:** Phase 2 (Spring Boot API)  
**Author:** LLM Agent  
**Duration:** 20 min

#### What Was Done
- Implemented the full REST API layer for token and document management.
- Created DTOs for structured request/response handling (`TokenCreateRequest`, `TokenResponse`, `DocumentResponse`).
- Implemented `TokenService`, `DocumentService`, and `AnalysisService` with business logic and transactional boundaries.
- Created `TokenController` exposing endpoints for token CRUD and document upload.
- Implemented `GlobalExceptionHandler` to provide standardized error responses (`ApiErrorResponse`).
- Verified compilation and build success via Docker.

#### Key Decisions
- **Standardized Error Handling:** Used `@ControllerAdvice` to ensure all API errors follow a predictable JSON format.
- **Service/Controller Separation:** Maintained strict separation of concerns, keeping business logic in services and request handling in controllers.

#### Issues / Blockers
- None.

#### Next Steps
- Begin **Task 5**: Implement unit and integration tests for the API.

---

### 2026-02-23 — Phase 2: Task 3 Completion - Spring Data JPA Repositories

**Phase:** Phase 2 (Spring Boot API)  
**Author:** LLM Agent  
**Duration:** 10 min

#### What Was Done
- Created Spring Data JPA repository interfaces in `com.cryptointel.api.repository`.
- Defined `TokenRepository`, `DocumentRepository`, `AnalysisResultRepository`, and `RiskScoreRepository`.
- Implemented custom query methods:
    - `TokenRepository`: `findBySymbol(String symbol)`
    - `DocumentRepository`: `findByTokenId(UUID tokenId)`
    - `AnalysisResultRepository`: `findByTokenIdOrderByCreatedAtDesc(UUID tokenId)`, `findByDocumentId(UUID documentId)`
    - `RiskScoreRepository`: `findByAnalysisResultId(UUID analysisId)`

#### Key Decisions
- **Standard JpaRepository:** Leveraged standard Spring Data JPA interfaces for basic CRUD operations to reduce boilerplate.
- **Query Method Naming:** Used Spring Data's naming convention for automatic query generation.

#### Issues / Blockers
- None.

#### Next Steps
- Begin **Task 4**: Implement REST endpoints, DTOs, and the service layer.

---

### 2026-02-23 — Phase 2: Task 2 Completion - JPA Entity Mapping

**Phase:** Phase 2 (Spring Boot API)  
**Author:** LLM Agent  
**Duration:** 15 min

#### What Was Done
- Created JPA entity classes in `com.cryptointel.api.entity` mapping to the `V1__Initial_Schema.sql` structure.
- Implemented `Token`, `Document`, `AnalysisResult`, and `RiskScore` entities.
- Defined relationships:
    - `Token` 1:N `Document`
    - `Token` 1:N `AnalysisResult`
    - `Document` 1:N `AnalysisResult`
    - `AnalysisResult` 1:N `RiskScore`
- Used Lombok `@Getter`, `@Setter`, `@Builder`, and Hibernate context annotations (`@CreationTimestamp`, `@UpdateTimestamp`).
- Mapped JSONB columns in PostgreSQL to `String` fields with `columnDefinition = "jsonb"`.

#### Key Decisions
- **Lazy Fetching:** Defaulted to lazy fetching for all `@ManyToOne` relationships to optimize performance and prevent over-fetching.
- **UUID Generation:** Utilized JPA's `GenerationType.UUID` to match the PostgreSQL primary key strategy.

#### Issues / Blockers
- None.

#### Next Steps
- Begin **Task 3**: Create Spring Data JPA repositories for the new entities.

---

### 2026-02-23 — Phase 2: Task 1 Completion - Spring Boot Initialization

**Phase:** Phase 2 (Spring Boot API)  
**Author:** LLM Agent  
**Duration:** 25 min

#### What Was Done
- Activated `.venv` for the session as mandated by `AGENT_INSTRUCTIONS.md`.
- Manually initialized Spring Boot project in `api/` after Docker-based `gradle init` encountered permission issues.
- Created `api/build.gradle` with core dependencies: Spring Web, Data JPA, Validation, Actuator, Flyway, PostgreSQL, Lombok, OpenAPI, and Resilience4j.
- Created `api/settings.gradle` and the main `CryptoIntelApplication.java` class.
- Configured `api/src/main/resources/application.yml` with PostgreSQL datasource, Flyway settings, and Actuator exposure.
- Updated `api/Dockerfile` to a multi-stage build (Gradle build -> JRE runtime) to ensure portability.
- Installed Gradle wrapper (`gradlew`) via Docker.

#### Key Decisions
- **Manual Skeleton Creation:** Opted for manual file creation instead of fighting `gradle init` Docker volume issues on Windows, ensuring a cleaner setup.
- **Gradle 8 + JDK 21:** Standardized on latest stable versions in Docker for builds.
- **Actuator & OpenAPI:** Included from day one to ensure observability and documentation are built-in.

#### Issues / Blockers
- **Docker Volume Permissions:** Encountered issues running `gradle init` directly in a Docker container on the host path; resolved by manually creating files and only using Docker for well-defined Gradle tasks (like `wrapper`).

#### Next Steps
- Begin **Task 2**: Create JPA entities mapping to the PostgreSQL schema.

---

### 2026-02-21 — Phase 1 Completion: Database & Schema Design

**Phase:** Phase 1 (Database)  
**Author:** LLM Agent  
**Duration:** 20 min

#### What Was Done
- Designed core relational schema for tokenomics analysis.
- Created Flyway migration script `api/src/main/resources/db/migration/V1__Initial_Schema.sql`.
- Created `docs/SCHEMA.md` with Mermaid ER diagram and table definitions.
- Created `scripts/seed_db.sql` with sample data for development.
- Enhanced `docker-compose.yml` with healthy-service dependencies.

#### Key Decisions
- **UUID as Primary Keys:** Used UUIDs for all entities to ensure scalability and ease of integration between services.
- **Flyway for Migrations:** Standardized on Flyway (v1 naming convention) for automated database evolution.
- **JSONB for LLM Data:** Used `JSONB` for `raw_llm_output` and `structured_output` to handle variable data structures from the RAG service.

#### Issues / Blockers
- **Docker Daemon Issue:** Resolved. User started Docker Desktop, and migrations were verified successfully.

#### Next Steps
- Begin **Phase 2**: Initialize Spring Boot API service.

---

### 2026-02-21 — Python Environment Hardening & Dependency Lockdown

**Phase:** Phase 0 (Hardening)  
**Author:** LLM Agent  
**Duration:** 15 min

#### What Was Done
- Purged previous `.venv` and verified no global/root `pip` installations.
- Re-initialized a fresh `.venv` in the project root.
- Created `rag-service/requirements.txt` with core dependencies (FastAPI, LangChain, Pydantic, etc.).
- Installed dependencies exclusively within `.venv` to ensure environmental isolation.

#### Key Decisions
- **Strict Dependency Discipline:** Enforced use of `.venv` for all agent operations to prevent environmental pollution.
- **Requirements Centralization:** Defined `rag-service/requirements.txt` to anchor the AI service's technical foundation.

#### Issues / Blockers
- Initial `pip install` was interrupted; successfully retried and completed.

#### Next Steps
- Finalize Phase 0 verification (Docker) or proceed to Phase 1 (Database).

---

### 2026-02-21 — Starting Phase 0: Scaffolding

**Phase:** Phase 0 (Scaffolding)  
**Author:** LLM Agent  
**Duration:** 5 min

#### What Was Done
- Read and internalized `AGENT_INSTRUCTIONS.md`.
- Updated `docs/CURRENT_STATE.md` to reflect Phase 0 progress and completion.
- Initialized project skeleton: created directories for `api`, `rag-service`, `frontend`, `k8s`, `scripts`, and `docs`.
- Updated `.gitignore` to fix case-sensitivity issues and add language-specific excludes.
- Created root `docker-compose.yml` with placeholder services.
- Created `Makefile` for developer tasks.
- Created `.env.example` and `.env` local file.
- Verified `.venv` exists and ensured all further Python operations use it.
- Attempted `docker-compose up` (verified Docker and Compose versions, but encountered daemon connection error).

#### Key Decisions
- Standardizing on lowercase `docs/` for all project documentation.
- Using `New-Item` in PowerShell for robust directory creation.
- Root `.venv` will be the primary environment for agent operations involving Python scripts.

#### Issues / Blockers
- **Docker Connection Error:** `open //./pipe/dockerDesktopLinuxEngine: The system cannot find the file specified.` This suggests Docker Desktop is either not running or having pipe issues. Needs user attention.

#### Next Steps
- Begin **Phase 1**: Database schema design and Flyway migrations.
- Resolve Docker connection issue to verify container orchestration.

---

### 2026-02-21 — Project Initialization & Planning

**Phase:** Pre-Phase 0 (Planning)  
**Author:** LLM Agent  
**Duration:** ~30 min

#### What Was Done
- Created `docs/IMPLEMENTATION_PLAN.md` — comprehensive 8-phase build plan covering scaffolding through production deployment
- Created `docs/CURRENT_STATE.md` — overwritable state tracker for engineer/LLM onboarding
- Created `docs/engineering_log.md` — this append-only development log
- Analyzed existing repository: fully greenfield (only `README.md` and `.gitignore` exist)

#### Key Decisions
- **Monorepo structure:** All services (`api/`, `rag-service/`, `frontend/`, `k8s/`) live in one repository for simpler development workflow
- **Phase ordering:** Database (Phase 1) before API (Phase 2) so JPA entities map to real tables. RAG service (Phase 3) can be built in parallel with Phase 2
- **Risk engine placement:** Recommended Java/Spring Boot (Phase 4) to keep deterministic business logic separate from AI inference
- **Risk rules as YAML config:** Enables tuning scoring rules without code changes
- **docs/ vs Docs/:** The `.gitignore` excludes `Docs` (capital D). Using lowercase `docs/` means these files *will* be tracked by git. If this is unintentional, update `.gitignore` accordingly

#### Issues / Blockers
- **`.gitignore` has `Docs` (capital D) listed** — the user created `docs/` (lowercase). Unclear if this was intentional. Need to clarify whether planning docs should be git-tracked or not.
- **No LLM API key strategy decided yet** — will need to determine if using OpenAI, Anthropic, or local models before Phase 3

#### Next Steps
- Begin **Phase 0**: Create directory skeleton, `docker-compose.yml`, `.env.example`, `Makefile`
- Decide on Java build tool: Gradle (recommended) vs Maven
- Decide on vector store: FAISS (simpler, file-backed) vs ChromaDB (more features, needs its own service)

---

<!-- 
  TEMPLATE FOR NEW ENTRIES — copy below this line:

### YYYY-MM-DD — [Title]

**Phase:** [Active phase]  
**Author:** [Name or "LLM Agent"]  
**Duration:** [Approx time spent]

#### What Was Done
- 

#### Key Decisions
- 

#### Issues / Blockers
- 

#### Next Steps
- 

-->
