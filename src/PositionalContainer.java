/**
 * (C) Juha Kari 2012.
 */

/**
 * Interface for positional containers.
 * 
 * @author Juha Kari
 *
 */
public interface PositionalContainer extends InspectablePositionalContainer {
	public void swapElements(Position v, Position w);
	
	public Object replaceElement(Position v, Object e);
}
