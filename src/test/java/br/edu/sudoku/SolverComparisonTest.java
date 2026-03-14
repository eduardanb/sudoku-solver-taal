package br.edu.sudoku;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.metrics.Metrics;
import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.solver.BacktrackingSolver;
import br.edu.sudoku.solver.BranchAndBoundSolver;
import br.edu.sudoku.solver.DynamicProgrammingSolver;
import br.edu.sudoku.solver.GreedySolver;
import br.edu.sudoku.utils.SudokuValidator;

public class SolverComparisonTest {

	@Test
	public void testSolversProduceValidSolution() throws Exception {

		String solverProp = System.getProperty("solver", "all").toLowerCase();
		String difficulty = System.getProperty("difficulty", "facil").toLowerCase();

		String path = switch (difficulty) {
			case "medio" -> "sudokus/sudoku_medio.txt";
			case "dificil" -> "sudokus/sudoku_dificil.txt";
			default -> "sudokus/sudoku_facil.txt";
		};

		Metrics m1 = new Metrics();
		Metrics m2 = new Metrics();

		if (solverProp.equals("all") || solverProp.equals("backtracking")) {
			SudokuBoard boardBt = SudokuReader.read(path);
			BacktrackingSolver bt = new BacktrackingSolver();
			boolean solvedBt = bt.solve(boardBt, m1);
			assertTrue(solvedBt, "BacktrackingSolver deve resolver o Sudoku");
			assertTrue(isCompleteAndValid(boardBt), "Solução do Backtracking deve ser válida");
		}

		if (solverProp.equals("all") || solverProp.equals("branchandbound") || solverProp.equals("branch_and_bound")) {
			SudokuBoard boardBb = SudokuReader.read(path);
			BranchAndBoundSolver bb = new BranchAndBoundSolver();
			boolean solvedBb = bb.solve(boardBb, m2);
			assertTrue(solvedBb, "BranchAndBoundSolver deve resolver o Sudoku");
			assertTrue(isCompleteAndValid(boardBb), "Solução do Branch and Bound deve ser válida");
		}

		if (solverProp.equals("greedy")) {
			SudokuBoard boardG = SudokuReader.read(path);
			GreedySolver gs = new GreedySolver();
			boolean solvedG = gs.solve(boardG, new Metrics());
			assertTrue(solvedG, "GreedySolver deve resolver o Sudoku quando implementado");
			assertTrue(isCompleteAndValid(boardG), "Solução do Greedy deve ser válida");
		}

		if (solverProp.equals("dynamic") || solverProp.equals("dp")) {
			SudokuBoard boardDp = SudokuReader.read(path);
			DynamicProgrammingSolver ds = new DynamicProgrammingSolver();
			boolean solvedDp = ds.solve(boardDp, new Metrics());
			assertTrue(solvedDp, "DynamicProgrammingSolver deve resolver o Sudoku quando implementado");
			assertTrue(isCompleteAndValid(boardDp), "Solução do Dynamic Programming deve ser válida");
		}
	}

	private boolean isCompleteAndValid(SudokuBoard board) {

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {

				int val = board.get(row, col);
				if (val < 1 || val > 9) return false;

				board.set(row, col, 0);
				boolean ok = SudokuValidator.isValid(board, row, col, val);
				board.set(row, col, val);

				if (!ok) return false;
			}
		}

		return true;
	}
}