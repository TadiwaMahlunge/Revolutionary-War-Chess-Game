package chess;

import chess.ChessPiece.Team;

public class PawnRules {
	private GameBoard board ;
	
	/**
	 * Initialises the board that the Pawn's rules apply to.
	 * @param board
	 */
	PawnRules(GameBoard board){
		this.board = board;
	}
	
	/**
	 *  Add all the possible square's the Pawn could capture to its Linked List
	 * @param piece The Pawn who's moves are being updated
	 */
	public void selectPawn(ChessPiece piece){
		GameSquare square = board.getSquareAt(piece.getXPos(), piece.getYPos());
		
		int x = square.getXPos();
		int y = square.getYPos();
		
		if (piece.getTeam() == Team.BLUE){			
			if (x+1 <=7 && (y-1>=0))
				if((board.getSquareAt(x+1, y-1).getChessPiece()!=null))
					piece.addToList(board.getSquareAt(x+1, y-1));

			if(x-1 >=0 && (y-1 >= 0))
				if((board.getSquareAt(x-1, y-1).getChessPiece()!=null))
					piece.addToList(board.getSquareAt(x-1, y-1));
			
			if ((y-1 >=0))	
				if((board.getSquareAt(x, y-1).getChessPiece()==null))
					piece.addToList(board.getSquareAt(x, y-1));
			
		}

		else if (piece.getTeam() == Team.RED){		
			if((x+1 < 8) && (y+1<= 7))
				if((board.getSquareAt(x+1, y+1).getChessPiece()!=null))
					piece.addToList(board.getSquareAt(x+1, y+1));

			if(x-1 >=0 && (y+1 <=7))
				if((board.getSquareAt(x-1, y+1).getChessPiece()!=null))
					piece.addToList(board.getSquareAt(x-1, y+1));
			
			if ((y+1 <=7))	
				if((board.getSquareAt(x, y+1).getChessPiece()==null))
					piece.addToList(board.getSquareAt(x, y+1));
		}
	}
	
	/**
	 * If a pawn can move twice (on it's first move) the square for its motion will be selected
	 * @param piece The Pawn who's moves are being selected.
	 */
	public void getMovesToSelectPawn(ChessPiece piece){
		GameSquare square = board.getSquareAt(piece.getXPos(), piece.getYPos());
		
		int x = square.getXPos();
		int y = square.getYPos();
		int moves = ((chess.Pawn) piece).getMoves();
		System.out.println("Pawn moves: " + moves);

		if (piece.getTeam() == Team.BLUE && moves == 2)		
				if((board.getSquareAt(x, y-moves).getChessPiece()==null))
					board.getSquareAt(x, y-moves).select(square);
		
		if (piece.getTeam() == Team.RED && moves == 2)
			if((board.getSquareAt(x, y+moves).getChessPiece()==null))
				board.getSquareAt(x, y+moves).select(square);
	}
}
