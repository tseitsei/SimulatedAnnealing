/**
 * (C) Juha Kari 2012.
 */

import java.util.LinkedList;

/**
 * Randomizer class for linked lists containing data structure positions.
 * 
 * @see DataStructure
 * @see Position
 * 
 * @author Juha Kari
 *
 */
public class PositionRandomizer {
	public static LinkedList<Position> randomize(LinkedList<Position> l)
	{
		//LinkedList<Position> originalList = (LinkedList<Position>)l.clone();
		LinkedList<Position> originalList = new LinkedList<Position>(l);
		
		LinkedList<Position> randomizedList = new LinkedList<Position>();
		
		while (originalList.size() > 0)
		{
			int randomNumber = Math.round((float)Math.random() * (originalList.size()-1));
			Position randomPosition = originalList.remove(randomNumber);
			randomizedList.add(randomPosition);
		}
		
		return randomizedList;
	}
}
