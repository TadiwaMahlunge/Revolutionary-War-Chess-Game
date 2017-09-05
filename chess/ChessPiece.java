package chess;

import java.util.LinkedList;

import javax.swing.ImageIcon;

import chess.ChessPiece.Team;


public class ChessPiece {
	
	public enum Team {RED , BLUE};
	
	protected int x;
	protected int y;
	protected LinkedList <GameSquare> canMoveTo = new LinkedList<>();
	protected Team team;
	protected ImageIcon icon;
	protected boolean threatToTheThrone;
	
	/**
	 *  This gets the Icon of the piece
	 * @return this is the Image Icon the piece uses
	 */
	public ImageIcon getIcon(){
		return icon;
	}
	
	/**
	 * Sets this ImageIcon of the piece to the icon input
	 * @param icon The icon to change to.
	 */
	public void setIcon(ImageIcon icon){
		this.icon = icon;
	}
	
	/**
	 * Gets the Team of the ChessPiece
	 * @return The team value of the chess piece
	 */
	public Team getTeam(){
		return team;
	}
	
	/**
	 * Moves a chess piece from one square to another and clears the record of squares it can move to.
	 * @param from The GameSquare being moved from
	 * @param to The GameSquare being moved to 
	 */
	public void moveTo(GameSquare from, GameSquare to){	
		clearList();
		to.setChessPiece(this);
		from.removeChessPiece();
	}
	
	/**
	 * Gets the x position of the chess piece on the board
	 * @return The x position of the chess piece on the board
	 */
	public int getXPos(){
		return x;
	}
	
	/**
	 * Gets the x position of the chess piece on the board
	 * @return The x position of the chess piece on the board
	 */
	public int getYPos(){
		return y;
	}
	
	/**
	 * Sets the y position of the chess piece on the board
	 * @param x1 the new  x position of the chess piece on the board
	 */
	public void setXPos(int x1){
		 x = x1;
	}
	
	/**
	 * Sets the y position of the chess piece on the board
	 * @param y1  The new y position of the chess piece on the board
	 */
	public void setYPos(int y1){
		y = y1;
	}
	
	/**
	 * Sets the chess piece's threat to a king to a new value
	 * @param t The new threat value of the chess piece
	 */
	public void setThreat(boolean t){
		threatToTheThrone = t;
	}
	
	/**
	 * Gets whether the chess piece is a threat to a king or not
	 * @return Returns the threat value of chess piece
	 */
	public boolean getThreat(){
		return threatToTheThrone;
	}
	
	/**
	 * Add the game square to the list of squares the chess piece can move to if the square contains an enemy or is empty. 
	 * If the square contains an enemy king, the king is put into check
	 * @param square The square to be added to the list.
	 */
	public void addToList(GameSquare square){
		if (square.getChessPiece()== null)
			canMoveTo.add(square);
		else if (square.getChessPiece().getTeam() != team)
			canMoveTo.add(square);
		if ((square.getChessPiece()!=null) && square.getChessPiece() instanceof chess.King && square.getChessPiece().getTeam() != team){
			threatToTheThrone = true;
			((King)square.getChessPiece()).setCheck(true);
		}
	}
	
	/**
	 * Gets the List of GameSquares the chess piece can move to 
	 * @return The List of GameSquares the chess piece can move to
	 */
	public LinkedList <GameSquare> getList(){
		return canMoveTo;
	}
	
	/**
	 * Checks whether the chess piece can move anywhere or not
	 * @return Whether the chess piece can move anywhere or not
	 */
	public boolean checkEmptyList(){
		return canMoveTo.isEmpty();
	}
	
 /**
  * Clears the list of squares the chess piece can move to
  */
	public void clearList(){
		while (!canMoveTo.isEmpty()){
			canMoveTo.removeFirst();
		}
	}
 
	/**
	 * Sets the team of the chess piece 
	 * @param t The new Team of the chess piece.
	 */
	public void setTeam(Team t) {
		team = t;
	}
}
