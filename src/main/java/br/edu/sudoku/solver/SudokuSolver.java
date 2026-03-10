/**
 * Interface que define o comportamento comum para todos os
 * algoritmos de resolução do Sudoku.
 *
 * Cada estratégia de solução (Backtracking, Branch and Bound,
 * Programação Dinâmica e Gulosa) deve implementar esta interface.
 *
 * Isso permite executar e comparar diferentes algoritmos de forma
 * padronizada dentro do sistema.
 */

package br.edu.sudoku.solver;

import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.metrics.Metrics;

public interface SudokuSolver {

    boolean solve(SudokuBoard board, Metrics metrics);

}