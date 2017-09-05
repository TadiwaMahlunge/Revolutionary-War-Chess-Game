package chess;

public class KingRules extends Searches{

	/**
	 * Initialises the board being played on and searched for squares
	 * @param board Initialises the board being played on and searched.
	 */
	KingRules(GameBoard board){
		super(board);
	}
	
	/**
	 *  Add all the possible square's the King could move to to its Linked List
	 * @param piece The King who's moves are being updated
	 */
	public void selectKing(ChessPiece piece){
		int x = piece.getXPos();
		int y = piece.getYPos();
		
		for (int j = y-1; j <= y+1; j++)
			for (int i = x-1; i <= x+1; i++)
				if (!(i == x && j == y ))
					if(i <= 7 && j <=7 && i >= 0 && j >= 0)
						if(!getDangerToSquare(board.getSquareAt(i, j), piece.getTeam()))
							piece.addToList(board.getSquareAt(i, j));
	}
}
