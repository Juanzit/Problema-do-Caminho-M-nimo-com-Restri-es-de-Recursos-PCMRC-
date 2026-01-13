# Problema de otimização em Redes: Caminho Mínimo com Restrição de Recursos (PCMRC)

**Instituto Federal de Educação, Ciência e Tecnologia do Sudeste de Minas Gerais - Campus Rio Pomba** **Bacharelado em Ciência da Computação**

Este projeto implementa uma meta-heurística baseada em **Simulated Annealing (Têmpera Simulada)** para resolver o Problema do Caminho Mínimo com Restrição de Recursos (PCMRC). O objetivo é encontrar o caminho de menor custo em um grafo, respeitando um limite máximo de consumo de recursos.

## 👥 Integrantes

* Emiliano Pessata Pereira
* João Vitor Ruza Cavalare
* Juan Silva Garcia
* Leonardo da Mota Melo

## 🚀 Funcionalidades

* **Geração Automática de Instâncias:** O código gera datasets de teste (pequeno, médio, grande e muito grande) se eles não existirem.
* **Algoritmo Simulated Annealing:** Implementação com estratégia de resfriamento geométrico.
* **Operador de Vizinhança:** Utiliza a técnica de *Cut-and-Reconnect* (Corte e Reconexão) para escapar de ótimos locais.
* **Relatório Automatizado:** Gera um arquivo `RELATORIO_FINAL.txt` com as métricas de execução.

## 🛠️ Pré-requisitos

* **Java JDK 11** ou superior.

## 💻 Como Compilar e Rodar

O projeto consiste em um único arquivo fonte principal que gerencia tanto a geração dos dados quanto a execução da meta-heurística.

1. **Abra o terminal** na pasta onde o arquivo `Main.java` está salvo.
2. **Compile o código:**
   ```bash
   javac Main.java
   ```
