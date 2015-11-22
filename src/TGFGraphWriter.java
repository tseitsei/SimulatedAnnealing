/**
 * (C) Juha Kari 2012.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Graph writer for Trivial Graph Format.
 * 
 * @see GraphWriter
 * @see GraphReader
 * 
 * @author Juha Kari
 *
 */
public class TGFGraphWriter implements GraphWriter {

	/* (non-Javadoc)
	 * @see GraphWriter#writeGraph(java.lang.String, Graph)
	 */
	@Override
	public void writeGraph(Graph g, String filename)
	{
		//System.out.println("Writing graph g = " + g);
		
		try
		{
			FileWriter f = new FileWriter(filename);
			PrintWriter w = new PrintWriter(f);
			
			Iterator<Position> vertices = g.vertices();
			while (vertices.hasNext())
			{
				Vertex v = (Vertex)vertices.next();
				
				String vStr = v.id + " " + v.o;
				
				w.print(vStr);
				if (vertices.hasNext())
				{
					w.println();
				}
			}
			
			w.println();
			w.println("#");
			
			Iterator<Position> edges = g.edges();
			while (edges.hasNext())
			{
				Edge e = (Edge)edges.next();
				
				String eStr = e.origin.id + " " + e.destination.id + " " + e.o;
				
				w.print(eStr);
				if (edges.hasNext())
				{
					w.println();
				}
			}
			
			w.flush();
			w.close();
		}
		catch (IOException e)
		{
			System.out.println("Stopped writing file " + new File(filename).getName() + ".");
			System.out.println("Error - " + e.toString());
		}
	}
}
