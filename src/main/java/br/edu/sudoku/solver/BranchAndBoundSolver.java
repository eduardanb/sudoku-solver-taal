/**
 * Implementação da técnica Branch and Bound para resolver o Sudoku.
 *
 * O algoritmo explora o espaço de busca de forma semelhante ao
 * Backtracking, porém aplica podas (bounds) para eliminar ramos
 * que não podem levar a uma solução válida.
 *
 * Isso reduz o número de estados explorados e melhora o desempenho.
 */

package br.edu.sudoku.solver;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;

public class BranchAndBoundSolver implements SudokuSolver {

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        System.out.println("Branch And Bound Solver ainda não implementado.");
        return false;
    }
}