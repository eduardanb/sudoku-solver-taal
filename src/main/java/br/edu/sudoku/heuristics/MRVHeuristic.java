/**
 * Implementação da heurística MRV (Minimum Remaining Values).
 * Escolhe a célula vazia com o menor número de valores possíveis.
 * Isso reduz o espaço de busca do algoritmo.
 */

package br.edu.sudoku.heuristics;

import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

import java.util.ArrayList;
import java.util.List;

public class MRVHeuristic implements VariableOrderingHeuristic {

    @Override
    public int[] selecionarCelula(SudokuBoard board) {

        int minimoOpcoes = Integer.MAX_VALUE;
        int[] melhorCelula = null;

        for (int linha = 0; linha < 9; linha++) {

            for (int coluna = 0; coluna < 9; coluna++) {

                if (board.get(linha, coluna) == 0) {

                    List<Integer> candidatos = obterCandidatos(board, linha, coluna);

                    if (candidatos.size() < minimoOpcoes) {

                        minimoOpcoes = candidatos.size();
                        melhorCelula = new int[]{linha, coluna};

                        if (minimoOpcoes == 1) {
                            return melhorCelula;
                        }
                    }
                }
            }
        }

        return melhorCelula;
    }

    private List<Integer> obterCandidatos(SudokuBoard board, int linha, int coluna) {

        List<Integer> candidatos = new ArrayList<>();

        for (int numero = 1; numero <= 9; numero++) {

            if (SudokuValidator.isValid(board, linha, coluna, numero)) {
                candidatos.add(numero);
            }
        }

        return candidatos;
    }
}