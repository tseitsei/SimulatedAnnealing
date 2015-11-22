/**
 * (C) Juha Kari 2012.
 */

import java.io.*;
import java.util.Vector;
import java.util.Iterator;

/**
 * Graph reader for Trivial Graph Format.
 * 
 * @see GraphReader
 * @see GraphWriter
 * 
 * @author Juha Kari
 *
 */
public class TGFGraphReader implements GraphReader {
	
	/* (non-Javadoc)
	 * @see GraphReader#readGraph(java.lang.String)
	 */
	@Override
	public Graph readGraph(String filename) throws GraphReadingException, IOException
	{
		Graph g = new Graph();
		
		Vector<String[]> vertexStringList = new Vector<String[]>();
		Vector<String[]> edgeStringList = new Vector<String[]>();
		
		Vector<Vertex> vertexList = new Vector<Vertex>();
		Vector<Edge> edgeList = new Vector<Edge>();
		
		FileReader f = new FileReader(filename);
		LineNumberReader l = new LineNumberReader(f);
		
		String line = null;
		while ((line = l.readLine()) != null)
		{
			line = line.trim();
			if (line.isEmpty() || line.charAt(0) == '#')
			{
				// Empty lines and comments are skipped.
				// Line beginning with '#' is a comment.
			}
			else
			{
				if (line.matches(".+ .+ .*")) // Two identifiers and a label -> this is an edge.
				{
					String identifier1 = line.split(" ")[0];
					String identifier2 = line.split(" ")[1];
					String label = line.split(" ")[2];
					
					String[] strings = new String[3];
					strings[0] = identifier1;
					strings[1] = identifier2;
					strings[2] = label;
					
					edgeStringList.add(strings);				
				}
				else if (line.matches(".+ .*")) // One identifier and a label -> this is a vertex.
				{
					String identifier1 = line.split(" ")[0];
					String label = line.split(" ")[1];
					
					String[] strings = new String[2];
					strings[0] = identifier1;
					strings[1] = label;

					vertexStringList.add(strings);
				}
			}
		}
		
		Iterator<String[]> v = vertexStringList.iterator();
		while (v.hasNext())
		{
			String[] strings = v.next();
			String identifier1 = strings[0];
			String label = strings[1];
			
			Vertex newVertex = new Vertex(label, identifier1);
			g.addVertex(newVertex);
			vertexList.add(newVertex);
		}
		
		Iterator<String[]> e = edgeStringList.iterator();

		while (e.hasNext())
		{
			String[] strings = e.next();
			String identifier1 = strings[0];
			String identifier2 = strings[1];
			String label = strings[2];
			
			if (g.getVertexByIdentifier(identifier1) == null)
			{
				String desc = "Illegal identifier in TGF file: " + identifier1 + "\n";
				desc += "Stopped reading file " + new File(filename).getName() + ".";
				throw new GraphReadingException(desc);
			}
			if (g.getVertexByIdentifier(identifier2) == null)
			{
				String desc = "Illegal identifier in TGF file: " + identifier2 + "\n";
				desc += "Stopped reading file " + new File(filename).getName() + ".";
				throw new GraphReadingException(desc);
			}
			
			Edge newEdge = new Edge(label, label, g.getVertexByIdentifier(identifier1), g.getVertexByIdentifier(identifier2), false);
			g.addEdge(newEdge);
			edgeList.add(newEdge);
		}

		return g;
	}
}
