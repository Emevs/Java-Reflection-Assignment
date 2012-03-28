package cs21120.assignment2.reflection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class handles the file reading for the menu options where the user can
 * input a file name that contains a list of classes to get statistics on.
 * 
 * @author Emily Evans (eme3)
 * 
 */
public class Reader
{
	/**
	 * Method to read all the classes in a named file into an array list. 
	 * 
	 * @param filename
	 * @return classArrayList
	 */
	public ArrayList<String> readFile(String filename)
	{
		FileReader fR;
		ArrayList<String> classArrayList = new ArrayList<String>();
		try
		{
			// Initialise new file reader and create and initialise buffered
			// reader
			fR = new FileReader(filename);
			BufferedReader bR = new BufferedReader(fR);
			String readInLine;
			int count = 0;
					
			try
			{
				// keep reading in each line, assigning it to line, until equal
				// to null
				while ((readInLine = bR.readLine()) != null)
				{
					classArrayList.add(count, readInLine);
					count++;
					System.out.println(readInLine);
				}
			} catch (IOException e)
			{
				System.out.println("There was an error reading in the file");
			}
		} catch (FileNotFoundException e)
		{
			System.out.println("Error, file not found");
		}
		return classArrayList;
	}

}
