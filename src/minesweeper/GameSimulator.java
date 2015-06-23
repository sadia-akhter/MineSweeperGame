package minesweeper;

import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameSimulator extends JFrame implements Config {
	
	public GameSimulator() {
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension max = new Dimension((int) (7 * GRID_SIZE * 1.5), (int)(7 * GRID_SIZE * 1.5));
		Dimension min = new Dimension(7 * GRID_SIZE , 7 * GRID_SIZE );
		this.setMaximumSize(max);
		this.setMinimumSize(min);
		this.setSize(min);
		this.setLocationRelativeTo(null);
		JPanel gameGUI = new MinesweeperGUI(this, BEGINNER);
		this.setContentPane(gameGUI);
	}

	public static void main(String[] args) {
		JFrame newGame = new GameSimulator();
		newGame.setVisible(true);

	}

}
