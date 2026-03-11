/**
 * Classe responsável pela leitura de instâncias do problema Sudoku
 * a partir de arquivos de entrada.
 * O arquivo contém uma matriz 9x9 onde:
 * - valores de 1 a 9 representam números fixos no tabuleiro
 * - o valor 0 representa uma célula vazia
 * O conteúdo lido é convertido para um objeto SudokuBoard,
 * que será utilizado pelos algoritmos de resolução.
 */

package br.edu.sudoku.io;

import br.edu.sudoku.model.SudokuBoard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SudokuReader {

    public static SudokuBoard read(String path) throws Exception {

        InputStream input = SudokuReader.class
                .getClassLoader()
                .getResourceAsStream(path);

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        int[][] board = new int[9][9];

        for (int i = 0; i < 9; i++) {

            String line = reader.readLine();
            String[] values = line.split(" ");

            for (int j = 0; j < 9; j++) {
                board[i][j] = Integer.parseInt(values[j]);
            }
        }

        reader.close();

        return new SudokuBoard(board);
    }
}