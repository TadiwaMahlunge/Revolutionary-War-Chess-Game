package chess;

import javax.swing.*;
import java.awt.*;

public class GameSquare extends JButton {

	private int x;
	private int y;
	private ChessPiece chessPiece;
	private boolean selected;
	private ImageIcon icon;
	
	/**
	 * Initialises the game square and all its instance variables 
	 * @param xPos This is the x position of the chess piece on the board
	 * @param yPos This is the y position of the chess piece on the board
	 */
	GameSquare(int xPos, int yPos){
		super();
		x = xPos;
		y = yPos;
		chessPiece = null; 
		selected = false;
		
		// Test to see colour of the base tile
		if ((xPos+yPos) % 2 == 1){
			icon = new ImageIcon(getClass().getResource("/pics/black.png"));
			this.setIcon(icon);
		}
		else if ((xPos+yPos) % 2 == 0) {
			icon = new ImageIcon(getClass().getResource("/pics/white.png"));
			this.setIcon(icon);
		}
	}
	
	/**
	 * Gets the x position of the chess piece on the board
	 * @return The x position of the chess piece on the board
	 */
	public int getXPos(){
		return x;
	}
	
	/***
	 * Gets the y position of the chess piece on the board
	 * @return The y position of the chess piece on the board
	 */
	public int getYPos(){
		return y;
	}
	
	/**
	 * Sets the chess piece on this GameSquare
	 * @param piece The piece being placed on the GameSquare
	 */
	public void setChessPiece(ChessPiece piece){
		icon = piece.getIcon();
		this.setIcon(icon);
		chessPiece = piece;
		chessPiece.setXPos(this.x);
		chessPiece.setYPos(this.y);
	}
	
	/**
	 * Removes the current chess piece from the square
	 */
	public void removeChessPiece(){
		if ((x+y) % 2 == 1){
			icon = new ImageIcon(getClass().getResource("/pics/black.png"));
			this.setIcon(icon);
		}
		else if ((x+y) % 2 == 0) {
			icon = new ImageIcon(getClass().getResource("/pics/white.png"));
			this.setIcon(icon);
		}
		chessPiece = null;
	}
	
	/**
	 * Gets the chess piece currents on the square
	 * @return The chess piece on the square
	 */
	public ChessPiece getChessPiece(){
		return chessPiece;
	}
	
	/**
	 * Sets the image of the square to its default image
	 */
	public void setDefaultImage(){
		setIcon(icon);
	}
	
	/**
	 * Returns whether the square is selected or not
	 * @return Returns true if the square has been selected
	 */
	public boolean getSelected(){
		return selected;
	}
	
	/**
	 * Returns whether the square contains a piece that's an enemy to the parameter chess piece
	 * @param piece The chess piece being tested for an enemy
	 * @return Returns true if an enemy is found
	 */
	public boolean squareHasEnemy(ChessPiece piece){
		if (chessPiece == null) return false;
		else if (chessPiece.getTeam() == piece.getTeam()) return false;
		else 
			return true;
	}
	
	/**
	 * If this square contains a chess piece of the same team as that on the parameter square, nothing happens. 
	 * Otherwise, this square is selected. 
	 * @param square The square being tested for a chess piece on the same team as this one.
	 */
	public void select(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		// this. is the piece being selected by another "piece"
		if (chessPiece == null)  {
			selected = true;
			ImageIcon icon0 = new ImageIcon(getClass().getResource("/pics/selected.png"));
			setIcon(icon0);
			return;
		}
		
		else if (chessPiece.getTeam() == piece.getTeam()) {
			return;
		}
		
		else if (chessPiece.getTeam() != piece.getTeam()){ 
			selected = true;
			ImageIcon icon1 = new ImageIcon(getClass().getResource("/pics/kill.png"));
			setIcon(icon1);
			return;
		}
	}
	
  /**
   * Checks if this square houses an enemy chess piece.
   * @param team The team who's enemy is checked against.
   * @return Returns 0 if the square is empty, 1 if there is a friendly chess piece and 2 if there is an enemy chess piece.
   */
	public int unobstructedEnemeySearch(ChessPiece.Team team){
		if (chessPiece == null)
			return 0;
		else if (chessPiece.getTeam() == team) {
			return 1;
		}
		else 
			return 2;
	}
	
	 /**
   * Checks if this square houses an enemy chess piece.
   * @param square The square housing a chess piece who's enemy is checked against.
   * @return Returns 0 if the square is empty, 1 if there is a friendly chess piece and 2 if there is an enemy chess piece.
   */
	public int unobstructedEnemeySearch(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		if (chessPiece == null)
			return 0;
		else if (chessPiece.getTeam() == piece.getTeam()) {
			if(square.equals(this)) return 0;
			else return 1;
		}
		else 
			return 2;
	}
	
	/**
	 * The square is no longer selected and its default image is now used.  
	 */
	public void deselect(){
		selected = false;
		setDefaultImage();
	}	
}
