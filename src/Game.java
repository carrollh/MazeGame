import java.util.Scanner;

/**
 * A simple maze game. The player should be able to 
 * type in one of the cardinal directions (N, E, S, W)
 * and watch an avatar move through the maze from
 * a random spot on the left to a random exit point on
 * the right.
 * 
 * @author <INSERT YOUR NAME>
 * @author Heath Carroll
 *
 */
public class Game {
	
	Maze maze;
	boolean exitWasFound = false;
	Scanner kb;
	
	/**
	 * A constructor allowing us to specify the
	 * dimensions of our maze.
	 * 
	 * @param rows
	 * @param cols
	 */
	public Game(int rows, int cols) {
		maze = new Maze(rows, cols);
		kb = new Scanner(System.in);
	}
	
	/**
	 * The main game loop. It allows the player to
	 * keep selecting a direction to move as long 
	 * as the exit has not been found.
	 */
	public void play() {
		while(!exitWasFound) {
			maze.drawMaze();
			playerMove(maze);
			exitWasFound = maze.checkForExit();
		}
		
		congratulatePlayer();
	}
	
	/**
	 * Takes a single character from the user and
	 * determines which direction to move their 
	 * avatar. It also performs basic collision
	 * detection.
	 * This method should rely on the Maze.move methods.
	 * 
	 * @param maze
	 */
	private void playerMove(Maze maze) {
		System.out.print("Direction? (N,E,S,W): ");
		char dir = kb.next().toUpperCase().trim().charAt(0);
		// try to move whichever direction the user chose.
		// 		if they chose a valid, but bad direction
		//			print the maze
		//			print an error message
		//			recurse on this method.
		//		otherwise do nothing
		// if their input was invalid
		//		print the maze
		//		print an error message
		//		recurse on this method.
		switch(dir) {
			case 'N':
				if(!maze.moveNorth()) {
					maze.drawMaze();
					System.out.print("Um, that's a wall...  ");
					playerMove(maze);
				}
				break;
			case 'E':
				if(!maze.moveEast()) {
					maze.drawMaze();
					System.out.print("Um, that's a wall...  ");
					playerMove(maze);
				}
				break;
			case 'S':
				if(!maze.moveSouth()) {
					maze.drawMaze();
					System.out.print("Um, that's a wall...  ");
					playerMove(maze);
				}
				break;
			case 'W':
				if(!maze.moveWest()) {
					maze.drawMaze();
					System.out.print("Um, that's a wall...  ");
					playerMove(maze);
				}
				break;
			default: 
				maze.drawMaze();
				System.out.print(dir + " is an INVALID DIRECTION!  ");
				playerMove(maze);
				break;
		}
	}
	
	/**
	 * A simple method that will display a win message
	 * to the player.
	 */
	private void congratulatePlayer() {
		System.out.println("\n\n\n*****************************");
		System.out.println("*                           *");
		System.out.println("* CONGRATULATIONS, YOU WON! *");
		System.out.println("*                           *");
		System.out.println("*****************************");
	}
}
