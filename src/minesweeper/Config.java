package minesweeper;

public interface Config {
	public static final int BEGINNER = 0;
	public static final int INTERMEDIATE = 1;
	public static final int EXPERT = 2;
	// all enumeration for level = {BEGINNER, INTERMEDIATE, EXPERT}
	public static final int GRID_DIMENSTIONS[][] = { 
		{7, 7}, // for BEGINNER
		{50, 50}, // for INTERMEDIATE
		{100, 100} // for EXPERT
	};
	
	public static final int GRID_SIZE = 30;
	public static final int[] MINES = {10, 50, 100};
	
	public static final int LOSE = 0;
	public static final int WIN = 1;

}
