package chess;

public class KnightRules {
	private GameBoard board;
	
	/**
	 * Initialises the board that will be played on
	 * @param board The board being played on and searched.
	 */
	KnightRules(GameBoard board){
		this.board = board;
	}

	/**
	 *  Add all the possible square's the Knight could move to to its Linked List
	 * @param piece The Knight who's moves are being updated
	 */
	public void selectKnight(ChessPiece piece){
		GameSquare square = board.getSquareAt(piece.getXPos(), piece.getYPos());
		
		int x = square.getXPos();
		int y = square.getYPos();
		
		int i,j;
		
		i = x+1;
		j = y+2;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x+1;
		j = y-2;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x-1;
		j = y+2;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x-1;
		j = y-2;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x+2;
		j = y+1;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x+2;
		j = y-1;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x-2;
		j = y+1;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
		
		i = x-2;
		j = y-1;
		if((i < 8) && (i >=0) && (j>=0) && (j<=7)){
			piece.addToList(board.getSquareAt(i, j));
		}
	}

}
