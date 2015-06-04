package minesweeper;

import java.awt.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class GridUI extends JPanel implements Config{
	private int level;
	private int gridRows;
	private int gridCols;
	private int mines;
	private Grid[][] theGrid;
	
	public GridUI(int gameLevel) {
		if (gameLevel != BEGINNER && gameLevel != INTERMEDIATE && gameLevel !=EXPERT) {
			System.exit(0);
		}
		
		level = gameLevel;
		gridRows = GRID_DIMENSTIONS[level][0];
		gridCols = GRID_DIMENSTIONS[level][1];
		mines = MINES[level];
		theGrid = new Grid[gridRows][gridCols];
		
		setLayout(new GridLayout(gridRows, gridCols));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		
		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				Grid grid = new Grid(GRID_SIZE, i, j);
				grid.setBorder(blackBorder);
				add(grid);
				theGrid[i][j] = grid;
			}
		}	
		
		Random r = new Random();
		int count = 0;
		int row, col;
		while (count < mines) {
			row = r.nextInt(gridRows);
			col = r.nextInt(gridCols);
			if (!theGrid[row][col].isMine()) {
				theGrid[row][col].setMine(true);
				theGrid[row][col].setMineCount(0);
				theGrid[row][col].setLabelText("M");
				count++;				
				incrementMineCount(row-1, col-1);
				incrementMineCount(row-1, col);
				incrementMineCount(row-1, col+1);
				incrementMineCount(row, col-1);
				incrementMineCount(row, col+1);
				incrementMineCount(row+1, col-1);
				incrementMineCount(row+1, col);
				incrementMineCount(row+1, col+1);		
				System.out.println("(x, y) = (" + row + ", " + col + ")");
			}		
		}
		
		System.out.println("mines: " + mines + " mines in grid: " + count);
	}
	
	private void incrementMineCount(int row, int col) {
		if (row <0 || col < 0 || row >= gridRows || col >= gridCols) {
			return;
		}
		
		if (theGrid[row][col].isMine()) {
			return;
		}
		
		Grid grid = theGrid[row][col];
		grid.incrementMineCount();
		grid.setLabelText(Integer.toString(grid.getMineCount()));
	}

	public static void main(String[] args) {

	}

}
