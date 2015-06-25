package minesweeper;

import java.awt.*;

public interface Config {
	public static final int BEGINNER = 0;
	public static final int INTERMEDIATE = 1;
	public static final int EXPERT = 2;
	// all enumeration for level = {BEGINNER, INTERMEDIATE, EXPERT}
	public static final int GRID_DIMENSTIONS[][] = { 
		{7, 7}, // for BEGINNER
		{49, 49}, // for INTERMEDIATE
		{99, 99} // for EXPERT
	};
	public static final int GRID_SIZE = 50;
	public static final Color GRID_COLORS[] = {new Color(50,75,210), new Color(50,75,230)};
	public static final Color GRID_COLORS_EXPOSED[] = {new Color(210,210,210), new Color(220,220,220)};
	
	public static final int[] MINES = {10, 50, 100};
	
	public static final int LOSE = 0;
	public static final int WIN = 1;

}
