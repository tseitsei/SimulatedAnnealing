/**
 * (C) Juha Kari 2012.
 */

import java.io.IOException;
import java.io.File;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import java.util.Vector;

/**
 * A program for determining the smallest amount of paths that can describe the given graph.
 * 
 * @see Graph
 * @see Solution
 * 
 * @author Juha Kari
 *
 */
public class SimulatedAnnealing {

	/**
	 * Main method for starting the program.
	 * <p>
	 * This program can read graphs in either Trivial Graph Format or GraphML.
	 * <p>
	 * <a href="http://en.wikipedia.org/wiki/Trivial_Graph_Format">Trivial Graph Format</a>
	 * <p>
	 * <a href="http://en.wikipedia.org/wiki/GraphML">GraphML</a>
	 * 
	 * @param args a filename for graph
	 */
	public static void main(String[] args)
	{
		// ----- Depth-first traversal -----
		
		// A-B-C-D
		// |\| |/|
		// E-F G H
		// |/ /|\|
		// I-J-K L
		// |\ /| |
		// M-N O-P
		
		// Print verbose messages or not.
		boolean verbose = false;
		
		// How many runs of simulated annealing per file?
		int runs = 10;
		
		// List for graph file names.
		LinkedList<String> fileList = new LinkedList<String>();
		
		// Number of paths in the initial solution.
		int numberOfPathsInInitialSolution = 0;
		
		// Number of paths in the final solution.
		int numberOfPathsInFinalSolution = 0;
		
		if (args.length < 1)
		{
			System.out.println("Give a graph directory as parameter!");
			System.exit(1);
		}
		else
		{
			System.out.println("Graph directory: " + args[0]);

			File dir = new File(args[0]);

			String[] children = dir.list();
			if (children == null)
			{
			    // Either directory does not exist or is not a directory
				System.out.println("Directory " + dir + " does not exist!");
			}
			else
			{
			    for (int i = 0; i < children.length; i++)
			    {
			        // Get filename of file or directory
			        String filename = dir + "\\" + children[i];
			        fileList.add(filename);
			        //System.out.println("file: " + filename);
			    }
			}
		}	
		
		// Iterating through all files in the file list.
		Iterator<String> i = fileList.iterator();
		
		// Currently processed graph.
		int currentGraph = 0;
		
		// Total number of graphs.
		int totalGraphs = fileList.size();
		
		// Total number of nodes.
		int totalNodes = 0;
		
		// Total number of edges.
		int totalEdges = 0;
		
		// Total number of paths in initial solutions.
		int totalPathsInInitialSolutions = 0;
		
		// Total number of paths in final solutions.
		int totalPathsInFinalSolutions = 0;
		
		// Total reduction in number of paths.
		int reduction = 0;
		
		// Statistics for simulated annealing.
		Statistics stats = new Statistics();
		
		System.out.println("Path decomposition of " + totalGraphs + " graphs.");
		while (i.hasNext())
		{
			String fn = (String)i.next();
			currentGraph++;
			
			for (int run = 0; run < runs; run++)
			{
				System.out.println("Run " + (run+1) + "/" + runs + ". Path decomposition for graph " + currentGraph + "/" + totalGraphs + ": " + fn);
				
				// Creating a graph.
				Graph g = new Graph();
				
				// Creating a Trivial Graph Format graph reader.
				//GraphReader gReader = new TGFGraphReader();
				
				// Creating a GraphML graph reader.
				GraphReader gReader = new GraphMLGraphReader();
				
				try
				{
					// Reading the graph file.
					g = gReader.readGraph(fn);
				}
				catch (IOException e)
				{
					System.out.println(e);
					System.exit(1);
				}
				catch (GraphReadingException e)
				{
					System.out.println(e);
					System.exit(1);
				}
				
				// Printing graph.
				if (verbose) System.out.println("g: " + g.toString());
				
				String graphInfo = g.getGraphInfo();
				
				if (verbose) System.out.println("g: " + graphInfo);
				
				if (g.isEmpty())
				{
					System.out.println("The given graph is empty.");
					System.exit(1);
				}
				
				// Setting parameters for simulated annealing.
				double initialTemperature = 5.5;
				double frozenTemperature = 5.0;
				double coolingRatio= 0.97;
				int equilibriumDetectionRate = 3;
				//int maxIterations = 1000;
				
				// -----------------------------
				// -----SIMULATED ANNEALING-----
				// -----------------------------
				
				double currentTemperature = initialTemperature;
				
				// Creating an initial solution.
				Solution s0 = new Solution(g);
				
				// Print graph info.
				System.out.println("g: " + s0.g.getGraphInfo());
				
				// Get the number of vertices in current graph.
				int nodes = s0.g.numVertices();
				// Get the number of edges in current graph.
				int edges = s0.g.numEdges();
				
				totalNodes += nodes;
				totalEdges += edges;
				
				// Create the initial solution with depth-first search (DFS).
				s0.createInitialSolutionDFS(verbose);
				
				// Create the initial solution with one edge long paths.
				//s0.createInitialSolutionOne(verbose);
				
				// Create the initial solution with DFS hybrid.
				//s0.createInitialSolutionDFSHybrid(verbose);
				
				numberOfPathsInInitialSolution = s0.pathList.size();
				
				// Printing the solution.  (Prints the paths from the shortest one to the longest one.)
				s0.printSolution();
				
				// Printing the shortest path.
				if (verbose) s0.printShortestPath();
				
				// Counting iterations during the simulated annealing.
				int iteration = 0;
				
				while (currentTemperature >= frozenTemperature)
				{
					int e = 0;
					
					while (e <= equilibriumDetectionRate)
					{
						e++;

						iteration++;
						System.out.println("Iteration: " + iteration);

						// 
						// Randomly select a new solution s1.
						// Sort the paths from the shortest to the longest.
						// Select the shortest path.
						// Move an edge from the selected path to an longer path.
						// 
						
						if (verbose) System.out.println(s0.pathList);
						PathList backupPathList = new PathList(s0.pathList);
						if (verbose) System.out.println("Created backup path list.");
						if (verbose) System.out.println(backupPathList);
						
						// Creating a neighbor solution.
						try
						{
							Solution s1 = s0.createNeighborSolution(verbose);  // FIXME: Search neighbors better.  This should return different s1 than the input s0.
		
							// Cost of these solutions.
							float d = s1.cost() - s0.cost();
							
							if (d != 0 && verbose)
							{
								System.out.println("------------------------------------------------------------");
								System.out.println("BEGIN-------------------------------------------------------");
								System.out.println("------------------------------------------------------------");
							}
							
							// Printing the cost of this solution.
							if (verbose) System.out.println("Cost: " + s0.cost() + " -> " + s1.cost());
							
							// Printing the delta.
							if (verbose) System.out.println("D   : " + d);
							
							double randomReal = Math.random();
							if (verbose) System.out.println("R   : " + randomReal);
							
							if (verbose) System.out.println("Temp: " + currentTemperature);
							
							double exp = -d/currentTemperature; 
							if (verbose) System.out.println("Exp : " + exp + " = -D/Temp");
							
							if (verbose) System.out.println("E   : " + e);
							
							double rT = Math.pow(e, exp);
							if (verbose) System.out.println("RT  : " + rT + " = e^(-D/Temp) = e^exp");
							
							if (randomReal <= rT)
							{
								if (verbose)
								{
									System.out.println("------------------------------------------------------------");
									System.out.println(randomReal + " <= " + rT + ".  Accepting the new solution.");
									System.out.println("------------------------------------------------------------");
								}
								
								// Accept the new solution s1.
								s0 = s1;
								numberOfPathsInFinalSolution = s0.pathList.size();
							}
							else
							{
								if (verbose)
								{
									System.out.println("------------------------------------------------------------");
									System.out.println(randomReal + " > " + rT + ".  Not accepting the new solution.");  // FIXME: Reject the new solution!
									System.out.println("------------------------------------------------------------");
									System.out.println("Using backup path list.");
									System.out.println(backupPathList);
								}
								s0.pathList = backupPathList;
							}
							
							if (d != 0 && verbose)
							{
								System.out.println("------------------------------------------------------------");
								System.out.println("END---------------------------------------------------------");
								System.out.println("------------------------------------------------------------");
							}
						}
						catch (NoSuchElementException ex)
						{
							// This happens if there is a randomly selected node without adjacent edges. 
							System.out.println("Ending program.");
							System.exit(1);
						}
						
						
					}
					
					currentTemperature = currentTemperature * coolingRatio;
					if (verbose) System.out.println("T   : " + currentTemperature);
					e = 0;
				}
				
				// return currentPathList;
				
				// -----------------------------
				// -----SIMULATED ANNEALING-----
				// -----------------------------
				
				// Creating a Trivial Graph Format graph reader.
				
				/*GraphWriter tgfWriter = new TGFGraphWriter();
				GraphWriter graphmlWriter = new GraphMLGraphWriter();
				*/
				//GraphWriter graphmlPathWriter = new GraphMLPathGraphWriter();
				
				// Writing the solution to an TGF file.
				//tgfWriter.writeGraph(s0.toGraph(), "C:\\Users\\Me\\Documents\\Eclipse\\workspace\\SimulatedAnnealing\\bin\\graph_output.tgf");
				
				// Writing the solution to an GraphML file.
				//graphmlWriter.writeGraph(s0.toGraph(), "C:\\Users\\Me\\Documents\\Eclipse\\workspace\\SimulatedAnnealing\\bin\\graph_output.graphml");
				
				s0.printSolution();
	
				stats.put(fn, nodes, edges, numberOfPathsInInitialSolution, numberOfPathsInFinalSolution);
				
				System.out.println("g: " + graphInfo);
				System.out.println("Paths in initial solution: " + numberOfPathsInInitialSolution);
				totalPathsInInitialSolutions += numberOfPathsInInitialSolution;
				numberOfPathsInInitialSolution = 0;
				System.out.println("Paths in final solution  : " + numberOfPathsInFinalSolution);
				totalPathsInFinalSolutions += numberOfPathsInFinalSolution;
				numberOfPathsInFinalSolution = 0;
				
				// Writing the solution with colored paths to an GraphML file.
				//graphmlPathWriter.writeGraph(s0.toGraph(), "C:\\Users\\Me\\Documents\\Eclipse\\workspace\\SimulatedAnnealing\\bin\\graph_output.graphml");
			}
		}
		
		// Iterating through all files in the file list.
		Iterator<String> i2 = fileList.iterator();
		
		while (i2.hasNext())
		{
			String fn = (String)i2.next();
			
			Vector<Stat> v = stats.get(fn);
			
			System.out.println(v.size());
			
			for (int index = 0; index < v.size(); index++)
			{
				Stat s = v.elementAt(index);
				String filename = s.getFilename();
				int initialPaths = s.getInitialPaths();
				int finalPaths = s.getFinalPaths();
				int nodes = s.getNodes();
				int edges = s.getEdges();
				
				System.out.println("E(" + index + ")/(" + filename + "): [" + nodes + "/" + edges + "]: (" + initialPaths + "), (" + finalPaths + ")");
			}
			
		}
		
		reduction = totalPathsInInitialSolutions - totalPathsInFinalSolutions;
		
		System.out.println("Total nodes in graphs           : " + totalNodes);
		System.out.println("Total edges in graphs           : " + totalEdges);
		System.out.println("Total paths in initial solutions: " + totalPathsInInitialSolutions);
		System.out.println("Total paths in final solutions  : " + totalPathsInFinalSolutions);
		System.out.println("Reduction                       : " + reduction);
		System.out.println("Reduction-%                     : " + (float)reduction/totalPathsInInitialSolutions*100);
		
		System.out.println("Stats.size()                    : " + stats.size());
	}
	
}
