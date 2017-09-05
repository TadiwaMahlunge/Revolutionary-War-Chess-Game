package chess;

import javax.swing.ImageIcon;

public class Queen extends ChessPiece {

	/**
	 * Initialises the team and icon of the Queen being instantiated.
	 * @param str
	 */
	Queen (String str){
		if(Team.valueOf(str) == Team.BLUE)
			icon = new ImageIcon(getClass().getResource("/pics/bluequeen.png")); 
		else 
			icon = new ImageIcon(getClass().getResource("/pics/redqueen.png")); 
		this.team = Team.valueOf(str);
	}
}
