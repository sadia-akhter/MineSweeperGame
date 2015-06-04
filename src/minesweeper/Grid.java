package minesweeper;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Grid extends JPanel {

	public static final int MAX_MINE_COUNT = 8;

	private boolean isMine;
	private int mineCount;
	private JLabel label;
	private String labelText;
	private int x;
	private int y;
	
	public Grid(int size, int row, int col) {
		if (size < 0) {
			System.exit(0);
		}
		
		isMine = false;
		mineCount = 0;
		x = row;
		y = col;
		labelText = "";
//		labelText = Integer.toString(x) + ", " + Integer.toString(y);
		label = new JLabel(labelText, JLabel.CENTER);
		label.setForeground(Color.BLACK);
//		label.setBackground(Color.CYAN);
		setLayout(new BorderLayout());
		add(label);
		
	}
	
	public boolean isMine() {
		return isMine;
	}
	
	public int getMineCount() {
		return mineCount;
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public String getLabelText() {
		return labelText;
	}
	
	public void setLabelText(String newLabelText) {
		labelText = newLabelText;
		label.setText(newLabelText);
	}
	
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	
	public boolean setMineCount(int newCount) {
		if (newCount < 0 || newCount > MAX_MINE_COUNT) {
			return false;
		} else {
			mineCount = newCount;
			return true;
		}
	}
	
	public boolean incrementMineCount() {
		if (mineCount == MAX_MINE_COUNT) {
			return false;
		} else {
			mineCount++;
			return true;
		}
	}

//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.setColor(Color.BLUE);
//		g.fillRect(0, 0, size, size); 
//	}
}
