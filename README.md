# N-Puzzle-Game
N Puzzle game made using Java with the Processing library

This program is an experiment with the following: processing library, priority queues, and A* algorithm with manhattan distance. The program is the puzzle game known as the "N Puzzle". A board of N x N size is generated and randomly scrambled into a puzzle.  The object of the game is to move tiles around until the board represents the unsrambled state.  The user can click the "Solve Puzzle" button and after a few moments the program will print out a complete series of steps to take to solve the current puzzle.  

Things that have yet to be implemented (2/27/2017):

Reset Button - The plan for this button is to provide the user a way to "reset" the board to its "solved state".

Caveat:

Boards with size 4 or greater (4x4, 5x5, ..., NxN) are now able to be solved but they take an extremely large amount of time and memory to do so. 


Resources used:

https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html - Useful website for determining the parity and solvability of a specific N puzzle.  

https://processing.org/reference/ - Reference for the processing library


