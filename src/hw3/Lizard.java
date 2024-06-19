package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;


/**
 * @author Aryan Saxena
 * Represents a Lizard as a collection of body segments.
 */
public class Lizard {
	/**
	 * ArrayList of body segments of the lizard (from tail to head)
	 */
	private ArrayList<BodySegment> segments = new ArrayList<BodySegment>();
	
	
	
	/**
	 * Constructs a Lizard object.
	 */
	public Lizard() {
		segments = null;
	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {
		this.segments = segments;
		
	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {
		return segments;
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {
		if (segments.size()<=1)
			return null;
		return segments.get(segments.size()-1);
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {
		if (segments.size()==0)
			return null;
		return segments.get(0);
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {
		for(BodySegment segment : segments) {
			if(segment.getCell() == cell )
				return segment;
		}
		return null;
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {
		//search for the index of the segment in the segments, then return the segment at the next index
		for(int i = 0; i< segments.size()-1; i++) {
			
			 if (segments.get(i) == segment) {
				return segments.get(i+1);
			}
		}
		return null;
	}

	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {
		//search for the index of the segment in the segments, then return the segment at the previous index
		for(int i = 1; i< segments.size(); i++) {
			if (segments.get(i) == segment) {
				return segments.get(i-1);
			}
		}
		return null;
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */
	public Direction getDirectionToSegmentAhead(BodySegment segment) {
		BodySegment ahead = getSegmentAhead(segment);
		if(ahead!=null) {
		int dy = ahead.getCell().getRow() - segment.getCell().getRow();
		int dx = ahead.getCell().getCol() - segment.getCell().getCol();
		
		if(dy== -1)
			return UP;
		else if(dy==1)
			return DOWN;
		else if (dx==1)
			return RIGHT;
		else if (dx==-1)
			return LEFT;
		else
			return null;
		}
		else {
			return null;
		}
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) {
		BodySegment behind = getSegmentBehind(segment);
		if(behind !=null) {
		int dy = behind.getCell().getRow() - segment.getCell().getRow();
		int dx = behind.getCell().getCol() - segment.getCell().getCol();
		
		if(dy== -1)
			return UP;
		else if(dy==1)
			return DOWN;
		else if (dx==1)
			return RIGHT;
		else if (dx==-1)
			return LEFT;
		else
			return null;
		}
		else
			return null;
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {
		
		if(getHeadSegment()!= null ) {
		BodySegment temp = segments.get( segments.size() - 2);
		
		int dy = getHeadSegment().getCell().getRow() - temp.getCell().getRow();
		int dx = getHeadSegment().getCell().getCol() - temp.getCell().getCol();
		
		if(dy== -1)
			return UP;
		else if(dy==1)
			return DOWN;
		else if (dx==1)
			return RIGHT;
		else if (dx==-1)
			return LEFT;
		}
		return null;
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {
		if(segments.size()==1) {return null;}
		else{
		BodySegment temp = segments.get(1);
		
		int dy = getTailSegment().getCell().getRow() - temp.getCell().getRow();
		int dx = getTailSegment().getCell().getCol() - temp.getCell().getCol();
		if(dy== -1)
			return UP;
		else if(dy==1)
			return DOWN;
		else if (dx==1)
			return RIGHT;
		else if (dx==-1)
			return LEFT;
		return null;
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}
}
