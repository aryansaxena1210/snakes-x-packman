package hw3;


import static api.Direction.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;

/**
 * @author Aryan Saxena
 * Class that models a game.
 */
public class LizardGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	/**
	 * stores the number of columns in the game's grid
	 */
	private int width;
	/**
	 * stores the number of rows in the game's grid
	 */
	private int height;
	/**
	 * stores an arraylist of all the lizards in the game
	 */
	private ArrayList<Lizard> lizards;
	/**
	 * stores an arraylist of all the cells in the game
	 */
	private ArrayList<Cell> grid;
	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		this.width=width;
		this.height=height;
		
		lizards = new ArrayList<Lizard>();
		grid = new ArrayList<Cell>();
		
		for(int i = 0; i<height;i++ ) {
			for (int j=0; j<width; j++) {
				grid.add(new Cell(j, i));
			}
		}
		//note: width represents the number of columns (elements per row)
	
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		wall.getCell().placeWall(wall);
	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		exit.getCell().placeExit(exit);
		
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		return lizards;
	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		lizards.add(lizard);
		scoreListener.updateScore(lizards.size());
		//adding the lizard's body segments to the grid
		for (BodySegment segment : lizard.getSegments() ) {
			Cell cellOfBodySegment = segment.getCell();
			for (Cell temp : grid) {
				if(temp == cellOfBodySegment) {
					temp.placeLizard(lizard);
				}
			}	
		}
	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {
		
		
		for(int i=0; i<lizards.size(); i++) {
			if(lizards.get(i)==lizard) {
				lizards.remove(i);
			}
		

		for (Cell c : grid) {
			for (BodySegment segmentToRemove: lizard.getSegments()) {
				if (segmentToRemove.getCell() == c) {
					c.removeLizard();
				}
			}
		}
		}
		scoreListener.updateScore(lizards.size());
	}

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {
		for(Cell cell : grid) {
			if(cell.getCol() == col && cell.getRow( )== row ) {
				return cell;
			}
		}
		return null;
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {
		if (dir == UP && row!=0) {
			return getCell(col, row -1);
		}
		else if(dir==DOWN && row!=height-1) 
			return getCell(col, row +1);
		else if(dir==LEFT && col!=0)
			return getCell(col-1, row);
		else if(dir==RIGHT && col!=width-1)
			return getCell(col+1, row);
		return null;
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {
		while (grid.size()>0) {
			grid.remove(0);
		}
		
		while(lizards.size()>0) {
			lizards.remove(0);
		}
		
		this.width = width;
		this.height= height;
		//note: height represents the number of row
				
		for(int i = 0; i<height;i++ ) {
			for (int j=0; j<width; j++) {
				grid.add(new Cell(j, i));
			}
		}
		
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {
		Cell temp = getCell(col, row);
				if(temp.getLizard() == null && temp.getWall() == null ) {
					return true;
				}
		return false;
	}

	/**
	 * Move the lizard specified by its body segment at the given position (col,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given column and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given column and row, in that
	 * case this method should do nothing. 0.10-0.1
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param col the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int col, int row, Direction dir) {
		
		//if there is no lizard present 
		if(getCell(col, row).getLizard()==null ) {/*do nothing*/ }
		
		
		else {
			//there is a lizard, check if the body segment at this cell is the head, tail or middle segment
			Lizard lizardToMove = getCell(col, row).getLizard();
			
				//if the lizard's head is on the cell
				if(getCell(col, row).getLizard().getHeadSegment().getCell()==getCell(col, row) ) {
					
					tryToMoveHead(lizardToMove, col, row, dir);
					
					}
				
				//if lizard's tail is on the cell 
				else if ( getCell(col, row).getLizard().getTailSegment().getCell()==getCell(col, row) ) {
					
					tryToMoveTail(lizardToMove, col, row, dir);
					
				}
				
				
				else {
					tryToMoveInnerSegment(lizardToMove, col, row, dir);
				}
				
			//check if any lizard exits and if game finishes
			 if(lizardToMove.getHeadSegment().getCell().getExit()!=null || lizardToMove.getTailSegment().getCell().getExit()!=null) 
				 removeLizard(lizardToMove);
			 if(lizards.size()==0)
				 dialogListener.showDialog("You win!");
					 
				 
			 }
			
			
		}
					



		
	/**
	 * Tries to move the lizard from the inner segment in the manner user specified
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void tryToMoveInnerSegment(Lizard lizardToMove, int col, int row, Direction dir) {
		if(dir == lizardToMove.getDirectionToSegmentAhead( lizardToMove.getSegmentAt(getCell(col, row)) ) ) {
			moveBodyForward(lizardToMove, col, row, dir);}
		else if(dir == lizardToMove.getDirectionToSegmentBehind( lizardToMove.getSegmentAt(getCell(col, row)) )) {
				moveBodyBackwards(lizardToMove, col, row, dir);
				}
		else {/*do nothing*/}
		}
		
	
	/**
	 * moves the body forward (in the direction where head points)
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void moveBodyForward(Lizard lizardToMove, int col, int row, Direction dir) {
			Direction moveHeadInThisDir = lizardToMove.getHeadDirection();
			int colHead = lizardToMove.getHeadSegment().getCell().getCol();
			int rowHead = lizardToMove.getHeadSegment().getCell().getRow();
			moveHeadForward(lizardToMove, colHead, rowHead, moveHeadInThisDir);
	}

	
	/**
	 * moves the body backward (in the direction where tail points)
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void moveBodyBackwards(Lizard lizardToMove, int col, int row, Direction dir) {
		Direction moveTailInThisDir = lizardToMove.getTailDirection();
		int colTail = lizardToMove.getTailSegment().getCell().getCol();
		int rowTail = lizardToMove.getTailSegment().getCell().getRow();
		moveTailBackwards(lizardToMove, colTail, rowTail, moveTailInThisDir);
		
	}

	/**
	 * Tries to move the lizard from the tail in the manner user specified

	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void tryToMoveTail(Lizard lizardToMove, int col, int row, Direction dir) {
		
		//if the adjacent cell is outside the grid, do nothing
		if(getAdjacentCell(col, row, dir) == null) {/*do nothing*/}  
		
		else {
			//if lizard is moving forward
			if(  dir == lizardToMove.getDirectionToSegmentAhead(lizardToMove.getTailSegment()) )  {
				moveTailForward(lizardToMove, col, row, dir);}
			else {
				//if lizard is moving back
				moveTailBackwards(lizardToMove, col, row, dir);
			}
		}

		
	}

	/**
	 * moves the tail in a direction that is NOT the same as the direction where the next segment of the lizard is 
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void moveTailBackwards(Lizard lizardToMove, int col, int row, Direction dir) {
		Cell moveTailToThis = getAdjacentCell(col, row, dir);
		
		if(!isAvailable(moveTailToThis.getCol(), moveTailToThis.getRow())) {/*do nothing*/}
		
		else{
		moveLizardAndEmptyHeadCell(lizardToMove, moveTailToThis);
		}
		
	}
	 /**
	  * moves the tail in a direction that is in the same as the direction where the next segment of the lizard is 
	  * @param lizardToMove
	  * @param col
	  * @param row
	  * @param dir
	  */
	private void moveTailForward(Lizard lizardToMove, int col, int row, Direction dir) {
		
		Direction dirToMoveHead= lizardToMove.getHeadDirection();
		Cell moveHeadToThis = getAdjacentCell( lizardToMove.getHeadSegment().getCell().getCol(), lizardToMove.getHeadSegment().getCell().getRow(), dirToMoveHead);
		
		if( !isAvailable(moveHeadToThis.getCol(), moveHeadToThis.getRow()) ){
			/*do nothing*/
		}
		else {
			moveLizardAndEmptyTailCell(lizardToMove, moveHeadToThis);
			
		}
		
	}

	/**
	 * Tries to move the lizard from the head in the manner user specified
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void tryToMoveHead(Lizard lizardToMove, int col, int row, Direction dir) {
		//if the adjacent cell is outside the grid, do nothing
		if(getAdjacentCell(col, row, dir) == null) {/*do nothing*/}  
		
		else {
			//if lizard is moving back
			if(  dir == lizardToMove.getDirectionToSegmentBehind(lizardToMove.getHeadSegment() ) ) {
				moveHeadBackwards(lizardToMove, col, row, dir);}
			else {
				//if lizard i moving back
				moveHeadForward(lizardToMove, col, row, dir);
			}
			
		}
	}


	/**
	 * moves the head in a direction that is the same as the direction where the previous segment of the lizard is 
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void moveHeadBackwards(Lizard lizardToMove, int col, int row, Direction dir) {
		
		
		Direction dirToMoveTail= lizardToMove.getTailDirection();
		Cell moveTailToThis = getAdjacentCell( lizardToMove.getTailSegment().getCell().getCol(), lizardToMove.getTailSegment().getCell().getRow(), dirToMoveTail);
		
		if( !isAvailable(moveTailToThis.getCol(), moveTailToThis.getRow())) {
			/*do nothing*/
		}
		else {
			moveLizardAndEmptyHeadCell(lizardToMove, moveTailToThis);
		}
		
	}
	
	
	
	/**
	 * moves the head in a direction that is NOT the same as the direction where the previous segment of the lizard is 
	 * @param lizardToMove
	 * @param col
	 * @param row
	 * @param dir
	 */
	private void moveHeadForward(Lizard lizardToMove, int col, int row, Direction dir) {
		
		
		Cell moveHeadToThis = getAdjacentCell(col, row, dir);
				
				if( !isAvailable(moveHeadToThis.getCol(), moveHeadToThis.getRow()) ){/*do nothing*/}
				
				else{
				{
				moveLizardAndEmptyTailCell(lizardToMove, moveHeadToThis);
				}
				}
	}
	
	/**
	 * moves the lizard in such a way that the tail cell is emptied
	 * @param lizardToMove
	 * @param moveHeadToThis
	 */
	private void moveLizardAndEmptyTailCell(Lizard lizardToMove, Cell moveHeadToThis){
		for(int i =0; i<lizardToMove.getSegments().size()-1; i++) {
			
			if(i==0) {
				lizardToMove.getTailSegment().getCell().removeLizard();
				}
			//set the cell of every body segment (except the head), to the cell occupied by the body segment infront of it
			lizardToMove.getSegments().get(i).setCell( lizardToMove.getSegments().get(i+1).getCell());
		}
		//adjusting the position of head
		lizardToMove.getHeadSegment().setCell(moveHeadToThis);
	}
	
	/**
	 * moves the lizard in such a way that the head cell is emptied
	 * @param lizardToMove
	 * @param moveTailToThis
	 */
	private void moveLizardAndEmptyHeadCell( Lizard lizardToMove, Cell moveTailToThis ) {
		for(int i =lizardToMove.getSegments().size()-1; i>0; i--) {
			
			if(i==lizardToMove.getSegments().size()-1) {
				lizardToMove.getHeadSegment().getCell().removeLizard();
				}
			//set the cell of every body segment (except the tail), to the cell occupied by the body segment behind it
			lizardToMove.getSegments().get(i).setCell( lizardToMove.getSegments().get(i-1).getCell());
		}
		//adjusting the position of tail
		lizardToMove.getTailSegment().setCell(moveTailToThis);
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) throws FileNotFoundException {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}
