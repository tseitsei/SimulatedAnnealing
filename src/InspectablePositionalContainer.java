/**
 * (C) Juha Kari 2012.
 */

import java.util.Iterator;

/**
 * Interface for inspectable positional containers.
 * 
 * @see PositionalContainer
 * 
 * @author Juha Kari
 *
 */
public interface InspectablePositionalContainer {
	public Iterator<Position> positions();
	
	public int size();
	
	public boolean isEmpty();
}
