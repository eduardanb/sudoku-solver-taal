// Implementação da técnica de Programação Dinâmica

package br.edu.sudoku.solver.dynamicprogramming;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;

public class DynamicProgrammingSolver implements DynamicProgrammingAlgorithm {

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        return dynamicSolve(board, metrics);
    }

    @Override
    public boolean dynamicSolve(SudokuBoard board, Metrics metrics) {

        throw new UnsupportedOperationException("Dynamic Programming ainda não implementado.");

    }
}