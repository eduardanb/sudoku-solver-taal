/**
 * Implementação de uma estratégia gulosa para resolver o "Sudoku".
 *
 * O algoritmo seleciona localmente a melhor escolha em cada etapa,
 * tentando preencher células com valores que pareçam mais promissores.
 *
 * Embora seja mais rápido, essa abordagem não garante sempre
 * encontrar a solução ótima.
 */

package br.edu.sudoku.solver;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;

public class GreedySolver implements SudokuSolver {

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        System.out.println("Greedy ainda não implementado.");
        return false;
    }
}