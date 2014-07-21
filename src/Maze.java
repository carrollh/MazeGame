import java.awt.Point;

/**
 * This is where all of your work should be done. The idea
 * is to have a 2D array of DisjointSets (forest) and a separate 
 * 2D char array for the maze representation (maze). In order
 * to keep track of which walls Kruskal's algorithm has and
 * has not looked at, we also need a list of Wall objects (walls).
 * 
 * @author Heath Carroll
 * @author <INSERT YOUR NAME HERE>
 */
public class Maze {
	
	/**
	 * A private helper class allowing us to represent
	 * a wall between two cells. Kruskal's algorithm will
	 * check if the sets on either side of a Wall have the
	 * same representative / parent. If they do, then there is already a path
	 * leading around the wall and we can leave it in place.
	 * 
	 * @author Heath Carroll
	 */
	private class Wall {
		DisjointSet x;
		DisjointSet y;
		
		public Wall(DisjointSet x, DisjointSet y) {
			this.x = x;
			this.y = y;
		}
	}
	
	int rows;
	int cols;
	
	DisjointSet[][] forest; // a 2D array of all the maze cells
	DLList<Wall> walls; // a list of all the walls between cells
	
	char[][] maze; // the thing that gets printed over and over
	
	Point player; // a location in our maze representing the player
	Point exit; // a location in our maze representing the exit
	
	public Maze(int rows, int cols) {
		
		this.rows = rows;
		this.cols = cols;
		
		walls = new DLList<Wall>();
		
		// initialize this.maze to some size based on how you 
		// want to draw the maze
		this.maze = new char[rows*2+1][cols*3+1];
		
		populateForest();
		createWalls();
		buildGrid();
		kruskal();
		
		// pick a random spot on the left hand side for the player to start
		// set that spot in the maze representation to your symbol for your player's avatar
		
		// pick a random spot on the right hand side for the exit
		// set that spot in the maze representation to your symbol for exit
		player = new Point((int)(Math.random() * rows) * 2 + 1, 1);
		exit = new Point((int)(Math.random() * rows) * 2 + 1, cols*3-2);
		maze[player.x][player.y] = '@';
		maze[exit.x][exit.y] = 'X';
	}
	
	/** 
	 * A private helper method that initializes each cell in 
	 * the maze as a Disjoint Set.
	 */
	private void populateForest() {
		
		// initialize the 2D array of cells
		forest = new DisjointSet[rows][cols];
		
		// Step through the entire 2D array and initialize
		// each cell as a disjoint singleton set. Make sure
		// you tell each cell where in the 2D array it sits.
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				forest[row][col] = new DisjointSet(row, col);
			}
		}
	}
	
	/**
	 * Create and add the walls between cells to the walls list.
	 * The first (cols-1) * (rows-1) walls are vertical.
	 * The last cols * (rows-1) walls are horizontal.
	 */
	private void createWalls() {
		// create and add all the vertical walls to the walls list
		for(int row = 0; row < rows; row++) {
			for(int col = 1; col < cols; col++) {
				walls.add(new Wall(forest[row][col-1], forest[row][col]));
			}
		}
		
		// create and add all the horizontal walls to the walls list 
		for(int row = 1; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				walls.add(new Wall(forest[row-1][col], forest[row][col]));
			}
		}
	}
	
	/**
	 * A method to implement Kruskal's algorithm. 
	 */
	private void kruskal() {
		
		// pick every wall randomly and do Kruskal's comparison on them all
		// keep looping until we've examined every wall.
		while(walls.size() > 0) {
			//pluck a random wall from the list of walls
			int random = (int)(Math.random() * walls.size());
			Wall wall = walls.get(random);
			
			// check to see if the two sides of the wall are connected.
			// If they are not, remove the wall from our representation
			// and union the cells on either side so that we know
			// a path exists between them later.
			if(DisjointSet.Find(wall.x) != DisjointSet.Find(wall.y)) {
				removeWallFromMaze(wall);
				DisjointSet.Union(wall.x, wall.y);
			}
			
			// remove that random wall from the walls list so we can't 
			// pick it again. It does not matter if the wall was removed 
			// from the maze. Either way we don't want to examine it again.
			// Also, this loop will never break if you don't remove all of
			// the walls.
			
			walls.remove(random);
		}
	}
	
	/**
	 * A helper method that looks at either side of a wall,
	 * determines where in the maze representation this wall
	 * is, then sets that location to whitespace.
	 * 
	 * @param wall
	 */
	private void removeWallFromMaze(Wall wall) {
		
		// based on the data stored in the nodes on either 
		// side of the wall, determine if this wall is a vertical
		// wall or a horizontal wall. Then clear the wall from the
		// maze representation. This is heavily dependent on 
		// how you decide to represent your maze.
		if(wall.x.data.x == wall.y.data.x) {
			int row = wall.x.data.x; // forest row coord
			int col;				 // forest col coord of left cell
			if(wall.x.data.y < wall.y.data.y) col = wall.x.data.y;
			else col = wall.x. data.y;
			
			maze[row * 2 + 1][(col+1) * 3] = ' '; // clear the vertical wall
		}
		else {
			int row;
			int col = wall.x.data.y;
			if(wall.x.data.x < wall.y.data.x) row = wall.x.data.x;
			else row = wall.y.data.x;
			
			maze[row * 2 + 2][col * 3 + 1] = ' ';
			maze[row * 2 + 2][col * 3 + 2] = ' ';
		}
	}
	
	/**
	 * Set your maze representation to an "all walls in 
	 * place" state. Later on the kruskal method will clear
	 * walls as needed to make the maze.
	 */
	public void buildGrid() {
		
		// rogue style
			// 43 -> '+'
			// 166-> '|'
			// 58 -> ':'
			// 64 -> '@'
		
		// create a grid representation for the cells
		for(int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze[row].length; col ++) {
				if(row % 2 == 0 && col % 3 == 0) maze[row][col] = ':';
				else if(row % 2 == 0) maze[row][col] = '-';
				else if(col % 3 == 0) maze[row][col] = '|';
				else maze[row][col] = ' ';
			}
		}
	}
	
	/**
	 * Just prints the maze. We could have overriden toString(),
	 * but this game could be refactored to use the Swing API.
	 * If that happens you really would need to draw it.
	 */
	public void drawMaze() {
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Game logic. Just checks to see if the players location
	 * is the same as the exits. 
	 * Yes Mr. Litwin, this can be one line of code...
	 * @return
	 */
	public boolean checkForExit() {
		return player.equals(exit);
	}

	/**
	 * Movement method for 'up'. It checks to see if the character
	 * immediately above the players current location is a horizontal
	 * wall symbol. Depending on your implementation this may actually
	 * be more than one symbol. In my implementation it was possible to 
	 * run into the bottom of vertical walls as well, so I had to 
	 * check if it was equal to those too.
	 * As long as that spot is clear, move the location of the avatar
	 * up, and return true.
	 * Otherwise return false because that was an invalid move. 
	 * 
	 * Note: in a more professional program, your collision detection
	 * would not be dependent on the visual representation of your game.
	 * I have left it like this for ease of mental logic. It does save
	 * on memory this way, but is slower. A better approach might be to 
	 * have a wall representation as well, maybe in the form of a boolean
	 * array. Or several actually. If you are interested, look up 
	 * bitboards. They are used in chess algorithms and many other game 
	 * AI situations. 
	 *  
	 * @return
	 */
	public boolean moveNorth() {
		if(maze[player.x-1][player.y] != '-' &&
		   maze[player.x-1][player.y] != ':') {
			maze[player.x][player.y] = ' ';
			player = new Point(player.x - 1, player.y);
			maze[player.x][player.y] = '@';
			
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Movement method for 'right'. Similar to moveNorth().
	 * 
	 * @return
	 */
	public boolean moveEast() {
		if(maze[player.x][player.y+1] != '|' &&
		   maze[player.x][player.y+1] != ':') {
			maze[player.x][player.y] = ' ';
			player = new Point(player.x, player.y + 1);
			maze[player.x][player.y] = '@';
			
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Movement method for 'down'. Similar to moveNorth().
	 * 
	 * @return
	 */
	public boolean moveSouth() {
		if(maze[player.x+1][player.y] != '-' &&
		   maze[player.x+1][player.y] != ':') {
		   maze[player.x][player.y] = ' ';
		   player = new Point(player.x + 1, player.y);
		   maze[player.x][player.y] = '@';
			
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Movement method for 'left'. Similar to moveEast().
	 * 
	 * @return
	 */
	public boolean moveWest() {
		if(maze[player.x][player.y-1] != '|' &&
		   maze[player.x][player.y-1] != ':') {
		   maze[player.x][player.y] = ' ';
		   player = new Point(player.x, player.y - 1);
		   maze[player.x][player.y] = '@';
			
			return true;
		}
		else {
			return false;
		}
	}
}
