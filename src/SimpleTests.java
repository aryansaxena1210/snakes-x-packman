import static api.Direction.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import api.Exit;
import api.Wall;
import hw3.GameFileUtil;
import hw3.Lizard;
import hw3.LizardGame;
import hw3.MockGameConsole;
import ui.GameConsole;

/**
 * Examples of using the LizardGame, GameFileUtil, and Lizard classes. The
 * main() method in this class only displays to the console. For the full game
 * GUI, run the ui.GameMain class.
 */
public class SimpleTests {
	public static void main(String args[]) {		
		LizardGame game = new LizardGame(20, 20);
		MockGameConsole listeners = new MockGameConsole();
		game.setListeners(listeners, listeners);

		Lizard liz1 = new Lizard();
		ArrayList<BodySegment> segments = new ArrayList<BodySegment>();
		Cell tailCell = game.getCell(2,1);
		Cell innerCell1 = game.getCell(3,1);
		Cell innerCell2 = game.getCell(4,1);
		Cell headCell = game.getCell(5,1);
		BodySegment tail = new BodySegment(liz1, tailCell);
		BodySegment inner1 = new BodySegment(liz1, innerCell1);
		BodySegment inner2 = new BodySegment(liz1, innerCell2);
		BodySegment head = new BodySegment(liz1, headCell);
		segments.add(tail);
		segments.add(inner1);
		segments.add(inner2);
		segments.add(head);
		liz1.setSegments(segments);
		game.addLizard(liz1);

		Lizard liz2 = new Lizard();
		segments = new ArrayList<BodySegment>();
		tailCell = game.getCell(2,0);
		innerCell1 = game.getCell(3,0);
		innerCell2 = game.getCell(4,0);
		headCell = game.getCell(5,0);
		BodySegment tail2 = new BodySegment(liz2, tailCell);
		BodySegment inner21 = new BodySegment(liz2, innerCell1);
		BodySegment inner22 = new BodySegment(liz2, innerCell2);
		BodySegment head2 = new BodySegment(liz2, headCell);
		segments.add(tail2);
		segments.add(inner21);
		segments.add(inner22);
		segments.add(head2);
		liz2.setSegments(segments);
		game.addLizard(liz2);
		
		game.addWall(new Wall(game.getCell(6, 1)));
		game.move(3, 1, RIGHT);

		assertEquals("liz1: ", game.getCell(5, 1), head.getCell());

		game.move(4, 0, UP);

		assertEquals("liz2: ", game.getCell(5, 0), head2.getCell());
		assertEquals("liz2: ", game.getCell(2, 0), tail2.getCell());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}


	
	
	
	
	
	
	
	
	
	
	
private static void createTestFile() {
	FileWriter fw;
	try {
		fw = new FileWriter("speckcheck.txt");
		fw.write("8x9\n"
				+ "  W  WW .\n"
				+ " WWW   W.\n"
				+ "        .\n"
				+ " WWWWWW .\n"
				+ "       E.\n"
				+ " WWWWW W.\n"
				+ "   W   W.\n"
				+ "   W  W .\n"
				+ "WWWWWWWW.\n"
				+ "L 5,1 6,1 6,2 5,2 4,2 3,2 2,2\n"
				+ "L 1,2 0,2 0,3 0,4 1,4 2,4 3,4 4,4 5,4 6,4 6,5 6,6 5,6 4,6");
		fw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

private static void deleteTestFile() {
	FileWriter fw;
	File f = new File("speckcheck.txt");
	f.delete();
}
}
