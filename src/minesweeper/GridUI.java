package minesweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
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
	private int flaggedMines;
	private int exposedMines;
	private int totalgrids;
	// timer
	private final ClockListener clockListener = new ClockListener();
	private final Timer timer = new Timer(100, clockListener);
	private final JLabel timeLabel = new JLabel();

	
	public GridUI(int gameLevel) {
		if (gameLevel != BEGINNER && gameLevel != INTERMEDIATE && gameLevel !=EXPERT) {
			System.exit(0);
		}
		
		level = gameLevel;
		gridRows = GRID_DIMENSTIONS[level][0];
		gridCols = GRID_DIMENSTIONS[level][1];
		mines = MINES[level];
		theGrid = new Grid[gridRows][gridCols];
		flaggedMines = 0;
		exposedMines = 0;
		totalgrids = gridRows * gridCols;
		System.out.println("totalgrids: " + totalgrids);

		setLayout(new GridLayout(gridRows, gridCols));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		
		int color=0;
		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				Grid grid = new Grid(GRID_SIZE, GRID_COLORS[++color % 2], i, j);
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
				incrementNeighborMineCount(row, col);				
			}		
		}
		
		// timer
		timer.setInitialDelay(0);

		
	}
	
	private void incrementNeighborMineCount(int row, int col) {
		for (int i = row - 1; i <= row + 1; i++) {
			if (i < 0 || i >= gridRows) {
				continue;
			}
			for (int j = col - 1; j <= col + 1; j++) {
				if (j < 0 || j >= gridCols) {
					continue;
				}
				if(i == row && j == col) {
					continue;
				}
				theGrid[i][j].incrementMineCount();
			}
		}
	}

	private void showMinesInGrid() {
		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				if (theGrid[i][j].isFlagged()) {
					if (theGrid[i][j].isMine()) {
						theGrid[i][j].getLabel().setBackground(Color.GREEN);
						theGrid[i][j].setLabelText("F");
					} else {
						theGrid[i][j].getLabel().setBackground(Color.RED);
						theGrid[i][j].setLabelText("F");						
					}
				} else {
					if (theGrid[i][j].isMine()) {
						theGrid[i][j].setLabelText("M");
					} 
				}
			}
		}
		repaint();
	}

	
	
	private class ClickOnGrid extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Grid clickedGrid = (Grid)e.getSource();
			
			if (e.getButton() == MouseEvent.BUTTON1) { // left button pressed
				if (clickedGrid.isMine()) {
					clickedGrid.setLabelText("M");
					clickedGrid.getLabel().setBackground(Color.RED);
					showMinesInGrid();
				} else {
					showContiguousZeroMineGrids(clickedGrid.getRow(), clickedGrid.getCol());
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) { // right button pressed
				flagMine(clickedGrid.getRow(), clickedGrid.getCol());
			}
			
			int totalgrids = gridRows * gridCols;
			System.out.println("flaggedMines: " + flaggedMines);
			System.out.println("exposedMines: " + exposedMines);
			if (flaggedMines + exposedMines == totalgrids) {
				System.out.println("Show game result");
			}

		}
		
		
		private void flagMine(int row, int col) {
			if (theGrid[row][col].isExposed()) {
				return;
			} 
			
			if(theGrid[row][col].isFlagged()) {
				flaggedMines--;
				theGrid[row][col].setFlagged(false);
				theGrid[row][col].setLabelText("");
			} else {
				flaggedMines++;
				theGrid[row][col].setFlagged(true);
				theGrid[row][col].setLabelText("F");
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
				exposedMines++;
				repaint();
				return;
			} else {
				theGrid[row][col].getLabel().setBackground(Color.LIGHT_GRAY);
				theGrid[row][col].setExposed(true);
				exposedMines++;
				repaint();
				for (int i = row - 1; i <= row + 1; i++) {
					if (i <0 || i >= gridRows) {
						continue;
					}
					for (int j = col - 1; j <= col + 1; j++) {
						if (j < 0 || j >= gridCols) {
							continue;
						}
						if (i == row && j== col) {
							continue;
						}
						showContiguousZeroMineGrids(i, j);
					}
				}
			}			
		}
	}	

	private class ClockListener implements ActionListener {
		private double time;
		NumberFormat numberForm;
		
		public ClockListener() {
			time = 0;
			numberForm = NumberFormat.getNumberInstance();
			numberForm.setMaximumFractionDigits(3);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			timeLabel.setText(numberForm.format(time));
		
			time += 0.1;
		}
	}
	


}
