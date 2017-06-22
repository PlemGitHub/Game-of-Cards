package threads;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JLabel;
import engine.CardsValues;
import engine.Constants;
import engine.PlayerTurn;
import screen.InterfaceElements;
import screen.Table;

public class FightThr extends Thread implements Constants, CardsValues {
		private Table table;
		private PlayerTurn playerTurn;
		private boolean IsActionPerformed;
		private int dX;
		private int dY;
		private int kX;
		private int moveDelayUpDown = 15;
		private int moveDelaySide = 5;
		private int delay;
		private FlashOnHP flashOnHP;
		private FlashOnMaana_GREEN greenFlash;
		
	public FightThr(Table table, PlayerTurn playerTurn) {
		this.table = table;
		this.playerTurn = playerTurn;
	}
	
	public void run(){
		for (int i = 0; i < 6; i++) {
			String power = playerTurn.getCardsOnTable_POWER()[4+i];
			if (!power.equals("n")){
				int focusedCard = 4+i;
				int x = playerTurn.getMy_XY().get(focusedCard*10+1)+10;
				int y = playerTurn.getMy_XY().get(focusedCard*10+2);
				Component component = table.findComponentOnMainPanel(x, y);
				doAttack(component, focusedCard);
			}
		}
		
		for (int i = 0; i < 6; i++) {
			String power = playerTurn.getCardsOnTable_POWER()[4+i];
			if (power.equals("m")){
				int focusedCard = 4+i;
				int x = playerTurn.getMy_XY().get(focusedCard*10+1)+10;
				int y = playerTurn.getMy_XY().get(focusedCard*10+2);
				Component component = table.findComponentOnMainPanel(x, y);
				doPlusMaana(component, focusedCard);
			}
		}
	}
	
	public void doAttack(Component component, int focusedCard){
		IsActionPerformed = false;	
		kX = playerTurn.getSide().equals("left")? 1:-1;
		int attack = playerTurn.getCardsOnTable_ATTACK()[focusedCard];
		String power = playerTurn.getCardsOnTable_POWER()[focusedCard];

		HashMap<Integer, Integer> enemy_XY = playerTurn.getEnemy_XY();
		int enemyCard = focusedCard>=7? focusedCard : focusedCard+3;
		int enemyX = enemy_XY.get(enemyCard*10+1)+10;
		int enemyY = enemy_XY.get(enemyCard*10+2);
		Component enemyComponent = table.findComponentOnMainPanel(enemyX, enemyY);
				
		if (attack!=0)
			if (focusedCard>=7 || (focusedCard<=6 && power.equals("r")))
			for (int k = 1; k <= 4; k++)
				for (int i = 1; i <= 20; i++) {
					switch (k) {
						case 1: {dX=0; dY=-1; delay=moveDelayUpDown;} break;
						case 2: {dX=1; dY=0; delay=moveDelaySide;
									if (IsActionPerformed==false && i==20){
										if (enemyComponent==table.getMainPanel()) doAttackToHP(attack);
										else doAttackToCard(attack);
									}
								} break;
						case 3: {dX=-1; dY=0; delay=moveDelaySide;} break;
						case 4: {dX=0; dY=1;delay=moveDelayUpDown;} break;
					}
					component.setLocation(component.getX()+dX*kX, component.getY()+dY);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
	}
	
		public void doAttackToHP(int attack){
			playerTurn.setHpEnemy(playerTurn.getHpEnemy()-attack);
			playerTurn.getHpEnemy_Label().setText(Integer.toString(playerTurn.getHpEnemy()));
			flashOnHP = new FlashOnHP(playerTurn.getHpEnemy_Label());
			flashOnHP.start();
			IsActionPerformed = true;
		}
		
		public void doAttackToCard(){
			
		}
	
	public void doPlusMaana(Component component, int focusedCard){
		IsActionPerformed = false;
		delay = moveDelayUpDown;
			for (int k = 1; k <= 2; k++)
				for (int i = 1; i <= 20; i++) {
					switch (k) {
						case 1: {dX=0; dY=-1;;
							if (IsActionPerformed==false && i==20){
								playerTurn.setMaana(playerTurn.getMaana()+1);
								playerTurn.getMaana_Label().setText(Integer.toString(playerTurn.getMaana()));
								greenFlash = new FlashOnMaana_GREEN(playerTurn);
								greenFlash.start();
								IsActionPerformed = true;
							}
						} break;
						case 2: {dX=0; dY=1;} break;
					}
					component.setLocation(component.getX()+dX*kX, component.getY()+dY);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
	}
	public void setTable(Table table){
		this.table = table;
	}
}
