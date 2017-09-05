package chess;

import javax.swing.ImageIcon;

public class Knight extends ChessPiece{
	Knight (String str){
		if(Team.valueOf(str) == Team.BLUE)
			icon = new ImageIcon(getClass().getResource("/pics/blueknight.png")); 
		else 
			icon = new ImageIcon(getClass().getResource("/pics/redknight.png")); 
		this.team = Team.valueOf(str);
	}
}
