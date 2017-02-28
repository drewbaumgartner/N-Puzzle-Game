import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;

import processing.core.PApplet;

public class ButtonGUI extends JFrame{
	private JButton btnDisplaySolution;
	private JButton btnReset;
	private JButton btnShuffle;
	private NPuzzleGUI parent;
	
	public ButtonGUI(NPuzzleGUI p)
	{
		setSize(125, 150);
		setTitle("Buttons Menu");
		setLayout(new GridLayout(3,1));
		
		parent = p;
		
		btnDisplaySolution = new JButton("Display Solution");
		btnReset = new JButton("Reset");
		btnShuffle = new JButton("Shuffle");
		
		btnDisplaySolution.addActionListener(new ButtonListener());
		btnReset.addActionListener(new ButtonListener());
		btnShuffle.addActionListener(new ButtonListener());
		
		add(btnDisplaySolution);
		add(btnReset);
		add(btnShuffle);
		
		pack();
		setVisible(true);
	}
	
	// ActionListener for buttons
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String actionCommand = event.getActionCommand();
			
			if(actionCommand.equals("Display Solution"))
			{
				System.out.println("Solving puzzle...please wait a moment!");
				solvePuzzle();
			}
			else if(actionCommand.equals("Reset")) 
			{
				System.out.println("Reset!");
			}
			else if(actionCommand.equals("Shuffle"))
			{
				System.out.println("Shuffle the board!");
				shuffleBoard();
			}
		}
	}
	
	public void solvePuzzle()
	{
		parent.solvePuzzle();
		parent.printSolution();
	}
	
	public void shuffleBoard()
	{
		parent.shuffleBoard();
	}
}
