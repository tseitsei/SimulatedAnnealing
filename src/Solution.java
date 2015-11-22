/**
 * (C) Juha Kari 2012.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Solution class for simulated annealing.
 * 
 * @see SimulatedAnnealing
 * 
 * @author Juha Kari
 *
 */
public class Solution {
	PathList pathList;
	Graph g;
	
	public Solution(Solution s)
	{
		this.pathList = new PathList(s.pathList);
		this.g = s.g;
	}
	
	public Solution(Graph g)
	{
		pathList = new PathList();
		this.g = g;
	}
	
	public String getSolutionInfo()
	{
		String info = "";
		
		info += "Graph: " + g.getGraphInfo();
		
		return info;
	}
	
	public int cost()
	{
		return pathList.size();
	}
	
	public void createInitialSolutionDFS(boolean verbose)
	{
		if (g == null || g.isEmpty()) return;
		
		boolean end = false;
		while (!end)
		{
			// Creating a new Path object.
			Path p = new Path();
			
			// Selecting a random vertex.
			Vertex randomVertex = g.aVertex();
			
			if (verbose) System.out.println("v: " + randomVertex);
			
			// Finding an arbitrary path.
			boolean randomizeEdgeTraversal = true;
			p = g.aPathDFS(randomVertex, null, p, randomizeEdgeTraversal);
			
			// Printing path.
			if (verbose) System.out.println("p: " + p.toString());
			
			// Adding the path to path list.
			pathList.add(p);
			
			// Printing the number of paths found so far.
			if (verbose) System.out.println("Number of paths: " + pathList.size());
			
			// Printing the shortest path.
			if (verbose) System.out.println("Shortest path: " + pathList.shortestPath());
			
			// Printing the end vertices of the shortest path.
			if (verbose) System.out.println("Shortest path ends: " + pathList.shortestPath().getOrigin() + ", " + pathList.shortestPath().getDestination() + ".");			
			
			// Deleting path from the graph.
			g.removePath(p);
			
			// Clearing the traces of traversal.
			g.clearSearch();
			
			// Printing graph.
			//System.out.println("g: " + g.toString());
			
			// End the loop if there are no edges or vertices left.
			if (g.numEdges() == 0 || g.numVertices() == 0) end = true;
			
			//System.out.println("---");
		}
	}
	
	public void createInitialSolutionDFSNoRandom(boolean verbose)
	{
		if (g == null || g.isEmpty()) return;
		
		boolean end = false;
		while (!end)
		{
			// Creating a new Path object.
			Path p = new Path();
			
			// Selecting a random vertex.
			Vertex randomVertex = g.aVertex();
			
			if (verbose) System.out.println("v: " + randomVertex);
			
			// Finding an arbitrary path.
			boolean randomizeEdgeTraversal = false;
			p = g.aPathDFS(randomVertex, null, p, randomizeEdgeTraversal);
			
			// Printing path.
			if (verbose) System.out.println("p: " + p.toString());
			
			// Adding the path to path list.
			pathList.add(p);
			
			// Printing the number of paths found so far.
			if (verbose) System.out.println("Number of paths: " + pathList.size());
			
			// Printing the shortest path.
			if (verbose) System.out.println("Shortest path: " + pathList.shortestPath());
			
			// Printing the end vertices of the shortest path.
			if (verbose) System.out.println("Shortest path ends: " + pathList.shortestPath().getOrigin() + ", " + pathList.shortestPath().getDestination() + ".");			
			
			// Deleting path from the graph.
			g.removePath(p);
			
			// Clearing the traces of traversal.
			g.clearSearch();
			
			// Printing graph.
			//System.out.println("g: " + g.toString());
			
			// End the loop if there are no edges or vertices left.
			if (g.numEdges() == 0 || g.numVertices() == 0) end = true;
			
			//System.out.println("---");
		}
	}
	
	public void createInitialSolutionDFSHybrid(boolean verbose)
	{
		if (g == null || g.isEmpty()) return;
		
		System.out.println("###createInitialSolutionDFSHybrid###");
		
		boolean end = false;
		while (!end)
		{
			// Creating a new Path object.
			Path p = new Path();
			
			// Selecting a random vertex.
			Vertex randomVertex = g.aVertex();
			
			if (verbose) System.out.println("v: " + randomVertex);
			
			// Set the maximum length of the path to (number of vertices / 2).
			int maxPathLength = (g.numVertices() / 2);
			
			// Finding an arbitrary path.
			boolean randomizeEdgeTraversal = true;
			p = g.aPathDFSHybrid(randomVertex, null, p, randomizeEdgeTraversal, maxPathLength);
			
			System.out.println("Initial path = " + p);
			
			// Printing path.
			if (verbose) System.out.println("p: " + p.toString());
			
			// Adding the path to path list.
			pathList.add(p);
			
			// Printing the number of paths found so far.
			if (verbose) System.out.println("Number of paths: " + pathList.size());
			
			// Printing the shortest path.
			if (verbose) System.out.println("Shortest path: " + pathList.shortestPath());
			
			// Printing the end vertices of the shortest path.
			if (verbose) System.out.println("Shortest path ends: " + pathList.shortestPath().getOrigin() + ", " + pathList.shortestPath().getDestination() + ".");			
			
			// Deleting path from the graph.
			g.removePath(p);
			
			// Clearing the traces of traversal.
			g.clearSearch();
			
			// Printing graph.
			//System.out.println("g: " + g.toString());
			
			// End the loop if there are no edges or vertices left.
			if (g.numEdges() == 0 || g.numVertices() == 0) end = true;
			
			//System.out.println("---");
		}
	}
	
	public void createInitialSolutionOne(boolean verbose)
	{
		if (g == null || g.isEmpty()) return;
		
		boolean end = false;
		while (!end)
		{
			// Creating a new Path object.
			Path p = new Path();
			
			// Selecting a random edge.
			Edge randomEdge = g.anEdge();
			
			if (verbose) System.out.println("e: " + randomEdge);
			
			// Finding the end vertices of the random edge.
			Vertex endOne = randomEdge.origin;
			Vertex endTwo = randomEdge.destination;
			
			// Setting the end vertices and the edge for a new path.
			p.addEdge(randomEdge);
			p.addVertex(endOne);
			p.addVertex(endTwo);
			
			// Printing path.
			if (verbose) System.out.println("p: " + p.toString());
			
			// Adding the path to path list.
			pathList.add(p);
			
			// Printing the number of paths found so far.
			if (verbose) System.out.println("Number of paths: " + pathList.size());
			
			// Printing the shortest path.
			if (verbose) System.out.println("Shortest path: " + pathList.shortestPath());
			
			// Printing the end vertices of the shortest path.
			if (verbose) System.out.println("Shortest path ends: " + pathList.shortestPath().getOrigin() + ", " + pathList.shortestPath().getDestination() + ".");			
			
			// Deleting path from the graph.
			g.removePath(p);
			
			// Clearing the traces of traversal.
			g.clearSearch();
			
			// Printing graph.
			//System.out.println("g: " + g.toString());
			
			// End the loop if there are no edges or vertices left.
			if (g.numEdges() == 0 || g.numVertices() == 0) end = true;
			
			//System.out.println("---");
		}
	}
	
	public Solution createNeighborSolution(boolean verbose) throws NoSuchElementException  // FIXME: Search neighbors better.  This should return different s1 than the input s0.
	{
		Solution s1 = new Solution(this);
		
		PathList possiblePathsForNeighbor = new PathList();
		
		Iterator<Path> i = s1.pathList.shortestPaths();
		
		if (i.hasNext() == false) return s1;
		
		if (verbose) System.out.println("Possible paths for neighbor solution:");
		
		// First path from the list of the shortest paths.
		Path p = i.next();
		// Length of the shortest path.
		int l = p.size();
		
		while (i.hasNext())
		{
			if (p.size() <= l)  // Print all paths which are as short as the shortest one. 
			{
				if (verbose) System.out.println(p);
				possiblePathsForNeighbor.add(p);  //FIXME: possiblePathsForNeighbor.add(p);  // Doesn't work: .add(new Path(p));
			}
			p = i.next();
		}

		boolean connected = false;
		
		while (connected == false && possiblePathsForNeighbor.isEmpty() == false)
		{
			if (verbose) System.out.println("Random path for neighbor solution:");
			Path randomPath = possiblePathsForNeighbor.aPath();
			possiblePathsForNeighbor.remove(randomPath);
			if (verbose) System.out.println(randomPath);
			
			Vertex randomVertex;
			Edge randomEdge;
			
			// Select randomly either origin or destination of the path.
			double randomSelection = Math.random();
			
			// Select vertex and edge.
			if (randomSelection >= 0.5)
			{
				if (verbose) System.out.println("Origin selected!");
				randomVertex = randomPath.getOrigin();
				randomEdge = randomPath.getOriginEdge();
			}
			else
			{
				if (verbose) System.out.println("Destination selected!");
				randomVertex = randomPath.getDestination();
				try
				{
					randomEdge = randomPath.getDestinationEdge();
				}
				catch (NoSuchElementException e)
				{
					System.out.println("Randomly selected node does not have edges!  Cannot start simulated annealing!");
					throw e;
				}
			}
			
			PathList possiblePathsForConnection = s1.possiblePathsForConnection(randomPath, randomVertex, randomEdge);
			
			connected = s1.connectPaths(possiblePathsForConnection, randomPath, randomVertex);
			
			// Connected two paths.  Breaking the while loop.
			if (connected) break;
			
			if (verbose) System.out.println("Trying the other end of the path.");
			// Select vertex and edge.
			if (randomSelection < 0.5)
			{
				if (verbose) System.out.println("Origin selected!");
				randomVertex = randomPath.getOrigin();
				randomEdge = randomPath.getOriginEdge();
			}
			else
			{
				if (verbose) System.out.println("Destination selected!");
				randomVertex = randomPath.getDestination();
				randomEdge = randomPath.getDestinationEdge();
			}
			
			possiblePathsForConnection = s1.possiblePathsForConnection(randomPath, randomVertex, randomEdge);

			connected = s1.connectPaths(possiblePathsForConnection, randomPath, randomVertex);
			
			// Connected two paths.  Breaking the while loop.
			if (connected) break;
			
			// Trying to create a new path if current path size is > 2.
			// FIXME: After creating a new path, remove the edge and the nodes from the old path.
			if (randomPath.size() > 2)
			{
				if (verbose) System.out.println("Trying to create a new path.");
				
				// Create a new path of size 1.
				Path newPath = new Path();
				newPath.addParent(g);
				s1.pathList.add(newPath);  // FIXME
				
				// Add random vertex and random edge to newPath.
				newPath.addVertex(randomVertex);
				newPath.addEdge(randomEdge);
				
				// Select the opposite vertex for random vertex.
				Vertex oppositeVertex = this.g.opposite(randomVertex, randomEdge);
				
				// Add the opposite vertex to new path.
				newPath.addVertex(oppositeVertex);
	
				if (verbose) System.out.println("New path: " + newPath);
				
				if (verbose) System.out.println("Path list : " + s1.pathList);
				
				if (verbose) System.out.println("Path: " + randomPath);
				// Remove vertex from random path.  This also removes all connected edges.
				// FIXME: Don't remove everything!
				if (verbose) System.out.println("Removing edge: " + randomEdge);
				randomPath.removeEdgeFromPath(randomEdge);
				
				//Vertex v1 = randomEdge.origin;
				//Vertex v2 = randomEdge.destination;
				
				if (verbose) System.out.println("Path: " + randomPath);
				if (verbose) System.out.println("Removing vertex: " + randomVertex);
				randomPath.removeVertexFromPath(randomVertex);
				//System.out.println("Removing vertex: " + oppositeVertex);
				//randomPath.removeVertexFromPath(oppositeVertex);
				if (verbose) System.out.println("Path: " + randomPath);
				
				s1.printSolution();
				
				// Remove random path.
				// FIXME: Why?
				//pathList.remove(randomPath);
				
				connected = true;
			}
			else
			{
				if (verbose) System.out.println("Randomly selected path is too short.  Cannot create a new path.");
			}
		}
		
		return s1;
	}

	private PathList possiblePathsForConnection(Path path, Vertex randomVertex, Edge randomEdge)
	{
		PathList possiblePathsForConnection = new PathList();
		
		//System.out.println("Random vertex and edge of the path (origin or destination):");
		//System.out.println("V: " + randomVertex);
		//System.out.println("E: " + randomEdge);

		//System.out.println("Other paths ending to vertex " + randomVertex + ":");
		
		Iterator<Path> j = this.pathList.pathsEndingToVertex(randomVertex);
		while (j.hasNext())
		{
			Path q = j.next();
			if (path != q)
			{
				possiblePathsForConnection.add(q);
				//System.out.println(q);
			}
		}
		
		return possiblePathsForConnection;
	}
	
	private boolean connectPaths(PathList possiblePathsForConnection, Path randomPath, Vertex randomVertex)
	{
		boolean connected = false;
		
		if (possiblePathsForConnection.size() < 1)
		{
			//System.out.println("Cannot connect! No possible paths for connection.");
		}
		else
		{
			//System.out.println("Selected path for connection:");
			int r = Math.round((float)Math.random() * (possiblePathsForConnection.size()-1));
			Path selectedPath = possiblePathsForConnection.get(r);
			//System.out.println(selectedPath);
			
			//System.out.println("Connecting paths.");
			connected = this.pathList.connectPaths(randomPath, selectedPath, randomVertex);
			
			/*if (connected)
			{
				System.out.println("Updated path list.");
				System.out.println(this.pathList);
			}
			else
			{
				System.out.println("Cannot connect paths.");
			}*/
		}
		
		return connected;
	}
	
	public void printSolution()
	{
		// Printing the solution.  (Prints the paths from the shortest one to the longest one.)
		System.out.println(pathList);
	}
	
	public void printShortestPath()
	{
		if (pathList.isEmpty()) return;
		System.out.println("Shortest path " + pathList.shortestPath());
	}
	
	public void printSolutionInfo()
	{
		System.out.println(this.getSolutionInfo());
	}

	public Graph toGraph()
	{
		return pathList.toGraph();
	}
}
