import java.util.PriorityQueue;
import java.util.Stack;

import processing.core.PApplet;

public class NPuzzleGUI extends PApplet {

	private final int N_SIZE = 3;
	private Board gameBoard;
	private BoardData initialState;
	private BoardData solvedState;
	private PriorityQueue<BoardData> boardQueue = new PriorityQueue<BoardData>();
	private Stack<BoardData> solutions = new Stack<BoardData>();
	private int totalMoves = 0;
	
	public static void main(String[] args) {
		PApplet.main("NPuzzleGUI");
		
	}
	
	// This method acts as an "On Load" method and will run once when the program first starts (part of the processing library)
	public void setup()
	{
		size(N_SIZE * 100, N_SIZE * 100);
		setSolvedState();
		gameBoard = new Board(this, N_SIZE);
		shuffleBoard();
		//gameBoard.shuffleBoard();
		new ButtonGUI(this);
		noLoop();
	}
	
	// This method is called automatically after setup() and draws the graphics on the screen.  It loops continuously proportional to the frame rate.  I turn this feature off 
	// by declaring the noLoop() function in the setup().  To call draw() I use redraw() to redraw the screen once.  This is a processing library function
	public void draw()
	{
		gameBoard.display();
	}
	
	// This function is called automatically by the processing library every time the mouse is moved
	public void mouseMoved()
	{
		gameBoard.mouseMoved();
	}
	
	// This function is called automatically by the processing library whenever a mouse button is clicked and released
	public void mouseClicked()
	{
		gameBoard.mouseClicked();
	}
	
	public void shuffleBoard()
	{
		gameBoard.shuffleBoard();
	}
	
	// This function probably needs to be broken down into more pieces. It uses the A* algorithm paired with the manhattan distance to determine the appropriate set of moves 
	// for solving the puzzle
	public void solvePuzzle()
	{
		BoardData temp; // holds the dequeued board
		BoardData[] neighbors; // this array stores all 4 neighbors (left, right, down, and up)
		initialState = gameBoard.getBoardData(); // stores the initial shuffled board
		boolean firstPass = true; // i use this boolean to get around a null pointer exception in the first iteration of the while loop (temp's previous will always be null for the first loop)
		
		boardQueue.add(initialState); // add initial scrambled board as starting point for solving the puzzle
		temp = boardQueue.poll(); // dequeue the minimum priority board (number of moves + manhattanDistance to solve the puzzle)
		
		// Loop until the dequeued game board matches the solvedState
		while(!(temp.equals(solvedState)))
		{
			neighbors = temp.getNeighboringBoards(); 
			totalMoves++;
			
			// loop through all 4 neighbors
			for(int i = 0; i < neighbors.length; i++)
			{
				if(neighbors[i] != null) // if a neighbor does not exist, do nothing
				{
					neighbors[i].setPrevious(temp); // store the previous board into the neighbor's previous variable
					
					// if temp has a previous board, compare it to the current neighbor, if they are the same board then do not enqueue the neighbor
					// (this means we've already checked the neighbors board in a past iteration)
					if(temp.getPrevious() != null && !(neighbors[i].equals(temp.getPrevious()))) 
					{
						neighbors[i].setMoves(totalMoves); // stores the total number of iterations through the while loop
						neighbors[i].calculatePriority(); // determines priority for the board (number of moves taken + manhattan distance)
						boardQueue.add(neighbors[i]); // add the neighbor to the priority queue	
					}
					// this firstPass is set to true initially and will only run once
					else if(firstPass)
					{
						neighbors[i].setMoves(totalMoves);
						neighbors[i].calculatePriority();	
						boardQueue.add(neighbors[i]);
						firstPass = false;
					}
				}
			}
			System.out.println("totalMoves = " + totalMoves); // this was used for debugging purposes to see how many iterations it takes to solve a puzzle
			temp = boardQueue.poll(); // dequeue the minimum priority board (number of moves + manhattanDistance to solve the puzzle)
		}
		
		// After the solution is found, follow the previous pointers from temp (the last dequeued board state) all way back until a previous pointer is null
		// Pushing each previous pointer onto a stack so that way the order of steps is reversed and when popped, the solution is printed in the correct order 
		while(temp.getPrevious() != null)
		{
			solutions.push(temp);
			temp = temp.getPrevious();
		}
	}
	
	// Prints the solution to the puzzle in the console.
	public void printSolution()
	{
		BoardData temp;
		int i = 1; // counter for step number (step 1, step 2, step 3, ... , step n)
		
		System.out.println("Starting Board");
		initialState.printBoard(); // print the starting board state
		
		// While the solution stack has boards to be displayed
		while(!(solutions.isEmpty()))
		{
			temp = solutions.pop(); // get the next step
			System.out.println("Step #" + i); 
			temp.printBoard(); // print the board state of the current step
			i++;
		}
	}
	
	// This function is called only once and during the setup().  It stores what the solved board should look like in the solvedState variable.
	// For the purposes of the 3x3 board		For the purposes of the 4x4 board
	// [ 1 ][ 2 ][ 3 ]							[ 1 ][ 2 ][ 3 ][ 4 ]
	// [ 4 ][ 5 ][ 6 ]							[ 5 ][ 6 ][ 7 ][ 8 ]
	// [ 7 ][ 8 ][   ]							[ 9 ][ 10][ 11][ 12]
	//											[ 13][ 14][ 15][   ]
	private void setSolvedState()
	{
		int[][] temp = new int[N_SIZE][N_SIZE];
		int i = 1;
		
		for(int row = 0; row < N_SIZE; row++)
		{
			for(int column = 0; column < N_SIZE; column++)
			{
				temp[row][column] = i;
				i++;
			}
		}
		
		solvedState = new BoardData(temp, N_SIZE - 1, N_SIZE - 1);
	}
}
