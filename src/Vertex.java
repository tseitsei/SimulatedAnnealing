/**
 * (C) Juha Kari 2012.
 */

import java.util.Hashtable;

/**
 * Vertex data structure.
 *
 * @see DataStructure
 * @see Edge
 * @see Graph
 * @see Position
 * 
 * @author Juha Kari
 *
 */
public class Vertex implements DataStructure, Position {
	// An object stored in this vertex.
	Object o;
	
	// An identifier stored in this vertex.
	Object id;
	
	// A reference to the graph this vertex is part of.
	DataStructure parent;
	
	// A number of incident undirected edges.
	int undirectedEdges;
	
	// A number of incoming directed edges.
	int incomingEdges;
	
	// A number of outgoing directed edges.
	int outgoingEdges;
	
	// Attributes for decorating Position are stored in a dictionary.
	Hashtable<Object, Object> attributes = new Hashtable<Object, Object>();
	
	// Methods for decorating Position.
	// Tests whether the position has attribute.
	public boolean has(Object attribute)
	{
		return (attributes.get(attribute) != null);
	}
	// Returns the value of attribute.
	public Object get(Object attribute)
	{
		return attributes.get(attribute);
	}
	// Sets the value of attribute.
	public void set(Object attribute, Object value)
	{
		attributes.put(attribute, value);
	}
	// Removes attribute and its associated value.
	public void destroy(Object attribute)
	{
		attributes.remove(attribute);
	}
	
	public Vertex()
	{
		o = null;
		id = null;
		parent = null;
		undirectedEdges = 0;
		incomingEdges = 0;
		outgoingEdges = 0;
	}
	
	public Vertex(Object o, Object id)
	{
		this.o = o;
		this.id = id;
		this.parent = null;
	}
	
	public void addParent(DataStructure parent)
	{
		this.parent = parent;
	}
	
	public DataStructure getParent()
	{
		return this.parent;
	}
	
	public Object element()
	{
		return getObject();
	}
	
	public Object identifier()
	{
		return getIdentifier();
	}
	
	public void setElement(Object o)
	{
		this.o = o;
	}
	
	public Object getObject()
	{
		return this.o;
	}
	
	public Object getIdentifier()
	{
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see DataStructure#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Vertex (" + this.o + ").";
	}
	
	public String toString()
	{
		if (this.o == null) return "null";
		else return this.o.toString();
	}
	
	public int degree()
	{
		// Return the degree of this vertex.  A cycle is counted twice.
		return undirectedEdges + incomingEdges + outgoingEdges;
	}

}
