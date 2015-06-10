package minesweeper;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
				grid.addMouseListener(new ClickOnGrid());
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
				count++;				
				incrementMineCount(row-1, col-1);
				incrementMineCount(row-1, col);
				incrementMineCount(row-1, col+1);
				incrementMineCount(row, col-1);
				incrementMineCount(row, col+1);
				incrementMineCount(row+1, col-1);
				incrementMineCount(row+1, col);
				incrementMineCount(row+1, col+1);		
			}		
		}
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
	}

	private class ClickOnGrid extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Grid clickedGrid = (Grid)e.getSource();
			
			if (e.getButton() == MouseEvent.BUTTON1) { // left button pressed
				if (clickedGrid.isMine()) {
					clickedGrid.setLabelText("M");
					clickedGrid.getLabel().setBackground(Color.LIGHT_GRAY);
				} else {
					showContiguousZeroMineGrids(clickedGrid.getRow(), clickedGrid.getCol());
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) { // right button pressed
				JOptionPane.showMessageDialog(null, "BUTTON3"); 
			}
			
		}
		
		private void showContiguousZeroMineGrids(int row, int col) {
			if (row < 0 || row >= gridRows
					|| col < 0 || col >= gridCols) {
				return;
			} 
			if (theGrid[row][col].isExposed()) {
				return;
			} 
			if (theGrid[row][col].getMineCount() != 0) {
				theGrid[row][col].getLabel().setBackground(Color.LIGHT_GRAY);
				theGrid[row][col].setLabelText(Integer.toString(theGrid[row][col].getMineCount()));
				theGrid[row][col].setExposed(true);
				return;
			} else {
				theGrid[row][col].getLabel().setBackground(Color.LIGHT_GRAY);
				theGrid[row][col].setExposed(true);
				for (int i = row - 1; i <= row + 1; i ++) {
					for (int j = col - 1; j <= col + 1; j++) {
						if (i == row && j== col) {
							continue;
						}
						showContiguousZeroMineGrids(i, j);
					}
				}
			}			
		}
	}	
}
