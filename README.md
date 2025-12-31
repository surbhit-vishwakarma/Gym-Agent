# 🏋️ AI Workout Planner (Agentic Backend – Java)

An **agentic AI workout planning backend** built using **Spring Boot**, **Embabel**, and **LangChain4j**, with **PostgreSQL + pgvector** for **Retrieval-Augmented Generation (RAG)**.

The system generates **personalized and safe workout plans** by combining:
- Structured user & gym data (relational database)
- Domain-specific fitness knowledge (RAG)
- Controlled LLM reasoning

---

## 🤔 Why Agentic AI?

A standalone LLM can generate generic workout plans, but it lacks **persistent context** and awareness of real-world constraints.

This system uses an **agentic AI approach** to provide:
- Persistent awareness of:
  - User gym association
  - Available machines
  - Historical fitness data
- Integration with RAG for knowledge-grounded exercise selection
- Reduced user effort (no repeated inputs)
- Goal-oriented behavior focused specifically on workout planning

By combining **user history**, **gym-specific constraints**, and **structured knowledge retrieval**, the agent generates **feasible and evolving workout plans** rather than one-off generic responses.

---

## ✨ Key Features

- Role-based authentication (**Gym Owner / Gym Freak**)
- Backend-driven role-based routing
- Agentic AI for personalized workout planning
- Gym-aware exercise selection based on available machines
- Retrieval-Augmented Generation (RAG) using **pgvector**
- Clean separation of business logic and AI reasoning

---

## 🧭 Application Flow (v1 – Happy Path)

This section describes the **initial user journey**, focusing on the **happy-path scenarios implemented in v1**.

---

## 🔐 Authentication

### Registration
- Users register by selecting a role:
  - **Gym Owner**
  - **Gym Freak (Normal User)**
- On successful registration, users are redirected to the landing page.
- Validation failures and advanced error handling are planned for **v2**.

### Login
- After login, users are redirected based on their role:
  - **Gym Owner** → Gym Owner dashboard
  - **Gym Freak** → Gym Freak user flow
- Role-based routing is handled **entirely by the backend** to keep frontend logic minimal and secure.

---

## 🏋️ Role-Based Landing Pages

### Gym Owner
- On login, the backend checks whether the gym owner already has a gym associated.
- If a gym exists, the owner can:
  - View the number of users associated with their gym
  - Add or manage gym machines
- In **v1**, a gym owner is limited to managing their own gym only.

### Gym Freak (Normal User)
- On login, the backend checks whether the user is associated with a gym.

**If not associated**
- The user is prompted to select a gym from the list of available gyms.

**If already associated**
- The user is redirected directly to the **AI Workout Planner** page.

Once associated with a gym, the user can run an agent to generate a **personalized, curated workout plan**.

---

## 🤖 AI Workout Planner

The AI Workout Planner is an **agentic backend system** built using:

- Spring Boot
- Embabel
- LangChain4j
- PostgreSQL + pgvector (RAG)

### What the system combines
- **Structured user & gym data** from the relational database
- **Exercise knowledge segments** retrieved via RAG
- **Controlled LLM reasoning** for workout plan generation

---

## 🧠 Architecture Overview

Controller
->
Orchestrator
->
Embabel Agent
->
LangChain4j
->
RAG (pgvector)
->
LLM


### Responsibilities
- Controllers expose stable APIs
- Orchestrators select the appropriate agent
- Agents coordinate data retrieval and AI invocation
- LLMs are used strictly for workout plan generation

---

## ✨ Key Concepts

### Embabel
- Controls deterministic agent workflows
- Keeps business logic and decision-making in Java

### LangChain4j
- Enables structured LLM interaction
- Uses system-level prompts and typed outputs

### RAG (pgvector)
- Stores embedded exercise knowledge segments
- Grounds AI responses in real fitness rules and safety guidelines

### PostgreSQL
- Source of truth for users, gyms, machines, and calorie history

---

## 📚 RAG Usage

Retrieval-Augmented Generation (RAG) is applied **selectively**, for example:
- Beginner users
- Safety-sensitive exercise selection
- Training principle enforcement

This ensures:
- Consistent workout rules
- Safer recommendations
- Reduced hallucinations

> User data and transactional information are **never stored in the vector database**.

---


## 📌 Tech Stack

- **Backend**: Java, Spring Boot
- **AI / Agents**: Embabel, LangChain4j
- **Database**: PostgreSQL
- **Vector DB**: pgvector
- **Authentication**: Role-based (backend enforced)

---
