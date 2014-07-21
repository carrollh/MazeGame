import java.awt.Point;

/**
 * A variation on the basic Disjoint Set
 * data structure. It provides Union by rank,
 * and can contain a data point representing 
 * a place in the maze.
 * 
 * @author Heath Carroll
 */
public class DisjointSet {

	public DisjointSet parent;
	public short rank;
	public Point data;
	
	/**
	 * A basic constructor allowing array position
	 * to be stored as data.
	 * 
	 * @param row
	 * @param col
	 */
	public DisjointSet(int row, int col) {
		parent = this;
		rank = 0;
		data = new Point(row, col);
	}
	
	/**
	 * Join two sets by making the shorter one a child
	 * of the taller one.
	 * 
	 * @param x
	 * @param y
	 */
	public static void Union(DisjointSet x, DisjointSet y) {
		DisjointSet xRoot = Find(x);
		DisjointSet yRoot = Find(y);
		
		if(xRoot != yRoot) {
			if(xRoot.rank < yRoot.rank) 
				xRoot.parent = yRoot;
			else if(xRoot.rank > yRoot.rank)
				yRoot.parent = xRoot;
			else {
				yRoot.parent = xRoot;
				xRoot.rank++;
			}
		}
	}
	
	/**
	 * Return the sets representative.
	 * @param x
	 * @return
	 */
	public static DisjointSet Find(DisjointSet x) {
		if(x.parent != x) x.parent = Find(x.parent);
		
		return x.parent;
	}
}
