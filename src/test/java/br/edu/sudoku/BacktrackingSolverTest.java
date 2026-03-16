package br.edu.sudoku;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.backtracking.BacktrackingSolver;
import br.edu.sudoku.utils.SudokuValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BacktrackingSolverTest {

    @Test
    public void testBacktrackingSolvesSudokus() throws Exception {

        for (int i = 0; i < 3; i++) {

            String dificuldade;

            if (i == 1) {
                dificuldade = "medio";
            } else if (i == 2) {
                dificuldade = "dificil";
            } else {
                dificuldade = "facil";
            }

            String rotulo = formatarRotuloDificuldade(dificuldade);
            System.out.println("Dificuldade de teste: (" + rotulo + ")");

            String caminho;

            if (dificuldade.equals("medio")) {
                caminho = "sudokus/sudoku_medio.txt";
            } else if (dificuldade.equals("dificil")) {
                caminho = "sudokus/sudoku_dificil.txt";
            } else {
                caminho = "sudokus/sudoku_facil.txt";
            }

            SudokuBoard tabuleiro = SudokuReader.read(caminho);
            Metrics metricas = new Metrics();

            BacktrackingSolver solver = new BacktrackingSolver(false);
            boolean resolvido = solver.solve(tabuleiro, metricas);

            assertTrue(resolvido, "BacktrackingSolver deve resolver o sudoku (" + rotulo + ")");
            assertTrue(tabuleiroCompletoEValido(tabuleiro), "Solução produzida deve ser completa e válida (" + rotulo + ")");

            System.out.println("Sudoku resolvido com sucesso (" + rotulo + ")\n");
        }
    }

    private String formatarRotuloDificuldade(String dificuldade) {
        String valor = dificuldade == null ? "facil" : dificuldade.trim().toLowerCase();
        if ("dificil".equals(valor)) {
            return "dificil";
        } else {
            return valor;
        }
    }

    private boolean tabuleiroCompletoEValido(SudokuBoard tabuleiro) {

        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {

                int valor = tabuleiro.get(linha, coluna);
                if (valor < 1 || valor > 9) {
                    return false;
                }

                tabuleiro.set(linha, coluna, 0);
                boolean valido = SudokuValidator.isValid(tabuleiro, linha, coluna, valor);
                tabuleiro.set(linha, coluna, valor);

                if (!valido) {
                    return false;
                }
            }
        }

        return true;
    }
}