package chess;

public class BishopRules extends Searches  {
	
	/**
	 * Initialises the Game Board that the Rules apply to
	 * @param board This is the Game Board being played on
	 */
	BishopRules(GameBoard board){
		super(board);
	}
	/**
	 *  Add all the possible square's the Bishop could move to to its Linked List
	 * @param piece The Bishop or Queen who's moves are being updated
	 */
	public void selectBishop(ChessPiece piece){
		
		GameSquare square = board.getSquareAt(piece.getXPos(), piece.getYPos());

			upperRightDiagonal(square);
			lowerRightDiagonal(square);
			upperLeftDiagonal(square);
			lowerLeftDiagonal(square);	
	}
}
