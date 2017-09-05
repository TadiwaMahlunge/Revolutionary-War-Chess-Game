package chess;

import javax.swing.SwingUtilities;

public class driver {
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

      /**
       * Initiates the game program.
       */
      public void run() {
      	GameBoard gameBoard = new GameBoard("The Revolutionary War",8,8);
      }
		});
	}
}
