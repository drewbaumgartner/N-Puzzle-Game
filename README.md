# N-Puzzle-Game
N Puzzle game made using Java with the Processing library

This program is an experiment with the following: processing library, priority queues, and A* algorithm with manhattan distance. The program is the puzzle game known as the "N Puzzle". A board of N x N size is generated and randomly scrambled into a puzzle.  The object of the game is to move tiles around until the board represents the unsrambled state.  The user can click the "Solve Puzzle" button and after a few moments the program will print out a complete series of steps to take to solve the current puzzle.  

Things that have yet to be implemented (2/27/2017):

Reset Button - The plan for this button is to provide the user a way to "reset" the board to its "solved state".

Shuffle Button - The plan for this button is to provide the user a way to shuffle the board.

Things that are not working correctly (2/27/2017):

The program currently cannot solve a 4x4 (parity checking is most likely the issue).  I am in the process of fixing this!


Resources used:

https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html - Useful website for determining the parity and solvability of a specific N puzzle.  

https://processing.org/reference/ - Reference for the processing library


