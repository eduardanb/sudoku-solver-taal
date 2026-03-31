// Implementação da técnica de Programação Dinâmica

package br.edu.sudoku.solver.dynamicprogramming;

import java.util.HashSet;
import java.util.Set;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

public class DynamicProgrammingSolver implements DynamicProgrammingAlgorithm {

    private final Set<String> deadStates = new HashSet<>();

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        return dynamicSolve(board, metrics);
    }

    @Override
    public boolean dynamicSolve(SudokuBoard board, Metrics metrics) {
        deadStates.clear();
        return solveWithMemo(board, metrics);
    }

    private boolean solveWithMemo(SudokuBoard board, Metrics metrics) {

        metrics.incrementVisitedNodes();

        String key = serialize(board);
        if (deadStates.contains(key)) {
            metrics.incrementBacktracks();
            return false;
        }

        int[] cell = selectMostConstrainedCell(board);
        if (cell == null) {
            return true;
        }

        int row = cell[0];
        int col = cell[1];

        for (int value = 1; value <= 9; value++) {
            if (!SudokuValidator.isValid(board, row, col, value)) {
                continue;
            }

            board.set(row, col, value);
            if (solveWithMemo(board, metrics)) {
                return true;
            }
            board.set(row, col, 0);
            metrics.incrementBacktracks();
        }

        deadStates.add(key);
        return false;
    }

    private int[] selectMostConstrainedCell(SudokuBoard board) {

        int bestRow = -1;
        int bestCol = -1;
        int bestDomainSize = Integer.MAX_VALUE;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board.get(row, col) != 0) {
                    continue;
                }

                int domainSize = countCandidates(board, row, col);
                if (domainSize < bestDomainSize) {
                    bestDomainSize = domainSize;
                    bestRow = row;
                    bestCol = col;

                    if (bestDomainSize == 1) {
                        return new int[]{bestRow, bestCol};
                    }
                }
            }
        }

        if (bestRow == -1) {
            return null;
        }

        return new int[]{bestRow, bestCol};
    }

    private int countCandidates(SudokuBoard board, int row, int col) {
        int count = 0;
        for (int value = 1; value <= 9; value++) {
            if (SudokuValidator.isValid(board, row, col, value)) {
                count++;
            }
        }
        return count;
    }

    private String serialize(SudokuBoard board) {
        StringBuilder builder = new StringBuilder(81);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                builder.append((char) ('0' + board.get(row, col)));
            }
        }
        return builder.toString();
    }
}