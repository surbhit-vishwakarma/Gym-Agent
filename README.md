🏋️ AI Workout Planner (Agentic Backend – Java)

An agentic AI workout planning backend built with Spring Boot, Embabel, and LangChain4j, using PostgreSQL + pgvector for Retrieval-Augmented Generation (RAG).

The system generates personalized and safe workout plans by combining:

structured user & gym data (RDBMS)

domain fitness knowledge (RAG)

controlled LLM reasoning

🧠 Architecture
Controller → Orchestrator → Embabel Agent
           → LangChain4j → RAG (pgvector) → LLM

✨ Key Concepts

Embabel: deterministic agent workflow & decisions

LangChain4j: structured LLM interaction

RAG (pgvector): grounded exercise knowledge

PostgreSQL: source of truth for user & gym data

📚 RAG Usage

RAG is applied selectively (e.g., beginner users) to enforce
consistent training rules and safety guidelines.
