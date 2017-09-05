package chess;

public class RookRules extends Searches {
	
	/**
	 * Initialises the board that the Rules will be applied to.
	 * @param board
	 */
	RookRules(GameBoard board){
		super(board);
	}
	
	/**
	 *  Add all the possible square's the Rook could capture or move to to its Linked List
	 * @param piece The Rook who's moves are being updated
	 */
	public void selectRook(ChessPiece piece){
		GameSquare square = board.getSquareAt(piece.getXPos(), piece.getYPos());
			upwards(square);
			downwards(square);
			leftwards(square);
    	rightwards(square);
	}
}
