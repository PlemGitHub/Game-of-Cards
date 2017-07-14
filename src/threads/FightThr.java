package threads;

import java.util.HashMap;
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
		private int moveDelayUpDown = 20;
		private int moveDelaySide = 10;
		private int delay;
		private FlashOnHP flashOnHP;
		private FlashOnMaana_GREEN greenFlash;

		private JPanel myComponent;
		private JPanel enemyComponent;
			private int myFocusedCard;
			private int enemyFocusedCard;
				private String myCardPower;
				private String enemyCardPower;
					private String playerToLog;
					private String enemyToLog;
						private int myCardAttack;
		
		
	public FightThr(Table table, PlayerTurn playerTurn) {
		this.table = table;
		this.playerTurn = playerTurn;
	}
	
	public void run(){
		table.getLogger().logFightStarts();
		//============= Найти всех, кто может нанести урон =============
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

		//============= Найти всех, кто может прибавить маану =============
		for (int i = 0; i < 6; i++) {
			myCardPower = playerTurn.getCardsOnTable_POWER()[4+i];
			if (myCardPower.equals("m")){
				myFocusedCard = 4+i;
				int x = playerTurn.getMy_XY().get(myFocusedCard*10+1)+10;
				int y = playerTurn.getMy_XY().get(myFocusedCard*10+2);
				myComponent = (JPanel) table.findComponentOnMainPanel(x, y);
				doPlusMaana();
			}
		}

		//============= Найти всех, кто может вылечить союзную карту =============
		for (int i = 0; i < 3; i++) {
			myCardPower = playerTurn.getCardsOnTable_POWER()[4+i];
			if (myCardPower.equals("h")){
				myFocusedCard = 4+i;
				int x = playerTurn.getMy_XY().get(myFocusedCard*10+1)+10;
				int y = playerTurn.getMy_XY().get(myFocusedCard*10+2);
				myComponent = (JPanel) table.findComponentOnMainPanel(x, y);
				doHealMyCard();
			}
		}

		table.getLogger().logFightEnds();
	}
	
	private void doMoveToPerformAttack(){
		int dX=0;
		int dY=0; 
		kX = playerTurn.getSide().equals("left")? 1:-1;
		playerToLog = playerTurn.getSide().equals("left")? "Player 1" : "Player 2";
		enemyToLog = playerTurn.getSide().equals("left")? "Player 2" : "Player 1";
		
		myCardAttack = playerTurn.getCardsOnTable_ATTACK()[myFocusedCard];
		int myCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];

		enemyFocusedCard = myFocusedCard>=7? myFocusedCard : myFocusedCard+3;
		enemyCardPower = playerTurn.getEnemyCardsOnTable_POWER()[enemyFocusedCard];
		
		HashMap<Integer, Integer> enemy_XY = playerTurn.getEnemy_XY();
		int enemyX = enemy_XY.get(enemyFocusedCard*10+1)+10;
		int enemyY = enemy_XY.get(enemyFocusedCard*10+2);
		enemyComponent = (JPanel) table.findComponentOnMainPanel(enemyX, enemyY);
		
		if (myFocusedCard>=7 || (myFocusedCard<=6 && myCardPower.equals("r")))
			if (enemyComponent == table.getMainPanel()){		// если в enemyComponent нет вражеской карты
				for (int k = 1; k <= 4; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=0; dY=-1; delay=moveDelayUpDown;} break;
							case 2: {dX=1; dY=0; delay=moveDelaySide;
										if (i==20){
											table.getLogger().logCardCanDoDamage(playerToLog, myCard_N, myFocusedCard, myCardPower);
											doDamageToHP("myAttack", myCardAttack, playerToLog, enemyToLog);
										}
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
						if (myComponent != null)
								myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY()+dY);
						if (enemyComponent != null)
								enemyComponent.setLocation(enemyComponent.getX()+dX*kX*-1, enemyComponent.getY()+dY);
						try {
							Thread.sleep(moveDelayUpDown);
						} catch (InterruptedException e) {e.printStackTrace();}
					}
	}
	
	private void doDamageToHP(String sideOfAttack, int cardDamage, String playerToLog, String enemyToLog){
		if (sideOfAttack.equals("myAttack")){
			playerTurn.setHpEnemy(playerTurn.getHpEnemy()-cardDamage);
			playerTurn.getHpEnemy_Label().setText(Integer.toString(playerTurn.getHpEnemy()));
			flashOnHP = new FlashOnHP(playerTurn.getHpEnemy_Label());
			flashOnHP.start();
			table.getLogger().logDoDamageToHp(playerTurn.getCardsOnTable_N()[myFocusedCard], 
								cardDamage, playerToLog, enemyToLog, playerTurn.getHpEnemy());
		}else{
			playerTurn.setHpMy(playerTurn.getHpMy()-cardDamage);
			playerTurn.getHpMy_Label().setText(Integer.toString(playerTurn.getHpMy()));
			flashOnHP = new FlashOnHP(playerTurn.getHpMy_Label());
			flashOnHP.start();
			table.getLogger().logDoDamageToHp(playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard], 
								cardDamage, playerToLog, enemyToLog, playerTurn.getHpMy());
		}
	}
		
	private void prepareSideMoveToPerformAttack(){
		int var=0; 															// карты в равном приоритете
		if (myCardPower.equals("s") && !enemyCardPower.equals("s")) var=1; 	// у моей карты меч, у врага нет
		if (!myCardPower.equals("s") && enemyCardPower.equals("s")) var=2; 	// у врага меч, у моей карты нет
//		
		switch (var) {
			case 0:
				performAttack(myComponent, enemyComponent, enemyCardPower, true, 1);
				break;
			case 1:
				performAttack(myComponent, enemyComponent, enemyCardPower, false, 1);
				break;
			case 2:
				performAttack(enemyComponent, myComponent, myCardPower, false, -1);			
				break;
		}
	}
	/**
	 * 
	 * @param first Карта, атакующая первой
	 * @param second Карта, атакующая второй
	 * @param secondPower Сила карты, отвечающей на атаку
	 * @param both True - карты без мечей, или у обеих карт меч. False - одна из карт с мечом
	 * @param kXtoSword Коэффициент 1 или -1 для правильного смещения карты с силой S
	 */
	private void performAttack(JPanel first, JPanel second, String secondPower, boolean both, int kXtoSword){
		int dX=0;
		if (myFocusedCard <= 6 && myCardPower.equals("r")){
			for (int k = 1; k <= 2; k++)
				for (int i = 1; i <= 20; i++) {
					switch (k) {
						case 1: {dX=1;
									if (i==20){
											int myCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];
											table.getLogger().logCardCanDoDamage(playerToLog, myCard_N, myFocusedCard, myCardPower);
										calculateDamageToCard(myComponent, enemyComponent, "r");
										checkIfCardIsDead();
									}
								}break;
						case 2: dX=-1;
								break;
					}
					myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY());
					try {
					Thread.sleep(moveDelaySide);
					} catch (InterruptedException e) {e.printStackTrace();}	
				}
		}else{
			if (both){
				for (int k = 1; k <= 2; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=1;
										if (i==20){
											int myCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];
											table.getLogger().logCardCanDoDamage(playerToLog, myCard_N, myFocusedCard, myCardPower);
												int enemyCard_N = playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard];
												table.getLogger().logCardCanDoDamage(enemyToLog, enemyCard_N, enemyFocusedCard, enemyCardPower);
											calculateDamageToCard(myComponent, enemyComponent, myCardPower);
											calculateDamageToCard(enemyComponent, myComponent, enemyCardPower);
											checkIfCardIsDead();
										}
									}break;
							case 2: dX=-1;
									break;
						}
						if (myComponent!=null)
								myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY());
						if (enemyComponent!=null)
								enemyComponent.setLocation(enemyComponent.getX()+dX*kX*-1, enemyComponent.getY());
						
						try {
						Thread.sleep(moveDelaySide);
						} catch (InterruptedException e) {e.printStackTrace();}	
					}
			}else{
				String firstToLog;
				String secondToLog;
				int firstCard_N;
				int secondCard_N;
				int firstFocusedCard;
				int secondFocusedCard;
				String firstCardPower;
				String secondCardPower;
					if (first==myComponent){
						firstToLog = playerToLog;
						secondToLog = enemyToLog;
						firstCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];
						secondCard_N = playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard];
						firstFocusedCard = myFocusedCard;
						secondFocusedCard = enemyFocusedCard;
						firstCardPower = playerTurn.getCardsOnTable_POWER()[myFocusedCard];
						secondCardPower = playerTurn.getEnemyCardsOnTable_POWER()[enemyFocusedCard];
					}else{
						firstToLog = enemyToLog;
						secondToLog = playerToLog;
						firstCard_N = playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard];
						secondCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];
						firstFocusedCard = enemyFocusedCard;
						secondFocusedCard = myFocusedCard;
						firstCardPower = playerTurn.getEnemyCardsOnTable_POWER()[enemyFocusedCard];
						secondCardPower = playerTurn.getCardsOnTable_POWER()[myFocusedCard];
					}
				for (int k = 1; k <= 2; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=1;
										if (i==20){		
											table.getLogger().logCardCanDoDamage(firstToLog, firstCard_N, firstFocusedCard, firstCardPower);
											calculateDamageToCard(first, second, "s");
											checkIfCardIsDead();
										}
									}break;
							case 2: dX=-1;
									break;
						}
						first.setLocation(first.getX()+dX*kX*kXtoSword, first.getY());
						try {
						Thread.sleep(moveDelaySide);
						} catch (InterruptedException e) {e.printStackTrace();}	
					}
				if (myComponent != null && enemyComponent != null)
					for (int k = 1; k <= 2; k++)
						for (int i = 1; i <= 20; i++) {
							switch (k) {
								case 1: {dX=1;
											if (i==20){
												table.getLogger().logCardCanDoDamage(secondToLog, secondCard_N, secondFocusedCard, secondCardPower);
												calculateDamageToCard(second, first, secondPower);
												checkIfCardIsDead();
											}
										}break;
								case 2: dX=-1;
										break;
							}
							second.setLocation(second.getX()+dX*kX*kXtoSword*-1, second.getY());
							try {
							Thread.sleep(moveDelaySide);
							} catch (InterruptedException e) {e.printStackTrace();}	
						}
			}
		}
	}
	
	private void calculateDamageToCard(JPanel attacker, JPanel defender, String attackerPower){
		int efC;
		int[] enemyCardsOnTable_HEALTH;
			int myCard_N;
			int enemyCard_N;
			int axeDefender_N;
		int damage;
		HashMap<Integer, Integer> enemyCard_XY;
		String sideOfAttack;
		String playerToLog;
		String enemyToLog;
		
		if (attacker == myComponent){
			sideOfAttack = "myAttack";
			efC = enemyFocusedCard;
				myCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];
				enemyCard_N = playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard];
				axeDefender_N = playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard-3];
			enemyCardsOnTable_HEALTH = playerTurn.getEnemyCardsOnTable_HEALTH();
			damage = playerTurn.getCardsOnTable_ATTACK()[myFocusedCard];
			enemyCard_XY = playerTurn.getEnemy_XY();
			playerToLog = this.playerToLog;
			enemyToLog = this.enemyToLog;
		}else{
			sideOfAttack = "enemyAttack";
			efC = myFocusedCard;
				myCard_N = playerTurn.getEnemyCardsOnTable_N()[enemyFocusedCard];
				enemyCard_N = playerTurn.getCardsOnTable_N()[myFocusedCard];
				axeDefender_N = playerTurn.getCardsOnTable_N()[myFocusedCard-3];
			enemyCardsOnTable_HEALTH = playerTurn.getCardsOnTable_HEALTH();
			damage = playerTurn.getEnemyCardsOnTable_ATTACK()[enemyFocusedCard];
			enemyCard_XY = playerTurn.getMy_XY();
			playerToLog = this.enemyToLog;
			enemyToLog = this.playerToLog;
		}

		enemyCardsOnTable_HEALTH[efC] -= damage;

		table.getLogger().logDoDamageToCard(myCard_N, enemyCard_N, damage, enemyCardsOnTable_HEALTH[efC], playerToLog, enemyToLog);
		
		if (enemyCardsOnTable_HEALTH[efC] <= 0){
				if (attackerPower.equals("a") && enemyCardsOnTable_HEALTH[efC]<0){	// если урон Топора больше здоровья карты
					int axeOverDamage = enemyCardsOnTable_HEALTH[efC]*-1;
					table.getLogger().logAxeDamageRemaining(myCard_N, axeOverDamage, playerToLog);
					int axeDefenderPos = efC-3;										// позиция за убитой Топором картой
					int x = enemyCard_XY.get(axeDefenderPos*10+1);
					int y = enemyCard_XY.get(axeDefenderPos*10+2);
					JPanel axeDefender = (JPanel) table.findComponentOnMainPanel(x, y); // карта за убитой Топором картой
						if (axeDefender == table.getMainPanel())	// если карты нет - удар в hp
							doDamageToHP(sideOfAttack, axeOverDamage, playerToLog, enemyToLog);
						else{										// если карта есть - удар в карту
							enemyCardsOnTable_HEALTH[axeDefenderPos] -= axeOverDamage;
							table.getLogger().logDoDamageToCard(myCard_N, axeDefender_N, axeOverDamage, 
									enemyCardsOnTable_HEALTH[axeDefenderPos], playerToLog, enemyToLog);
							if (enemyCardsOnTable_HEALTH[axeDefenderPos] <= 0)
								enemyCardsOnTable_HEALTH[axeDefenderPos] = 0;
							else
								drawLowHpOnDamagedCard(axeDefender, enemyCardsOnTable_HEALTH[axeDefenderPos]);
						}
				}
			enemyCardsOnTable_HEALTH[efC]=0;
		}else
			if (damage != 0)
				drawLowHpOnDamagedCard(defender, enemyCardsOnTable_HEALTH[efC]);
	}
	
	private void drawLowHpOnDamagedCard(JPanel defender, int defenderCardHealth){
		defender.remove(defender.findComponentAt(CARD_WIDTH-10, CARD_HEIGHT-10));
			JPanel lowHP = new ImageImport("hp"+defenderCardHealth);
			lowHP.setBounds(CARD_WIDTH-30, CARD_HEIGHT-30, 30, 30);
			lowHP.setOpaque(false);
			defender.setLayout(null);
			defender.add(lowHP);
			defender.repaint();
	}
	
	private void checkIfCardIsDead(){
		for (int i = 0; i < 3; i++) {
			int checkPos = 9-i;
			
			String powerToCheck = playerTurn.getCardsOnTable_POWER()[checkPos];
			String powerToCheckOnSecondLine = playerTurn.getCardsOnTable_POWER()[checkPos-3];
			int hpToCheck = playerTurn.getCardsOnTable_HEALTH()[checkPos];
			int nToLog = playerTurn.getCardsOnTable_N()[checkPos];
			int nOnSecondLineToLog = playerTurn.getCardsOnTable_N()[checkPos-3];
			
				if (!powerToCheck.equals("n") && hpToCheck==0 && powerToCheckOnSecondLine.equals("n")){
						table.getLogger().logCardIsDead(playerToLog, nToLog, checkPos);	// нельзя в конце одним методом, потому что в клетку записываются нули
					int x = playerTurn.getMy_XY().get(checkPos*10+1)+50; 	// +50, потому что карта может быть на позиции удара
					int y = playerTurn.getMy_XY().get(checkPos*10+2);
						moveCardToDeathPlace(x,y);
					
						playerTurn.setCardsOnTable_N(checkPos, 0);
						playerTurn.setCardsOnTable_POWER(checkPos, "n");
						playerTurn.setCardsOnTable_HEALTH(checkPos, 0);
						playerTurn.setCardsOnTable_ATTACK(checkPos, 0);
						
					myComponent = null;
				}
				if (!powerToCheck.equals("n") && hpToCheck==0 && !powerToCheckOnSecondLine.equals("n")){
						// нельзя в конце одним методом, потому что в клетку записываются значения второй клетки
						table.getLogger().logCardIsDead(playerToLog, nToLog, checkPos);	
						table.getLogger().logCardAutomoveToFirstLine(playerToLog, nOnSecondLineToLog, checkPos);
					int x = playerTurn.getMy_XY().get(checkPos*10+1)+50;	// +50, потому что карта может быть на позиции удара
					int y = playerTurn.getMy_XY().get(checkPos*10+2);
						moveCardToDeathPlace(x,y);
					
					playerTurn.setCardsOnTable_N(checkPos, playerTurn.getCardsOnTable_N()[checkPos-3]);
					playerTurn.setCardsOnTable_POWER(checkPos, playerTurn.getCardsOnTable_POWER()[checkPos-3]);
					playerTurn.setCardsOnTable_HEALTH(checkPos, playerTurn.getCardsOnTable_HEALTH()[checkPos-3]);
					playerTurn.setCardsOnTable_ATTACK(checkPos, playerTurn.getCardsOnTable_ATTACK()[checkPos-3]);
						playerTurn.setCardsOnTable_N(checkPos-3, 0);
						playerTurn.setCardsOnTable_POWER(checkPos-3, "n");
						playerTurn.setCardsOnTable_HEALTH(checkPos-3, 0);
						playerTurn.setCardsOnTable_ATTACK(checkPos-3, 0);
						
						
					int old_x = playerTurn.getMy_XY().get((checkPos-3)*10+1)+10;
					int old_y = playerTurn.getMy_XY().get((checkPos-3)*10+2);
					int new_x = playerTurn.getMy_XY().get((checkPos)*10+1);
					int new_y = playerTurn.getMy_XY().get((checkPos)*10+2);
					
					CardsMovementThr cardsMovementThr = new CardsMovementThr(table, old_x, old_y, new_x, new_y, CARDS_SHIFT_DELAY);
					cardsMovementThr.start();
						try {
							cardsMovementThr.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					myComponent = null;
				}
			
			powerToCheck = playerTurn.getEnemyCardsOnTable_POWER()[checkPos];
			powerToCheckOnSecondLine = playerTurn.getEnemyCardsOnTable_POWER()[checkPos-3];
			hpToCheck = playerTurn.getEnemyCardsOnTable_HEALTH()[checkPos];
			nToLog = playerTurn.getEnemyCardsOnTable_N()[checkPos];
			nOnSecondLineToLog = playerTurn.getEnemyCardsOnTable_N()[checkPos-3];
			
				if (!powerToCheck.equals("n") && hpToCheck==0 && powerToCheckOnSecondLine.equals("n")){
						table.getLogger().logCardIsDead(enemyToLog, nToLog, checkPos);	// нельзя в конце одним методом, потому что в клетку записываются нули
					int x = playerTurn.getEnemy_XY().get(checkPos*10+1)+50; 	// +50, потому что карта может быть на позиции удара
					int y = playerTurn.getEnemy_XY().get(checkPos*10+2);
						moveCardToDeathPlace(x,y);
						
						playerTurn.setEnemyCardsOnTable_N(checkPos, 0);
						playerTurn.setEnemyCardsOnTable_POWER(checkPos, "n");
						playerTurn.setEnemyCardsOnTable_HEALTH(checkPos, 0);
						playerTurn.setEnemyCardsOnTable_ATTACK(checkPos, 0);
					
					enemyComponent = null;
				}
				if (!powerToCheck.equals("n") && hpToCheck==0 && !powerToCheckOnSecondLine.equals("n")){
						// нельзя в конце одним методом, потому что в клетку записываются значения второй клетки
						table.getLogger().logCardIsDead(enemyToLog, nToLog, checkPos);	
						table.getLogger().logCardAutomoveToFirstLine(enemyToLog, nOnSecondLineToLog, checkPos);
					int x = playerTurn.getEnemy_XY().get(checkPos*10+1)+50;	// +50, потому что карта может быть на позиции удара
					int y = playerTurn.getEnemy_XY().get(checkPos*10+2);
						moveCardToDeathPlace(x,y);
					
					playerTurn.setEnemyCardsOnTable_N(checkPos, playerTurn.getEnemyCardsOnTable_N()[checkPos-3]);
					playerTurn.setEnemyCardsOnTable_POWER(checkPos, playerTurn.getEnemyCardsOnTable_POWER()[checkPos-3]);
					playerTurn.setEnemyCardsOnTable_HEALTH(checkPos, playerTurn.getEnemyCardsOnTable_HEALTH()[checkPos-3]);
					playerTurn.setEnemyCardsOnTable_ATTACK(checkPos, playerTurn.getEnemyCardsOnTable_ATTACK()[checkPos-3]);
						playerTurn.setEnemyCardsOnTable_N(checkPos-3, 0);
						playerTurn.setEnemyCardsOnTable_POWER(checkPos-3, "n");
						playerTurn.setEnemyCardsOnTable_HEALTH(checkPos-3, 0);
						playerTurn.setEnemyCardsOnTable_ATTACK(checkPos-3, 0);
						
						
					int old_x = playerTurn.getEnemy_XY().get((checkPos-3)*10+1)+10;
					int old_y = playerTurn.getEnemy_XY().get((checkPos-3)*10+2);
					int new_x = playerTurn.getEnemy_XY().get((checkPos)*10+1);
					int new_y = playerTurn.getEnemy_XY().get((checkPos)*10+2);
	
					CardsMovementThr cardsMovementThr = new CardsMovementThr(table, old_x, old_y, new_x, new_y, CARDS_SHIFT_DELAY);
					cardsMovementThr.start();
						try {
							cardsMovementThr.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					enemyComponent = null;
				}
		}
	}

	
	private void moveCardToDeathPlace(int old_x, int old_y){
		CardsMovementThr cmt = new CardsMovementThr(table, old_x, old_y, MIDDLE_X, -2*CARD_HEIGHT, 10);
		cmt.start();
	}
	
	private void doPlusMaana(){
		int dX=0;
		int dY=0;
			for (int k = 1; k <= 2; k++)
				for (int i = 1; i <= 20; i++) {
					switch (k) {
						case 1: {dX=0; dY=-1;;
							if (i==20){
								playerTurn.setMaana(playerTurn.getMaana()+1);
								playerTurn.getMaana_Label().setText(Integer.toString(playerTurn.getMaana()));
								greenFlash = new FlashOnMaana_GREEN(playerTurn);
								greenFlash.start();
									int nCardToLog = playerTurn.getCardsOnTable_N()[myFocusedCard];
									table.getLogger().logCardGivesMaana(playerToLog, nCardToLog, playerTurn.getMaana());
							}
						} break;
						case 2: {dX=0; dY=1;} break;
					}
					myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY()+dY);
					try {
						Thread.sleep(moveDelayUpDown);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
	}
	
	public void setTable(Table table){
		this.table = table;
	}
	
	private void doHealMyCard(){
		int dX=0;
		int dY=0;
			int focusedCardToBeHealed = myFocusedCard+3;
			int damagedHP = playerTurn.getCardsOnTable_HEALTH()[focusedCardToBeHealed];
			int nHealer = playerTurn.getCardsOnTable_N()[myFocusedCard];
			int nToBeHealed = playerTurn.getCardsOnTable_N()[focusedCardToBeHealed];
			int x = playerTurn.getMy_XY().get(focusedCardToBeHealed*10+1)+10;
			int y = playerTurn.getMy_XY().get(focusedCardToBeHealed*10+2);
			
			JPanel cToBeHealed = (JPanel) table.findComponentOnMainPanel(x, y);
			
			if (damagedHP < HEALTH.get(nToBeHealed)){ // если хп у карты меньше базового значения
				int newHP = damagedHP+1;
				playerTurn.setCardsOnTable_HEALTH(focusedCardToBeHealed, newHP);
				table.getLogger().logCardHealsAnotherCard(playerToLog, nHealer, nToBeHealed, 
															myFocusedCard, focusedCardToBeHealed, newHP);
				for (int k = 1; k <= 2; k++)
					for (int i = 1; i <= 20; i++) {
						switch (k) {
							case 1: {dX=0; dY=-1;;
								if (i==20){
									cToBeHealed.remove(cToBeHealed.findComponentAt(CARD_WIDTH-10, CARD_HEIGHT-10));
									JPanel lowHP = new ImageImport("hp"+newHP);
									lowHP.setBounds(CARD_WIDTH-30, CARD_HEIGHT-30, 30, 30);
									lowHP.setOpaque(false);
									cToBeHealed.add(lowHP);
									cToBeHealed.repaint();
								}
							} break;
							case 2: {dX=0; dY=1;} break;
						}
						myComponent.setLocation(myComponent.getX()+dX*kX, myComponent.getY()+dY);
						try {
							Thread.sleep(moveDelayUpDown);
						} catch (InterruptedException e) {e.printStackTrace();}
					}
			}else{
				table.getLogger().logNoHealNeeded(playerToLog, nHealer, myFocusedCard);
			}
	}
}
