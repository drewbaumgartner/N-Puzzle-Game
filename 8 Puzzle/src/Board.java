import java.util.Random;

import processing.core.*;

public class Board implements Comparable<Board>{
	private PApplet parent;
	private Tile[][] board;
	private int[] parityCheckArray;
	private int boardSize;
	private int numMovesToSolve = 0;
	private int blankRow = 0; // Stores the row index of the blank tile
	private int blankColumn = 0; // Stores the column index of the blank tile
	private int blankValue = 0;
	private boolean solvable = false;
	
	
	public Board(PApplet p, int size)
	{
		parent = p;
		boardSize = size;
		blankRow = boardSize - 1;
		blankColumn = boardSize - 1;
		blankValue = boardSize * boardSize;
		buildBoard();
	}
	
	public void display()
	{
		for(int row = 0; row < board.length; row++)
		{
			for(int column = 0; column < board[row].length; column++)
			{
				board[row][column].display(row, column);
			}
		}
	}
	
	// Builds the square board of tiles
	private void buildBoard()
	{
		board = new Tile[boardSize][boardSize];
				
		int number = 1;
		
		for(int row = 0; row < board.length; row++)
		{
			for(int column = 0; column < board[row].length; column++)
			{
				board[row][column] = new Tile(parent, number, boardSize);
				number++;
			}
		}
	}
	
	public int compareTo(Board other)
	{
		if(numMovesToSolve == other.getNumberOfMoves())
		{
			return 0;
		}
		else if(numMovesToSolve > other.getNumberOfMoves())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	// When the Left mouse button is clicked, if it is hovering over a tile that is touching the blank tile, then those tiles swap places
	public void mouseClicked()
	{
		int temp = 0;
		
		for(int row = 0; row < board.length; row++)
		{
			for(int column = 0; column < board[row].length; column++)
			{
				if(board[row][column].isHovering() && parent.mouseButton == parent.LEFT && isNeighborBlank(row, column))
				{
					temp = board[row][column].getNumber(); // stores numeric value of the clicked tile
					board[row][column].setNumber(blankValue);
					board[blankRow][blankColumn].setNumber(temp);
					blankRow = row;
					blankColumn = column;
					parent.redraw();
				}
			}
		}
	}
	
	// Check the neighboring tiles for the empty tile.  Checks left, right, down, and up.  If the empty tile is a neighbor then return true.
	public boolean isNeighborBlank(int rowIndex, int columnIndex)
	{
		// Check Left
		if(columnIndex - 1 < board[rowIndex].length && columnIndex - 1 >= 0 && board[rowIndex][columnIndex - 1].getNumber() == blankValue) return true;
		// Check Right
		if(columnIndex + 1 < board[rowIndex].length && board[rowIndex][columnIndex + 1].getNumber() == blankValue) return true;
		// Check Down
		if(rowIndex + 1 < board.length && board[rowIndex + 1][columnIndex].getNumber() == blankValue) return true;
		// Check Up
		if(rowIndex - 1 < board.length && rowIndex - 1 >= 0 && board[rowIndex - 1][columnIndex].getNumber() == blankValue) return true;

		return false;
	}
	
	// Shuffles the board until a solvable board is found
	public void shuffleBoard()
	{
		Random random = new Random();
		
		while(!(solvable))
		{
			for(int row = board.length - 1; row > 0; row--)
			{
				for(int column = board[row].length - 1; column > 0; column--)
				{
					int r = random.nextInt(row + 1);
					int c = random.nextInt(column + 1);
					
					Tile temp = board[row][column];
					board[row][column] = board[r][c];
					board[r][c] = temp;
				}
			}
			isSolvable();
		}
		setBlankCoordinates();
	}
	
	// Sets the boolean "isSolvable" to true if the current shuffled board can be solved
	private void isSolvable()
	{
		int numInversions = findNumberOfInversions();		
		
		// If the number of inversions is odd, then the puzzle cannot be solved
		if(numInversions % 2 == 1)
		{
			solvable = false;
		}
		// If the number of inversions is even, then the puzzle can be solved
		else
		{
			solvable = true;
		}
	}
	
	// Calculates the number of inversions in the shuffled array.  Helper function for the isSolvable method
	private int findNumberOfInversions()
	{
		fillParityCheckArray();
		int temp = 0;
		
		// Loop through the shuffled array and compare each element[i] with its next elements[i+1], [i+2], [i+3], ...
		for(int i = 0; i < parityCheckArray.length; i++)
		{
			for(int j = i + 1; j < parityCheckArray.length; j++)
			{
				if(parityCheckArray[i] > parityCheckArray[j] && parityCheckArray[i] != blankValue  && parityCheckArray[j] != blankValue)
				{
					temp++;
				}
			}
		}
		return temp;
	}
	
	// Fills the 1D array, parityCheckArray, with the numeric values from the board[][] starting from top left and ending at bottom right
	private void fillParityCheckArray()
	{
		parityCheckArray = new int[boardSize * boardSize];
		
		int i = 0;
		
		for(int row = 0; row < board.length; row++)
		{
			for(int column = 0; column < board[row].length; column++)
			{
				parityCheckArray[i] = board[row][column].getNumber();
				i++;
			}
		}
	}
	
	// Finds the indexes where the blank tile is located
	private void setBlankCoordinates()
	{
		for(int row = 0; row < board.length; row++)
		{
			for(int column = 0; column < board[row].length; column++)
			{
				if(board[row][column].getNumber() == blankValue)
				{
					blankRow = row;
					blankColumn = column;
				}
			}
		}
	}
	
	public void mouseMoved()
	{
		for(Tile[] row : board) for(Tile t : row) t.isHovering();
		parent.redraw();
	}
	
	public int getNumberOfMoves()
	{
		return numMovesToSolve;
	}
	
	public void setNumberOfMoves(int n)
	{
		numMovesToSolve = n;
	}
	
}
