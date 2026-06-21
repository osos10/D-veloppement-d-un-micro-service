# 🏦 Bank Account Service — Microservice Spring Boot

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-6DB33F?logo=springboot&logoColor=white)
![GraphQL](https://img.shields.io/badge/GraphQL-E10098?logo=graphql&logoColor=white)
![H2](https://img.shields.io/badge/H2-in--memory-blue)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

Microservice de gestion de comptes bancaires exposé via **trois interfaces** : **API REST**,
**API GraphQL** et **Spring Data REST**, avec documentation **OpenAPI/Swagger**. Persistance
sur **H2 en mémoire** (aucune configuration de base requise).

---

## 🧩 Modèle

| Entité | Description |
|--------|-------------|
| `BankAccount` | Compte (id, date, solde, devise, `AccountType`) lié à un `Customer` |
| `Customer` | Client possédant plusieurs comptes |
| `AccountType` | Enum : type de compte |

Architecture en couches : `web` (REST + GraphQL) → `service` → `repositories` → `entities`,
avec **DTO** (`BankAccountRequestDTO`/`ResponseDTO`) et **mapper**.

---

## 🔌 Interfaces

### API REST (`/api`)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/bankAccounts` | Liste des comptes |
| `GET` | `/api/bankAccounts/{id}` | Compte par id |
| `POST` | `/api/bankAccounts` | Création (corps `BankAccountRequestDTO`) |
| `PUT` | `/api/bankAccounts/{id}` | Mise à jour |
| `DELETE` | `/api/bankAccounts/{id}` | Suppression |

### GraphQL
Requêtes : `accountsList`, `bankAccountById(id)`, `customersList` — Mutations :
`addAccount`, `updateAccount`, `deleteAccount`.

---

## 🚀 Démarrage

```bash
./mvnw spring-boot:run          # Windows : mvnw.cmd spring-boot:run
```

| Interface | URL |
|-----------|-----|
| Swagger UI | <http://localhost:8081/swagger-ui.html> |
| GraphiQL | <http://localhost:8081/graphiql> |
| Console H2 | <http://localhost:8081/h2-console> |

Tests : `./mvnw test`.

---

## 🛠️ Corrections & améliorations apportées

- 🐛 **NullPointerException** dans `AccountRestController` : le `POST /api/bankAccounts`
  appelait `accountService` qui n'était jamais injecté (le constructeur n'injectait que le
  repository). Ajout de l'injection de `AccountService` et suppression d'un champ
  `AccountMapper` inutilisé (+ imports morts).
- 🗜️ Suppression de l'archive `bank-account-service.zip` (dump redondant du projet avec
  fichiers `.idea`).
- 🧹 Aplatissement de l'arborescence (`bank-account-service/bank-account-service/` → racine),
  ajout de `.gitignore`, `LICENSE`, README, et arrêt du suivi de `target/`.

---

## 👤 Auteur

**EL HAKKI Ossama** — Master SDIA, ENSET.

## 📄 Licence

Distribué sous licence **MIT**. Voir [`LICENSE`](LICENSE).
