/**
 * (C) Juha Kari 2012.
 */

import java.io.IOException;

/**
 * Interface for different graph readers.
 * 
 * @see Graph
 * @see GraphMLGraphReader
 * @see TGFGraphReader
 * 
 * @author Juha Kari
 *
 */
public interface GraphReader {
	public Graph readGraph(String filename) throws GraphReadingException, IOException;
}
