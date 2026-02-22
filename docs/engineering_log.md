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
