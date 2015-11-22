/**
 * (C) Juha Kari 2012.
 */

import java.util.LinkedList;
import java.util.Iterator;

/**
 * Path list data structure.
 * 
 * @see Path
 * 
 * @author Juha Kari
 *
 */
public class PathList {
	// List of paths.
	LinkedList<Path> pathList;

	public PathList()
	{
		pathList =  new LinkedList<Path>();
	}
	
	public PathList(PathList p)
	{
		//this.pathList = new LinkedList<Path>(p.pathList);
		this.pathList = new LinkedList<Path>();
		
		Iterator<Path> i = p.paths();
		while (i.hasNext())
		{
			Path path = new Path(i.next());
			this.pathList.add(path);
		}
	}
	
	public void add(Path p)
	{
		pathList.add(p);
	}
	
	public void remove(Path p)
	{
		pathList.remove(p);
	}
	
	public Path get(int i)
	{
		return pathList.get(i);
	}
	
	public int size()
	{
		return pathList.size();
	}
	
	public boolean isEmpty()
	{
		return (size() == 0);
	}
	
	public Path aPath()
	{
		if (pathList.size() > 0)
		{
			int r = Math.round((float)Math.random() * (pathList.size()-1));
			//System.out.println("r: " + r);
			return (Path) pathList.get(r);
		}
		else return null;
	}
	
	public Path shortestPath()  // FIXME: Selection of the shortest path should be randomized.
	{
		PathList tmp = new PathList(this);
		return this.shortestPath(tmp.pathList);
	}
	
	public Path removeShortestPath()
	{
		return this.shortestPath(this.pathList);
	}
	
	public Path shortestPath(LinkedList<Path> pathList)  // FIXME: Selection of the shortest path should be randomized.
	{
		Iterator<Path> i = pathList.iterator();
		
		Path shortestPath = i.next();
		
		while (i.hasNext())
		{
			Path p = i.next();
			if (p.size() < shortestPath.size())
			{
				shortestPath = p;
			}
		}
		
		pathList.remove(shortestPath);
		
		return shortestPath;
	}
	
	public Iterator<Path> paths()
	{
		return pathList.iterator();
	}
	
	public Iterator<Path> shortestPaths()
	{
		LinkedList<Path> shortestPaths = new LinkedList<Path>();
		
		LinkedList<Path> paths = new LinkedList<Path>(this.pathList);
		
		while (!paths.isEmpty())
		{
			shortestPaths.add(shortestPath(paths));
		}
		
		return shortestPaths.iterator();
	}
	
	public Iterator<Path> pathsEndingToVertex(Vertex v)
	{
		LinkedList<Path> paths = new LinkedList<Path>();

		Iterator<Path> i = pathList.iterator();
		
		while (i.hasNext())
		{
			Path p = i.next();
			
			if (p.getOrigin() == v || p.getDestination() == v) paths.add(p);
		}
		
		return paths.iterator();		
	}
	
	public boolean connectPaths(Path p1, Path p2, Vertex v)
	{
		boolean connected = false;
		
		if (p1 != p2)  // Connect only two different paths.
		{
			//System.out.println("PathList.connectPaths:");
			//System.out.println("P1: " + p1);
			//System.out.println("P2: " + p2);
			//System.out.println("V : " + v);
			connected = p1.connectToPath(p2, v);
			if (connected) pathList.remove(p2);
		}
		
		return connected;
	}
	
	public String toString()
	{
		Iterator<Path> i = shortestPaths();
		String paths = "P = {";
		
		int counter = 0;
		while (i.hasNext())
		{
			counter++;
			Path p = i.next();
			paths += "\n (" + counter + ") " + p.toString();
			
			if (i.hasNext()) paths += " ";
		}
		paths += "\n}\n";
		
		return paths;
	}
	
	public Graph toGraph()
	{
		Graph g = new Graph();
		
		Iterator<Path> i = pathList.iterator();
		
		while (i.hasNext())
		{
			Path p = i.next();
			
			// Adding vertices to the graph.
			Iterator<Position> j = p.vertices();
			while (j.hasNext())
			{
				Vertex v = (Vertex)j.next();
				if (g.contains(v) == false) g.addVertex(v);
			}
			
			// Adding edges to the graph.
			Iterator<Position> k = p.edges();
			while (k.hasNext())
			{
				Edge e = (Edge)k.next();
				if (g.contains(e) == false) g.addEdge(e);
			}
		}
		
		return g;
	}
}
