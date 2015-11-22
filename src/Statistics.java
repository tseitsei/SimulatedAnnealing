/**
 * (C) Juha Kari 2012.
 */

import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Juha Kari
 *
 */
public class Statistics
{
	// List of stats.
	Hashtable<String, Vector<Stat>> stats = new Hashtable<String, Vector<Stat>>();
	
	public Statistics()
	{
	}
	
	public void put(String filename, int nodes, int edges, int initialPaths, int finalPaths)
	{
		Vector<Stat> v;
		
		if (stats.get(filename) == null)
		{
			v = new Vector<Stat>();
		}
		else
		{
			v = stats.get(filename);
		}
		
		Stat newStat = new Stat(filename, nodes, edges, initialPaths, finalPaths);
		
		v.add(newStat);
		
		stats.put(filename, v);
	}
	
	public Vector<Stat> get(String filename)
	{
		return stats.get(filename);
	}
	
	public int size()
	{
		return stats.size();
	}
}
