
package labyrinth;
/**
 *
 * @author Serhat
 */
public class Coordinate {
    int row ;
    int column;
    
    Coordinate(int row, int column) {
        this.row = row ;
        this.column = column ;
    }
    @Override
    public String toString() {
		Character character = 'a';
		character = (char)((int)character+column);
		return row+1+" "+ character;
	}
}
