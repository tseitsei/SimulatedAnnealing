/**
 * (C) Juha Kari 2012.
 */

/**
 * @author Juha Kari
 *
 */
public class Stat
{
	String filename;
	
	int nodes;
	int edges;
	
	int initialPaths;
	int finalPaths;
	
	public Stat(String filename, int nodes, int edges, int initialPaths, int finalPaths)
	{
		this.filename = filename;
		
		this.nodes = nodes;
		this.edges = edges;
		
		this.initialPaths = initialPaths;
		this.finalPaths = finalPaths;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public int getNodes()
	{
		return nodes;
	}
	
	public int getEdges()
	{
		return edges;
	}
	
	public int getInitialPaths()
	{
		return initialPaths;
	}
	
	public int getFinalPaths()
	{
		return finalPaths;
	}
}