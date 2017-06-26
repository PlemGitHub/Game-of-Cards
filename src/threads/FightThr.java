package threads;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import engine.CardsValues;
import engine.Constants;
import engine.PlayerTurn;
import panels.ImageImport;
import screen.Table;

public class FightThr extends Thread implements Constants, CardsValues {
		private Table table;
		private PlayerTurn playerTurn;
		private int kX;
		private int moveDelayUpDown = 15;
		private int moveDelaySide = 5;
		private int delay;
		private FlashOnHP flashOnHP;
		private FlashOnMaana_GREEN greenFlash;

		private JPanel myComponent;
		private JPanel enemyComponent;
			private int myFocusedCard;
			private int enemyFocusedCard;
				private String myCardPower;
				private String enemyCardPower;
					private int myCardHP;
					private int enemyCardHP;
						private int myCardAttack;
						private int enemyCardAttack;
		
		
	public FightThr(Table table, PlayerTurn playerTurn) {
		this.table = table;
		this.playerTurn = playerTurn;
	}
	
	public void run(){
		for (int i = 0; i < 6; i++) {
			myCardPower = playerTurn.getCardsOnTable_POWER()[4+i];
			if (!myCardPower.equals("n")){
				myFocusedCard = 4+i;
				int x = playerTurn.getMy_XY().get(myFocusedCard*10+1)+10;
				int y = playerTurn.getMy_XY().get(myFocusedCard*10+2);
				myComponent = (JPanel) table.findComponentOnMainPanel(x, y);
				doMoveToPerformAttack();
			}
		}
		
		for (int i = 0; i < 6; i++) {
			myCardPower = playerTurn.getCardsOnTable_POWER()[4+i];
			if (myCardPower.equals("m")){
				myFocusedCard = 4+i;
				int x = playerTurn.getMy_XY().get(myFocusedCard*10+1)+10;
				int y = playerTurn.getMy_XY().get(myFocusedCard*10+2);
				myComponent = (JPanel) table.findComponentOnMainPanel(x, y);
				doPlusMaana(myComponent, myFocusedCard);
			}
		}
	}
	
	private void doMoveToPerformAttack(){
		int dX=0;
		int dY=0; 
		kX = playerTurn.getSide().equals("left")? 1:-1;
		
		myCardAttack = playerTurn.getCardsOnTable_ATTACK()[myFocusedCard];
		myCardHP = playerTurn.getCardsOnTable_HEALTH()[myFocusedCard];
		myCardPower = playerTurn.getEnemyCardsOnTable_POWER()[myFocusedCard];

		enemyFocusedCard = myFocusedCard>=7? myFocusedCard : myFocusedCard+3;
		enemyCardAttack = playerTurn.getEnemyCardsOnTable_ATTACK()[enemyFocusedCard];
		enemyCardHP = playerTurn.getEnemyCardsOnTable_HEALTH()[enemyFocusedCard];
		enemyCardPower = playerTurn.getEnemyCardsOnTable_POWER()[enemyFocusedCard];
		
		HashMap<Integer, Integer> enemy_XY = playerTurn.getEnemy_XY();
		int enemyX = enemy_XY.get(enemyFocusedCard*10+1)+10;
		int enemyY = enemy_XY.get(enemyFocusedCard*10+2);
		enemyComponent = (JPanel) table.findComponentOnMainPanel(enemyX, enemyY);
			
		if (myCardAttack!=0)
			if (myFocusedCard>=7 || (myFocusedCard<=6 && myCardPower.equals("r")))
				if (enemyComponent == table.getMainPanel()){		// если в enemyComponent нет вражеской карты
					for (int k = 1; k <= 4; k++)
						for (int i = 1; i <= 20; i++) {
							switch (k) {
								case 1: {dX=0; dY=-1; delay=moveDelayUpDown;} break;
								case 2: {dX=1; dY=0; delay=moveDelaySide;
											if (i==20)
												doDamageToHP();
										} break;
								case 3: {dX=-1; dY=0; delay=moveDelaySide;} break;
								case 4: {dX=0; dY=1;delay=moveDelayUpDown;} break;
							}
							myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY()+dY);
							try {
								Thread.sleep(delay);
							} catch (InterruptedException e) {e.printStackTrace();}
						}
				}
				else
					for (int k = 1; k <= 2; k++)
						for (int i = 1; i <= 20; i++) {
							switch (k) {
								case 1: {dY=-1;
											if (i==20)
												prepareSideMoveToPerformAttack();
										}break;
								case 2: dY=1;
										break;
							}
							myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY()+dY);
							enemyComponent.setLocation(enemyComponent.getX()+dX*kX*-1, enemyComponent.getY()+dY);
							try {
								Thread.sleep(moveDelayUpDown);
							} catch (InterruptedException e) {e.printStackTrace();}
						}
	}
	
	private void doDamageToHP(){
		playerTurn.setHpEnemy(playerTurn.getHpEnemy()-myCardAttack);
		playerTurn.getHpEnemy_Label().setText(Integer.toString(playerTurn.getHpEnemy()));
		flashOnHP = new FlashOnHP(playerTurn.getHpEnemy_Label());
		flashOnHP.start();
	}
		
	private void prepareSideMoveToPerformAttack(){
		int var=0; 															// карты в равном приоритете
		if (myCardPower.equals("s") && !enemyCardPower.equals("s")) var=1; 	// у моей карты меч, у врага нет
		if (!myCardPower.equals("s") && enemyCardPower.equals("s")) var=2; 	// у врага меч, у моей карты нет
		
		switch (var) {
			case 0:
				performAttack(myComponent, enemyComponent, true, 1);
				break;
			case 1:
				performAttack(myComponent, enemyComponent, false, 1);
				break;
			case 2:
				performAttack(enemyComponent, myComponent, false, -1);			
				break;
		}
	}
	/**
	 * 
	 * @param first Карта, атакующая первой
	 * @param second Карта, атакующая второй
	 * @param both True - карты без мечей, или у обеих карт меч. False - одна из карт с мечом
	 */
	private void performAttack(JPanel first, JPanel second, boolean both, int kXtoSword){
		int dX=0;
		if (myFocusedCard <= 6 && myCardPower.equals("r")){
			for (int k = 1; k <= 2; k++)
				for (int i = 1; i <= 20; i++) {
					switch (k) {
						case 1: {dX=1;
									if (i==20)
										calculateDamageToCard(myComponent, enemyComponent);
//										checkIfCardIsDead();
								}break;
						case 2: dX=-1;
								break;
					}
					myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY());
					try {
					Thread.sleep(moveDelaySide);
					} catch (InterruptedException e) {e.printStackTrace();}	
				}
		} else {
			if (both){
				for (int k = 1; k <= 2; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=1;
										if (i==20){
											calculateDamageToCard(myComponent, enemyComponent);
											calculateDamageToCard(enemyComponent, myComponent);
//											checkIfCardIsDead();
										}
									}break;
							case 2: dX=-1;
									break;
						}
						myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY());
						enemyComponent.setLocation(enemyComponent.getX()+dX*kX*-1, enemyComponent.getY());
						try {
						Thread.sleep(delay);
						} catch (InterruptedException e) {e.printStackTrace();}	
					}
			}else{
				for (int k = 1; k <= 2; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=1;
										if (i==20)
											calculateDamageToCard(first, second);
//											checkIfCardIsDead();
									}break;
							case 2: dX=-1;
									break;
						}
						first.setLocation(first.getX()+dX*kX*kXtoSword, first.getY());
						try {
						Thread.sleep(delay);
						} catch (InterruptedException e) {e.printStackTrace();}	
					}
				for (int k = 1; k <= 2; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=1;
										if (i==20)
											calculateDamageToCard(second, first);
//											checkIfCardIsDead();
									}break;
							case 2: dX=-1;
									break;
						}
						second.setLocation(second.getX()+dX*kX*kXtoSword*-1, second.getY());
						try {
						Thread.sleep(delay);
						} catch (InterruptedException e) {e.printStackTrace();}	
					}
			}
		}
	}
	
	private void calculateDamageToCard(JPanel attacker, JPanel defender){
//		int aAttack;
//		int dAttack;
//		int aHP;
//		int dHP;
//		if (attacker == myComponent){
//			aAttack = myCardAttack;
//			aHP = myCardHP;
//			String aPower = myCardPower;
//				dAttack = enemyCardAttack;
//				dHP = enemyCardHP;
//				String dPower = enemyCardPower;
//		}else{
//			aAttack = enemyCardAttack;
//			aHP = enemyCardHP;
//			String aPower = enemyCardPower;
//				dAttack = myCardAttack;
//				dHP = myCardHP;
//				String dPower = myCardPower;
//		}
//		
//		dHP -= aAttack;
//		if (dHP<0) dHP=0;
//		else{
//			JPanel lowHP = new ImageImport("hp"+dHP);
//			lowHP.setBounds(CARD_WIDTH-30, CARD_HEIGHT-30, 30, 30);
//			defender.add(lowHP);
//			defender.repaint();
//		}
	}
	

	private void doPlusMaana(Component component, int focusedCard){
		int dX=0;
		int dY=0;
		delay = moveDelayUpDown;
			for (int k = 1; k <= 2; k++)
				for (int i = 1; i <= 20; i++) {
					switch (k) {
						case 1: {dX=0; dY=-1;;
							if (i==20){
								playerTurn.setMaana(playerTurn.getMaana()+1);
								playerTurn.getMaana_Label().setText(Integer.toString(playerTurn.getMaana()));
								greenFlash = new FlashOnMaana_GREEN(playerTurn);
								greenFlash.start();
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
