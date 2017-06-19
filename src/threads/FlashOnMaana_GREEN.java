package threads;

import java.awt.Color;
import java.awt.Font;
import engine.Constants;
import engine.PlayerTurn;

public class FlashOnMaana_GREEN extends Thread implements Constants{
	private PlayerTurn playerTurn;
	
	public FlashOnMaana_GREEN(PlayerTurn playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	@Override
	public void run(){
			playerTurn.getMaana_Label().setForeground(Color.GREEN);
			playerTurn.getMaana_Label().setFont(new Font("Segoe Script", Font.BOLD, BIG_FONT_SIZE));
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			playerTurn.getMaana_Label().setForeground(Color.BLUE);
			playerTurn.getMaana_Label().setFont(new Font("Segoe Script", Font.BOLD, NORMAL_FONT_SIZE));
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	}
}
