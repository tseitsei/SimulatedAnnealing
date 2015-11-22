/**
 * (C) Juha Kari 2012.
 */

/**
 * Interface for positions in data structures.
 * 
 * @see DataStructure
 * 
 * @author Juha Kari
 *
 */
public interface Position {
	// Return the element stored at this position.
	public Object element();
	public void setElement(Object o);
	
	// --- Methods for decorating Position. ---
	
	// Tests whether the position has attribute.
	public boolean has(Object attribute);
	// Returns the value of attribute.
	public Object get(Object attribute);
	// Sets the value of attribute.
	public void set(Object attribute, Object value);
	// Removes attribute and its associated value.
	public void destroy(Object attribute);
}
