import processing.core.*;

public class Node {
	private PApplet parent;
	private int number = 0;
	private int boardSize = 0;
	private int foregroundColor = 0;
	private int backgroundColor = 0;
	private float xPos = 0;
	private float yPos = 0;
	private float xSize = 0;
	private float ySize = 0;
	private boolean isHovering;
	
	
	public Node(PApplet p, int n, int boardSize)
	{
		parent = p;
		number = n;
		this.boardSize = boardSize;
	}
	
	public void display(int rowIndex, int columnIndex)
	{
		float xScale = 2 * boardSize; // double column array length [Row][Column] - used as a scaling factor
		float yScale = 2 * boardSize; // double the row array length [Row][Column] - used as a scaling factor 
		xSize = parent.width / xScale; // this is the size of the square in the X axis
		ySize = parent.height / yScale; // this is the size of the square in the Y axis
		xPos = (parent.width * ((1 + columnIndex * 2) / xScale)); // position of the square based on its center in the X axis.  The (1 + index * 2) is the offset from the left edge (X axis)
		yPos = (parent.height * ((1 + rowIndex * 2) / yScale));  // position of the square based on its center in the Y axis  The (1 + index * 2) is the offset from the top edge (Y axis)
		
		parent.stroke(0); // set the outline of the geometry to black
		
		this.setBackgroundColor(number); // set the background color to the "dark" variant
		parent.fill(backgroundColor); 
		parent.rectMode(PConstants.RADIUS);
		parent.rect(xPos, yPos, xSize, ySize); 
		

		if(isHovering)
		{
			parent.stroke(255);
		}
		
		if(number != boardSize * boardSize)
		{
			this.setForegroundColor(number);
			parent.fill(foregroundColor);
			parent.rectMode(PConstants.CENTER);
			parent.rect(xPos, yPos, parent.width * 9/32, parent.height * 9/32, 5);
		}
		
		parent.fill(0);
		parent.textSize(40);
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
		parent.text(number, xPos, yPos - 5);
	}
	
	public boolean isHovering()
	{
		return isHovering = parent.mouseX > xPos - xSize && parent.mouseX < xPos + xSize && parent.mouseY > yPos -ySize && parent.mouseY < yPos + ySize;
	}
	
	public void setForegroundColor(int numberValue)
	{
		// If the number is equal to the "unplayed" number (3*3 board is #9, 4*4 board is #16, etc).
		if(numberValue == boardSize * boardSize)
		{
			foregroundColor = parent.color(0, 0, 0);
		}
		// If the number is even
		else if(numberValue % 2 == 0)
		{
			foregroundColor = parent.color(51, 204, 204);
		}
		// If the number is odd
		else if(numberValue % 2 == 1)
		{
			foregroundColor = parent.color(153, 51, 255);
		}
	}
	
	public void setBackgroundColor(int numberValue)
	{
		// If the number is equal to the "unplayed" number (3*3 board is #9, 4*4 board is #16, etc).
		if(numberValue == boardSize * boardSize)
		{
			backgroundColor = parent.color(0, 0, 0);
		}
		// If the number is even
		else if(numberValue % 2 == 0)
		{
			backgroundColor = parent.color(36, 143, 143);
		}
		// If the number is odd
		else if(numberValue % 2 == 1)
		{
			backgroundColor = parent.color(115, 0, 230);
		}
	}
	
	public void setNumber(int n)
	{
		number = n;
	}
	
	public int getNumber()
	{
		return number;
	}
	
}
