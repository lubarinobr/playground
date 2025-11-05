#!/bin/bash

BASE_URL="http://localhost:8080"

echo "Executando busca de documentos a cada 1 segundo..."
echo "Pressione Ctrl+C para parar"
echo ""

while true; do
    timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    echo "[$timestamp] Buscando documentos..."
    
    response=$(curl -s -w "\nHTTP_CODE:%{http_code}" -X GET "${BASE_URL}/documentos")
    http_code=$(echo "$response" | grep "HTTP_CODE" | cut -d':' -f2)
    body=$(echo "$response" | sed '/HTTP_CODE/d')
    
    if [ "$http_code" = "200" ]; then
        echo "✓ Sucesso: $body"
    else
        echo "✗ Erro HTTP $http_code: $body"
    fi
    
    echo ""
    #sleep 1
done


