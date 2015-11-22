/**
 * (C) Juha Kari 2012.
 */

import java.util.Hashtable;

/**
 * Edge data structure.
 * 
 * @see DataStructure
 * @see Graph
 * @see Position
 * @see Vertex
 * 
 * @author Juha Kari
 *
 */
public class Edge implements DataStructure, Position { 
	// An object stored in this edge.
	Object o;
	
	// An identifier stored in this edge.
	Object id;
	
	// A reference to the graph this edge is part of.
	DataStructure parent;
	
	// A boolean indicator of whether e is directed or undirected.
	boolean isDirected;
	
	// Origin vertex.
	Vertex origin;
	
	// Destination vertex.
	Vertex destination;
	
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
	
	public Edge(Object o, Object id, Vertex origin, Vertex destination, boolean isDirected)
	{
		this.o = o;
		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.isDirected = isDirected;
		this.parent = null;
		
		if (isDirected == true)  // If this edge is directed...
		{
			origin.outgoingEdges++;
			destination.incomingEdges++;
		}
		else  // If this edge is undirected...
		{
			origin.undirectedEdges++;
			destination.undirectedEdges++;
		}
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
	
	public Position getOrigin()
	{
		return this.origin;
	}
	
	public Position getDestination()
	{
		return this.destination;
	}
	
	public boolean isDirected()
	{
		return isDirected;
	}
	
	/* (non-Javadoc)
	 * @see DataStructure#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Edge (" + this.o + ").";
	}
	
	public String toString()
	{
		String desc = "[";
		if (this.origin != null) desc += this.origin.toString();
		desc += ", ";
		if (this.destination != null) desc += this.destination.toString();
		desc += "]";
		
		return desc;
	}

}
