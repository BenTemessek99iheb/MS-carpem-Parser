# Carpem Parser Service

## 🧠 Overview

The **Carpem Parser Service** is a Spring Boot application designed to process, validate, and persist data from both **HL7** and **Excel** files into the **Carpem mysql database**. It integrates seamlessly with a **front-end Angular application** to provide real-time feedback, monitoring, and control over the file ingestion process.

---

## 📂 Features

- ✅ **HL7 File Parsing**  
  Automatically extracts patient and clinical data from standard HL7 messages.

- ✅ **Excel File Parsing**  
  Supports structured Excel files with dynamic headers and data validation.

- 🔍 **File Validation**  
  Ensures files meet structural and content-based integrity before persisting.

- 🧾 **Data Persistence**  
  Persists parsed and validated data into a mysql database.

- 🌐 **Angular Integration**  
  Provides REST endpoints consumed by a modern Angular front-end UI.

---

## 📁 Input Formats

### 1. HL7 Files (`.hl7`, `.txt`)
- Standard HL7 v2.x message structure.
- Patient demographics, lab results, and encounter info are extracted.

### 2. Excel Files (`.xls`, `.xlsx`)
- Supported Excel sheets should contain structured columns.
- Can include health-related metadata, device measurements, etc.

---

## 🧰 Tech Stack

| Layer          | Technology                         |
|----------------|------------------------------------|
| Backend        | Java 17, Spring Boot 3.4.4         |
| File Parsing   | Apache POI (Excel), HAPI HL7 API   |
| Database       | mysql                         |
| Frontend       | Angular (ngx-admin)                |
| API Docs       | springdoc-openapi (Swagger UI)     |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- mysql
- Node.js & Angular CLI (for frontend)

### Backend Setup

```bash
git clone https://github.com/BenTemessek99iheb/MS-carpem-Parser.git
cd MS-carpem-Parser
./mvnw clean install
./mvnw spring-boot:run
