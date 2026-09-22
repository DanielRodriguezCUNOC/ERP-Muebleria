#!/usr/bin/env bash

# ==========================================
# Variables de base de datos
# ==========================================
export DB_URL="jdbc:postgresql://localhost:5432/muebleria_erp"
export DB_USER="paboomi"
export DB_PASSWORD="P@boomi79"

# ==========================================
# Variables de JWT
# ==========================================
export JWT_SECRET="$(cat ~/.muebleria_jwt_secret)"
export JWT_EXPIRATION="86400000"

# ==========================================
# Levantar la app
# ==========================================
set -e

cd "$(dirname "$0")"

echo "→ Limpiando target/ y compilando..."
mvn clean compile -DskipTests

echo "→ Levantando Spring Boot..."
mvn spring-boot:run -DskipTests