package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class MinesweeperGUI extends JPanel implements Config{

	// Fields for GUI
	private JFrame frame;
	private JPanel gridPanel;
	private JPanel comboPanel;	
	private JLabel timerLabel;
	private JLabel minesLeftLabel;
	
	// Fields for game logic
	private int level;
	private int gridRows;
	private int gridCols;
	private int mines;
	private Grid[][] theGrid;
	private int flaggedMines;
	private int exposedMines;
	private boolean gameStart;
	private boolean gameOver;
	
	// timer
	private ClockListener clockListener;
	private Timer timer;

	
	public MinesweeperGUI(JFrame jframe, int gameLevel) {
		
		if (gameLevel != BEGINNER && gameLevel != INTERMEDIATE && gameLevel !=EXPERT) {
			System.exit(0);
		}

		frame = jframe;
		level = gameLevel;
		gridRows = GRID_DIMENSTIONS[level][0];
		gridCols = GRID_DIMENSTIONS[level][1];
		mines = MINES[level];
		theGrid = new Grid[gridRows][gridCols];
		flaggedMines = 0;
		exposedMines = 0;
		gameStart = false;
		gameOver = false;
		
		this.setLayout(new BorderLayout());
		
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(gridRows, gridCols));
				
		int colorIndex = 0;
		for (int i = 0; i < gridRows; i++) {
			for (int j = 0; j < gridCols; j++) {
				Grid grid = new Grid(GRID_SIZE, GRID_COLORS[++colorIndex%2], i, j);
				grid.addMouseListener(new ClickOnGrid());
				gridPanel.add(grid);
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
		
		comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(1,2));
		
		timerLabel = new JLabel("0.0");
		comboPanel.add(timerLabel);
		
		minesLeftLabel = new JLabel(Integer.toString(mines), SwingConstants.RIGHT);
		comboPanel.add(minesLeftLabel);
		
		this.add(comboPanel, BorderLayout.PAGE_START);
		this.add(gridPanel, BorderLayout.CENTER);
		
		// timer
		clockListener = new ClockListener();
		timer = new Timer(10, clockListener);
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
			
			if (!gameStart) {
				gameStart = true;
				gameOver = false;
				timer.start();
			}

			if(gameOver) {
				return;
			}
			
			Grid clickedGrid = (Grid)e.getSource();
			
			if (e.getButton() == MouseEvent.BUTTON1) { // left button pressed
				if (clickedGrid.isMine()) {
					clickedGrid.setLabelText("M");
					clickedGrid.getLabel().setBackground(Color.RED);
					showMinesInGrid();
					stopGame();
					JOptionPane.showMessageDialog(frame, "Mine Explodes!\nGame Over");
				} else {
					showContiguousZeroMineGrids(clickedGrid.getRow(), clickedGrid.getCol());
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) { // right button pressed
				flagMine(clickedGrid.getRow(), clickedGrid.getCol());
			}
			
			int totalgrids = gridRows * gridCols;
			if (flaggedMines + exposedMines == totalgrids) {
				// successfully completed the game
				stopGame();
				JOptionPane.showMessageDialog(frame, "You win!");
			}

		}
		
		private void stopGame() {
			timer.stop();
			gameOver = true;			
		}
		
		private void flagMine(int row, int col) {
			if (theGrid[row][col].isExposed()) {
				return;
			} 
			
			if(theGrid[row][col].isFlagged()) {
				flaggedMines--;
				theGrid[row][col].setFlagged(false);
				theGrid[row][col].setLabelText("");
				minesLeftLabel.setText(Integer.toString(mines - flaggedMines));
				repaint();
			} else {
				flaggedMines++;
				theGrid[row][col].setFlagged(true);
				theGrid[row][col].setLabelText("F");
				minesLeftLabel.setText(Integer.toString(mines - flaggedMines));
			}

		}
		
		private void showContiguousZeroMineGrids(int row, int col) {
			if (row < 0 || row >= gridRows
					|| col < 0 || col >= gridCols) {
				return;
			} 
			if (theGrid[row][col].isExposed() || theGrid[row][col].isFlagged()) {
				return;
			} 
			if (theGrid[row][col].getMineCount() != 0) {
				Color oldGridColor = theGrid[row][col].getGridColor();
				Color newGridColor = (oldGridColor == GRID_COLORS[0] ? GRID_COLORS_EXPOSED[0] : GRID_COLORS_EXPOSED[1]);
				theGrid[row][col].getLabel().setBackground(newGridColor);
				theGrid[row][col].setLabelText(Integer.toString(theGrid[row][col].getMineCount()));
				theGrid[row][col].setExposed(true);
				exposedMines++;
				repaint();
				return;
			} else {
				Color oldGridColor = theGrid[row][col].getGridColor();
				Color newGridColor = (oldGridColor == GRID_COLORS[0] ? GRID_COLORS_EXPOSED[0] : GRID_COLORS_EXPOSED[1]);
				theGrid[row][col].getLabel().setBackground(newGridColor);
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
			timerLabel.setText(numberForm.format(time));
		
			time += 0.01;
		}
	}

}
