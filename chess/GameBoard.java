package chess;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chess.ChessPiece.Team;
import java.lang.Class;

public class GameBoard extends JFrame implements ActionListener
{
	private ImageIcon checkIcon = new ImageIcon(getClass().getResource("/pics/check.png"));
	private ImageIcon redWin = new ImageIcon(getClass().getResource("/pics/redwin.png"));
	private ImageIcon blueWin = new ImageIcon(getClass().getResource("/pics/bluewin.png"));
	private JPanel boardPanel = new JPanel();
	private RuleBook rules;
	private int boardHeight;
	private int boardWidth;
	private GameSquare[][] board; 
	private GameSquare activeSquare; // The one that's on the go.
	private ChessPiece[] redPieces;
	private ChessPiece[] bluePieces;

	
	public enum Turn {RED, BLUE};
	private Turn turn = Turn.RED;
	
	/**
	 * Create a new game board of the given size.
	 * Its instance variables are all initialised and pieces are added to the board.
	 * As soon as an instance of this class is created, it is visualised
	 * on the screen.
	 * 
	 * @param title the name printed in the window bar.
	 * @param width the width of the game area, in squares.
	 * @param height the height of the game area, in squares.
	 */
	public GameBoard(String title, int width, int height)
	{
		super();
		activeSquare = null;
		rules = new RuleBook(this);
		this.boardWidth = width;
		this.boardHeight = height;

		// Create game state
		this.board = new GameSquare[width][height];

		// Set up window
		setTitle(title);
		setSize(700,700);
		setLayout(new BorderLayout());
		setContentPane(boardPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(new GridLayout(height,width));
		
		for (int y = 0; y<height; y++){
			for (int x = 0; x<width; x++){

				board[x][y] = new GameSquare(x, y);
				board[x][y].addActionListener(this);
				
				boardPanel.add(board[x][y]);
			}
		}
		
		redPieces = new ChessPiece[16];
		bluePieces = new ChessPiece[16];
	
		
		
		addPieces();
		clearThreat();
		updateMovesForAllPieces();
		
		
		// make our window visible
	 	setVisible(true);
	 	JOptionPane.showMessageDialog(boardPanel, "Red Starts", "Begin Game", JOptionPane.INFORMATION_MESSAGE, redWin);
	}
	
	/**
	 * Add all the pieces to their respective arrays and to the game board.
	 */
	public void addPieces(){
		for (int i = 0; i<8; i++){
			redPieces[i] = new Pawn("RED");
			board[i][1].setChessPiece(redPieces[i]);
			bluePieces[i] = new Pawn("BLUE");
			board[i][6].setChessPiece(bluePieces[i]);
		}
		
		redPieces[8] = new Rook("RED");
		redPieces[9] = new Rook("RED");
		board[0][0].setChessPiece(redPieces[8]);
		board[7][0].setChessPiece(redPieces[9]);
		
		bluePieces[8] = new Rook("BLUE");
		bluePieces[9] = new Rook("BLUE");
		board[7][7].setChessPiece(bluePieces[8]);
		board[0][7].setChessPiece(bluePieces[9]);
		
		redPieces[10] = new Knight("RED");
		redPieces[11] = new Knight("RED");
		board[1][0].setChessPiece(redPieces[10]);
		board[6][0].setChessPiece(redPieces[11]);
		
		bluePieces[10] = new Knight("BLUE");
		bluePieces[11] = new Knight("BLUE");
		board[1][7].setChessPiece(bluePieces[10]);
		board[6][7].setChessPiece(bluePieces[11]);
		
		redPieces[12] = new Bishop("RED");
		redPieces[13] = new Bishop("RED");
		board[2][0].setChessPiece(redPieces[12]);
		board[5][0].setChessPiece(redPieces[13]);
		
		bluePieces[12] = new Bishop("BLUE");
		bluePieces[13] = new Bishop("BLUE");
		board[2][7].setChessPiece(bluePieces[12]);
		board[5][7].setChessPiece(bluePieces[13]);
		
		redPieces[14] = new Queen("RED");
		redPieces[15] = new King("RED");
		board[3][0].setChessPiece(redPieces[14]);
		board[4][0].setChessPiece(redPieces[15]);
		
		bluePieces[14] = new Queen("BLUE");
		bluePieces[15] = new King("BLUE");
		board[3][7].setChessPiece(bluePieces[14]);
		board[4][7].setChessPiece(bluePieces[15]);
	}

	/**
	 * Returns the GameSquare instance at a given location. 
	 * @param x the x co-ordinate of the square requested.
	 * @param y the y co-ordinate of the square requested.
	 * @return the GameSquare instance at a given location 
	 * if the x and y co-ordinates are valid - null otherwise. 
	 */
	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;

		return (GameSquare) board[x][y];
	}
	
	/**
	 * Returns the array of blue pieces
	 * @return All the blue pieces
	 */
	public ChessPiece[] getBlues(){
		return bluePieces;
	}
	
	/**
	 * Returns the array of red pieces
	 * @return All the red pieces
	 */
	public ChessPiece[] getReds(){
		return redPieces;
	}
	
	/**
	 * For every square on the board, it is no longer selected.
	 */
	public void deselection (){
		for (int x = 0; x < 8 ; x++){
			for (int y = 0; y < 8; y++){
				board[x][y].deselect();
			}
		}
	}
	
	/**
	 * Changes the turn and updates all the squares that pieces can move to.
	 * If a king is in check, turns off all immovable squares. If all the squares on the kings team are off, the game ends.
	 */
	public void changeTurn(){
		System.out.println("\n Turn Changing \n");
		
		updateAllElements();
	
		if (turn == Turn.RED) {
			turn = Turn.BLUE;

		// Search for whether a piece is in check.
			if (blueInCheck()) {
				System.out.println("Blue in Check");
				disableUnMovables();
				((King)bluePieces[15]).setCheckIcon(getSquareAt(bluePieces[15].getXPos(), bluePieces[15].getYPos()));
				if (checkForBlueCheckMate())
					gameOver();
			}
		}
		
		else{
			turn = Turn.RED;

			if (redInCheck()) {
				System.out.println("Red in Check");
				disableUnMovables();
				((King)redPieces[15]).setCheckIcon(getSquareAt(redPieces[15].getXPos(), redPieces[15].getYPos()));
				if (checkForRedCheckMate())
					gameOver();
			}
		}
		
		System.out.println("GameBoard: 212\n");
		deselection();
	}
	
	/**
	 * Gets the ChessPiece that threatens the king.
	 * @return Returns the ChessPiece threatening the king, if there is none, returns null.
	 */
	public ChessPiece getThreatToThrone(){
		if (turn == Turn.RED)
			for (ChessPiece p : bluePieces)
				if(p != null)
					if(p.getThreat())
						return p;
	  if (turn == Turn.BLUE)
			for (ChessPiece p : redPieces)
				if(p != null)
					if(p.getThreat())
						return p;
		return null;
	}
	
	/**
	 * If a queen is threatening the king and we want to disable pieces, the queen either threatens the king as a rook 
	 * or as a bishop. This method returns that rook or bishop.
	 * @param piece The Queen threatening the king
	 * @return The Rook or Bishop version of this Queen.
	 */
	public ChessPiece getDummyPiece(ChessPiece piece){
		ChessPiece dummyPiece;
	
		if (piece.getTeam() == Team.RED)
			dummyPiece = new Rook("RED");
		else 
			dummyPiece = new Rook("BLUE");
		
		dummyPiece.setXPos(piece.getXPos());
		dummyPiece.setYPos(piece.getYPos());
		getSquareAt(piece.getXPos(), piece.getYPos()).setChessPiece(dummyPiece);
		rules.updatePossibleMoves(dummyPiece);
			
		System.out.println( " " + dummyPiece.getThreat() + dummyPiece.getClass().getName() + " dummy X " + dummyPiece.getXPos() + " dummy Y " + dummyPiece.getYPos());

		if(dummyPiece.getThreat() == false){
			if(dummyPiece.getTeam() == Team.BLUE)
				dummyPiece = new Bishop("BLUE");
			else 
				dummyPiece = new Bishop("RED");
			dummyPiece.setXPos(piece.getXPos());
			dummyPiece.setYPos(piece.getYPos());
			getSquareAt(piece.getXPos(), piece.getYPos()).setChessPiece(dummyPiece);
			rules.updatePossibleMoves(dummyPiece);
			}
		System.out.println( " " + dummyPiece.getThreat() + dummyPiece.getClass().getName() + " dummy X " + dummyPiece.getXPos() + " dummy Y " + dummyPiece.getYPos());
		getSquareAt(piece.getXPos(), piece.getYPos()).setChessPiece(piece);
		return dummyPiece;
	}
	
	/**
	 * Disables every piece that cannot move to save the king.
	 */
	public void disableUnMovables(){
		ChessPiece piece = getThreatToThrone();
		System.out.println(piece.getClass().getName() + " X " + piece.getXPos() + " Y " + piece.getYPos());
		if(piece instanceof chess.Queen)
			piece = getDummyPiece(piece);
		if (piece.checkEmptyList())
			System.out.println("Empty List \n");
		
		// Turn off all empty squares
		if(redInCheck() || blueInCheck())
			for (GameSquare sq[]: board)
				for(GameSquare s: sq)
					if (s.getChessPiece() == null){
						s.setDisabledIcon(s.getIcon());
						s.setEnabled(false);
					}
		
		if (turn == Turn.RED){		
			if (redInCheck()){
				//Turn off all empty squares
				for (ChessPiece p : redPieces){
					if(p!= null){
						if(p instanceof King)
							getSquareAt(p.getXPos(), p.getYPos()).setDisabledIcon(checkIcon);
						else
							getSquareAt(p.getXPos(), p.getYPos()).setDisabledIcon(p.getIcon());
						getSquareAt(p.getXPos(), p.getYPos()).setEnabled(false);
					}
				}		
				// Turns on all that are in path to save king or can kill the attacker
				for(GameSquare g: piece.getList())
					for (ChessPiece p: redPieces)
						if(p!= null){
							for (GameSquare square : p.getList()){
								if (square.equals(g) || square.equals(getSquareAt(piece.getXPos(), piece.getYPos()))){
									square.setEnabled(true);
									getSquareAt(p.getXPos(), p.getYPos()).setEnabled(true);
								}
							}
						}
			}
		}
		
		if(turn == Turn.BLUE){		
			if (blueInCheck()){
				for (ChessPiece p : bluePieces){
					if(p!= null){
						if(p instanceof King)
							getSquareAt(p.getXPos(), p.getYPos()).setDisabledIcon(checkIcon);
						else
							getSquareAt(p.getXPos(), p.getYPos()).setDisabledIcon(p.getIcon());
						getSquareAt(p.getXPos(), p.getYPos()).setEnabled(false);
					}
				}
				for(GameSquare g: piece.getList())
					for (ChessPiece p: bluePieces)
						if(p!= null){
							for (GameSquare square : p.getList()){
								if (square.equals(g) || square.equals(getSquareAt(piece.getXPos(), piece.getYPos()))){
									square.setEnabled(true);
									getSquareAt(p.getXPos(), p.getYPos()).setEnabled(true);
								}
							}
						}
			}
		}
	}
		
	/**
	 * Enables every square on the board.
	 */
	public void enableAllSquares(){
		for (GameSquare[] g: board){
			for (GameSquare square : g){
				square.setEnabled(true);
			}
		}
	}
	
	/**
	 * Removes a Chess Piece from play.
	 * @param piece The chess piece to remove from play.
	 */
	public void popPiece(ChessPiece piece){
		for (int i = 0; i< 16; i++)
			if(bluePieces[i] != null)
				if (bluePieces[i].equals(piece))
					bluePieces[i] = null;
		for (int i = 0; i< 16; i++)
			if(redPieces[i] != null)
				if (redPieces[i].equals(piece))
					redPieces[i] = null;
	}
	
	/**
	 * Looks for a threat to the throne and makes it not threaten the kings. This also puts the kings out of check.
	 */
	public void clearThreat(){
		for (ChessPiece p : redPieces)
			if(p!= null)
				p.setThreat(false);
		for (ChessPiece p : bluePieces)
			if(p!= null)
				p.setThreat(false);
		((King) bluePieces[15]).setCheck(false);
		((King) redPieces[15]).setCheck(false);
		
		((King)bluePieces[15]).setBlueKingIcon(getSquareAt(bluePieces[15].getXPos(), bluePieces[15].getYPos()));
		((King)redPieces[15]).setRedKingIcon(getSquareAt(redPieces[15].getXPos(), redPieces[15].getYPos()));
	}
	
	/**
	 * Updates the Linked Lists containing all the position the chess pieces can move to.
	 */
	public void updateMovesForAllPieces(){
		for (int i = 0; i < 15 ; i++)
			if(redPieces[i]!= null)
			 rules.updatePossibleMoves(redPieces[i]);
		for (int i = 0; i < 15 ; i++)
			if(bluePieces[i]!= null)
			 rules.updatePossibleMoves(bluePieces[i]);
		if (redPieces[15] != null)
			rules.updatePossibleMoves((King)redPieces[15]);
		if(bluePieces[15]!= null)
			rules.updatePossibleMoves((King)bluePieces[15]);
	}
	
	/**
	 * This decides what should be done with the Square clicked on.
	 * If the square is contains a piece and it's not that piece's turn to move, nothing happens.
	 * If the active square is re-clicked, all the selected squares are unselected and nothing happens.
	 * If a square on the same team as the active square is clicked, the selected squares are unselected and the new square
	 *  is now the active square.
	 * If a selected square is clicked, then the chess piece on the active square moves to the selected square. 
	 * @param e The event that triggers the function
	 */
	public void actionPerformed(ActionEvent e)
	{
		// The button that has been pressed
		GameSquare square = (GameSquare)e.getSource();
		if (square.getChessPiece()!=null)
			System.out.println("Type: " + square.getChessPiece().getClass().getName()+ " clicked\n");
		// This is because of null call above
		if(turn == Turn.BLUE && !square.getSelected() && square.getChessPiece()!=null && square.getChessPiece().getTeam() == Team.RED)
			return;
		
		if(turn == Turn.RED && !square.getSelected() && square.getChessPiece()!=null && square.getChessPiece().getTeam() == Team.BLUE)
			return;
	
		if (isActiveSquareReclicked(square))
				return;	
		
		if (!square.getSelected()){
			unSelectedSquare(square);
			return;
		}
		
		else if (square.getSelected()){
			selectedSquare(square);
		}
	}
	
	/**
	 * If A pawn moves all the way to the other side of the board a dialogue box
	 *  pops up and the user can select how they want to upgrade the pawn.
	 * @param piece The pawn to upgrade.
	 */
	public void upGradePawn( ChessPiece piece){
		System.out.println("GameBoard: 311 upgradePawn Triggered");
		String[] options= new String[]{"Knight", "Bishop", "Rook", "Queen"};
		ChessPiece newPiece = null;
		ImageIcon icon;
		if (piece.getTeam() == Team.RED)
			icon = redWin;
		else 
			icon = blueWin;
		
		int selector = JOptionPane.showOptionDialog(boardPanel, "What do you want to upgrade to?", "Upgrade Pawn", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[1]);

		System.out.println("Selector: " + selector);
		switch (selector){
			case 0: newPiece = new Knight("RED"); break;
			case 1: newPiece = new Bishop("RED"); break;
			case 2: newPiece = new Rook("RED"); break;
			case 3: newPiece = new Queen("RED"); break;
		}
		
		if ((piece.getTeam() == Team.RED) && (piece.getYPos()==7)){
			for (int i = 0; i < 8 ; i++){
				if (redPieces[i] != null){
					if (redPieces[i].equals(piece)){
						if (newPiece == null)
							System.out.println("Catastrophic error GameBoard: 474");
						else{
							redPieces[i] = newPiece;
						}
						this.getSquareAt(piece.getXPos(), piece.getYPos()).setChessPiece(redPieces[i]);
					}
				}
			}
		}
		
		if ((piece.getTeam() == Team.BLUE) && (piece.getYPos()==0))
			for (int i = 0; i < 8 ; i++){
				if (bluePieces[i].equals(piece)){
					if (newPiece == null)
						System.out.println("Catastrophic error GameBoard: 379");
					else{
						newPiece.setTeam(Team.BLUE);
						bluePieces[i] = newPiece;
					}
					this.getSquareAt(piece.getXPos(), piece.getYPos()).setChessPiece(bluePieces[i]);
			}
		}
	}
	
	/** 
	 * Checks all the squares housing the red pieces for whether they're enabled. If they're all disabled, then check mate.
	 * @return True if check mate
	 */
	public boolean checkForRedCheckMate(){
		boolean checkMate = true;
		for (ChessPiece p: redPieces)
			if (p!=null)
			if (getSquareAt(p.getXPos(), p.getYPos()).isEnabled()){
				checkMate = false;
				break;
			}
		return checkMate;
	}
	
	/** 
	 * Checks all the squares housing the blue pieces for whether they're enabled. If they're all disabled, then check mate.
	 * @return True if check mate
	 */
	public boolean checkForBlueCheckMate(){
		boolean checkMate = true;
		for (ChessPiece p: bluePieces)
			if (p!=null)
			if (getSquareAt(p.getXPos(), p.getYPos()).isEnabled()){
				checkMate = false;
				break;
			}
		return checkMate;
	}
	
	/** 
	 * Checks if the red king is in check
	 * @return True if red in check
	 */
	public boolean redInCheck(){
		return ((King)redPieces[15]).getCheck();
	}
	
	/** 
	 * Checks if the blue king is in check
	 * @return True if blue in check
	 */
	public boolean blueInCheck(){
		return ((King)bluePieces[15]).getCheck();
	}
	
	/**
	 * Returns whether the active square was clicked again or a square on the same team as the active square was clicked.
	 * @param square The square clicked.
	 * @return True if the active square is re-clicked or a square on the same team as the active square was clicked. False otherwise.
	 */
	public boolean isActiveSquareReclicked(GameSquare square){
		if(activeSquare != null){
			if (square.equals(activeSquare)){
				deselection();
				activeSquare = null;
				System.out.println("GameBoard 556: An active square was reclicked.\n");
				return true;
			}
			else if (!square.equals(activeSquare) && square.getChessPiece()!=null && square.getChessPiece().getTeam() == activeSquare.getChessPiece().getTeam()){
				deselection();
				unSelectedSquare(square);
				System.out.println("GameBoard 562: An inactive square was clicked of the same team.\n");
				return true;
			}
		}
		System.out.println("GameBoard 566: A inactive square was clicked either blank or enemy.\n");
		return false;
	}
	
	/**
	 * Selects all the squares the chess piece on the parameter square can move to.
	 * @param square The square holding the chess piece that wants to move.
	 */
	public void unSelectedSquare (GameSquare square){
		if(square.getChessPiece() == null) {
			System.out.println("Nothing found.");
			return;
		}
		activeSquare = square;
		rules.selectMoves(square);
		System.out.println("GameBoard 357: The square's possible moves are now visible.\n");
	}
	
	public void updateAllElements(){
		enableAllSquares();
		
		//Makes no piece a threat to the throne, and makes all Kings not in check.
		clearThreat();
		
		//Finds Threats to the throne
		updateMovesForAllPieces();
	}
	
	/**
	 * Returns true if a move has compromised the king
	 * @return True if a move has compromised the king.
	 */
	public boolean checkUndo() {
		boolean undo = false;
		updateAllElements();
		if (turn == Turn.RED && redInCheck())
			undo = true;
		if (turn == Turn.BLUE && blueInCheck())
			undo = true;
		return undo;
	}
	
	/**
	 * Moves the chess piece on the active square to the parameter square. 
	 * Then changes the turn.
	 * @param square The square the chess piece on the active square will move to.
	 */
	public void selectedSquare(GameSquare square){
		int moves = 0;
		ChessPiece popPiece = square.getChessPiece();
		System.out.println("GameBoard 361: Square has been selected to move.");
		
		if ((activeSquare.getChessPiece() instanceof chess.Pawn) && ((Pawn)activeSquare.getChessPiece()).getMoves() == 2)
			moves = 2;
		activeSquare.getChessPiece().moveTo(activeSquare, square);
		if (checkUndo()){
			square.getChessPiece().moveTo(square, activeSquare);
			if (popPiece !=null)
				square.setChessPiece(popPiece);
			String[] options = {"Sorry"};
			JOptionPane.showOptionDialog(boardPanel, "YOU WILL EXPOSE YOUR KING", "BAD MOVE", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, activeSquare.getChessPiece().getIcon(), options, options[0]);
			if ((activeSquare.getChessPiece() instanceof chess.Pawn) && moves == 2)
				((Pawn)activeSquare.getChessPiece()).setMoves(2);
			deselection();
			updateAllElements();
			activeSquare = null;
			return;
		}
		else{
			deselection();
			updateAllElements();
			if (square.getChessPiece()!= null)
				popPiece(popPiece);
			if ((square.getChessPiece() instanceof chess.Pawn) && ((square.getYPos() == 0) || (square.getYPos() ==7) ))
				upGradePawn(square.getChessPiece()); 
			changeTurn();
		activeSquare = null;
		}
	}

	/**
	 * Ends the game in a visually satisfying way for the victor.
	 */
	public void gameOver(){ 
		ImageIcon icon = null;
		for (GameSquare[] g: board){
			for (GameSquare square : g){
				if (turn == Turn.RED){
					square.setDisabledIcon(blueWin);
					icon = blueWin;
				}
				
				else if (turn == Turn.BLUE){
					square.setDisabledIcon(redWin);
					icon = redWin;
				}
				square.setEnabled(false);
			}
		}
		String[] options = {"Good Game"};
		int selector = JOptionPane.showOptionDialog(boardPanel, "WINNER", "VICTORY", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
		if (selector == 0) {
			setVisible(false);
			dispose();
		}
	}
}

