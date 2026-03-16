/**
 * Implementa heurísticas de ordenação de variáveis utilizadas para
 * otimizar o processo de busca no espaço de soluções do Sudoku.
 *
 * Essas heurísticas determinam qual célula do tabuleiro deve ser
 * explorada primeiro durante a execução dos algoritmos de busca,
 * reduzindo o número de estados visitados.
 *
 * Exemplo: selecionar a célula com menor número de valores possíveis.
 */

package br.edu.sudoku.heuristics;

import br.edu.sudoku.model.SudokuBoard;

/**
 * Interface para heurísticas de ordenação de variáveis.
 * Responsável por escolher qual célula vazia será expandida
 * primeiro durante a busca.
 */
public interface VariableOrderingHeuristic {

    int[] selectCell(SudokuBoard board);

}