/**
 * Classe responsável pela escrita da solução do Sudoku em arquivo.
 *
 * Após a execução de um algoritmo de resolução, o estado final
 * do tabuleiro é exportado para um arquivo de saída.
 *
 * Pode também registrar métricas do experimento, como:
 * - tempo de execução
 * - número de nós visitados
 * - quantidade de podas realizadas.
 */

package br.edu.sudoku.io;

import br.edu.sudoku.model.SudokuBoard;

public class SudokuWriter {

    public static void printBoard(SudokuBoard board) {
        board.printBoard();
    }
}