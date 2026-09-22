#!/bin/bash

find . \
  -not -path './.git*' \
  -not -path './target*' \
  -not -path './out*' \
  | sort \
  | sed 's|^\./||' > arbol.txt

echo "Árbol generado en: arbol.txt"