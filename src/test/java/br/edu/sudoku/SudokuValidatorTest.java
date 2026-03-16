package br.edu.sudoku;

import br.edu.sudoku.model.SudokuBoard;
import br.edu.sudoku.utils.SudokuValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuValidatorTest {

	@BeforeEach
	public void printDifficulty() {
		String difficulty = System.getProperty("difficulty", "facil");
		String label = "dificil".equalsIgnoreCase(difficulty) ? "DIFICIL" : difficulty.toLowerCase();
		System.out.println("Dificuldade de teste: (" + label + ")");
	}

	@Test
	public void testValidInsertion() {
		int[][] board = new int[9][9];
		board[0][0] = 5;
		SudokuBoard sb = new SudokuBoard(board);

		assertTrue(SudokuValidator.isValid(sb, 0, 1, 3));
	}

	@Test
	public void testRowConflict() {
		int[][] board = new int[9][9];
		board[0][0] = 5;
		SudokuBoard sb = new SudokuBoard(board);

		assertFalse(SudokuValidator.isValid(sb, 0, 2, 5));
	}

	@Test
	public void testColumnConflict() {
		int[][] board = new int[9][9];
		board[0][0] = 7;
		SudokuBoard sb = new SudokuBoard(board);

		assertFalse(SudokuValidator.isValid(sb, 2, 0, 7));
	}

	@Test
	public void testBoxConflict() {
		int[][] board = new int[9][9];
		board[1][1] = 9; 
		SudokuBoard sb = new SudokuBoard(board);

		assertFalse(SudokuValidator.isValid(sb, 0, 2, 9));
	}

	@Test
	public void testInvalidZero() {
		int[][] board = new int[9][9];
		SudokuBoard sb = new SudokuBoard(board);

		assertFalse(SudokuValidator.isValid(sb, 0, 0, 0));
	}

	@Test
	public void testInvalidGreaterThanNine() {
		int[][] board = new int[9][9];
		SudokuBoard sb = new SudokuBoard(board);

		assertFalse(SudokuValidator.isValid(sb, 0, 0, 10));
	}
}
