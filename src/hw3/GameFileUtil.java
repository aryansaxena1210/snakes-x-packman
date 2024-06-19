package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import api.BodySegment;
import api.Cell;
import api.Exit;
import api.ScoreUpdateListener;
import api.Wall;

/**
 * Utility class with static methods for loading game files.
 */
public class GameFileUtil {
	/**
	 * @author Aryan Saxena
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) {
		
		try {
			
		File gameFile = new File(filePath);
		Scanner scnr = new Scanner(gameFile);
		
		//find the width and height of the game 
		String temp = scnr.nextLine();
		int[] dim= getWidhtAndHeight(temp);
		
		//note: dim[0]=width of the game, dim[1] = height of the game

		// reset the grid size
		game.resetGrid(dim[0], dim[1]);
		
		//fill in the walls, exits and ground on the grid
		renderGrid(scnr, temp, game, dim);

		
		//resetting lizard count to 0
		for(int i=0; i<game.getLizards().size(); i++) {
			game.removeLizard( game.getLizards().get(i));
		}
		
		
		//making new lizards
		makeLizards(scnr, game, temp);
			
		scnr.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
	
	}
	
	/**
	 * makes the lizards as specified in the document
	 * @param scnr
	 * @param game
	 * @param temp
	 */
	private static void makeLizards(Scanner scnr, LizardGame game, String temp) {
		
		ArrayList<BodySegment> lizardBody;

		while (scnr.hasNextLine()) {			
			lizardBody = new ArrayList<BodySegment>();
			Lizard lizard = new Lizard();
			temp = scnr.nextLine();//stores the first line beginning with 'L'
			temp = temp.substring(2);//removes the initial "L " part of the string
			
			//stores all the coordinates where body segments are to be added, in an array
			String[] coordinatesForBodySegments=temp.split(" ");
			
			for(String coordinate : coordinatesForBodySegments) {
				
				String[] ColumnAndRow = coordinate.split(",");
				Cell c = game.getCell(Integer.parseInt(ColumnAndRow[0]), Integer.parseInt(ColumnAndRow[1]));
				
				if (c.getExit()!=null || c.getWall()!=null) {
					System.out.println(" CELL OR WALL CANNOT HAVE A BODY SEGMENT!!!");
				}

				lizardBody.add( new BodySegment (lizard, c ));
				
			}
			
			lizard.setSegments(lizardBody);
			game.addLizard(lizard);
		}
		
	}

/**
 * sets the walls and exits as specified in the document
 * @param scnr
 * @param temp
 * @param game
 * @param dim
 */
	private static void renderGrid(Scanner scnr, String temp, LizardGame game, int[] dim) {
		for (int i=0; i<dim[1]; i++) {
			temp=scnr.nextLine();
			for (int j=0; j<dim[0]; j++) {
				
				if(temp.charAt(j)=='W') {
					Wall w = new Wall(game.getCell(j, i));
					game.addWall(w);
				}
				else if(temp.charAt(j) == 'E') {
					Exit e = new Exit(game.getCell(j, i));
					game.addExit(e);
				}
				else {}
			}
		}

		
	}


	/**
	 * returns the width (no of elements per row) and height (no. of rows)
	 * @param temp first line of the text file
	 * @return
	 */
	private static int[] getWidhtAndHeight(String temp) {
		
		String[] widthAndHeight = temp.split("x");
		
		 
		int[] dimensions = {Integer.parseInt(widthAndHeight[0]), Integer.parseInt(widthAndHeight[1])};
		return dimensions;
		}
		
	}


