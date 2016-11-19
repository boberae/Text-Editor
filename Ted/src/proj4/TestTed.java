package proj4;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTed {

	private final ByteArrayOutputStream outContent = 
			new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
	
	@Test
	public void testI1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		assertEquals("Line 2", ed.getCurrentLine());
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 0", ed.getLine(0));
	}
	
	@Test
	public void testI2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("i Line 1");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 0", ed.getLine(0));
	}
	
	@Test
	public void testI3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("iLine 2");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Please include a space after the 'i'\n", 
				outContent.toString());
	}
	
	@Test
	public void testB1() {
		Editor ed = new Editor();
		ed.processCommand("b Line 2");
		ed.processCommand("b Line 1");
		ed.processCommand("b Line 0");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
	}
	
	@Test
	public void testB2() {
		Editor ed = new Editor();
		ed.processCommand("b Line 2");
		ed.processCommand("b Line 0");
		ed.processCommand("m");
		ed.processCommand("b Line 1");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 2", ed.getLine(2));
	}
	
	@Test
	public void testB3() {
		Editor ed = new Editor();
		ed.processCommand("b Line 2");
		ed.processCommand("b Line 1");
		ed.processCommand("bLine 0");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Please include a space after the 'b'\n", 
				outContent.toString());
	}
	
	@Test
	public void testU1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		assertEquals("Line 1", ed.getCurrentLine());
		ed.processCommand("u");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("u");
		assertEquals("Line 0", ed.getCurrentLine());
	}
	
	@Test
	public void testU2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
	}
	
	@Test
	public void testU3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 3");
		assertEquals("Line 0", ed.getCurrentLine());
	}
	
	@Test
	public void testU4() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 1");
		assertEquals("Line 1", ed.getCurrentLine());
	}
	
	@Test
	public void testU5() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 0");
		assertEquals("Line 2", ed.getCurrentLine());
	}
	
	@Test
	public void testU6() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u -1");
		assertEquals("Line 2", ed.getCurrentLine());
	    assertEquals("Must use positive numbers\n", 
	    		outContent.toString());
	}
	
	@Test
	public void testU7() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2.0");
		assertEquals("Line 2", ed.getCurrentLine());
	    assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testU8() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u2");
		assertEquals("Line 2", ed.getCurrentLine());
	    assertEquals("Please include a space after the 'u'\n", 
	    		outContent.toString());
	}
	
	@Test
	public void testM1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m");
		assertEquals("Line 1", ed.getCurrentLine());
		ed.processCommand("m");
		assertEquals("Line 2", ed.getCurrentLine());
		ed.processCommand("m");
		assertEquals("Line 2", ed.getCurrentLine());
	}
	
	@Test
	public void testM2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m 2");
		assertEquals("Line 2", ed.getCurrentLine());
	}
	
	@Test
	public void testM3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m 3");
		assertEquals("Line 2", ed.getCurrentLine());
	}
	
	@Test
	public void testM4() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m 1");
		assertEquals("Line 1", ed.getCurrentLine());
	}
	
	@Test
	public void testM5() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m 0");
		assertEquals("Line 0", ed.getCurrentLine());
	}
	
	@Test
	public void testM6() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m -1");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Must use positive numbers\n", 
				outContent.toString());
	}
	
	@Test
	public void testM7() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m 2.0");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testM8() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		assertEquals("Line 0", ed.getCurrentLine());
		ed.processCommand("m2");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Please include a space after the 'm'\n", 
				outContent.toString());
	}
	
	@Test
	public void testR1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("r");
		assertEquals("Line 2", ed.getCurrentLine());
	}
	
	@Test
	public void testR2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("r");
		assertEquals("Line 1", ed.getCurrentLine());
	}
	
	@Test
	public void testR3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u 2");
		ed.processCommand("r 2");
		assertEquals("Line 2", ed.getCurrentLine());
	}
	
	@Test
	public void testR4() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("r 3");
		assertEquals("Line 0", ed.getCurrentLine());
	}
	
	@Test
	public void testR5() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("r 0");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Line 2", ed.getLine(2));
	}
	
	@Test
	public void testR6() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("r -2");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Must use positive numbers\n", 
				outContent.toString());
	}
	
	@Test
	public void testR7() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("r 2.0");
		assertEquals("Line 1", ed.getCurrentLine());
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testD1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("d");
		assertEquals("Start of the file:\n"
				+ "0     Line 0\n"
				+ "1     Line 1\n"
				+ "2 >   Line 2\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testD2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("u");
		ed.processCommand("d");
		assertEquals("Start of the file:\n"
				+ "0     Line 0\n"
				+ "1 >   Line 1\n"
				+ "2     Line 2\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testD3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d 1 2");
		assertEquals("1     Line 1\n"
				+ "2     Line 2\n", outContent.toString());
	}
	
	@Test
	public void testD4() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d 2 4");
		assertEquals("Values are outside of allowable range\n", 
				outContent.toString());
	}
	
	@Test
	public void testD5() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d -1 2");
		assertEquals("Values are outside of allowable range\n", 
				outContent.toString());
	}
	
	@Test
	public void testD6() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d 1.0 2");
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testD7() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d 2");
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testD8() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d1 2");
		assertEquals("Please include a space after the 'd', and "
				+ "between line values\n", outContent.toString());
	}
	
	@Test
	public void testD9() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("d 2 1");
		assertEquals("Selected line numbers are out of order\n", 
				outContent.toString());
	}
	
	@Test
	public void testC1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("c");
		assertEquals("You must save first!\n", outContent.toString());
		assertEquals("Line 2", ed.getCurrentLine());
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 0", ed.getLine(0));
	}
	
	@Test
	public void testC2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("s testFile");
		ed.processCommand("c");
		ed.processCommand("d");
		assertEquals("Saved!\n" 
				+ "Start of the file:\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testS1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("s testFile");
		assertEquals("Saved!\n", outContent.toString());
	}
	
	@Test
	public void testS2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("stestFile");
		assertEquals("Please include a space after the 's'\n", 
				outContent.toString());
	}
	
	@Test
	public void testS3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("s ");
		assertEquals("Invalid file name\n", outContent.toString());
	}
	
	@Test
	public void testL1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("s testFile");
		ed.processCommand("c");
		ed.processCommand("i first line");
		ed.processCommand("i second line");
		ed.processCommand("l testFile");
		assertEquals("Saved!\n"
				+ "You must save first!\n", outContent.toString());
	}
	
	@Test
	public void testL2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("s testFile");
		ed.processCommand("c");
		ed.processCommand("i first line");
		ed.processCommand("i second line");
		ed.processCommand("s testFile2");
		ed.processCommand("l testFile");
		assertEquals("Saved!\n"
				+ "Saved!\n"
				+ "Loaded!\n", outContent.toString());
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
	}
	
	@Test
	public void testX1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("x");
		assertEquals("You must save first!\n", outContent.toString());
	}
	
	@Test
	public void testCutPaste1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut 1 3");
		ed.processCommand("u");
		ed.processCommand("pas");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Line 1", ed.getLine(0));
		assertEquals("Line 2", ed.getLine(1));
		assertEquals("Line 3", ed.getLine(2));
		assertEquals("Line 0", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
	}
	
	@Test
	public void testCutPaste2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut 1 1");
		ed.processCommand("m");
		ed.processCommand("pas");
		assertEquals("Line 3", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 2", ed.getLine(1));
		assertEquals("Line 1", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
	}
	
	@Test
	public void testCutPaste3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut 2 1");
		ed.processCommand("pas");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
	}
	
	@Test
	public void testCutPaste4() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut 3 5");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
		assertEquals("Values are outside of allowable range\n", 
				outContent.toString());
	}
	
	@Test
	public void testCutPaste5() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut -1 3");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
		assertEquals("Values are outside of allowable range\n", 
				outContent.toString());
	}
	
	@Test
	public void testCutPaste6() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut 1 3.0");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testCutPaste7() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut one 3");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	@Test
	public void testCutPaste8() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut1 3");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
		assertEquals("Invalid input!\n", outContent.toString());
	}
	
	public void testCutPaste9() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("i Line 3");
		ed.processCommand("i Line 4");
		ed.processCommand("cut1 3");
		assertEquals("Line 4", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Line 2", ed.getLine(2));
		assertEquals("Line 3", ed.getLine(3));
		assertEquals("Line 4", ed.getLine(4));
		assertEquals("Selected line numbers are out of order\n", 
				outContent.toString());
	}
	
	@Test
	public void testUndo1() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("ud");
		ed.processCommand("d");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Line 1", ed.getLine(1));
		assertEquals("Start of the file:\n"
				+ "0 >   Line 0\n"
				+ "1     Line 1\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testUndo2() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("ud");
		ed.processCommand("ud");
		ed.processCommand("d");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("Start of the file:\n"
				+ "0 >   Line 0\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testUndo3() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("ud");
		ed.processCommand("ud");
		ed.processCommand("ud");
		ed.processCommand("d");
		assertEquals("Start of the file:\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testUndo4() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("ud");
		ed.processCommand("ud");
		ed.processCommand("i New Line 1");
		ed.processCommand("d");
		assertEquals("New Line 1", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("New Line 1", ed.getLine(1));
		assertEquals("Start of the file:\n"
				+ "0     Line 0\n"
				+ "1 >   New Line 1\n"
				+ "End of the file\n", outContent.toString());
	}
	
	@Test
	public void testUndo5() {
		Editor ed = new Editor();
		ed.processCommand("i Line 0");
		ed.processCommand("i Line 1");
		ed.processCommand("i Line 2");
		ed.processCommand("ud");
		ed.processCommand("ud");
		ed.processCommand("i New Line 1");
		ed.processCommand("i New Line 2");
		ed.processCommand("ud");
		ed.processCommand("d");
		assertEquals("Line 0", ed.getCurrentLine());
		assertEquals("Line 0", ed.getLine(0));
		assertEquals("New Line 1", ed.getLine(1));
		assertEquals("Start of the file:\n"
				+ "0 >   Line 0\n"
				+ "1     New Line 1\n"
				+ "End of the file", outContent.toString());
	}

}
