import processing.core.PApplet;

public class NPuzzleGUI extends PApplet {

	private final int N_SIZE = 3;
	private Node grid[][];	
	private int[] blankCoords = {N_SIZE - 1, N_SIZE - 1};
	
	public static void main(String[] args) {
		PApplet.main("NPuzzleGUI");
	}
	
	public void setup()
	{
		size(N_SIZE * 100, N_SIZE * 100);
		buildGrid();
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
	
	public void mouseMoved()
	{
		for(Node[] row : grid) for(Node n : row) n.isHovering();
		redraw();
	}
	
	public void mouseClicked()
	{
		int temp = 0;
		
		for(int row = 0; row < grid.length; row++)
		{
			for(int column = 0; column < grid[row].length; column++)
			{
				if(grid[row][column].isHovering() && mouseButton == LEFT && isNeighborBlank(row, column))
				{
					// Swap clicked with blank coordinates
					temp = grid[row][column].getNumber();
					grid[row][column].setNumber(N_SIZE * N_SIZE);
					grid[blankCoords[0]][blankCoords[1]].setNumber(temp);
					blankCoords[0] = row;
					blankCoords[1] = column;
					redraw();
				}
			}
		}
	}
	
	// Check the neighboring nodes for the empty node.  Checks left, right, up, and down.  If the empty node is a neighbor then return true.
	public boolean isNeighborBlank(int rowIndex, int columnIndex)
	{
		// Check Left
		if(columnIndex - 1 < grid[rowIndex].length && columnIndex - 1 >= 0 && grid[rowIndex][columnIndex - 1].getNumber() == N_SIZE * N_SIZE) return true;
		// Check Right
		if(columnIndex + 1 < grid[rowIndex].length && grid[rowIndex][columnIndex + 1].getNumber() == N_SIZE * N_SIZE) return true;
		// Check Up
		if(rowIndex + 1 < grid.length && grid[rowIndex + 1][columnIndex].getNumber() == N_SIZE * N_SIZE) return true;
		// Check Down
		if(rowIndex - 1 < grid.length && rowIndex - 1 >= 0 && grid[rowIndex - 1][columnIndex].getNumber() == N_SIZE * N_SIZE) return true;

		return false;
	}
	
	public void shuffleGrid()
	{
		
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
	
}
