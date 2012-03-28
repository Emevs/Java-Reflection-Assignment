package cs21120.assignment2.reflection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;

/**
 * This class generates the totals and averages for methods, members, fields,
 * constructors and parameters and standard deviation for methods and fields and
 * then prints them to csv files.
 * 
 * @author Emily Evans (eme3)
 * 
 */
public class GetStatistics
{
	private Hashtable<String, ClassInfo> allClasses = new Hashtable<String, ClassInfo>();
	private double totalMethods = 0;
	private double totalMembers = 0;
	private double totalFields = 0;
	private double totalConstructors = 0;
	private double totalParameters = 0;
	private double averageMethods = 0;
	private double averageMembers = 0;
	private double averageFields = 0;
	private double averageConstructors = 0;
	private double averageParameters = 0;
	private double standDevMethods = 0;
	private double standDevFields = 0;
	
	/**
	 * Constructor to create a get statistics object that converts a hashtable
	 * of ClassInfo objects into an array. It then calls the methods to
	 * calculate the statistics and print the information to a file.
	 * 
	 * @param classes
	 * @param className
	 */
	public GetStatistics(Hashtable<String, ClassInfo> classes, String className)
	{
		allClasses = classes;
		Collection collection = allClasses.values();
		ClassInfo[] classInformation = (ClassInfo[]) collection
				.toArray(new ClassInfo[0]);
		getTotal(classInformation);
		getAverages(classInformation);
		standardDeviation(classInformation);
		printToFile(className);
		methodsAndFields(className);
	}
	
	/**
	 * Method to loop through the array of ClassInfo objects to find the total
	 * methods, members, fields, constructors and average parameters.
	 * 
	 * @param classes
	 */
	public void getTotal(ClassInfo[] classes)
	{
		for (int i = 0; i < classes.length; i++)
		{
			totalMethods += classes[i].getTotalMethods();
			totalMembers += classes[i].getTotalMembers();
			totalFields += classes[i].getFields();
			totalConstructors += classes[i].getConstructors();
			totalParameters += classes[i].getAverageParameters();
		}
	}
	
	/**
	 * Method to calculate the average number of methods, members, fields,
	 * constructors and parameters for each class. This is done by dividing the
	 * totals calculated in the method above, by the number of classInfo objects
	 * in the array.
	 * 
	 * @param classes
	 */
	public void getAverages(ClassInfo[] classes)
	{
		averageMethods = totalMethods / classes.length;
		averageMembers = totalMembers / classes.length;
		averageFields = totalFields / classes.length;
		averageConstructors = totalConstructors / classes.length;
		averageParameters = totalParameters / classes.length;
	}
	
	/**
	 * Method to calculate the standard deviation of the fields and methods.
	 * 
	 * @param classes
	 */
	public void standardDeviation(ClassInfo[] classes)
	{
		for (int i = 0; i < classes.length; i++)
		{
			// take the average from each method/ field in the array, then
			// square the answer and keep a running total
			standDevMethods += Math.pow(
					(classes[i].getTotalMethods() - averageMethods), 2);
			standDevFields += Math.pow(
					(classes[i].getFields() - averageFields), 2);
			
		}
		// divide the answers by the length
		standDevMethods = standDevMethods / classes.length;
		standDevFields = standDevFields / classes.length;
		
		// square root the answer above to get the standard deviation
		standDevMethods = Math.sqrt(standDevMethods);
		standDevFields = Math.sqrt(standDevFields);
	}

	/**
	 * Method to print the average number of members, constructors and parameters to a csv file.
	 * @param className
	 */
	public void printToFile(String className)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(className
					+ "MembConsParam.csv");
			BufferedWriter bWriter = new BufferedWriter(fileWriter);
			
			bWriter.write(" , Members, Constructors, Parameters");
			bWriter.write("\nAverage," + averageMembers + ", "
					+ averageConstructors + "," + averageParameters);
			bWriter.close();
			fileWriter.close();
			System.out
					.println("Average members, constructors and parameters have been saved to: "
							+ className + "MembConsParam.csv");
			
		} catch (IOException e)
		{
			System.out.println("Error, unable to create file.");
		}
	}
	
	/**
	 * Method to write the total, average and standard deviation of the fields and methods.
	 * @param className
	 */
	public void methodsAndFields(String className)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(className
					+ "FieldsMethods.csv");
			BufferedWriter bWriter = new BufferedWriter(fileWriter);
			
			bWriter.write(" , Fields, Methods");
			bWriter.write("\nTotal," + totalFields + ", " + totalMethods);
			bWriter.write("\nAverage," + averageFields + ", " + averageMethods);
			bWriter.write("\nStandard Deviation," + standDevFields + ", "
					+ standDevMethods);
			bWriter.close();
			fileWriter.close();
			System.out
					.println("Total, average and standard deviation of fileds and methods have been saved to: "
							+ className + "FieldsMethods.csv");
		} catch (IOException e)
		{
			System.out.println("Error, unable to create file.");
		}
	}
}
