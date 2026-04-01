// Implementação da técnica Branch and Bound

package br.edu.sudoku.solver.branchandbound;

import br.edu.sudoku.heuristics.MRVHeuristic;
import br.edu.sudoku.heuristics.VariableOrderingHeuristic;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;

public class BranchAndBoundSolver implements BranchAndBoundAlgorithm {

    private final boolean visualizar;
    private final String rotuloDificuldade;
    private final VariableOrderingHeuristic heuristica;

    // Contador de passos local a cada execucao — resetado em branchAndBound()
    private int passos;

    // Funcao objetiva: melhor (menor) numero de celulas inviáveis encontrado ate agora.
    // Começa em MAX para garantir que qualquer solucao inicial seja aceita.
    private int melhorBound;

    public BranchAndBoundSolver() {
        this(true);
    }

    public BranchAndBoundSolver(boolean visualizar) {
        this.visualizar = visualizar;
        this.rotuloDificuldade = resolverRotuloDificuldade();
        this.heuristica = new MRVHeuristic();
    }

    /**
     * Le a propriedade de dificuldade do sistema uma unica vez.
     */
    private static String resolverRotuloDificuldade() {
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
        passos = 0;
        melhorBound = Integer.MAX_VALUE;
        return resolver(tabuleiro, metricas);
    }

    /**
     * Implementacao recursiva do Branch and Bound.
     *
     * A funcao objetiva e: minimizar o numero de celulas vazias que ficam sem
     * candidatos validos apos cada atribuicao (celulas inviáveis).
     *
     * O bound de cada no e calculado ANTES de descer na recursao.
     * Se o bound indicar que alguma celula ficou sem candidatos, o no e podado —
     * pois nenhuma solucao valida pode ser obtida a partir desse estado.
     */
    private boolean resolver(SudokuBoard tabuleiro, Metrics metricas) {
        metricas.incrementVisitedNodes();

        int[] celula = heuristica.selecionarCelula(tabuleiro);
        if (celula == null) {
            // Nenhuma celula vazia: tabuleiro resolvido
            return true;
        }

        int linha  = celula[0];
        int coluna = celula[1];

        int[] candidatos   = obterCandidatos(tabuleiro, linha, coluna);
        int numCandidatos  = candidatos[0];

        if (numCandidatos == 0) {
            // Celula sem candidatos: no inviavel por violacao de restricoes (poda por restricao)
            metricas.incrementBacktracks();
            return false;
        }

        for (int i = 1; i <= numCandidatos; i++) {
            int numero = candidatos[i];

            tabuleiro.set(linha, coluna, numero);
            passos++;
            exibirPasso(tabuleiro, linha, coluna, numero, false);

            // --- CALCULO DO BOUND ---
            // Conta quantas celulas vazias ficam sem nenhum candidato valido
            // apos atribuir este valor. Se alguma ficar inviavel, o bound
            // indica que nenhuma solucao pode ser obtida por este ramo.
            int bound = calcularBound(tabuleiro);

            if (bound > 0) {
                // Poda por bound: este ramo nao pode levar a solucao valida.
                // O bound (celulas inviáveis) e pior que o ideal (zero).
                tabuleiro.set(linha, coluna, 0);
                metricas.incrementBacktracks();
                passos++;
                exibirPasso(tabuleiro, linha, coluna, numero, true);
                continue;
            }

            // Bound == 0: nenhuma celula ficou inviavel, vale a pena explorar este ramo.
            // Atualiza o melhor bound conhecido.
            melhorBound = bound;

            if (resolver(tabuleiro, metricas)) {
                return true;
            }

            tabuleiro.set(linha, coluna, 0);
            passos++;
            exibirPasso(tabuleiro, linha, coluna, numero, true);
        }

        // Nenhum candidato passou pelo bound ou levou a solucao
        metricas.incrementBacktracks();
        return false;
    }

    /**
     * Funcao de bound: conta quantas celulas vazias ficam sem candidatos validos
     * no estado atual do tabuleiro.
     *
     * Bound == 0  -> estado viavel, vale explorar.
     * Bound  > 0  -> pelo menos uma celula ficou inviavel, poda o ramo.
     *
     * @return numero de celulas vazias sem candidatos validos
     */
    private int calcularBound(SudokuBoard tabuleiro) {
        int celulasSemCandidatos = 0;
        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {
                if (tabuleiro.get(l, c) == 0 && contarCandidatos(tabuleiro, l, c) == 0) {
                    celulasSemCandidatos++;
                }
            }
        }
        return celulasSemCandidatos;
    }

    /**
     * Conta quantos valores (1-9) sao validos para a celula (linha, coluna).
     */
    private int contarCandidatos(SudokuBoard tabuleiro, int linha, int coluna) {
        int cont = 0;
        for (int numero = 1; numero <= 9; numero++) {
            if (SudokuValidator.isValid(tabuleiro, linha, coluna, numero)) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Retorna os candidatos validos para a celula (linha, coluna).
     * resultado[0] = quantidade; resultado[1..n] = valores candidatos.
     */
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

    /**
     * Exibe o estado atual do tabuleiro no console, se a visualizacao estiver ativa.
     *
     * @param backtrack true = exibe mensagem de remocao; false = exibe mensagem de tentativa
     */
    private void exibirPasso(SudokuBoard tabuleiro, int linha, int coluna, int numero, boolean backtrack) {
        if (!visualizar) {
            return;
        }

        limparConsole();
        System.out.println("=== Sudoku Solver (Branch and Bound) ===");
        System.out.println("Dificuldade: " + rotuloDificuldade);
        System.out.println("Passo: " + passos);

        if (backtrack) {
            System.out.println("Backtracking removendo " + numero + " de (" + linha + "," + coluna + ")\n");
        } else {
            System.out.println("Tentando colocar " + numero + " em (" + linha + "," + coluna + ")\n");
        }

        tabuleiro.printBoard();
        pausar();
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