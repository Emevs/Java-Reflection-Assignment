package cs21120.assignment2.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Hashtable;

/**
 * This class gets all the classes that are recursively referred to by a given
 * class.
 * 
 * @author Emily Evans (eme3)
 * 
 */
public class ReferredClasses
{
	private Hashtable<String, Class<?>> classHashtable = new Hashtable<String, Class<?>>(
			211);
	
	/**
	 * Empty constructor.
	 */
	public ReferredClasses()
	{
		
	}
	
	/**
	 * Adapter method for the method below. Loops through a class array,
	 * converting them into an array of type string, containing the class names
	 * only.
	 * 
	 * @param className
	 * @return
	 */
	public String[] getReferredClasses(String className)
	{
		Class<?> classRef = null;
		String[] refClassesString = null;
		try
		{
			classRef = Class.forName(className);
			Class<?>[] refClasses = getReferredClasses(classRef);
			refClassesString = new String[refClasses.length];
			for (int i = 0; i < refClasses.length; i++)
			{
				if (refClasses[i] != null)
				{
					refClassesString[i] = refClasses[i].getName();
				}
			}
		} catch (ClassNotFoundException e)
		{
			System.out.println("There is no Class of that name.");
		}
		return refClassesString;
	}
	
	/**
	 * Method to call the recursion method and convert the hashtable of classes
	 * into an array of classes.
	 * 
	 * @param source
	 * @return
	 */
	public Class<?>[] getReferredClasses(Class<?> source)
	{
		recursion(source);
		Collection collection = classHashtable.values();
		Class<?>[] hashtableArray = (Class<?>[]) collection
				.toArray(new Class<?>[0]);
		return hashtableArray;
	}
	
	/**
	 * Method to recursively get the classes referred to by the class passed
	 * into this method.
	 * 
	 * @param source
	 */
	public void recursion(Class<?> source)
	{
		Class<?>[] fieldTypes = getFieldTypes(source.getDeclaredFields());
		
		Method[] methods = source.getDeclaredMethods();
		Class<?>[] returnTypes = getReturnTypes(methods);
		
		Class<?>[] interfaces = source.getInterfaces();
		
		Class<?>[] referredClasses = new Class[fieldTypes.length
				+ returnTypes.length + interfaces.length + 1];
		referredClasses[0] = source.getSuperclass();
		
		// Copies the above arrays into one array for less for loops.
		System.arraycopy(fieldTypes, 0, referredClasses, 1, fieldTypes.length);
		System.arraycopy(returnTypes, 0, referredClasses,
				fieldTypes.length + 1, returnTypes.length);
		System.arraycopy(interfaces, 0, referredClasses,
				returnTypes.length + 1, interfaces.length);
		
		for (int i = 0; i < referredClasses.length; i++)
		{
			// adds classes to the hashtable, if they are not primitive, null,
			// already in the hash table or if they are an array.
			if ((referredClasses[i] != null)
					&& (!(classHashtable.containsKey(referredClasses[i]
							.getName())))
					&& (!(referredClasses[i].isPrimitive()))
					&& (!referredClasses[i].isArray()))
			{
				classHashtable.put(referredClasses[i].getName(),
						referredClasses[i]);
				recursion(referredClasses[i]);
			}
		}
		
		// gets all the parameter types for each method of the class and adds to
		// hashtable if not already inside
		for (int j = 0; j < methods.length; j++)
		{
			Class<?>[] paramTypes = methods[j].getParameterTypes();
			for (int k = 0; k < paramTypes.length; k++)
			{
				if ((paramTypes[k] != null)
						&& (!(classHashtable.containsKey(paramTypes[k]
								.getName())))
						&& (!(paramTypes[k].isPrimitive()))
						&& (!paramTypes[k].isArray()))
				{
					classHashtable.put(paramTypes[k].getName(), paramTypes[k]);
					recursion(paramTypes[k]);
				}
			}
		}
		
		// gets all the parameter types for each constructor of the class and
		// adds to hashtable if not already inside
		Constructor[] constructors = source.getConstructors();
		for (int l = 0; l < constructors.length; l++)
		{
			Class<?>[] constructorTypes = constructors[l].getParameterTypes();
			for (int k = 0; k < constructorTypes.length; k++)
			{
				if ((constructorTypes[k] != null)
						&& (!(classHashtable.containsKey(constructorTypes[k]
								.getName())))
						&& (!(constructorTypes[k].isPrimitive()))
						&& (!constructorTypes[k].isArray()))
				{
					classHashtable.put(constructorTypes[k].getName(),
							constructorTypes[k]);
					recursion(constructorTypes[k]);
				}
			}
		}
		
	}
	
	/**
	 * Method to get all the field types in a class. 
	 * 
	 * @param fields
	 * @return fieldTypes
	 */
	public Class<?>[] getFieldTypes(Field[] fields)
	{
		Class<?>[] fieldTypes = new Class[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			fieldTypes[i] = fields[i].getType();
		}
		return fieldTypes;
		
	}
	
	/**
	 * Method to get all the return types for each method.
	 * 
	 * @param allMethods
	 * @return returnTypes
	 */
	public Class<?>[] getReturnTypes(Method[] allMethods)
	{
		Class<?>[] returnTypes = new Class[allMethods.length];
		for (int i = 0; i < allMethods.length; i++)
		{
			returnTypes[i] = allMethods[i].getReturnType();
			
		}
		return returnTypes;
	}
}
