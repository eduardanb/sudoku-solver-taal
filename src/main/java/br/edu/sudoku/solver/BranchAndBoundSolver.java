/**
 * Implementação da técnica Branch and Bound para resolver o Sudoku.
 *
 * O algoritmo explora o espaço de busca de forma semelhante ao
 * Backtracking, porém aplica podas (bounds) para eliminar ramos
 * que não podem levar a uma solução válida.
 *
 * Isso reduz o número de estados explorados e melhora o desempenho.
 */