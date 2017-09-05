package chess;

import chess.ChessPiece.Team;

public class Searches {
	protected GameBoard board;
	
	/**
	 * Initialises the board the searches will be applied to.
	 * @param board The board the searches will be applied to.
	 */
 	Searches(GameBoard board){
		this.board = board;
	}
 	
 	/**
 	 * Searches all the upper right diagonal squares for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void upperRightDiagonal(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int i = 0; i <=7 ; i++){
			int m = x+i;
			int n = y-i;
			if((m < 8) && (m >=0) && (n>=0) && (n<=7)){
				piece.addToList(board.getSquareAt(m, n));
				if (board.getSquareAt(m, n).unobstructedEnemeySearch(square) != 0)
					break;
			}
		}
	}
	
	/**
 	 * Searches all the lower right diagonal squares for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void lowerRightDiagonal(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();

		for (int i = 0; i <=7 ; i++){
			int m = x+i;
			int n = y+i;
			if((m < 8) && (m >=0) && (n>=0) && (n<=7)){
				piece.addToList(board.getSquareAt(m, n));
				if (board.getSquareAt(m, n).unobstructedEnemeySearch(square) != 0)
					break;
			}
		}
	}
	
	/**
 	 * Searches all the upper left diagonal squares for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void upperLeftDiagonal(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int i = 0; i <=7 ; i++){
			int m = x-i;
			int n = y-i;
			if((m < 8) && (m >=0) && (n>=0) && (n<=7)){
				piece.addToList(board.getSquareAt(m, n));
				if (board.getSquareAt(m, n).unobstructedEnemeySearch(square) != 0)
					break;
			}
		}
	}
	
	/**
 	 * Searches all the lower left diagonal squares for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void lowerLeftDiagonal(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int i = 0; i <=7 ; i++){
			int m = x-i;
			int n = y+i;
			if((m < 8) && (m >=0) && (n>=0) && (n<=7)){
				piece.addToList(board.getSquareAt(m, n));
				if (board.getSquareAt(m, n).unobstructedEnemeySearch(square) != 0)
					break;
			}
		}
	}
	
	/**
 	 * Searches all the squares above the chess piece for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void upwards(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int j = y; j >= 0 ; j--){
			piece.addToList(board.getSquareAt(x, j));
			if (board.getSquareAt(x, j).unobstructedEnemeySearch(square) != 0)
				break;
		}
	}
	
	/**
 	 * Searches all the squares below the chess piece for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void downwards(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int j = y; j <=7 ; j++){
			piece.addToList(board.getSquareAt(x, j));
			if (board.getSquareAt(x, j).unobstructedEnemeySearch(square) != 0)
				break;
		}
	}

	/**
 	 * Searches all the squares to the left of the chess piece for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void leftwards(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int i = x; i >= 0 ; i--){
			piece.addToList(board.getSquareAt(i, y));
			if (board.getSquareAt(i, y).unobstructedEnemeySearch(square) != 0)
				break;
		}
	}
	
	/**
 	 * Searches all the squares to the right of the chess piece for squares the piece can move to and stops at the first obstruction. 
 	 * @param square The square holding the piece being updated.
 	 */
	public void rightwards(GameSquare square){	
		ChessPiece piece = square.getChessPiece();
		int x = square.getXPos();
		int y = square.getYPos();
		
		for (int i = x; i <=7 ; i++){
			piece.addToList(board.getSquareAt(i, y));
			if (board.getSquareAt(i, y).unobstructedEnemeySearch(square) != 0)
				break;
		}
	}
	
	/**
 	 * Searches all the enemy pieces for whether they can move to the square parameter
 	 * @param square A square adjacent to the king being checked
 	 * @param team The team of the king who may be in danger.
 	 * @return Whether the square is in danger or not
 	 */
	public boolean getDangerToSquare(GameSquare square, chess.ChessPiece.Team team){
		boolean squareInDanger = false;
		
		if (square.getChessPiece()!=null)
			if (square.getChessPiece().getTeam() != team)
				return false;
		
		ChessPiece[] searchArray = null;
		if (team == Team.BLUE)
			searchArray = board.getReds();
		else
			searchArray = board.getBlues();
		
		outerloop: 
		for(ChessPiece p: searchArray)
			if(p!= null)
				for (GameSquare g: p.getList())
					if (square.equals(g)){
						squareInDanger = true;
						break outerloop;
					}			
		return squareInDanger;
	}
}
