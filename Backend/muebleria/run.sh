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
mvn spring-boot:run -DskipTest