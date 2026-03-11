/**
 * Implementação de uma abordagem baseada em Programação Dinâmica
 * para resolver o "Sudoku".
 *
 * A técnica busca reutilizar soluções parciais previamente
 * computadas, evitando recomputações desnecessárias durante
 * a exploração do espaço de busca.
 */

package br.edu.sudoku.solver;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;

public class DynamicProgrammingSolver implements SudokuSolver {

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        System.out.println("Dynamic Programming Solver ainda não implementado.");
        return false;
    }
}