package minesweeper;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameSimulator extends JFrame implements Config {
	
	public GameSimulator() {
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel gridUI = new GridUI(BEGINNER);
		setContentPane(gridUI);
		pack();
	}

	public static void main(String[] args) {
		JFrame newGame = new GameSimulator();
		newGame.setVisible(true);

	}

}
