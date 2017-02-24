import java.util.Random;

import processing.core.PApplet;

public class NPuzzleGUI extends PApplet {

	private final int N_SIZE = 3;
	private Node grid[][];	
	private int[] shuffled;
	private int[] blankCoords = {N_SIZE - 1, N_SIZE - 1};
	private boolean solvable = false;
	
	public static void main(String[] args) {
		PApplet.main("NPuzzleGUI");
	}
	
	public void setup()
	{
		size(N_SIZE * 100, N_SIZE * 100);
		buildGrid();
		shuffleGrid();
		noLoop();
	}
	
	public void draw()
	{
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				grid[row][column].display(row, column);
			}
		}
	}
	
	// This function is called automatically by the processing library every time the mouse is moved
	public void mouseMoved()
	{
		// Loops through each node "n" in the 2D array "grid[][]" and checks if the mouse is hovering over the node
		for(Node[] row : grid) for(Node n : row) n.isHovering();
		// Call redraw to redraw the screen because in our "Node" class there is code to change the border color if the mouse is hovering over the node
		redraw();
	}
	
	// This function is called automatically by the processing library whenever a mouse button is clicked and released
	public void mouseClicked()
	{
		int temp = 0;
		
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				// If the mouse is hovering over the node at indexes grid[row][column] and the mouse button that was clicked was the left mouse button
				// and if the clicked node has the blank neighbor, then swap positions with the clicked node and the blank node
				if(grid[row][column].isHovering() && mouseButton == LEFT && isNeighborBlank(row, column))
				{
					temp = grid[row][column].getNumber(); // stores numeric value of the clicked node
					grid[row][column].setNumber(N_SIZE * N_SIZE);
					grid[blankCoords[0]][blankCoords[1]].setNumber(temp);
					blankCoords[0] = row;
					blankCoords[1] = column;
					redraw();
				}
			}
		}
	}
	
	// Check the neighboring nodes for the empty node.  Checks left, right, down, and up.  If the empty node is a neighbor then return true.
	public boolean isNeighborBlank(int rowIndex, int columnIndex)
	{
		// Check Left
		if(columnIndex - 1 < grid[rowIndex].length && columnIndex - 1 >= 0 && grid[rowIndex][columnIndex - 1].getNumber() == N_SIZE * N_SIZE) return true;
		// Check Right
		if(columnIndex + 1 < grid[rowIndex].length && grid[rowIndex][columnIndex + 1].getNumber() == N_SIZE * N_SIZE) return true;
		// Check Down
		if(rowIndex + 1 < grid.length && grid[rowIndex + 1][columnIndex].getNumber() == N_SIZE * N_SIZE) return true;
		// Check Up
		if(rowIndex - 1 < grid.length && rowIndex - 1 >= 0 && grid[rowIndex - 1][columnIndex].getNumber() == N_SIZE * N_SIZE) return true;

		return false;
	}
	
	// Shuffles the grid and once it is shuffled the program then calls "setBlankCoordinates" to find the blank tile's indexes (coordinates)
	public void shuffleGrid()
	{
		Random random = new Random();
		
		while(!(solvable))
		{
			for(int row = grid.length - 1; row > 0; row--)
			{
				for(int column = grid[row].length - 1; column > 0; column--)
				{
					int r = random.nextInt(row + 1);
					int c = random.nextInt(column + 1);
					
					Node temp = grid[row][column];
					grid[row][column] = grid[r][c];
					grid[r][c] = temp;
				}
			}
			
			isSolvable();
		}

		
		setBlankCoordinates();
	}
	
	
	public void isSolvable()
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
	public int findNumberOfInversions()
	{
		fill1DArray();
		int temp = 0;
		
		// Loop through the shuffled array and compare each element[i] with its next elements[i+1], [i+2], [i+3], ...
		for(int i = 0; i < shuffled.length; i++)
		{
			for(int j = i + 1; j < shuffled.length; j++)
			{
				if(shuffled[i] > shuffled[j])
				{
					temp++;
				}
			}
		}
		
		return temp;
	}
	
	// Fills the 1D array named "shuffled" with the numeric values from the grid[][] array starting from top left and ending at bottom right
	public void fill1DArray()
	{
		shuffled = new int[N_SIZE * N_SIZE];
		
		int i = 0;
		
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				shuffled[i] = grid[row][column].getNumber();
				i++;
			}
		}
	}
	
	// Finds the indexes where the blank tile is located and stores them in the array called blankCoords
	public void setBlankCoordinates()
	{
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				if(grid[row][column].getNumber() == N_SIZE * N_SIZE)
				{
					blankCoords[0] = row;
					blankCoords[1] = column;
				}
			}
		}
	}
	
	// Construct the data grid of Nodes.  Assigns a value to each node in "ascending" order
	// The bottom rightmost node is the "dummy" node (the empty space on the game board)
	// Example: N_SIZE = 3, then grid = 3x3.  
	// [ 1 ][ 2 ][ 3 ]
	// [ 4 ][ 5 ][ 6 ]
	// [ 7 ][ 8 ][ 9 ]
	public void buildGrid()
	{
		grid = new Node[N_SIZE][N_SIZE];
		
		int number = 1;
		
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				grid[row][column] = new Node(this, number, N_SIZE);	
				number++;
			}
		}
	}
	
	// Used for debugging purposes only
	public void printGrid()
	{
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				System.out.print("[ " + grid[row][column].getNumber() + " ]");
			}
			System.out.println();
		}
	}
}
