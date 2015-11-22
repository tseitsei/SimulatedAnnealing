/**
 * (C) Juha Kari 2012.
 */

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Path data structure.
 * 
 * @see Graph
 * 
 * @author Juha Kari
 *
 */
public class Path implements DataStructure, PositionalContainer {
	// List of vertices.
	private LinkedList<Position> vertexList;
	
	// List of edges.
	private LinkedList<Position> edgeList;
	
	// Parent.
	private DataStructure parent;
	
	public Path()
	{
		vertexList = new LinkedList<Position>();
		edgeList = new LinkedList<Position>();
	}

	public Path(Path p)
	{
		vertexList = new LinkedList<Position>(p.vertexList);
		edgeList = new LinkedList<Position>(p.edgeList);
	}

	public void addParent(DataStructure parent)
	{
		this.parent = parent;
	}
	
	public DataStructure getParent()
	{
		return this.parent;
	}
	
	public int size()  // FIXME: Not the number of vertices!  Number of edges!
	{
		return numVertices();
	}
	
	public boolean isEmpty()
	{
		return (size() == 0);
	}
	
	public void swapElements(Position v, Position w)
	{
		// FIXME: Update origin and destination vertices if needed.
		Position tmp = v;
		v = w;
		w = tmp;
	}
	
	public Object replaceElement(Position v, Object o)
	{
		v.setElement(o);
		return v;
	}
	
	public Iterator<Position> positions()
	{
		return vertices();
	}
	
	public String getDescription()
	{
		String desc = "";
		
		Iterator<Position> v = vertices();
		Iterator<Position> e = edges();
		
		desc += "V = { ";
		while (v.hasNext())
		{
			Position vertex = v.next();
			if (vertex == null) desc += "null";
			else desc += vertex.toString();
			
			if (v.hasNext()) desc += ", ";
		}
		desc += " }, ";
		
		desc += "E = { ";
		while (e.hasNext())
		{
			Position edge = e.next();
			desc += edge.toString();
			if (e.hasNext()) desc += ", ";
		}
		desc += " }.";
		
		if (numVertices() < 1 || numEdges() < 1)
		{
			desc += " Empty path. Length = " + size();
		}
		else if (getOrigin() != null && getDestination() != null)
		{
			desc += " Origin = " + getOrigin() + ". Destination = " + getDestination() + ". Length = " + size();
		}
		
		return desc;
	}
	
	public Vertex getOrigin()
	{
		return (Vertex)vertexList.getFirst();
	}
	
	public Edge getOriginEdge()
	{
		return (Edge)edgeList.getFirst();
	}
	
	public Vertex getDestination()
	{
		return (Vertex)vertexList.getLast();
	}
	
	public Edge getDestinationEdge()
	{
		return (Edge)edgeList.getLast();
	}
	
	public String toString()
	{
		return this.getDescription();
	}
	
	public int numVertices()
	{
		return vertexList.size();
	}

	public int numEdges()
	{
		return edgeList.size();
	}

	public Iterator<Position> vertices()
	{
		return vertexList.iterator();
	}

	public Iterator<Position> edges()
	{
		return edgeList.iterator();
	}
	
	public Vertex lastVertex()
	{
		if (vertexList.size() > 0) return (Vertex) vertexList.getLast();
		else return null;
	}
	
	public void addVertex(Vertex v)
	{
		vertexList.add(v);
	}
	
	public void addEdge(Edge e)
	{
		edgeList.add(e);
	}
	
	public Edge removeLastEdge()
	{
		int lastEdge = edgeList.size() - 1;
		return (Edge)edgeList.remove(lastEdge);		
	}
	
	public Vertex removeLastVertex()
	{
		int lastVertex = vertexList.size() - 1;
		return (Vertex)vertexList.remove(lastVertex);
	}
	
	public Iterator<Position> adjacentVertices(Vertex v)
	{
		// TODO: Does not work correctly if there are multiple edges between two vertices in the graph.
		// TODO: Does not work correctly if there is an edge which origin and destination is the same vertex.
		
		LinkedList<Position> adjacentVertexList = new LinkedList<Position>();
		
		Iterator<Position> i = edges();
		
		while (i.hasNext())  // Iterating through the edge list.
		{
			Edge e = (Edge)i.next();
			
			if (e.getDestination() == v)
			{
				adjacentVertexList.add(e.getOrigin());
			}
			else if (e.getOrigin() == v)
			{
				adjacentVertexList.add(e.getDestination());
			}
		}
		
		Iterator<Position> a = adjacentVertexList.iterator(); 
		
		return a;
	}
	
	public Iterator<Position> incidentEdges(Vertex v)
	{
		// TODO: Does not work correctly if there are multiple edges between two vertices in the graph.
		// TODO: Does not work correctly if there is an edge which origin and destination is the same vertex.
		
		LinkedList<Position> incidentEdgeList = new LinkedList<Position>();
		
		Iterator<Position> i = edges();
		
		while (i.hasNext())
		{
			Edge e = (Edge)i.next();
			
			if (e.getDestination() == v)
			{
				incidentEdgeList.add(e);
			}
			else if (e.getOrigin() == v)
			{
				incidentEdgeList.add(e);
			}
		}
		
		Iterator<Position> a = incidentEdgeList.iterator();
		
		return a;
	}
	
	public Vertex[] endVertices(Edge e)
	{
		Vertex[] endVertices = new Vertex[2];
		
		endVertices[0] = e.origin;
		endVertices[1] = e.destination;
		
		return endVertices;
	}
	
	public Vertex opposite(Vertex v, Edge e)
	{
		Vertex opposite = null;
		
		if (v == e.destination) opposite = e.origin;
		else if (v == e.origin) opposite = e.destination;
		
		return opposite;
	}
	
	public boolean areAdjacent(Vertex v, Vertex w)
	{
		boolean areAdjacent = false;
		Iterator<Position> i = edges();
		
		while (i.hasNext())
		{
			Edge e = (Edge)i.next();
			
			if (e.getOrigin() == v && e.getDestination() == w)
			{
				areAdjacent = true;
				break;
			}
			else if (e.getOrigin() == w && e.getDestination() == v)
			{
				areAdjacent = true;
				break;
			}
		}
		
		return areAdjacent;
	}
	
	public Edge insertEdge(Vertex v, Vertex w, Object o, Object id)
	{
		Edge newEdge = new Edge(o, id, v, w, false);
		this.addEdge(newEdge);
		
		return newEdge;
	}
	
	public Edge insertDirectedEdge(Vertex v, Vertex w, Object o, Object id)
	{
		Edge newEdge = new Edge(o, id, v, w, true);
		this.addEdge(newEdge);
		return newEdge;
	}
	
	public Vertex insertVertex(Object o, Object id)
	{
		Vertex newVertex = new Vertex(o, id);
		this.addVertex(newVertex);
		return newVertex;
	}
	
	public void removeVertex(Vertex v)
	{
		Iterator<Position> e = edges();
		
		// Remove all edges connected to this vertex.
		while (e.hasNext())
		{
			Edge edge = (Edge)e.next();
			//System.out.println("Removing edge: " + edge);
			
			if (edge.getDestination() == v || edge.getOrigin() == v)
			{
				edge.origin = null;
				edge.destination = null;
				edge.parent = null;
				edge.o = null;
				e.remove();
			}
		}
		
		v.o = null;
		v.parent = null;
		vertexList.remove(v);
	}
	
	public void removeVertexFromPath(Vertex v)
	{
		//v.o = null;
		//v.parent = null;
		vertexList.remove(v);
	}
	
	public void removeEdge(Edge e)
	{
		edgeList.remove(e);
		
		if (e.isDirected == true)
		{
			e.destination.incomingEdges--;
			e.origin.outgoingEdges--;
		}
		else
		{
			e.destination.undirectedEdges--;
			e.origin.undirectedEdges--;
		}
		
		e.destination = null;
		e.origin = null;
	}

	public void removeEdgeFromPath(Edge e)
	{
		edgeList.remove(e);
		
		/*if (e.isDirected == true)
		{
			e.destination.incomingEdges--;
			e.origin.outgoingEdges--;
		}
		else
		{
			e.destination.undirectedEdges--;
			e.origin.undirectedEdges--;
		}*/
		
		//e.destination = null;  // FIXME
		//e.origin = null;       // FIXME
	}

	public boolean isPartOfPath(Vertex v)
	{
		return vertexList.contains(v);
	}
	
	private boolean connectToPath(Path p)
	{
		boolean connected = false;
		
		if (this != p)  // Connect only two different paths.
		{
			if (this.getOrigin() == p.getOrigin())
			{
				while (p.vertexList.isEmpty() == false)
				{
					this.vertexList.addFirst(p.vertexList.pollFirst());
				}
				while (p.edgeList.isEmpty() == false)
				{
					this.edgeList.addFirst(p.edgeList.pollFirst());
				}
				connected = true;
			}
			else if (this.getOrigin() == p.getDestination())
			{
				while (p.vertexList.isEmpty() == false)
				{
					this.vertexList.addFirst(p.vertexList.pollLast());
				}
				while (p.edgeList.isEmpty() == false)
				{
					this.edgeList.addFirst(p.edgeList.pollLast());
				}
				connected = true;
			}
			else if (this.getDestination() == p.getOrigin())
			{
				while (p.vertexList.isEmpty() == false)
				{
					this.vertexList.addLast(p.vertexList.pollFirst());
				}
				
				while (p.edgeList.isEmpty() == false)
				{
					this.edgeList.addLast(p.edgeList.pollFirst());
				}
				connected = true;
			}
			else if (this.getDestination() == p.getDestination())
			{
				while (p.vertexList.isEmpty() == false)
				{
					this.vertexList.addLast(p.vertexList.pollLast());
				}
				while (p.edgeList.isEmpty() == false)
				{
					this.edgeList.addLast(p.edgeList.pollLast());
				}
				connected = true;
			}
		}
		return connected;		
	}
	
	public boolean connectToPath(Path p, Vertex connectingVertex)
	{
		boolean connected = false;
		
		if (this != p)  // Connect only two different paths.
		{
			if (this.size() < 3 && p.size() < 3)
			{
				// Add a path of one edge to the other path of one edge.
				connected = this.connectToPath(p);
				this.vertexList.remove(connectingVertex);
			}
			else if (this.size() < 3 && p.size() >= 3)
			{
				Vertex otherVertex = null;
				
				if (this.getOrigin() == connectingVertex) otherVertex = this.getDestination();
				else if (this.getDestination() == connectingVertex) otherVertex = this.getOrigin();
				
				if (p.isPartOfPath(otherVertex) == false)
				{
					connected = this.connectToPath(p);
					this.vertexList.remove(connectingVertex);
				}
				else
				{
					//System.out.println("A cycle detected. Cannot connect!");
					//System.out.println(otherVertex + " is part of path!");
					connected = false;
				}
			}
			else if (this.size() >= 3 && p.size() < 3)
			{
				Vertex otherVertex = null;
				
				if (p.getOrigin() == connectingVertex) otherVertex = p.getDestination();
				else if (p.getDestination() == connectingVertex) otherVertex = p.getOrigin();
				
				if (this.isPartOfPath(otherVertex) == false)
				{
					connected = this.connectToPath(p);
					this.vertexList.remove(connectingVertex);
				}
				else
				{
					//System.out.println("A cycle detected. Cannot connect!");
					//System.out.println(otherVertex + " is part of path!");
					connected = false;
				}
			}
			else if (this.size() >= 3 && p.size() >= 3)
			{
				Iterator<Position> i = p.vertices();
				while (i.hasNext())
				{
					Vertex v = (Vertex)i.next();
					if (v != connectingVertex)
					{
						if (this.isPartOfPath(v) == false)
						{
							connected = true;
						}
						else
						{
							//System.out.println("A cycle detected. Cannot connect!");
							//System.out.println(v + " is part of path!");
							connected = false;
							break;
						}
					}
				}
				if (connected)
				{
					connected = this.connectToPath(p);
					this.vertexList.remove(connectingVertex);
				}
			}
			
		}
		return connected;
	}
}
