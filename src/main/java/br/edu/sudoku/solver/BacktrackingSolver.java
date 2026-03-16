/**
 * O tempo depende da ordem das casas vazias
 depende da ordem dos números testados (1–9)
 * Um "Sudoku" difícil pode acabar sendo resolvido antes
 de um médio dependendo do caminho que o algoritmo tenta.
 */

package br.edu.sudoku.solver;

import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

public class BacktrackingSolver implements SudokuSolver {

    private int steps = 0;

    @Override
    public boolean solve(SudokuBoard board, Metrics metrics) {
        return backtrack(board, metrics);
    }

    private boolean backtrack(SudokuBoard board, Metrics metrics) {

        // Conta um novo estado explorado
        metrics.incrementVisitedNodes();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board.get(row, col) == 0) {

                    for (int num = 1; num <= 9; num++) {

                        if (SudokuValidator.isValid(board, row, col, num)) {

                            board.set(row, col, num);
                            steps++;

                            clearConsole();

                            System.out.println("");
                            System.out.println("=== Sudoku Solver (Backtracking) ===");
                            System.out.println("Passo: " + steps);
                            System.out.println("Tentando colocar " + num + " em (" + row + "," + col + ")\n");

                            board.printBoard();

                            pause();

                            if (backtrack(board, metrics)) {
                                return true;
                            }

                            // Backtrack
                            board.set(row, col, 0);
                            metrics.incrementBacktracks();

                            steps++;

                            clearConsole();

                            System.out.println("");
                            System.out.println("=== Sudoku Solver (Backtracking) ===");
                            System.out.println("Passo: " + steps);
                            System.out.println("Backtracking removendo " + num + " de (" + row + "," + col + ")\n");

                            board.printBoard();

                            pause();
                        }
                    }

                    return false;
                }
            }
        }

        return true;
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