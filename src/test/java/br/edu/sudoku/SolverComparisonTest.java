package br.edu.sudoku;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.backtracking.BacktrackingSolver;
import br.edu.sudoku.solver.branchandbound.BranchAndBoundSolver;
import br.edu.sudoku.solver.dynamicprogramming.DynamicProgrammingSolver;
import br.edu.sudoku.solver.greedy.GreedySolver;
import br.edu.sudoku.utils.SudokuValidator;

public class SolverComparisonTest {

	@Test
	public void testarSolversProduzemSolucaoValida() throws Exception {

		String solverProp = System.getProperty("solver", "all").toLowerCase();
		String dificuldade = System.getProperty("difficulty", "medio").toLowerCase(); // Defina aqui o nível de dificuldade a ser testado
		String rotulo = formatarRotuloDificuldade(dificuldade);
		System.out.println("Dificuldade de teste: (" + rotulo + ")");

		String caminho;
		if (dificuldade.equals("medio")) {
			caminho = "sudokus/sudoku_medio.txt";
		} else if (dificuldade.equals("dificil")) {
			caminho = "sudokus/sudoku_dificil.txt";
		} else {
			caminho = "sudokus/sudoku_facil.txt";
		}

		Metrics metricasBt = new Metrics();
		Metrics metricasBb = new Metrics();

		// -------- Backtracking --------
		if (solverProp.equals("all") || solverProp.equals("backtracking")) {

			SudokuBoard tabuleiroBt = SudokuReader.read(caminho);
			BacktrackingSolver bt = new BacktrackingSolver(false);
			boolean resolvidoBt = bt.solve(tabuleiroBt, metricasBt);

			assertTrue(resolvidoBt, "BacktrackingSolver deve resolver o Sudoku (" + rotulo + ")");
			assertTrue(tabuleiroCompletoEValido(tabuleiroBt), "Solução do Backtracking deve ser válida (" + rotulo + ")");
		}

		// -------- Branch and Bound --------
		if (solverProp.equals("all") || solverProp.equals("branchandbound") || solverProp.equals("branch_and_bound")) {

			SudokuBoard tabuleiroBb = SudokuReader.read(caminho);
			BranchAndBoundSolver bb = new BranchAndBoundSolver(false);
			boolean resolvidoBb = bb.solve(tabuleiroBb, metricasBb);

			assertTrue(resolvidoBb, "BranchAndBoundSolver deve resolver o Sudoku (" + rotulo + ")");
			assertTrue(tabuleiroCompletoEValido(tabuleiroBb), "Solução do Branch and Bound deve ser válida (" + rotulo + ")");
		}

		// -------- Greedy --------
		if (solverProp.equals("greedy")) {

			SudokuBoard tabuleiroG = SudokuReader.read(caminho);
			GreedySolver gs = new GreedySolver();
			boolean resolvidoG = gs.solve(tabuleiroG, new Metrics());

			assertTrue(resolvidoG, "GreedySolver deve resolver o Sudoku quando implementado (" + rotulo + ")");
			assertTrue(tabuleiroCompletoEValido(tabuleiroG), "Solução do Greedy deve ser válida (" + rotulo + ")");
		}

		// -------- Dynamic Programming --------
		if (solverProp.equals("dynamic") || solverProp.equals("dp")) {

			SudokuBoard tabuleiroDp = SudokuReader.read(caminho);
			DynamicProgrammingSolver ds = new DynamicProgrammingSolver();
			boolean resolvidoDp = ds.solve(tabuleiroDp, new Metrics());

			assertTrue(resolvidoDp, "DynamicProgrammingSolver deve resolver o Sudoku quando implementado (" + rotulo + ")");
			assertTrue(tabuleiroCompletoEValido(tabuleiroDp), "Solução do Dynamic Programming deve ser válida (" + rotulo + ")");
		}
	}

	private String formatarRotuloDificuldade(String dificuldade) {
		if ("dificil".equalsIgnoreCase(dificuldade)) {
			return "dificil";
		} else {
			return dificuldade.toLowerCase();
		}
	}

	private boolean tabuleiroCompletoEValido(SudokuBoard tabuleiro) {

		for (int linha = 0; linha < 9; linha++) {
			for (int coluna = 0; coluna < 9; coluna++) {

				int valor = tabuleiro.get(linha, coluna);
				if (valor < 1 || valor > 9) {
					return false;
				}

				tabuleiro.set(linha, coluna, 0);
				boolean valido = SudokuValidator.isValid(tabuleiro, linha, coluna, valor);
				tabuleiro.set(linha, coluna, valor);

				if (!valido) {
					return false;
				}
			}
		}

		return true;
	}
}