package cs21120.assignment2.reflection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * This class displays a menu of options for the user to select and carries out
 * the correct tasks.
 * 
 * @author Emily
 * 
 */
public class UI
{
	private Hashtable<String, ClassInfo> classes = new Hashtable<String, ClassInfo>();
	
	/**
	 * Method to handle what happens when the user selects an option from the
	 * menu.
	 */
	public void menu()
	{
		// create scanner to read in users menu choice
		Scanner in = new Scanner(System.in);
		String temp;
		char menu_choice;
		System.out.println("Reflection Assignment:");
		do
		{
			System.out.println("Menu");
			this.printMenu();
			temp = in.next();
			menu_choice = temp.charAt(0);
			switch (menu_choice)
			{
				case 'S':
				case 's':
					System.out.print("Please input a class name: ");
					String className = inputName();
					ReflectionClass refClass = new ReflectionClass(classes);
					ClassInfo classInfo = refClass.classInformation(className);
					if (classInfo != null)
					{
						classInfo.getStatistics();
					}
					break;
				case 'M':
				case 'm':
					System.out.print("Please input a file name: ");
					String fileName = inputName();
					MultipleClasses multiClass = new MultipleClasses();
					multiClass.allClasses(fileName, false);
					break;
				case 'R':
				case 'r':
					System.out.print("Please input a class name: ");
					String inputClassName = inputName();
					ReflectionClass reflecClass = new ReflectionClass(classes);
					ClassInfo classInformation = reflecClass
							.classInformation(inputClassName);
					if (classInformation != null)
					{
						String[] referredClassesString = reflecClass
								.referredClasses();
						classInformation.setClasses(referredClassesString);
						classInformation.getStatistics();
						reflecClass.getAllStatistics(referredClassesString);
					}
					break;
				case 'A':
				case 'a':
					System.out.print("Please input a file name: ");
					String inputFileName = inputName();
					MultipleClasses multipleClasses = new MultipleClasses();
					multipleClasses.allClasses(inputFileName, true);
					break;
				case 'Q':
				case 'q':
					System.out.println("Thank you, goodbye!");
					break;
			}
		} while (menu_choice != 'Q' && menu_choice != 'q');
	}
	
	/**
	 * Method displays menu options onto the screen
	 */
	public void printMenu()
	{
		System.out.println("S - Statistics for a single class");
		System.out.println("M - Statistics for multiple classes in a file");
		System.out
				.println("R - Statistics for a single class including all classes recursively called");
		System.out
				.println("A -  Statistics for multiple classes in a file including all classes recursively called");
		System.out.println("Q - Quit");
	}
	
	/**
	 * Method to read in users input filename and return it
	 * 
	 * @return filename
	 */
	public String inputName()
	{
		// create buffered reader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputName = "";
		try
		{
			// read in user input
			inputName = br.readLine();
		}
		// handle IO exception
		catch (IOException ioe)
		{
			System.out.println("There was an error reading input.");
		}
		return inputName;
	}
	
	/**
	 * Main method to run the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		UI ui = new UI();
		ui.menu();
	}
}
