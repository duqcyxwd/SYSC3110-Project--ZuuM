package GamePackage;

import java.util.LinkedList;

import javax.swing.ImageIcon;

/**
 * The Abstract Avatar class defines the controllable tile on the game: it is the only one that will be moved.
 * Avatar may pick up items, but not go through walls (generally). The behaviour is further developed in the subclasses Hero and NPC.
 * If an avatar is to die, it is to die upon collision, thus there is no need for hitPoints.
 * @author
 */

@SuppressWarnings("serial")
public abstract class Avatar extends Tile {
	
	private int lives;
	private int points;
	
	/**
	 * @param image pictorial representation of the Avatar to be used on a Tile
	 * @param lives represents the starting number of lives of the Avatar
	 * @param position represents the position of the Avatar on the Game
	 * @param game represents the game that is associated to this Avatar
	 */
	public Avatar(Position position, Game game, ImageIcon image, int lives) {
		super(position, game);
		setImage(image);
		this.lives = lives;
		this.points = 0;
	}
	
	
	/**
	 * Gets the next position to move to given a position. The position returned
	 * will be either up, down, left or right of the avatar.
	 * 
	 * @param position to move to or towards
	 * @return Position calculated
	 * @throws IllegalArgumentException if move is out of bounds, or if the Avatar
	 * will move into a wall.
	 */
	public Position getNextPosition(Position position) throws IllegalArgumentException{
		try {
			if (canMoveTo(position)) {
				return position;
			}
		} catch (ArrayIndexOutOfBoundsException exc) {
			throw new IllegalArgumentException("Not a possible move");
		}
		// determine which direction is wanted
		/*
		 * + - - - - - - - - - -
		 * + + - - - - - - - - +
		 * + + + - - - - - - + +
		 * + + + + - - - - + + +
		 * + + + + + - - + + + +
		 * + + + + +   + + + + +
		 * + + + + - - + + + + +
		 * + + + - - - - + + + +
		 * + + - - - - - - + + +
		 * + - - - - - - - - + +
		 * - - - - - - - - - - +
		 * 
		 * The pluses and minuses indicate which direction (up,down,left,right)
		 * to move in depending on where the user clicked.
		 */
		int gotoRow = position.getRow();
		int gotoCol = position.getCol();
		int myRow = this.position.getRow();
		int myCol = this.position.getCol();
		if (myRow == gotoRow && myCol == gotoCol) {
			throw new IllegalArgumentException("Cannot move to same position");
		}
		try {
			if (gotoRow <= myRow) { // we are moving upwards
				if (gotoCol <= myCol) { // we are moving left or up
					if (myCol-gotoCol >= myRow-gotoRow) return getNextPosition(LEFT);
					else return getNextPosition(TOP);
				} else { // we are moving right or up
					if (gotoCol-myCol > myRow-gotoRow) return getNextPosition(RIGHT);
					else return getNextPosition(TOP);
				}
			} else { // we are moving downwards
				if (gotoCol <= myCol) { // we are moving left or down
					if (myCol-gotoCol > gotoRow-myRow) return getNextPosition(LEFT);
					else return getNextPosition(BOTTOM);
				} else { // we are moving right or down
					if (gotoCol-myCol > gotoRow-myRow) return getNextPosition(RIGHT);
					else return getNextPosition(BOTTOM);
				}
			}
		} catch (IllegalArgumentException exc) {
			throw new IllegalArgumentException("Not a possible move");
		}
	}
	
	/**
	 * Gets the next position to move to given a direction. The position returned
	 * will be either up, down, left or right of the avatar.
	 * 
	 * @param position position to move to or towards
	 * @return Position calculated
	 * @throws IllegalArgumentException if move is out of bounds, or if the Avatar
	 * will move into a wall.
	 */
	protected Position getNextPosition(int direction) throws IllegalArgumentException {
		Position newPos;
		switch (direction) {
			case TOP:
				newPos = new Position(position.getRow()-1, position.getCol());
				if (!this.canMoveTo(newPos)) throw(new IllegalArgumentException("Move is not permitted"));
				return newPos;
			case BOTTOM:
				newPos = new Position(position.getRow()+1, position.getCol());
				if (!this.canMoveTo(newPos)) throw(new IllegalArgumentException("Move is not permitted"));
				return newPos;
			case LEFT:
				newPos = new Position(position.getRow(), position.getCol()-1);
				if (!this.canMoveTo(newPos)) throw(new IllegalArgumentException("Move is not permitted"));
				return newPos;
			case RIGHT:
				newPos = new Position(position.getRow(), position.getCol()+1);
				if (!this.canMoveTo(newPos)) throw(new IllegalArgumentException("Move is not permitted"));
				return newPos;
			default:
				throw(new IllegalArgumentException("Invalid direction"));
		}
	}

	/**
	 * Determines if the Tile at that position is Accessible and is adjacent to the current Position
	 * 
	 * @param position
	 * @return false if the position not accessible, true if if the new position is possible
	 */
	public boolean canMoveTo(Position position) {
		if (position.getRow() < 0 || position.getCol() < 0
				|| position.getRow() >= game.getHeight() || position.getCol() >= game.getWidth())
			return false;
		else if (!game.getTile(position).getAccessible()) return false;
		else if ((Math.abs(position.getRow() - this.position.getRow()) == 1 && Math.abs(position.getCol() - this.position.getCol()) == 0))
			return true; // moved one space vertically
		else if ((Math.abs(position.getRow() - this.position.getRow()) == 0 && Math.abs(position.getCol() - this.position.getCol()) == 1))
			return true; // moved one space horizontally
		else return false;
	}
	
	/**
	 * Get number of lives remaining
	 * 
	 * @return lives
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Increment lives by 1
	 */
	public void addLife() {
		lives++;
	}
	
	/**
	 * Decrement lives by 1
	 * 
	 * @return true if the number of lives has reached 0 or less
	 */
	public boolean removeLife() {
		lives--;
		if (lives == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the number of lives
	 */
	public void setLives(int lives) {
		this.lives=lives;
	}
	
	/**
	 * Increase point count by amount.
	 * @param amount
	 */
	public void addPoints(int amount) {
		points += amount;
	}
	
	/**
	 * Decrease point count by amount.
	 * @param amount
	 */
	public void subtractPoints(int amount) {
		points -= amount;
	}
	
	/**
	 * @return points collected
	 */
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	/**
	 * determine what happens when the avatar collides with another
	 * 
	 * @param avatar
	 * @return true if the character dies
	 */
	public abstract boolean collidesWith(Avatar avatar);
	
}