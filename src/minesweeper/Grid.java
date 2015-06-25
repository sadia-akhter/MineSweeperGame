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
	private boolean exposed;
	private boolean flagged;
//	private Color gridColor;
	
	public Grid(int size, Color gridColor, int row, int col) {
		if (size < 0) {
			System.exit(0);
		}
		
		isMine = false;
		mineCount = 0;
		x = row;
		y = col;
		labelText = "";
		exposed = false;
		flagged = false;
//		this.gridColor = gridColor;
		
		Dimension dim = new Dimension(size, size);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setMaximumSize(dim);
		label = new JLabel(labelText, JLabel.CENTER);
		label.setBackground(gridColor);
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
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
	
	public Color getGridColor() {
		return label.getBackground();
	}
	
	public Color setGridColor(Color newColor) {
		Color oldGridColor = label.getBackground();
		label.setBackground(newColor);
		return oldGridColor;
	}
	
	public void setLabelText(String newLabelText) {
		labelText = newLabelText;
		label.setText(newLabelText);
	}
	
	public void setMine(boolean isMine) {
		this.isMine = isMine;
		mineCount = 0;
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
		if (isMine) {
			return false;
		}
		if (mineCount == MAX_MINE_COUNT) {
			return false;
		} else {
			mineCount++;
			return true;
		}
	}
	
	public int getRow() {
		return x;
	}
	
	public int getCol() {
		return y;
	}
	
	public boolean isExposed() {
		return exposed == true;
	}
	
	public void setExposed(boolean ex) {
		exposed = ex;
	}
	
	public boolean isFlagged() {
		return flagged == true;
	}
	
	public void setFlagged(boolean flag) {
		flagged = flag;
	}
	
	@Override
	public String toString() {
		return "isMine: " + isMine 
				+ "\nmineCount: " + mineCount
				+ "\nlabelText: " + labelText
				+ "\nx: " + x 
				+ "\ny: " + y;
	}
}
