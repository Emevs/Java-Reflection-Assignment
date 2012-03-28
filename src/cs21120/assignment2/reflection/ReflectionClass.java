package cs21120.assignment2.reflection;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Hashtable;

/**
 * This class gathers the main statistics by making use of the Java Reflection
 * API.
 * 
 * @author Emily Evans (eme3)
 * 
 */
public class ReflectionClass
{
	private Class<?> classData = null;
	private Hashtable<String, ClassInfo> allClasses = new Hashtable<String, ClassInfo>();
	private ClassInfo classInfo = null;
	
	/**
	 * Constructor, to take in a hashtable of ClassInfo objects.
	 * 
	 * @param classes
	 */
	public ReflectionClass(Hashtable<String, ClassInfo> classes)
	{
		allClasses = classes;
	}
	
	/**
	 * Method to create a new ClassInfo object and put into the hashtable, if
	 * its not in there already.
	 * 
	 * @param className
	 * @return
	 */
	public ClassInfo classInformation(String className)
	{
		try
		{
			classData = Class.forName(className);
			classInfo = new ClassInfo(classData.getName(),
					getConstructors().length, getPublicMethods(),
					getMethods().length, getFields().length,
					getNumberOfParameters(getMethods()));
			if (!allClasses.containsKey(classInfo.getClassName()))
			{
				allClasses.put(classInfo.getClassName(), classInfo);
				
			}
		} catch (ClassNotFoundException e)
		{
			System.out.println("There is no Class of that name.");
		}
		return classInfo;
	}
	
	/**
	 * Method to get the number of public methods a class has.
	 * 
	 * @return publicMethods.length
	 */
	public int getPublicMethods()
	{
		Method[] publicMethods = classData.getMethods();
		return publicMethods.length;
	}
	
	/**
	 * Method to get the number of methods in a class.
	 * 
	 * @return the number of declared methods.
	 */
	public Method[] getMethods()
	{
		return classData.getDeclaredMethods();
	}
	
	/**
	 * Method to get the number of constructors in a class.
	 * 
	 * @return the number of declared constructors.
	 */
	public Constructor[] getConstructors()
	{
		return classData.getConstructors();
	}
	
	/**
	 * Method to get the number of parameters from each method and add to an
	 * array.
	 * 
	 * @param allMethods
	 * @return parameters
	 */
	public int[] getNumberOfParameters(Method[] allMethods)
	{
		int[] parameters = new int[allMethods.length];
		for (int i = 0; i < allMethods.length; i++)
		{
			if (getParameterType(allMethods[i]) != null)
			{
				parameters[i] = getParameterType(allMethods[i]).length;
			} else
			{
				parameters[i] = 0;
			}
		}
		return parameters;
	}
	
	/**
	 * Method to get the parameter types from each method of a class.
	 * 
	 * @param meth
	 * @return parameter types
	 */
	public Class<?>[] getParameterType(Method meth)
	{
		return meth.getParameterTypes();
	}
	
	/**
	 * Method to get the an array of fields from each class.
	 * 
	 * @param meth
	 * @return an array of fields/
	 */
	public Field[] getFields()
	{
		return classData.getDeclaredFields();
	}
	
	/**
	 * Method to create a ReferredClasses object, to get all the classes
	 * recursively called by a class. This is to separate the different
	 * functionalities of the program.
	 * 
	 * @return referredClasses.
	 */
	public String[] referredClasses()
	{
		ReferredClasses refClasses = new ReferredClasses();
		String[] referredClasses = refClasses.getReferredClasses(classData
				.getName());
		getAllStatistics(referredClasses);
		
		return referredClasses;
	}
	
	/**
	 * Method to loop through the referred Classes, getting the statistics of
	 * each one.
	 * 
	 * @param referredClasses
	 */
	public void getAllStatistics(String[] referredClasses)
	{
		for (int i = 0; i < referredClasses.length; i++)
		{
			ReflectionClass newRefClass = new ReflectionClass(allClasses);
			ClassInfo classInfo = newRefClass
					.classInformation(referredClasses[i]);
			classInfo.getStatistics();
		}
		GetStatistics getStats = new GetStatistics(allClasses,
				classData.getName());
		
	}
	
}