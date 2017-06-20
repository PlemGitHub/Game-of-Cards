package threads;

import java.awt.Component;

import engine.Constants;
import screen.Table;

public class CardsMovementThr extends Thread implements Constants{
	private Table table;
	private int old_x;
	private int old_y;
	private int new_x;
	private int new_y;
	private int dX;
	private int dY;
	private int delay = 100/CARDS_MOVEMENT_SPEED;
	
	public CardsMovementThr(Table table, int old_x, int old_y, int new_x, int new_y) {
		this.table = table;
		this.old_x = old_x;
		this.old_y = old_y;
		this.new_x = new_x;
		this.new_y = new_y;
		dX = (new_x-old_x)/CARDS_MOVEMENT_SPEED;
		dY = (new_y-old_y)/CARDS_MOVEMENT_SPEED;
	}
	
	@Override
	public void run(){
		Component cardToMove = table.findComponentOnMainPanel(old_x+10, old_y);
			for (int i = 1; i <= CARDS_MOVEMENT_SPEED-1; i++) {
				cardToMove.setLocation(cardToMove.getX()+dX, cardToMove.getY()+dY);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			cardToMove.setLocation(new_x, new_y);
	}
}
