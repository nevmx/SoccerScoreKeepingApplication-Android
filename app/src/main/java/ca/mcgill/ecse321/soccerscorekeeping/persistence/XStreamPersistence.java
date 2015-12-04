package ca.mcgill.ecse321.soccerscorekeeping.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;

/*
 * This class has been modified from the original "Java Swing" app because the methods used to read
 * and write XML files are different for the Android internal storage.
 * - Max Neverov 11/29/2015
 */

public class XStreamPersistence
{
	private static XStream xstream = new XStream(new PureJavaReflectionProvider(
			new FieldDictionary(new SequenceFieldKeySorter())));
	private static String filename = "data.xml";
	private static File filesDir = null;


	public static boolean saveToXMLwithXStream(Object obj)
	{

		xstream.setMode(XStream.ID_REFERENCES);
		String xml = xstream.toXML(obj); //save our xml file
		
		try
		{
			File file = new File(filesDir, filename);
			FileWriter writer = new FileWriter(file);
			writer.write(xml);
			writer.close();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static Object loadFromXMLwithXStream()
	{
		xstream.setMode(XStream.ID_REFERENCES);
		try
		{
			File file = new File(filesDir, filename);
			FileReader fileReader = new FileReader(file);//load our xml file
			return xstream.fromXML(fileReader);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setAlias(String xmlTagName, Class<?> className)
	{
		xstream.alias(xmlTagName, className);
	}
	
	public static void setFilename(String fn)
	{
		filename=fn;
	}

	public static void setFilesDir(File dir) {
		filesDir = dir;
	}
}