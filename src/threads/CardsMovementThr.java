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
	private int delay;
	private int currentX;
	private int currentY;
	
	public CardsMovementThr(Table table, int old_x, int old_y, int new_x, int new_y, int delay) {
		this.table = table;
		this.old_x = old_x;
		this.old_y = old_y;
		this.new_x = new_x;
		this.new_y = new_y;
		if (delay==0)
			this.delay = CARDS_MOVEMENT_TIME_MS/CARDS_MOVEMENT_DIV;
		else this.delay = delay;
		
		dX = (new_x-old_x)/CARDS_MOVEMENT_DIV;
		dY = (new_y-old_y)/CARDS_MOVEMENT_DIV;
	}
	
	@Override
	public void run(){
			Component cardToMove = table.findComponentOnMainPanel(old_x+10, old_y);
				for (int i = 1; i <= CARDS_MOVEMENT_DIV-1; i++) {
					currentX = cardToMove.getX()+dX;
					currentY = cardToMove.getY()+dY;
					cardToMove.setLocation(currentX, currentY);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			currentX = new_x;
			currentY = new_y;
			cardToMove.setLocation(currentX, currentY);
	}
}
