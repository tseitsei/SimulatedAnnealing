/**
 * (C) Juha Kari 2012.
 */

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Graph data structure.
 * 
 * @see Vertex
 * @see Edge
 * 
 * @author Juha Kari
 *
 */
public class Graph implements DataStructure, PositionalContainer {
	
	// Attribute and its two values for the visit status of positions.
	protected static Object STATUS = new Object();
	protected static Object VISITED = new Object();
	protected static Object UNVISITED = new Object();
	protected static Object DISCOVERYEDGE = new Object();
	protected static Object BACKEDGE = new Object();
	
    // BFS cross edge.
	protected static Object CROSSEDGE = new Object();
	
	// List of vertices.
	private LinkedList<Position> vertexList;
	
	// List of edges.
	private LinkedList<Position> edgeList;
	
	// Parent.
	private DataStructure parent;
	
	public Graph()
	{
		vertexList = new LinkedList<Position>();
		edgeList = new LinkedList<Position>();
		parent = null;
		
		this.clearSearch();
	}
	
	public Graph(Graph g)
	{
		vertexList = new LinkedList<Position>(g.vertexList);
		edgeList = new LinkedList<Position>(g.edgeList);
		parent = null;
		
		this.clearSearch();
	}
	
	public int size()
	{
		return numVertices();
	}
	
	public boolean isEmpty()
	{
		return (size() == 0);
	}
	
	public void swapElements(Position v, Position w)
	{
		Position tmp = v;
		v = w;
		w = tmp;
	}
	
	public Object replaceElement(Position v, Object o)
	{
		v.setElement(o);
		return v;
	}
	
	public boolean contains(Position p)
	{
		return (vertexList.contains(p) || edgeList.contains(p));
	}
	
	public Vertex getVertexByObject(Object o) // FIXME: What if there are multiple vertices with the same Object o stored?
	{
		Iterator<Position> i = vertices();
		while (i.hasNext())
		{
			Vertex v = (Vertex)i.next();
			if (v.element().toString().equals(o.toString()))
			{
				return v;
			}
		}
		return null;
	}
	
	public Vertex getVertexByIdentifier(Object id) // FIXME: What if there are multiple vertices with the same Object o stored?
	{
		Iterator<Position> i = vertices();
		while (i.hasNext())
		{
			Vertex v = (Vertex)i.next();
			if (v.identifier().toString().equals(id.toString()))
			{
				return v;
			}
		}
		return null;
	}
	
	public Edge getEdgeByObject(Object o) // FIXME: What if there are multiple edges with the same Object o stored?
	{
		Iterator<Position> i = edges();
		while (i.hasNext())
		{
			Edge e = (Edge)i.next();
			if (e.element().toString().equals(o.toString()))
			{
				return e;
			}
		}
		return null;
	}
	
	public Edge getEdgeByIdentifier(Object id) // FIXME: What if there are multiple edges with the same Object o stored?
	{
		Iterator<Position> i = edges();
		while (i.hasNext())
		{
			Edge e = (Edge)i.next();
			if (e.identifier().toString().equals(id.toString()))
			{
				return e;
			}
		}
		return null;
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
			desc += vertex.toString();
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
		
		return desc;
	}
	
	public String getGraphInfo()
	{
		String info = "";
		
		info += "Nodes: " + vertexList.size() + ". " + "Edges: " + edgeList.size() + ".";  //FIXME: Print all info.
		
		return info;
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
	
	public Vertex aVertex()
	{
		if (vertexList.size() > 0)
		{
			int r = Math.round((float)Math.random() * (vertexList.size()-1));
			return (Vertex) vertexList.get(r);
		}
		else return null;
	}
	
	public Edge anEdge()
	{
		if (edgeList.size() > 0)
		{
			int r = Math.round((float)Math.random() * (edgeList.size()-1));
			return (Edge) edgeList.get(r);
		}
		else return null;
	}
	
	public int degree(Vertex v)
	{
		return v.degree();
	}
	
	public void addParent(DataStructure parent)
	{
		this.parent = parent;
	}
	
	public DataStructure getParent()
	{
		return this.parent;
	}
	
	public void addVertex(Vertex v)
	{
		vertexList.add(v);
	}
	
	public void addEdge(Edge e)
	{
		edgeList.add(e);
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
	
	public int adjacentVerticesNum(Vertex v)
	{
		int adjacentVerticesNum = 0;
		
		Iterator<Position> i = adjacentVertices(v);
		
		while (i.hasNext())
		{
			i.next();
			adjacentVerticesNum++;
		}
		
		return adjacentVerticesNum;
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
	
	public int incidentEdgesNum(Vertex v)
	{
		int incidentEdgeNum = 0;
		
		Iterator<Position> i = incidentEdges(v);
		
		while (i.hasNext())
		{
			i.next();
			incidentEdgeNum++;
		}
		
		return incidentEdgeNum;
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
		
		//System.out.println("Vertex (" + v + "), edge (" + e + "), opposite (" + opposite + ").");
		
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
		
		while (e.hasNext())
		{
			Edge edge = (Edge)e.next();
			
			if (edge.getDestination() == v || edge.getOrigin() == v)
			{
				//edge.origin = null;
				//edge.destination = null;
				//edge.parent = null;
				//edge.o = null;
				e.remove();
			}
		}
		
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
		
		//e.destination = null;
		//e.origin = null;
	}
	
	public void makeUndirected(Edge e)
	{
		e.isDirected = false;
	}
	
	public void reverseDirection(Edge e)
	{
		Vertex tmp = e.origin;
		e.origin = e.destination;
		e.destination = tmp;
	}
	
	public boolean isDirected()
	{
		boolean isDirected = false;
		
		Iterator<Position> i = edges();
		
		while (i.hasNext())
		{
			Edge e = (Edge) i.next();
			if (e.isDirected() == true)
			{
				isDirected = true;
				break;
			}
		}
		
		return isDirected;
	}
	
	public boolean isUndirected()
	{
		return !this.isDirected();
	}
	
	public boolean isTree()
	{
		boolean isTree = false;
		
		if (isUndirected())
		{
			if (numEdges() == numVertices()-1) isTree = true;
		}
		
		return isTree;
	}
	
	protected void visit(Position p)
	{
		p.set(STATUS, VISITED);
	}
	
	protected void unVisit(Position p)
	{
		p.set(STATUS, UNVISITED);
	}
	
	protected boolean isVisited(Position p)
	{
		//return (p.get(STATUS) == VISITED);
		if (p == null) return false; 
		else return (p.get(STATUS) == VISITED);
	}
	
	protected void discoveryEdge(Position p)
	{
		p.set(STATUS, DISCOVERYEDGE);
	}
	
	protected void backEdge(Position p)
	{
		p.set(STATUS, BACKEDGE);
	}
	
	/*protected void crossEdge(Position p)
	{
		p.set(STATUS, CROSSEDGE);
	}*/
	
	public void clearSearch()
	{
		Iterator<Position> vertices = vertices();
		while (vertices.hasNext())
		{
			Vertex tmp = (Vertex) vertices.next();
			unVisit(tmp);
		}	
	}
	
	public void depthFirstSearch(Vertex v)
	{
		//System.out.println("Starting depth-first search traversal...");
		//System.out.println("Current vertex: " + v);
		
		Iterator<Position> edges = this.incidentEdges(v);
		
		while (edges.hasNext())
		{
			Edge e = (Edge) edges.next();
			//System.out.println("Current edge: " + e);
			this.visit(v);
			
			Vertex w = this.opposite(v, e);
			//System.out.println("Opposite vertex: " + w);
			
			if (this.isVisited(w) == false)
			{
				this.discoveryEdge(e);
				//System.out.println("Discovery: " + e);
				this.depthFirstSearch(w);
			}
			else
			{
				//System.out.println("isVisited: " + w);
				this.backEdge(e);
				//System.out.println("Back: " + e);
			}
		}
		
		//System.out.println("Ending depth-first search traversal...");
	}
	
	public Path aPathDFS(Vertex v, Edge lastEdge, Path p, boolean randomizeEdgeTraversal)
	{
		//System.out.println("Starting depth-first search traversal...");
		//System.out.println("Current vertex: " + v);
		
		if (p.isEmpty())
		{
			p.addVertex(v);
		}
		else if (this.areAdjacent(p.lastVertex(), v))
		{
			p.addVertex(v);
			p.addEdge(lastEdge);
		}
		else
		{
			return p;
		}
		
		// Iterator for incident edges.
		Iterator<Position> edges;
		
		if (randomizeEdgeTraversal == true)
		{
			LinkedList<Position> randomizedEdgeList = PositionRandomizer.randomize(edgeList);
		
			edges = randomizedEdgeList.iterator();
		}
		else
		{
			edges = edgeList.iterator();
		}
		
		while (edges.hasNext())
		{
			Edge e = (Edge) edges.next();

			this.visit(v);
			
			Vertex w = this.opposite(v, e);
			
			if (this.isVisited(w) == false)
			{
				this.discoveryEdge(e);
				
				this.aPathDFS(w, e, p, randomizeEdgeTraversal);
			}
			else
			{
				this.backEdge(e);
			}
		}
		
		//System.out.println("Ending depth-first search traversal...");		
		
		return p;
	}
	
	public Path aPathDFSHybrid(Vertex v, Edge lastEdge, Path p, boolean randomizeEdgeTraversal, int maxPathLength)
	{
		//System.out.println("Starting depth-first search traversal...");
		//System.out.println("Current vertex: " + v);
		
		if (p.size() <= maxPathLength)
		{
			if (p.isEmpty())
			{
				p.addVertex(v);
			}
			else if (this.areAdjacent(p.lastVertex(), v))
			{
				p.addVertex(v);
				p.addEdge(lastEdge);
			}
			else
			{
				return p;
			}
			
			// Iterator for incident edges.
			Iterator<Position> edges;
			
			if (randomizeEdgeTraversal == true)
			{
				LinkedList<Position> randomizedEdgeList = PositionRandomizer.randomize(edgeList);
			
				edges = randomizedEdgeList.iterator();
			}
			else
			{
				edges = edgeList.iterator();
			}
			
			while (edges.hasNext())
			{
				Edge e = (Edge) edges.next();
	
				this.visit(v);
				
				Vertex w = this.opposite(v, e);
				
				if (this.isVisited(w) == false)
				{
					this.discoveryEdge(e);
					
					this.aPathDFSHybrid(w, e, p, randomizeEdgeTraversal, maxPathLength);
				}
				else
				{
					this.backEdge(e);
				}
			}
		}
		
		//System.out.println("Ending depth-first search traversal...");		
		
		return p;
	}
	
	public void removePath(Path p)
	{
		// Iterating through the edges.
		// Removing the edges of given path.
		Iterator<Position> edges = p.edges();
		while (edges.hasNext())
		{
			Edge e = (Edge)edges.next();
			//System.out.println("Removing " + e.toString());
			this.removeEdge(e);
		}
		
		// Iterating through the vertices.
		// Removing the vertices which have no edges.
		Iterator<Position> vertices = p.vertices();
		while (vertices.hasNext())
		{
			Vertex v = (Vertex)vertices.next();
			if (v.degree() == 0)
			{
				//System.out.println("Removing " + v.toString());
				this.removeVertex(v);
			}
		}
	}
}
