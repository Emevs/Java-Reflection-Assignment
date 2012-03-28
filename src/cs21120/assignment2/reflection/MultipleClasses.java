package cs21120.assignment2.reflection;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This class handles the options where the user can select a file of classes to
 * get statistics on.
 * 
 * @author Emily Evans (eme3)
 * 
 */
public class MultipleClasses
{
	
	private Hashtable<String, ClassInfo> multiClassesTable = new Hashtable<String, ClassInfo>();
	
	/**
	 * Method to get the statistics for all classed in the file, firstly by
	 * using the Reader object to read the classes in from a file, and then
	 * using the ReflectionClass to get the information.
	 * 
	 * @param fileName
	 * @param recursion
	 */
	public void allClasses(String fileName, boolean recursion)
	{
		ArrayList<String> classArrayList = new ArrayList<String>();
		Reader reader = new Reader();
		classArrayList = reader.readFile(fileName);
		ReflectionClass rClass = new ReflectionClass(multiClassesTable);
		ClassInfo[] classInfo = new ClassInfo[classArrayList.size()];
		
		for (int i = 0; i < classArrayList.size(); i++)
		{
			classInfo[i] = rClass.classInformation(classArrayList.get(i));
			// this is for converting option 2 into option 4 on the menu in the
			// UI class. It handles the recursion if that is what the user
			// selected.
			if (recursion)
			{
				String[] referredClassesString = rClass.referredClasses();
				classInfo[i].setClasses(referredClassesString);
				classInfo[i].getStatistics();
				rClass.getAllStatistics(referredClassesString);
			}
			// adds ClassInfo objects to the hashtable, if not already in there.
			if (!multiClassesTable.containsKey(classInfo[i].getClassName()))
			{
				multiClassesTable
						.put(classInfo[i].getClassName(), classInfo[i]);
			}
		}
		
		getAllStatistics(classInfo);
	}
	
	/**
	 * Method to loop through the array of ClassInfo objects and get their
	 * statistics.
	 * 
	 * @param classInfo
	 */
	public void getAllStatistics(ClassInfo[] classInfo)
	{
		for (int i = 0; i < classInfo.length; i++)
		{
			classInfo[i].getStatistics();
		}
	}
}
