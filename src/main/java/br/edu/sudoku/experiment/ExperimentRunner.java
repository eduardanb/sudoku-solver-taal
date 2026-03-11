/**
 * Classe responsável por executar os "experimentos" do projeto
 * e coordenar o fluxo principal do sistema.
 */

package br.edu.sudoku.experiment;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.*;

import java.util.Scanner;

public class ExperimentRunner {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("-------------------------------------------------------");
            System.out.println("\n" +
                    "███████╗██╗   ██╗██████╗  ██████╗ ██╗  ██╗██╗   ██╗\n" +
                    "██╔════╝██║   ██║██╔══██╗██╔═══██╗██║ ██╔╝██║   ██║\n" +
                    "███████╗██║   ██║██║  ██║██║   ██║█████╔╝ ██║   ██║\n" +
                    "╚════██║██║   ██║██║  ██║██║   ██║██╔═██╗ ██║   ██║\n" +
                    "███████║╚██████╔╝██████╔╝╚██████╔╝██║  ██╗╚██████╔╝\n" +
                    "╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ \n" +
                    "                     SOLVER");
            System.out.println("-------------------------------------------------------");
            System.out.println("1 - Backtracking");
            System.out.println("2 - Branch and Bound");
            System.out.println("3 - Greedy");
            System.out.println("4 - Dynamic Programming");
            System.out.println("0 - Sair");

            System.out.print("\nEscolha o algoritmo: ");
            int option = scanner.nextInt();

            if (option == 0) {
                System.out.println("Encerrando...");
                break;
            }

            SudokuSolver solver = null;

            switch (option) {

                case 1:
                    solver = new BacktrackingSolver();
                    break;

                case 2:
                    solver = new BranchAndBoundSolver();
                    break;

                case 3:
                    solver = new GreedySolver();
                    break;

                case 4:
                    solver = new DynamicProgrammingSolver();
                    break;

                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

            SudokuBoard board = SudokuReader.read("sudokus/sudoku3.txt");

            System.out.println("\nSudoku inicial:\n");
            board.printBoard();

            Metrics metrics = new Metrics();

            long start = System.currentTimeMillis();

            boolean solved = solver.solve(board, metrics);

            long end = System.currentTimeMillis();

            if (solved) {
                System.out.println("\nSudoku resolvido:\n");
                board.printBoard();
            } else {
                System.out.println("\nNão foi possível resolver.");
            }

            System.out.println("\nTempo: " + (end - start) + " ms");
            System.out.println("Nós visitados: " + metrics.getVisitedNodes());
        }

        scanner.close();
    }
}