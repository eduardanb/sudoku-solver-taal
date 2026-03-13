/**
 * Classe responsável por executar os "experimentos" do projeto
 * e coordenar o fluxo principal do sistema.
 */

package br.edu.sudoku.experiment;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.io.SudokuWriter;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.BacktrackingSolver;
import br.edu.sudoku.solver.SudokuSolver;

import java.util.Scanner;

public class ExperimentRunner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("-------------------------------------------------------");
            System.out.println("███████╗██╗   ██╗██████╗  ██████╗ ██╗  ██╗██╗   ██╗");
            System.out.println("██╔════╝██║   ██║██╔══██╗██╔═══██╗██║ ██╔╝██║   ██║");
            System.out.println("███████╗██║   ██║██║  ██║██║   ██║█████╔╝ ██║   ██║");
            System.out.println("╚════██║██║   ██║██║  ██║██║   ██║██╔═██╗ ██║   ██║");
            System.out.println("███████║╚██████╔╝██████╔╝╚██████╔╝██║  ██╗╚██████╔╝");
            System.out.println("╚══════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ");
            System.out.println("                     SOLVER");
            System.out.println("-------------------------------------------------------");

            System.out.println("1 - Backtracking");
            System.out.println("2 - Branch and Bound");
            System.out.println("3 - Greedy");
            System.out.println("4 - Dynamic Programming");
            System.out.println("0 - Sair");

            System.out.print("\nEscolha o algoritmo: ");
            int algoritmo = scanner.nextInt();

            if (algoritmo == 0) {
                System.out.println("Encerrando...");
                break;
            }

            System.out.println("-------------------------------------------------------");
            System.out.println("\nEscolha a dificuldade:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Médio");
            System.out.println("3 - Difícil");
            System.out.println("0 - Voltar");

            System.out.print("Opção: ");
            int dificuldade = scanner.nextInt();

            if (dificuldade == 0) {
                continue;
            }

            String caminhoArquivo = "";
            String nomeDificuldade = "";

            switch (dificuldade) {

                case 1:
                    caminhoArquivo = "sudokus/sudoku1.txt";
                    nomeDificuldade = "Fácil";
                    break;

                case 2:
                    caminhoArquivo = "sudokus/sudoku2.txt";
                    nomeDificuldade = "Médio";
                    break;

                case 3:
                    caminhoArquivo = "sudokus/sudoku3.txt";
                    nomeDificuldade = "Difícil";
                    break;

                default:
                    System.out.println("Dificuldade inválida.");
                    continue;
            }

            try {

                SudokuBoard board = SudokuReader.read(caminhoArquivo);

                System.out.println("\nAlgoritmo escolhido: " +
                        "\n╔══════════════════════╗\n" +
                        "      BACKTRACKING\n" +
                        "╚══════════════════════╝");
                System.out.println("Dificuldade: " + nomeDificuldade);

                System.out.println("\nSudoku inicial:\n");
                SudokuWriter.printBoard(board);

                SudokuSolver solver = null;

                switch (algoritmo) {

                    case 1:
                        solver = new BacktrackingSolver();
                        break;

                    case 2:
                        System.out.println("Branch and Bound ainda não implementado.");
                        continue;

                    case 3:
                        System.out.println("Greedy ainda não implementado.");
                        continue;

                    case 4:
                        System.out.println("Dynamic Programming ainda não implementado.");
                        continue;

                    default:
                        System.out.println("Algoritmo inválido.");
                        continue;
                }

                Metrics metrics = new Metrics();

                long inicio = System.currentTimeMillis();

                boolean resolvido = solver.solve(board, metrics);

                long fim = System.currentTimeMillis();

                if (resolvido) {

                    System.out.println("\nSudoku resolvido:\n");
                    SudokuWriter.printBoard(board);

                    // GERA AUTOMATICAMENTE O ARQUIVO DE SAÍDA CORRETO
                    String arquivoSaida = "src/main/resources/" + caminhoArquivo.replace(".txt", "_resolvido.txt");

                    SudokuWriter.writeToFile(board, arquivoSaida);

                    System.out.println("\nTempo de execução: " + (fim - inicio) + " ms");
                    System.out.println("Nós visitados: " + metrics.getVisitedNodes());

                } else {

                    System.out.println("Não foi possível resolver o Sudoku.");
                }

            } catch (Exception e) {

                System.out.println("Erro ao carregar o arquivo.");
                e.printStackTrace();
            }

            System.out.println("\n");
        }

        scanner.close();
    }
}