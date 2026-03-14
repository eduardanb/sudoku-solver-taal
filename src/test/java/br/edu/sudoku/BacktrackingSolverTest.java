package br.edu.sudoku;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.BacktrackingSolver;
import br.edu.sudoku.utils.SudokuValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BacktrackingSolverTest {

    @Test
    public void testBacktrackingSolvesEasySudoku() throws Exception {
        String[] difficulties = {"facil", "medio", "dificil"};

        for (String diff : difficulties) {
            String path = switch (diff) {
                case "medio" -> "sudokus/sudoku_medio.txt";
                case "dificil" -> "sudokus/sudoku_dificil.txt";
                default -> "sudokus/sudoku_facil.txt";
            };

            SudokuBoard board = SudokuReader.read(path);
            Metrics metrics = new Metrics();

            BacktrackingSolver solver = new BacktrackingSolver();
            boolean solved = solver.solve(board, metrics);

            assertTrue(solved, "BacktrackingSolver deve resolver o sudoku (" + diff + ")");
            assertTrue(isCompleteAndValid(board), "Solução produzida deve ser completa e válida (" + diff + ")");
        }
    }

    private boolean isCompleteAndValid(SudokuBoard board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = board.get(r, c);
                if (v < 1 || v > 9) return false;

                board.set(r, c, 0);
                boolean ok = SudokuValidator.isValid(board, r, c, v);
                board.set(r, c, v);
                if (!ok) return false;
            }
        }
        return true;
    }
}
