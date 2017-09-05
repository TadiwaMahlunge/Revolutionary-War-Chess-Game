package chess;

import chess.ChessPiece.Team;

public class RuleBook {
	private GameBoard board;
	private RookRules rookRules;
	private BishopRules bishopRules;
	private KnightRules knightRules;
	private PawnRules pawnRules;
	private KingRules kingRules;
	
	/**
	 * Initialises the game board the rules will be applied to and uses aggregation to house the different kinds of rules that will be used.
	 * @param board
	 */
	RuleBook (GameBoard board){
		this.board = board;
		rookRules = new RookRules(board);
		bishopRules = new BishopRules(board);
		knightRules = new KnightRules(board);
		pawnRules = new PawnRules(board);
		kingRules = new KingRules(board);
	}
	
	/**
	 * Updates the possible squares the chess piece could move to by clearing and refilling the Linked list containing the squares it can move to.
	 * @param piece The chess piece who's moves are being updated.
	 */
	public void updatePossibleMoves(ChessPiece piece){
		piece.clearList();
		
		if (piece instanceof chess.Pawn)
			pawnRules.selectPawn(piece);
		
		else if (piece instanceof chess.King){
		  kingRules.selectKing(piece);
		}
		
		else if (piece instanceof chess.Rook){
			rookRules.selectRook(piece);
		}
		
		else if (piece instanceof chess.Bishop){
			bishopRules.selectBishop(piece);
		}
			
		
		else if (piece instanceof chess.Queen){
			bishopRules.selectBishop(piece);
			rookRules.selectRook(piece);
		}
		
		else if (piece instanceof chess.Knight)
			knightRules.selectKnight(piece);
		
	}
	
	/**
	 * Selects all the squares that the chess piece on the parameter square can move to, giving a visual indication to the user.
	 * @param square The square holding a chess piece that wants to move.
	 */
	public void selectMoves(GameSquare square){
		ChessPiece piece = square.getChessPiece();
		System.out.println("Type: " + square.getChessPiece().getClass().getName()+ " moves selected\n");
		
		if (piece instanceof chess.Pawn){
			pawnRules.getMovesToSelectPawn(piece);
		}
		
		for (GameSquare g: piece.getList())
    	g.select(square);
	}
	
}
