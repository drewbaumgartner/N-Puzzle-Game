import processing.core.PApplet;

public class NPuzzleGUI extends PApplet {

	private final int N_SIZE = 3;
	private Board gameBoard;
	
	public static void main(String[] args) {
		PApplet.main("NPuzzleGUI");
	}
	
	public void setup()
	{
		size(N_SIZE * 100, N_SIZE * 100);
		gameBoard = new Board(this, N_SIZE);
		gameBoard.shuffleBoard();
		noLoop();
	}
	
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
}
