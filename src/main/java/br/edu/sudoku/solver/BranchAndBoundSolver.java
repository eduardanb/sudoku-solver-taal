/**
 * Implementação da técnica Branch and Bound para resolver o "Sudoku".
 *
 * O algoritmo explora o espaço de busca de forma semelhante ao
 * Backtracking, porém aplica podas (bounds) para eliminar ramos
 * que não podem levar a uma solução válida.
 *
 * Isso reduz o número de estados explorados e melhora o desempenho.
 */

package br.edu.sudoku.solver;

import br.edu.sudoku.heuristics.MRVHeuristic;
import br.edu.sudoku.heuristics.VariableOrderingHeuristic;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBoundSolver implements SudokuSolver {

    private int steps = 0;

    private VariableOrderingHeuristic heuristic = new MRVHeuristic();

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        return branchAndBound(board, metrics);
    }

    private boolean branchAndBound(SudokuBoard board, Metrics metrics) {

        // Conta um estado explorado
        metrics.incrementVisitedNodes();

        int[] cell = heuristic.selectCell(board);

        if (cell == null) {
            return true;
        }

        int row = cell[0];
        int col = cell[1];

        List<Integer> candidates = getCandidates(board, row, col);

        for (int num : candidates) {

            board.set(row, col, num);
            steps++;

            clearConsole();

            System.out.println("=== Sudoku Solver (Branch and Bound) ===");
            System.out.println("Passo: " + steps);
            System.out.println("Tentando colocar " + num + " em (" + row + "," + col + ")\n");

            board.printBoard();

            pause();

            if (branchAndBound(board, metrics)) {
                return true;
            }

            // Backtrack
            board.set(row, col, 0);
            metrics.incrementBacktracks();

            steps++;

            clearConsole();

            System.out.println("=== Sudoku Solver (Branch and Bound) ===");
            System.out.println("Passo: " + steps);
            System.out.println("Backtracking removendo " + num + " de (" + row + "," + col + ")\n");

            board.printBoard();

            pause();
        }

        return false;
    }

    private List<Integer> getCandidates(SudokuBoard board, int row, int col) {

        List<Integer> candidates = new ArrayList<>();

        for (int num = 1; num <= 9; num++) {

            if (SudokuValidator.isValid(board, row, col, num)) {
                candidates.add(num);
            }
        }

        return candidates;
    }

    private void pause() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}