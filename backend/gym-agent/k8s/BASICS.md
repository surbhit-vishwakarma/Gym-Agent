# Basic Kubernetes Setup - Understand Step by Step

## 3 Files You Have:

### 1. **gym-agent-configmap.yaml**
Stores application configuration and secrets

```
ConfigMap: Non-sensitive settings (can be seen)
├─ DB_HOST: postgres          (where database is)
├─ DB_PORT: 5432             (database port)
├─ DB_NAME: postgres         (database name)
├─ OLLAMA_HOST: http://ollama:11434  (where LLM is)
└─ etc...

Secret: Sensitive data (encrypted)
├─ DB_PASSWORD: admin        (encrypted)
└─ JWT_SECRET: ...           (encrypted)
```

**How it works:**
- ConfigMap & Secret values get injected into pods as environment variables
- Pod reads these variables and connects to database & ollama

---

### 2. **gym-agent-deployment.yaml**
Tells Kubernetes how to run your app

```
Deployment
├─ replicas: 2              (run 2 copies of your app)
│
└─ Pod Template
   ├─ image: surbhit/gym-agent:latest  (your docker image)
   ├─ port: 8080                       (which port app uses)
   │
   └─ envFrom:                         (get config from)
      ├─ configMapRef: gym-agent-config
      └─ secretRef: gym-agent-secrets
```

**How it works:**
- Kubernetes creates 2 pods from your docker image
- Each pod gets environment variables from ConfigMap/Secret
- Each pod connects to postgres and ollama using those variables

---

### 3. **gym-agent-service.yaml**
Routes traffic to your running pods

```
Service 1: gym-agent (ClusterIP)
├─ Type: ClusterIP (internal only)
├─ Port: 8080
└─ Connects to: Pods with label "app: gym-agent"

Service 2: gym-agent-lb (LoadBalancer)
├─ Type: LoadBalancer (external access)
├─ Port: 8080
└─ Routes to: gym-agent service
```

**How it works:**
- ClusterIP gives internal DNS name "gym-agent" to pods
- LoadBalancer exposes to localhost:8080 for your laptop

---

## 📊 How They Connect

```
ConfigMap & Secret
      ↓
   (injected as env vars)
      ↓
Deployment (2 pods)
      ↓
   Service: gym-agent
   Service: gym-agent-lb
      ↓
Your Laptop (localhost:8080)
```

---

## 🎯 What Happens When You Deploy

```bash
kubectl apply -f .
```

This creates:
1. ConfigMap (app settings)
2. Secret (passwords)
3. Deployment (2 running pods)
4. 2 Services (network routing)

---

## 🚀 Next Steps

Tell me:
1. **PostgreSQL deployment & service YAML** - I'll add to understand the connection
2. **Ollama deployment & service YAML** - I'll add to understand the connection

Then we'll see how gym-agent connects to both.

---

## Variables Explained

**In ConfigMap:**
- `DB_HOST: postgres` → This is the PostgreSQL service name
- `OLLAMA_HOST: http://ollama:11434` → This is the Ollama service name

**When pod starts:**
- Kubernetes DNS converts "postgres" → IP address (e.g., 10.96.0.5)
- Kubernetes DNS converts "ollama" → IP address (e.g., 10.96.1.10)
- Your app connects to those IPs

---

## ✅ Ready for next step

Please share:
- PostgreSQL Deployment YAML
- PostgreSQL Service YAML
- Ollama Deployment YAML
- Ollama Service YAML

I'll help you understand how all 4 services connect to each other.

