package threads;

import java.awt.Component;

import engine.CardsValues;
import engine.Constants;
import engine.PlayerTurn;
import screen.Table;

public class FightThr extends Thread implements Constants, CardsValues {
		private Table table;
		private PlayerTurn playerTurn;
		
	public FightThr(Table table, PlayerTurn playerTurn) {
		this.table = table;
		this.playerTurn = playerTurn;
	}
	
	public void run(){
		for (int i = 0; i < 6; i++) {
			if (!playerTurn.getCardsOnTable_POWER()[4+i].equals("n"))
				moveInFight(4+i);
		}
	}
	
	private void moveInFight(int focusedCard){
		int dX = 0;
		int dY = 0;
		int delay=0;
		int kX = playerTurn.getSide().equals("left")? 1:-1;
		int x = playerTurn.getCard_XY().get(focusedCard*10+1)+10;
		int y = playerTurn.getCard_XY().get(focusedCard*10+2);
		Component component = table.findComponentOnMainPanel(x, y);
		for (int k = 1; k <= 4; k++) {
			for (int i = 1; i <= 20; i++) {
				switch (k) {
					case 1: {dX=0; dY=-1;delay=15;} break;
					case 2: {dX=1; dY=0;delay=5;
						if (playerTurn.getCardsOnTable_POWER()[focusedCard].equals("m") ||
								playerTurn.getCardsOnTable_POWER()[focusedCard].equals("h")) dX=0;} break;
					case 3: {dX=-1; dY=0;
						if (playerTurn.getCardsOnTable_POWER()[focusedCard].equals("m") ||
								playerTurn.getCardsOnTable_POWER()[focusedCard].equals("h")) dX=0;} break;
					case 4: {dX=0; dY=1;delay=15;} break;
				}
				component.setLocation(component.getX()+dX*kX, component.getY()+dY);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setTable(Table table){
		this.table = table;
	}
}
