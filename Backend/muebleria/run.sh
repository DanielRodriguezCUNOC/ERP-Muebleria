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
if [ -f "$HOME/.muebleria_jwt_secret" ]; then
    export JWT_SECRET="$(cat "$HOME/.muebleria_jwt_secret")"
elif [ -f "$(dirname "$0")/../../../muebleria_jwt_secret" ]; then
    export JWT_SECRET="$(cat "$(dirname "$0")/../../../muebleria_jwt_secret")"
elif [ -f "./muebleria_jwt_secret" ]; then
    export JWT_SECRET="$(cat ./muebleria_jwt_secret)"
else
    export JWT_SECRET="muebleria_erp_super_secret_jwt_key_2026_default_fallback"
fi
export JWT_EXPIRATION="86400000"

# ==========================================
# Levantar la app
# ==========================================
set -e

cd "$(dirname "$0")"

echo "→ Limpiando target/ y compilando..."
./mvnw clean compile -DskipTests

echo "→ Levantando Spring Boot..."
./mvnw spring-boot:run -DskipTests