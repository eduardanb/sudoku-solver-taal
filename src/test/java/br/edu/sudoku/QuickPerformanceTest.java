//  Aqui testamos as técnicas de projeto de algoritmo de acordo com o nível de dificuldade escolhido.

package br.edu.sudoku;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.backtracking.BacktrackingSolver;
import br.edu.sudoku.solver.branchandbound.BranchAndBoundSolver;
import org.junit.jupiter.api.Test;

public class QuickPerformanceTest {

    @Test
    public void compararDesempenhoSolvers() throws Exception {

        String dificuldade = "medio"; // Coloque aqui o nível de dificuldade que deseja testar.

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

        SudokuBoard tabuleiro = SudokuReader.read(caminho);

        System.out.println("\n==============================");
        System.out.println(" TESTE RÁPIDO DE DESEMPENHO ");
        System.out.println("==============================");
        System.out.println("Sudoku: " + dificuldade);
        System.out.println();

        // -------- Backtracking --------
        SudokuBoard tabuleiroBt = SudokuReader.read(caminho);
        Metrics metricasBt = new Metrics();

        long inicioBt = System.nanoTime();

        BacktrackingSolver backtracking = new BacktrackingSolver(false);
        backtracking.solve(tabuleiroBt, metricasBt);

        long fimBt = System.nanoTime();

        long tempoBt = (fimBt - inicioBt) / 1_000_000;

        System.out.println("Backtracking:");
        System.out.println("Tempo (ms): " + tempoBt);
        System.out.println("Nós visitados: " + metricasBt.getVisitedNodes());
        System.out.println("Backtracks: " + metricasBt.getBacktracks());
        System.out.println();

        // -------- Branch and Bound --------
        SudokuBoard tabuleiroBb = SudokuReader.read(caminho);
        Metrics metricasBb = new Metrics();

        long inicioBb = System.nanoTime();

        BranchAndBoundSolver branchAndBound = new BranchAndBoundSolver(false);
        branchAndBound.solve(tabuleiroBb, metricasBb);

        long fimBb = System.nanoTime();

        long tempoBb = (fimBb - inicioBb) / 1_000_000;

        System.out.println("Branch and Bound:");
        System.out.println("Tempo (ms): " + tempoBb);
        System.out.println("Nós visitados: " + metricasBb.getVisitedNodes());
        System.out.println("Backtracks: " + metricasBb.getBacktracks());
        System.out.println();

        System.out.println("==============================\n");
    }
}