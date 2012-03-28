package cs21120.assignment2.reflection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is a storage object, to hold the number of constructors, fields,
 * average number of parameters, members, methods and a string array of the
 * classes that are recursively referred to by this class. It also contains a
 * getStatistics() method, that prints the information to the console, as well
 * as printing the number of referred classes to a csv file.
 * 
 * @author Emily Evans (eme3)
 * 
 */
public class ClassInfo
{
	private String className;
	private int constructors;
	private int fields;
	private int[] numberOfParameters;
	private int totalMembers;
	private int publicMethods;
	private int totalMethods;
	private String[] referredClasses;
	
	/**
	 * Constructor to create a class info object that has a name and the number
	 * of constructors, public methods, total methods, fields and and array of
	 * parameters for the number of parameters per method.
	 * 
	 * @param name
	 * @param constructs
	 * @param publicMeth
	 * @param totalMeth
	 * @param field
	 * @param numberOfParams
	 */
	public ClassInfo(String name, int constructs, int publicMeth,
			int totalMeth, int field, int[] numberOfParams)
	{
		className = name;
		constructors = constructs;
		publicMethods = publicMeth;
		totalMethods = totalMeth;
		fields = field;
		numberOfParameters = numberOfParams;
	}
	
	/**
	 * Getter method to return the class name.
	 * 
	 * @return className
	 */
	public String getClassName()
	{
		return className;
	}
	
	/**
	 * Getter method to return the number of constructors.
	 * 
	 * @return constructors
	 */
	public int getConstructors()
	{
		return constructors;
	}
	
	/**
	 * Getter method to return the number of public methods.
	 * 
	 * @return publicMethods
	 */
	public int getPublicMethods()
	{
		return publicMethods;
	}
	
	/**
	 * Getter method to return the number of total methods.
	 * 
	 * @return totalMethods
	 */
	public int getTotalMethods()
	{
		return totalMethods;
	}
	
	/**
	 * Getter method to return the number of fields.
	 * 
	 * @return fields
	 */
	public int getFields()
	{
		return fields;
	}
	
	/**
	 * Getter method to return an array list of the number of parameters that
	 * all methods in a class have.
	 * 
	 * @return numberOfParameters
	 */
	public int[] getNumberOfParameters()
	{
		return numberOfParameters;
	}
	
	/**
	 * Method to loop through the integer array 'numberOfParameters' and
	 * calculate the average number of parameters within a class.
	 * 
	 * @return averageParameters
	 */
	public int getAverageParameters()
	{
		int averageParams = 0;
		for (int i = 0; i < numberOfParameters.length; i++)
		{
			averageParams += numberOfParameters[i];
		}
		if (numberOfParameters.length != 0)
		{
			averageParams = averageParams / numberOfParameters.length;
		}
		return averageParams;
	}
	
	/**
	 * Getter to add the total number of methods and fields together and then
	 * return the answer.
	 * 
	 * @return totalMembers
	 */
	public int getTotalMembers()
	{
		totalMembers = totalMethods + fields;
		return totalMembers;
	}
	
	/**
	 * Getter to return an array of strings containing the names of all the
	 * classes recursively called to by this class.
	 * 
	 * @return referredClasses
	 */
	public String[] getReferedClasses()
	{
		return referredClasses;
	}
	
	/**
	 * Setter to pass in a string array containing the names of all the classes
	 * recursively called to by this class.
	 * 
	 * @param classes
	 */
	public void setClasses(String[] classes)
	{
		referredClasses = classes;
	}
	
	/**
	 * Method to print out the statistics to the console and to print the number
	 * of referred classes to a csv file.
	 */
	public void getStatistics()
	{
		
		System.out.println("Class Name: " + className);
		System.out.println("Total Public Methods: " + publicMethods);
		System.out.println("Total Methods: " + totalMethods);
		System.out.println("Total Members: " + getTotalMembers());
		System.out.println("Average Parameters: " + getAverageParameters());
		
		if (referredClasses != null)
		{
			System.out.println("Total Referred Classes: "
					+ referredClasses.length);
			FileWriter fileWriter;
			try
			{
				fileWriter = new FileWriter(className + "referredClasses.csv");
				BufferedWriter bWriter = new BufferedWriter(fileWriter);
				bWriter.write(" , Referred Classes, \n" + className + ", "
						+ referredClasses.length);
				bWriter.close();
				fileWriter.close();
			} catch (IOException e)
			{
				System.out.println("Unable to write to file.");
			}
		}
	}
}
