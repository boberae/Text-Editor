package proj4;

import javax.swing.*;

import java.io.*;
import java.util.Scanner;


/******************************************************************
Custom text editor that allows files to be uploaded/saved and
manipulated in the buffer with a variety of basic text commands.

@author Tony Bober
@version 1.0
 *****************************************************************/
public class Editor implements IEditor {

	/** Primary linked list which contains manipulatable data */
	private LinkedList myDoc;

	/** Secondary linked list for cut and paste commands */
	private LinkedList clipboard;

	/** Tracks if saved version of file is the most recent one */
	private boolean saved = true;

	/** Scans for new inputs from user */
	private Scanner scans = new Scanner(System.in);

	/** Counts number of successful commands entered for undo */
	private int n;

	/******************************************************************
    Constructor initializes the top pointer and cursor pointer to null
	 *****************************************************************/
	public Editor() {
		myDoc = new LinkedList();
		clipboard = new LinkedList();
		n = 0;
		//		saveCommand();
	}


	/******************************************************************
    Main method instantiates and runs the editor
	 *****************************************************************/
	public static void main(String[] args) {
		Editor v = new Editor();
		v.runEditor();
	}


	/******************************************************************
    Processes the given editor command

    @param command Text entered by user
    @returns True if command was accurately processed
	 *****************************************************************/
	@Override
	public boolean processCommand(String command) {
		Scanner scan = new Scanner(command);

		String line;

		// Make sure command was entered
		if(command.length() > 0) {

			//Begin by isolating first character received
			switch (command.charAt(0)) {

			//Insert sentence before the current line; make the 
			//inserted line the current line
			case 'b':
				//takes out letter b and white space
				if (command.length() > 1 && command.charAt(1) == ' '){
					line = scan.nextLine();
					myDoc.addBefore(line.substring(2));
					saveCommand();
				}
				else
					System.out.println("Please include a space"
							+ " after the 'b'");
				break;		


				//Insert sentence after the current line; make the
				//inserted line the current line
			case 'i':
				//takes out letter i & white space
				if (command.length() > 1 && command.charAt(1) == ' '){
					line = scan.nextLine();
					myDoc.addAfter(line.substring(2));
					saveCommand();
				}
				else
					System.out.println("Please include a space "
							+ "after the 'i'");
				break;		

				//Move current line indicator down 1 position or the 
				//number of positions specified by the user
			case 'm':
				if (command.length() == 1){
					myDoc.next();
				}
				else if (command.length() > 1 && command.charAt(1) == 
						' '){
					line = scan.nextLine();
					try{
						int numLines = Integer.parseInt
								(line.substring(2));
						if (numLines < 0)
							System.out.println("Must use "
									+ "positive numbers");
						else {
							for (int i = 0; i < numLines; i++)
								myDoc.next();
						}
					}
					catch(NumberFormatException e){
						System.out.println("Invalid input!");
					}
				}
				else
				{
					System.out.println("Please include a space "
							+ "after the 'm'");
				}
				break;	

			case 'u':
				//Undo previous operations (excluding save/load)
				if (command.length() > 1 && command.charAt(1) == 'd') {
					if (n > 1){
						loadDatabase("save" + (n-2));
						n--;
					}				
					else if (n == 1) {
						myDoc = new LinkedList();
					}
					saved = false;
				}

				//Move current line indicator up 1 position, or the
				//number of positions specified by the user
				else if (command.length() == 1){
					myDoc.previous();
				}
				else if (command.length() > 1 && command.charAt(1) == 
						' '){
					line = scan.nextLine();
					try{
						int numLines = Integer.parseInt
								(line.substring(2));
						if (numLines < 0)
							System.out.println("Must use "
									+ "positive numbers");
						for (int i = 0; i < numLines; i++)
							myDoc.previous();
					}
					catch(NumberFormatException e){
						System.out.println("Invalid input!");
					}
				}
				else{
					System.out.println("Please include a space "
							+ "after the 'u'");
				}
				break;	

				//Remove the current line, or the line values specified
				//by the user; make the next line the current line, if
				//there is a next line; otherwise make the previous
				//line the current line, if there is a previous line
			case 'r':
				if (command.length() == 1) {
					myDoc.remove();
					saveCommand();
				}
				else if (command.length() > 1 && command.charAt(1) == 
						' '){
					line = scan.nextLine();
					int lineNum = myDoc.getLineNumber();
					try{
						int numLines = Integer.parseInt
								(line.substring(2));
						if (numLines < 0)
							System.out.println("Must use "
									+ "positive numbers");
						else if (lineNum + numLines > myDoc.size()){
							for (int i = lineNum; i <= myDoc.size();
									i++)
								myDoc.remove();
							saveCommand();
						}
						else {
							for (int i = 0; i < numLines; i++)
								myDoc.remove();
							saveCommand();
						}
					}
					catch(NumberFormatException e){
						System.out.println("Invalid input!");
					}
				}
				break;	

				//Display all lines in the buffer/file, or the lines 
				//specified by the user WITH LINE NUMBERS. 
				//Cursor is displayed with '>'
			case 'd':
				if (command.length() == 1){
					int lineNum = myDoc.getLineNumber();
					System.out.println("Start of the file:");
					for (int i = 0; i < myDoc.size(); i++) {
						if (i != lineNum)
							System.out.println("" + i + "     " 
									+ getLine(i));
						else 
							System.out.println("" + i + " >   " 
									+ getLine(i));
					}
					System.out.println("End of the file");
				}
				else if (command.length() > 1 && command.charAt(1) == 
						' '){
					// scan twice to get first number, and a third time 
					// to get the second number 
					scan.next();
					line = scan.next();
					int lineNum = myDoc.getLineNumber();
					try{
						int firstLine = Integer.parseInt(line);
						line = scan.next();
						int lastLine = Integer.parseInt(line);
						if (firstLine >= 0 && lastLine < myDoc.size() 
								&& lastLine >= firstLine){
							for (int i = firstLine; i <= lastLine; i++){
								if (i != lineNum)
									System.out.println("" + i + "     " 
											+ getLine(i));
								else 
									System.out.println("" + i + " >   " 
											+ getLine(i));
							}
							saved = false;
						}
						else if (lastLine < firstLine)
							System.out.println("Selected line numbers "
									+ "are out of order");
						else
							System.out.println("Values are outside of "
									+ "allowable range");
					}
					catch(Exception e){
						System.out.println("Invalid input!");
					}
				}
				else{
					System.out.println("Please include a space "
							+ "after the 'd', and between line values");
				}
				break;		

			case 'c':
				//Cut specified lines from the file, and append onto an
				//internal clipboard. Make the next line the current
				//line, if there is a next line
				if (command.length() > 2 && 
						command.substring(0,3).equals("cut")) {
					scan.next();
					line = scan.next();
					try{
						int firstLine = Integer.parseInt(line);
						line = scan.next();
						int lastLine = Integer.parseInt(line);
						if (firstLine >= 0 && lastLine < myDoc.size() 
								&& lastLine >= firstLine){
							clipboard.removeAll();
							myDoc.gotoLineNumber(firstLine);
							for (int i = firstLine; i <= lastLine; i++){
								clipboard.addAfter(getCurrentLine());
								myDoc.remove();
							}
							saveCommand();
						}
						else if (lastLine < firstLine)
							System.out.println("Selected line numbers "
									+ "are out of order");
						else
							System.out.println("Values are outside of "
									+ "allowable range");
					}
					catch(Exception e){
						System.out.println("Invalid input!");
					}
				}

				//Clear (remove) all lines in the buffer/file (only if 
				//saved); set the current line indicator to null
				else if (command.length() == 1){
					if (saved)
						myDoc.removeAll();
					else
						System.out.println("You must save first!");

					saveCommand();
				}
				else{
					System.out.println("Invalid input!");
				}
				break;	

				//Save the contents to the specified TEXT file"
			case 's':
				line = scan.nextLine();

				// Create text file
				PrintWriter out = null;
				try 
				{
					if (command.length() > 1 && command.charAt(1) == ' '
							&& n > 0){
						out = new PrintWriter(new BufferedWriter
								(new FileWriter(line.substring(2))));
						//Add contents to file
						for (int i = 0; i < myDoc.size(); i++) {
							out.println("" + i + " " + getLine(i));
						}
						//Close file
						out.close();
						System.out.println("Saved!");
						saved = true;
					}
					else if (n < 1)
					{
						System.out.println("Nothing to save!");
						saved = true;
					}
					else
						System.out.println("Please include a space "
								+ "after the 's'");
				} 
				// file name invalid
				catch (IOException e) 
				{
					System.out.println("Invalid file name");
				}

				break;	

				//Display a help page of editor commands
			case 'h':
				System.out.println("COMMAND HELP:");
				System.out.println("-----------------------------");
				System.out.println("Command:        Meaning");
				System.out.println("b <sentence>    Insert sentence "
						+ "before the current line; make the inserted "
						+ "line the current line");
				System.out.println("i <sentence>    Insert sentence "
						+ "after the current line; make the inserted "
						+ "line the current line");
				System.out.println("m               Move current line "
						+ "indicator down 1 position");
				System.out.println("u               Move current line "
						+ "indicator up 1 position");
				System.out.println("r               Remove the current "
						+ "line; make the next line the current line, "
						+ "if there is a next line; otherwise make the "
						+ "previous line the current line, if there is "
						+ "a previous line");
				System.out.println("d               Display all lines "
						+ "in the buffer/file WITH LINE NUMBERS. "
						+ "Cursor is displayed with '>'");
				System.out.println("c               Clear (remove) all "
						+ "lines in the buffer/file (only if saved); "
						+ "set the current line indicator to 0");
				System.out.println("s <filename>    Save the contents "
						+ "to the specified TEXT file");
				System.out.println("h               Display a help "
						+ "page of editor commands");
				System.out.println("x               Exit the editor "
						+ "(only if saved)");
				System.out.println("e <sentence>    Insert sentence "
						+ "after the last line; make the inserted line "
						+ "the current line");
				System.out.println("m #             Move current line "
						+ "indicator down # positions");
				System.out.println("u #             Move current line "
						+ "indicator up # positions");
				System.out.println("r #             Remove # lines "
						+ "starting at the current line; make the next "
						+ "line the current line, if there is a next "
						+ "line; otherwise make the previous line the "
						+ "current line. ");
				System.out.println("d # *           Display from line "
						+ "# to * (inclusive) in the buffer/file WITH "
						+ "LINE NUMBERS.");
				System.out.println("l <filename>    Load the contents "
						+ "of the specified TEXT file into editor "
						+ "buffer (only if saved); make the first line "
						+ "the current line");
				System.out.println("cut # $         Cut lines from the "
						+ "file from # to $, and append onto an "
						+ "internal clipboard. Make the next line the "
						+ "current line, if there is a next line; "
						+ "otherwise make the previous line the "
						+ "current line, if there is a previous line");
				System.out.println("pas             Paste Clipboard "
						+ "before the current line position.  Leave "
						+ "current line unchanged. ");
				System.out.println("ud              Undo the effects "
						+ "of the last operation (excluding save and "
						+ "load); should support multiple undo "
						+ "operations.");

				break;	

				//Exit the editor (only if saved)
			case 'x':
				if (saved)
					System.exit(0);
				else
					System.out.println("You must save first!");	
				break;

				//Insert sentence after the last line; make the 
				//inserted line the current line
			case 'e':
				if (command.length() > 1 && command.charAt(1) == ' '){
					line = scan.nextLine();
					myDoc.last();
					myDoc.addAfter(line.substring(2));
					saveCommand();
				}
				else
					System.out.println("Please include a space "
							+ "after the 'e'");
				break;	

				//Load the contents of the specified TEXT file into 
				//editor buffer (only if saved); make the first line 
				//the current line
			case 'l':
				if (saved){
					scan.next();
					line = scan.next();
					if (loadDatabase(line))
						System.out.println("Loaded!");
				}
				else{
					System.out.println("You must save first!");	
				}
				break;

				//Paste clipboard before the current line position.  
				//Leave current line unchanged
			case 'p':
				if (command.length() > 2 && 
						command.substring(0,3).equals("pas")){
					for (int i = clipboard.size() - 1; i >= 0; i--)
						myDoc.addBefore(clipboard.get(i));

					for (int i = clipboard.size() - 1; i >= 0; i--)
						myDoc.next();

					saveCommand();
				}
				break;	

				//Invalid command entry
			default:
				System.out.println("Not a command!");
			}
			scan.close();
			return true;
		}
		scan.close();
		return false;
	}


	/******************************************************************
    Given a line number, return the data in that node.

    @param lineNbr Line number for which to obtain data
    @returns String data from the specified node
	 *****************************************************************/
	@Override
	public String getLine(int lineNbr) {
		return myDoc.get(lineNbr); 
	}


	/******************************************************************
    Return the data in the active node

    @returns String data from the active node
	 *****************************************************************/
	@Override
	public String getCurrentLine() {
		return myDoc.get();
	}


	/******************************************************************
    Runs the editor in an infinite loop. Only 'x' command can exit the
    program 
	 *****************************************************************/
	private void runEditor() {
		String command;
		while(true) {
			System.out.println("Command:");
			command = scans.nextLine();
			processCommand(command);
		} 
	}


	/******************************************************************
    Attempts to load a new database from specified filename

	@param True if load was successful
	 *****************************************************************/
	private boolean loadDatabase(String filename) {

		try {
			Scanner fileReader = new Scanner(new File(filename)); 
			myDoc = new LinkedList();
			clipboard = new LinkedList();
			String line;
			while (fileReader.hasNextLine()){
				line = fileReader.nextLine();
				myDoc.addAfter(line.substring(2));
			}
			myDoc.first();
			fileReader.close();
		} catch (Exception ex) {
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Error in loading "
					+ "file");
			return false;
		}

		return true;
	}


	/******************************************************************
    Saves output after every valid manipulative command, in order for
    the undo command to function properly.
	 *****************************************************************/
	private void saveCommand() {
		saved = false;
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter
					(new FileWriter("save" + n)));
			//Add contents to file
			for (int i = 0; i < myDoc.size(); i++) {
				out.println("" + i + " " + getLine(i));
			}
			//Close file
			out.close();
			n++;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
