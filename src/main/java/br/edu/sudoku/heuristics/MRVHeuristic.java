package br.edu.sudoku.heuristics;

import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da heurística MRV (Minimum Remaining Values).
 *
 * Escolhe a célula vazia com o menor número de valores possíveis.
 * Isso reduz o espaço de busca do algoritmo.
 */
public class MRVHeuristic implements VariableOrderingHeuristic {

    @Override
    public int[] selectCell(SudokuBoard board) {

        int minOptions = Integer.MAX_VALUE;
        int[] bestCell = null;

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                if (board.get(row, col) == 0) {

                    List<Integer> candidates = getCandidates(board, row, col);

                    if (candidates.size() < minOptions) {

                        minOptions = candidates.size();
                        bestCell = new int[]{row, col};

                        if (minOptions == 1) {
                            return bestCell;
                        }
                    }
                }
            }
        }

        return bestCell;
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
}