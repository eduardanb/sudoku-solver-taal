/**
 * Classe responsável por armazenar métricas de desempenho dos algoritmos.
 *
 * As métricas são utilizadas para análise experimental e comparação
 * entre diferentes estratégias de resolução do Sudoku.
 *
 * Exemplos de métricas coletadas:
 * - tempo de execução
 * - número de nós visitados
 * - uso de memória
 * - número de podas no espaço de busca.
 */

package br.edu.sudoku.metrics;

public class Metrics {

    private long visitedNodes = 0;

    public void incrementVisitedNodes() {
        visitedNodes++;
    }

    public long getVisitedNodes() {
        return visitedNodes;
    }
}