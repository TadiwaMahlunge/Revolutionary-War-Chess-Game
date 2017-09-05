package chess;

import javax.swing.ImageIcon;

public class Pawn extends ChessPiece{
	private int moves;
	
	/**
	 * Initialises the team and icon the Pawn uses.
	 * @param str This is the Team that the chess Piece is on.
	 */
	Pawn (String str){
		moves = 2;
		
		if(Team.valueOf(str) == Team.BLUE)
			icon = new ImageIcon(getClass().getResource("/pics/bluepawn.png")); 
		else 
			icon = new ImageIcon(getClass().getResource("/pics/redpawn.png")); 
		this.team = Team.valueOf(str);
	}
	
	/**
	 * This returns the number of moves that the pawn has left. After the first move, it will always be 1.
	 * @return The number of moves the pawn has left.
	 */
	public int getMoves(){
		return moves;
	}
	
	/**
	 * This overrides the general ChessPiece moveTo method as Pawns must set their number of moves to 1 after their first move.
	 */
	public void moveTo(GameSquare from, GameSquare to){
		setMoves(1);
		to.setChessPiece(this);
		from.removeChessPiece();
		clearList();
	}

	/**
	 * Sets the number of squares forward this pawn can move to to i
	 * @param i Number of squares the pawn can move forward.
	 */
	public void setMoves(int i) {
		moves = i;		
	}
}
