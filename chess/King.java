package chess;

import javax.swing.ImageIcon;


public class King extends ChessPiece{
	
	private ImageIcon blueKing = new ImageIcon(getClass().getResource("/pics/blueking.png")); 
	private ImageIcon redKing = new ImageIcon(getClass().getResource("/pics/redking.png")); 	
	private ImageIcon checkIcon = new ImageIcon(getClass().getResource("/pics/check.png"));
	private boolean check;
	
	King (String str){
		if(Team.valueOf(str) == Team.BLUE)
			icon = blueKing;
		else 
			icon = redKing;
		this.team = Team.valueOf(str);
		check = false;
	}
	
	public boolean getCheck(){
		return check;
	}
	
	public void setCheck(boolean c){
		check = c;
	}
	
	public void setBlueKingIcon(GameSquare square){
		icon = blueKing;
		square.setChessPiece(this);
	}
	
	public void setRedKingIcon(GameSquare square){
		icon = redKing;
		square.setChessPiece(this);
	}
	
	public void setCheckIcon(GameSquare square){
		icon = checkIcon;
		square.setChessPiece(this);
	}
	
	public void moveTo(GameSquare from, GameSquare to){
		if(team == Team.BLUE)
			icon = blueKing;
		else 
			icon = redKing;
		setCheck(false);
		to.setChessPiece(this);
		from.removeChessPiece();
		clearList();
	}
}
