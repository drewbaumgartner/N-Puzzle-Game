
public class BoardData implements Comparable<BoardData>{
	private int numbers[][];
	private int moves = 0;
	private int manhattanDistance = 0;
	private int priority = 0;
	private int blankRow = 0;
	private int blankColumn = 0;
	private BoardData previous = null;
	
	public BoardData(Board b, int blankRow, int blankColumn)
	{
		numbers = new int[b.getBoardSize()][b.getBoardSize()];
		
		for(int row = 0; row < numbers.length; row++)
		{
			for(int column = 0; column < numbers[row].length; column++)
			{
				numbers[row][column] = b.getTileAt(row, column).getNumber();
			}
		}
		
		this.blankRow = blankRow;
		this.blankColumn = blankColumn;
	}
	
	// This constructor is used for generating duplicate copies of a board in order to change positions for each neighbor.
	public BoardData(int[][] board, int blankRow, int blankColumn)
	{
		numbers = new int[board.length][board[0].length];
		
		for(int row = 0; row < numbers.length; row++)
		{
			for(int column = 0; column < numbers[row].length; column++)
			{
				numbers[row][column] = board[row][column];
			}
		}
		
		this.blankRow = blankRow;
		this.blankColumn = blankColumn;		
	}
	
	public void setMoves(int n)
	{
		moves = n;
	}
	
	public int getMoves()
	{
		return moves;
	}
	
	public void setManhattanDistance(int n)
	{
		manhattanDistance = n;
	}
	
	public int getManhattanDistance()
	{
		return manhattanDistance;
	}
	
	public void setPriority(int n)
	{
		priority = n;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public int getNumberAt(int rowIndex, int columnIndex)
	{
		return numbers[rowIndex][columnIndex];
	}
	
	public void setNumberAt(int rowIndex, int columnIndex, int value)
	{
		numbers[rowIndex][columnIndex] = value;
	}
	
	public BoardData getPrevious()
	{
		return previous;
	}
	
	public void setPrevious(BoardData prev)
	{
		previous = prev;
	}
	
	public int getBlankRow()
	{
		return blankRow;
	}
	
	public int getBlankColumn()
	{
		return blankColumn;
	}
	
	public void setBlankRow(int row)
	{
		blankRow = row;
	}
	
	public void setBlankColumn(int column)
	{
		blankColumn = column;
	}
	
	// This method checks the board and calculates the "neighbors".  A neighbor is a board state that can be achieved from the given board by moving just 1 tile.
	// Example: Listed below is an example of the neighbors from the given board.  The blank space "X" is in the middle row and can be moved in 3 different directions.
	// If it is moved "Up" (swap places with the 3) it generates the "Up neighbor".  If it is moved "left" (swap places with the 2).  And lastly, it can move "down" (swap with 5).
	// numbers[][] has 3 neighbors ==> 	"Up neighbor"		"Left neighbor"		"Down neighbor"
	// [ 8 ][ 1 ][ 3 ]					[ 8 ][ 1 ][ X ]		[ 8 ][ 1 ][ 3 ]		[ 8 ][ 1 ][ 3 ]
	// [ 4 ][ 2 ][ X ]					[ 4 ][ 2 ][ 3 ]		[ 4 ][ X ][ 2 ]		[ 4 ][ 2 ][ 5 ]
	// [ 7 ][ 6 ][ 5 ]					[ 7 ][ 6 ][ 5 ]		[ 7 ][ 6 ][ 5 ]		[ 7 ][ 6 ][ X ]
	public BoardData[] getNeighboringBoards()
	{
		BoardData[] neighbors = new BoardData[4]; // 4 neighboring boards is the maximum any board can possibly have (left, right, down, up)
		int temp = 0;
		
		for(int i = 0; i < neighbors.length; i++)
		{
			neighbors[i] = new BoardData(numbers, blankRow, blankColumn);
		}
		
		// LEFT - If the board has a valid left neighbor, swap the places and store the board, else store null.
		if(neighbors[0].blankRow < numbers.length && neighbors[0].blankColumn - 1 < numbers.length && neighbors[0].blankColumn - 1 >= 0)
		{
			temp = neighbors[0].getNumberAt(neighbors[0].blankRow, neighbors[0].blankColumn - 1);
			neighbors[0].setNumberAt(neighbors[0].blankRow, neighbors[0].blankColumn - 1, neighbors[0].getNumberAt(neighbors[0].blankRow, neighbors[0].blankColumn));
			neighbors[0].setNumberAt(neighbors[0].blankRow, neighbors[0].blankColumn, temp);
			neighbors[0].setBlankColumn(neighbors[0].blankColumn - 1);
			neighbors[0].calculateManhattanDistance();
		}
		else
		{
			neighbors[0] = null;
		}
		
		// RIGHT - If the board has a valid right neighbor, swap the places and store the board, else store null.
		if(neighbors[1].blankRow < numbers.length && neighbors[1].blankColumn + 1 < numbers.length)
		{
			temp = neighbors[1].getNumberAt(neighbors[1].blankRow, neighbors[1].blankColumn + 1);
			neighbors[1].setNumberAt(neighbors[1].blankRow, neighbors[1].blankColumn + 1, neighbors[1].getNumberAt(neighbors[1].blankRow, neighbors[1].blankColumn));
			neighbors[1].setNumberAt(neighbors[1].blankRow, neighbors[1].blankColumn, temp);
			neighbors[1].setBlankColumn(neighbors[1].blankColumn + 1);
			neighbors[1].calculateManhattanDistance();
		}
		else
		{
			neighbors[1] = null;
		}
		
		// DOWN - If the board has a valid down neighbor, swap the places and store the board, else store null.
		if(neighbors[2].blankRow + 1 < numbers.length && neighbors[2].blankColumn < numbers.length)
		{
			temp = neighbors[2].getNumberAt(neighbors[2].blankRow + 1, neighbors[2].blankColumn);
			neighbors[2].setNumberAt(neighbors[2].blankRow + 1, neighbors[2].blankColumn, neighbors[2].getNumberAt(neighbors[2].blankRow, neighbors[2].blankColumn));
			neighbors[2].setNumberAt(neighbors[2].blankRow, neighbors[2].blankColumn, temp);
			neighbors[2].setBlankRow(neighbors[2].blankRow + 1);
			neighbors[2].calculateManhattanDistance();
		}
		else
		{
			neighbors[2] = null;
		}
		
		// UP - If the board has a valid up neighbor, swap the places and store the board, else store null.
		if(neighbors[3].blankRow - 1 < numbers.length && neighbors[3].blankColumn < numbers.length && neighbors[3].blankRow - 1 >= 0)
		{
			temp = neighbors[3].getNumberAt(neighbors[3].blankRow - 1, neighbors[3].blankColumn);
			neighbors[3].setNumberAt(neighbors[3].blankRow - 1, neighbors[3].blankColumn, neighbors[3].getNumberAt(neighbors[3].blankRow, neighbors[3].blankColumn));
			neighbors[3].setNumberAt(neighbors[3].blankRow, neighbors[3].blankColumn, temp);
			neighbors[3].setBlankRow(neighbors[3].blankRow - 1);
			neighbors[3].calculateManhattanDistance();
		}
		else
		{
			neighbors[3] = null;
		}
		
		return neighbors;
	}
	
	// Calculates the manhattan distance = ( | x1 - x2 | + | y1 - y2 | )
	private void calculateManhattanDistance()
	{	
		for(int row = 0; row < numbers.length; row++)
		{
			for(int column = 0; column < numbers[row].length; column++)
			{
				int value = numbers[row][column];
				
				// If the value is not equal to the blank space's value
				if(value != numbers.length * numbers[row].length)
				{
					int targetX = (value - 1) / numbers.length;
					int targetY = (value - 1) % numbers.length;
					
					int xDis = row - targetX;
					int yDis = column - targetY;
					
					manhattanDistance = manhattanDistance + (Math.abs(xDis) + Math.abs(yDis));
				}
			}
		}
	}
	
	public void calculatePriority()
	{
		priority = moves + manhattanDistance;
	}
		
	public void printBoard()
	{
		for(int row = 0; row < numbers.length; row++)
		{
			for(int column = 0; column < numbers[row].length; column++)
			{
				if(numbers[row][column] == numbers.length * numbers.length)
				{
					System.out.print("[   ]");
				}
				else
				{
					System.out.print("[ " + numbers[row][column] + " ]");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// Returns true if the current game board matches the given game board
	public Boolean equals(BoardData that)
	{
		for(int row = 0; row < numbers.length; row++)
		{
			for(int column = 0; column < numbers[row].length; column++)
			{
				if(!(numbers[row][column] == that.getNumberAt(row, column)))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Used in the Priority Queue, this board is compared to a given board based on their priority value (number of moves taken to get to that board state plus their manhattan distance)
	public int compareTo(BoardData that)
	{
		if(priority == that.getPriority())
		{
			return 0;
		}
		else if(priority > that.getPriority())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
