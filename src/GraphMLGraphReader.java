/**
 * (C) Juha Kari 2012.
 */

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.Hashtable;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Graph reader for GraphML format.
 * 
 * @see Graph
 * @see GraphReader
 * @see GraphWriter
 * 
 * @author Juha Kari
 * 
 */
public class GraphMLGraphReader implements GraphReader {

	/* (non-Javadoc)
	 * @see GraphReader#readGraph(java.lang.String)
	 */
	@Override
	public Graph readGraph(String filename) throws GraphReadingException, IOException
	{
		Graph g = new Graph();
		
		Vector<Vertex> vertexList = new Vector<Vertex>();
		Vector<Edge> edgeList = new Vector<Edge>();
		
		try
		{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(filename));

            doc.getDocumentElement().normalize();
            String rootElement = doc.getDocumentElement().getNodeName();
            
            if (rootElement != "graphml")
            {
                System.out.println("Root element of the document is: " + rootElement);
            	System.out.println("Error - document should be graphml.");
            	return g;
            }
			
            NodeList listOfNodes = doc.getElementsByTagName("node");
            
            for (int i = 0; i < listOfNodes.getLength(); i++)
            {
            	Node n = listOfNodes.item(i);
            	NamedNodeMap m = n.getAttributes();
            	
            	Hashtable<String, String> h = new Hashtable<String, String>();
            	for (int j = 0; j < m.getLength(); j++)
            	{
            		String name = m.item(j).getNodeName();
            		String value = m.item(j).getNodeValue();
            		h.put(name, value);
            	}
            	
            	if (h.get("id") == null || h.get("id").length() < 1)
            	{
					String desc = "Illegal identifier in TGF file.  Node is missing the ID.\n";
					desc += "Stopped reading file " + new File(filename).getName() + ".";
					throw new GraphReadingException("Error - " + desc);
            	}
            	
				Vertex newVertex = new Vertex(h.get("id"), h.get("id"));
				g.addVertex(newVertex);
				vertexList.add(newVertex);
            }
            
            NodeList listOfEdges = doc.getElementsByTagName("edge");
            
            for (int i = 0; i < listOfEdges.getLength(); i++)
            {
            	Node n = listOfEdges.item(i);
            	NamedNodeMap m = n.getAttributes();
            	
            	Hashtable<String, String> h = new Hashtable<String, String>();
            	for (int j = 0; j < m.getLength(); j++)
            	{
            		String name = m.item(j).getNodeName();
            		String value = m.item(j).getNodeValue();
            		h.put(name, value);
            	}
            	
            	if (h.get("id") == null || h.get("id").length() < 1)
            	{
					String desc = "Illegal identifier in TGF file.  Edge is missing the ID.\n";
					desc += "Stopped reading file " + new File(filename).getName() + ".";
					throw new GraphReadingException("Error - " + desc);
            	}
            	if (h.get("source") == null || h.get("source").length() < 1)
            	{
					String desc = "Illegal identifier in TGF file.  Edge is missing the source.\n";
					desc += "Stopped reading file " + new File(filename).getName() + ".";
					throw new GraphReadingException("Error - " + desc);
            	}
            	if (h.get("target") == null || h.get("target").length() < 1)
            	{
					String desc = "Illegal identifier in TGF file.  Edge is missing the target.\n";
					desc += "Stopped reading file " + new File(filename).getName() + ".";
					throw new GraphReadingException("Error - " + desc);
            	}
            	
            	Edge newEdge = new Edge(h.get("id"), h.get("id"), g.getVertexByIdentifier(h.get("source")), g.getVertexByIdentifier(h.get("target")), false);
				g.addEdge(newEdge);
				edgeList.add(newEdge);
			}
		}
		catch (ParserConfigurationException e)
		{
			throw new GraphReadingException("Error - " + e.toString());
		}
		catch (SAXException e)
		{
			throw new GraphReadingException("Error - " + e.toString());
		}
		catch (NullPointerException e)
		{
			throw new GraphReadingException("Error - " + e.toString());
		}

		return g;
	}

}
