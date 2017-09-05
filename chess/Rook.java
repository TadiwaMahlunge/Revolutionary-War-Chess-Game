package chess;

import javax.swing.ImageIcon;

public class Rook extends ChessPiece {
	/**
	 * Initialises the team and icon of the Rook being instantiated.
	 * @param str
	 */
	Rook (String str){
		if(Team.valueOf(str) == Team.BLUE)
			icon = new ImageIcon(getClass().getResource("/pics/bluerook.png")); 
		else 
			icon = new ImageIcon(getClass().getResource("/pics/redrook.png")); 
		this.team = Team.valueOf(str);
	}
}
