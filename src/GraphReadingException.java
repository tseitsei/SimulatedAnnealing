/**
 * (C) Juha Kari 2012.
 */

/**
 * Exception in reading graphs.
 * 
 * @see GraphReader
 * 
 * @author Juha Kari
 *
 */
public class GraphReadingException extends Exception {
	public static final long serialVersionUID = 1L;
	public GraphReadingException(String desc)
	{
		super(desc);
	}
}
