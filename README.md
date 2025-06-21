# Java JDBC + MySQL with Docker

This project demonstrates how to build and run a **Java application using Maven**, which connects to a **MySQL database** using **JDBC**, all containerized with Docker.

---



## ⚙️ Technologies Used

- Java 17
- Maven
- MySQL 8
- JDBC
- Docker

---

## 🚀 How to Run

### 1️⃣ Create a Docker Network

```bash
docker network create java-net

docker run -d \
  --name mysql-db \
  --network java-net \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=mydb \
  -p 3306:3306 \
  mysql:8.0
```

## 2️⃣  Build Java App Image
```bash
cd app/
docker build -t java-app:1.0 .
```

## 3️⃣ Run Java App
```bash
docker run --rm \
  --name java-app \
  --network java-net \
  java-app:1.0
```