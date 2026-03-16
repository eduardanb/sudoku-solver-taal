// Implementação da técnica Branch and Bound

package br.edu.sudoku.solver.branchandbound;

import br.edu.sudoku.heuristics.MRVHeuristic;
import br.edu.sudoku.heuristics.VariableOrderingHeuristic;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

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

        int[] candidatos = obterCandidatos(tabuleiro, linha, coluna);
        int numCandidatos = candidatos[0]; // índice 0 = quantidade de candidatos

        for (int i = 1; i <= numCandidatos; i++) {
            int numero = candidatos[i];

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

    private int[] obterCandidatos(SudokuBoard tabuleiro, int linha, int coluna) {

        int[] candidatos = new int[10];
        int cont = 0;

        for (int numero = 1; numero <= 9; numero++) {
            if (SudokuValidator.isValid(tabuleiro, linha, coluna, numero)) {
                cont++;
                candidatos[cont] = numero;
            }
        }

        candidatos[0] = cont;
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