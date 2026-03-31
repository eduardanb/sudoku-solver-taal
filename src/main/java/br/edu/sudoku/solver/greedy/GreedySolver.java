// Implementação de uma estratégia gulosa

package br.edu.sudoku.solver.greedy;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

public class GreedySolver implements GreedyAlgorithm {

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        return greedySolve(board, metrics);
    }

    @Override
    public boolean greedySolve(SudokuBoard board, Metrics metrics) {
        return greedySearch(board, metrics);
    }

    private boolean greedySearch(SudokuBoard board, Metrics metrics) {

        metrics.incrementVisitedNodes();

        int[] cell = selectMostConstrainedCell(board);
        if (cell == null) {
            return true;
        }

        int row = cell[0];
        int col = cell[1];
        int[] orderedCandidates = orderCandidatesByConstraint(board, row, col);
        int candidateCount = orderedCandidates[0];

        if (candidateCount == 0) {
            metrics.incrementBacktracks();
            return false;
        }

        for (int i = 1; i <= candidateCount; i++) {
            int candidate = orderedCandidates[i];
            board.set(row, col, candidate);

            if (greedySearch(board, metrics)) {
                return true;
            }

            board.set(row, col, 0);
            metrics.incrementBacktracks();
        }

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

    private int[] orderCandidatesByConstraint(SudokuBoard board, int row, int col) {

        int[] values = new int[9];
        int[] costs = new int[9];
        int size = 0;

        for (int value = 1; value <= 9; value++) {
            if (!SudokuValidator.isValid(board, row, col, value)) {
                continue;
            }

            board.set(row, col, value);
            values[size] = value;
            costs[size] = estimateConstraintCost(board, row, col);
            board.set(row, col, 0);
            size++;
        }

        sortByCost(values, costs, size);

        int[] result = new int[10];
        result[0] = size;
        for (int i = 0; i < size; i++) {
            result[i + 1] = values[i];
        }
        return result;
    }

    private int estimateConstraintCost(SudokuBoard board, int row, int col) {

        int cost = 0;
        for (int c = 0; c < 9; c++) {
            if (board.get(row, c) == 0) {
                cost += countCandidates(board, row, c);
            }
        }

        for (int r = 0; r < 9; r++) {
            if (board.get(r, col) == 0) {
                cost += countCandidates(board, r, col);
            }
        }

        return cost;
    }

    private void sortByCost(int[] values, int[] costs, int size) {
        for (int i = 0; i < size - 1; i++) {
            int bestIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (costs[j] < costs[bestIndex]) {
                    bestIndex = j;
                }
            }

            if (bestIndex != i) {
                int tempCost = costs[i];
                costs[i] = costs[bestIndex];
                costs[bestIndex] = tempCost;

                int tempValue = values[i];
                values[i] = values[bestIndex];
                values[bestIndex] = tempValue;
            }
        }
    }
}