package chess;

import javax.swing.ImageIcon;

/**
 * 
 * @author TadiwaM
 * Bishop sets its team and icon.
 */
public class Bishop extends ChessPiece {
	Bishop (String str){
		if(Team.valueOf(str) == Team.BLUE)
			icon = new ImageIcon(getClass().getResource("/pics/bluebishop.png")); 
		else 
			icon = new ImageIcon(getClass().getResource("/pics/redbishop.png")); 
		this.team = Team.valueOf(str);
	}
}
