// Implementação de uma estratégia gulosa

package br.edu.sudoku.solver.greedy;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;

public class GreedySolver implements GreedyAlgorithm {

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        return greedySolve(board, metrics);
    }

    @Override
    public boolean greedySolve(SudokuBoard board, Metrics metrics) {

        throw new UnsupportedOperationException("Greedy ainda não implementado.");

    }
}