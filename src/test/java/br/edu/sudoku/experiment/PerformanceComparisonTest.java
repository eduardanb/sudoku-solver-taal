package br.edu.sudoku.experiment;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.SudokuSolver;
import br.edu.sudoku.solver.backtracking.BacktrackingSolver;
import br.edu.sudoku.solver.branchandbound.BranchAndBoundSolver;

import java.util.ArrayList;
import java.util.List;

public class PerformanceComparisonTest {

    private static final int EXECUCOES = 10;

    public static void main(String[] args) throws Exception {

        System.out.println("==================================");
        System.out.println("      COMPARAÇÃO DE DESEMPENHO");
        System.out.println("==================================");

        String[] dificuldades = {"facil", "medio", "dificil"};

        for (String dificuldade : dificuldades) {

            System.out.println("\n==================================");
            System.out.println("Sudoku: " + dificuldade.toUpperCase());
            System.out.println("==================================");

            executarExperimento(new BacktrackingSolver(false), "Backtracking", dificuldade);
            executarExperimento(new BranchAndBoundSolver(false), "Branch and Bound", dificuldade);
        }
    }

    private static void executarExperimento(SudokuSolver solver, String nome, String dificuldade) throws Exception {

        String caminho;

        if (dificuldade.equals("medio")) {
            caminho = "sudokus/sudoku_medio.txt";
        } else if (dificuldade.equals("dificil")) {
            caminho = "sudokus/sudoku_dificil.txt";
        } else {
            caminho = "sudokus/sudoku_facil.txt";
        }

        System.setProperty("difficulty", dificuldade);
        System.setProperty("sudoku.difficulty", dificuldade);

        List<Double> tempos = new ArrayList<>();
        List<Long> nosVisitados = new ArrayList<>();
        List<Long> backtracks = new ArrayList<>();

        for (int i = 0; i < EXECUCOES; i++) {

            SudokuBoard tabuleiro = SudokuReader.read(caminho);
            Metrics metricas = new Metrics();

            long inicio = System.nanoTime();

            solver.solve(tabuleiro, metricas);

            long fim = System.nanoTime();

            double tempoMs = (fim - inicio) / 1_000_000.0;

            tempos.add(tempoMs);
            nosVisitados.add(metricas.getVisitedNodes());
            backtracks.add(metricas.getBacktracks());
        }

        double tempoMedio = media(tempos);
        double desvioPadrao = desvio(tempos, tempoMedio);

        double mediaNos = mediaLong(nosVisitados);
        double mediaBacks = mediaLong(backtracks);

        System.out.println("\nAlgoritmo: " + nome);
        System.out.println("Execuções: " + EXECUCOES);

        System.out.printf("Tempo médio: %.3f ms\n", tempoMedio);
        System.out.printf("Desvio padrão: %.3f ms\n", desvioPadrao);

        System.out.println("Nós visitados (média): " + mediaNos);
        System.out.println("Backtracks (média): " + mediaBacks);
    }

    private static double media(List<Double> lista) {

        double soma = 0;

        for (double valor : lista) {
            soma += valor;
        }

        return soma / lista.size();
    }

    private static double desvio(List<Double> lista, double media) {

        double soma = 0;

        for (double valor : lista) {
            soma += Math.pow(valor - media, 2);
        }

        return Math.sqrt(soma / lista.size());
    }

    private static double mediaLong(List<Long> lista) {

        long soma = 0;

        for (long valor : lista) {
            soma += valor;
        }

        return (double) soma / lista.size();
    }
}