// Implementação da técnica Branch and Bound

package br.edu.sudoku.solver.branchandbound;

import br.edu.sudoku.heuristics.MRVHeuristic;
import br.edu.sudoku.heuristics.VariableOrderingHeuristic;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBoundSolver implements BranchAndBoundAlgorithm {

    private int passos = 0;
    private boolean visualizar = true;

    private VariableOrderingHeuristic heuristica = new MRVHeuristic();

    public BranchAndBoundSolver() {
        this(true);
    }

    public BranchAndBoundSolver(boolean visualizar) {
        this.visualizar = visualizar;
    }

    private String obterRotuloDificuldade() {
        String bruto = System.getProperty("difficulty", System.getProperty("sudoku.difficulty", "facil"));
        String valor = bruto == null ? "facil" : bruto.trim().toLowerCase();

        if (valor.equals("medio")) {
            return "MEDIO";
        } else if (valor.equals("dificil")) {
            return "DIFICIL";
        } else {
            return "FACIL";
        }
    }

    @Override
    public boolean solve(SudokuBoard tabuleiro, Metrics metricas) {
        return branchAndBound(tabuleiro, metricas);
    }

    @Override
    public boolean branchAndBound(SudokuBoard tabuleiro, Metrics metricas) {

        metricas.incrementVisitedNodes();

        int[] celula = heuristica.selecionarCelula(tabuleiro);

        if (celula == null) {
            return true;
        }

        int linha = celula[0];
        int coluna = celula[1];

        List<Integer> candidatos = obterCandidatos(tabuleiro, linha, coluna);

        for (int numero : candidatos) {

            tabuleiro.set(linha, coluna, numero);
            passos++;

            if (visualizar) {
                limparConsole();

                System.out.println("=== Sudoku Solver (Branch and Bound) ===");
                System.out.println("Dificuldade: " + obterRotuloDificuldade());
                System.out.println("Passo: " + passos);
                System.out.println("Tentando colocar " + numero + " em (" + linha + "," + coluna + ")\n");

                tabuleiro.printBoard();
                pausar();
            }

            if (branchAndBound(tabuleiro, metricas)) {
                return true;
            }

            tabuleiro.set(linha, coluna, 0);
            metricas.incrementBacktracks();
            passos++;

            if (visualizar) {
                limparConsole();

                System.out.println("=== Sudoku Solver (Branch and Bound) ===");
                System.out.println("Dificuldade: " + obterRotuloDificuldade());
                System.out.println("Passo: " + passos);
                System.out.println("Backtracking removendo " + numero + " de (" + linha + "," + coluna + ")\n");

                tabuleiro.printBoard();
                pausar();
            }
        }

        return false;
    }

    private List<Integer> obterCandidatos(SudokuBoard tabuleiro, int linha, int coluna) {

        List<Integer> candidatos = new ArrayList<>();

        for (int numero = 1; numero <= 9; numero++) {

            if (SudokuValidator.isValid(tabuleiro, linha, coluna, numero)) {
                candidatos.add(numero);
            }
        }

        return candidatos;
    }

    private void pausar() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}