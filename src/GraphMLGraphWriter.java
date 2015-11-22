/**
 * (C) Juha Kari 2012.
 */

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Graph writer for GraphML format.
 * 
 * @see Graph
 * @see GraphWriter
 * @see GraphReader
 * 
 * @author Juha Kari
 *
 */
public class GraphMLGraphWriter implements GraphWriter {

	/* (non-Javadoc)
	 * @see GraphWriter#writeGraph(Graph, java.lang.String)
	 */
	@Override
	public void writeGraph(Graph g, String filename)
	{
		System.out.println("Writing graph g = " + g);
		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("root");
			doc.appendChild(root);
			
			OutputStream f = new FileOutputStream(new File(filename));
			
			XMLOutputFactory xfactory = XMLOutputFactory.newInstance();
			XMLStreamWriter w = xfactory.createXMLStreamWriter(f, "UTF-8");
			
			//w.writeStartDocument();
			w.writeStartDocument("UTF-8", "1.0");
			
			w.writeCharacters("\n");
			w.writeDTD("<!DOCTYPE graphml SYSTEM \"http://www.graphdrawing.org/dtds/graphml.dtd\">");
			w.writeCharacters("\n");
			
			// <graphml>
			w.writeStartElement("graphml");
			w.writeCharacters("\n");
			
			// <graph>
			w.writeStartElement("graph");
			w.writeAttribute("edgedefault", "undirected");
			w.writeAttribute("id", "G");
			w.writeCharacters("\n");
			
			Iterator<Position> vertices = g.vertices();
			while (vertices.hasNext())
			{
				Vertex v = (Vertex)vertices.next();
				
				// <node />
				w.writeEmptyElement("node");
				w.writeAttribute("id", v.o.toString());
				w.writeCharacters("\n");
			}
			
			Iterator<Position> edges = g.edges();
			while (edges.hasNext())
			{
				Edge e = (Edge)edges.next();
				
				// <edge />
				w.writeEmptyElement("edge");
				w.writeAttribute("id", e.id.toString());
				w.writeAttribute("source", e.origin.toString());
				w.writeAttribute("target", e.destination.toString());
				w.writeCharacters("\n");
			}

			// </graph>
			w.writeEndElement();
			w.writeCharacters("\n");
			
			// </graphml>
			w.writeEndElement();
			
			w.writeEndDocument();
			w.flush();
			w.close();
		}
		catch (ParserConfigurationException e)
		{
			System.out.println("Stopped writing file " + new File(filename).getName() + ".");
			System.out.println("Error - " + e.toString());
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Stopped writing file " + new File(filename).getName() + ".");
			System.out.println("Error - " + e.toString());
		}
		catch (XMLStreamException e)
		{
			System.out.println("Stopped writing file " + new File(filename).getName() + ".");
			System.out.println("Error - " + e.toString());
		}
	}
}
