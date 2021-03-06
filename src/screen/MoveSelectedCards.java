package screen;

import java.util.HashMap;
import TESTS.Tests;
import engine.CardsValues;
import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;
import threads.CardsMovementThr;

public class MoveSelectedCards implements Constants, CardsValues {
	private Table table;
	private Engine engine;
	private Tests tests;
	private InterfaceElements iel;
	private PlayerTurn playerTurn;
	private HashMap<Integer, Integer> card_xy;
	private int startCardsPos;
	private int endCardPos=0;
	private int old_x;
	private int old_y;
	private int new_x;
	private int new_y;
	private CardsMovementThr cardsMovementThr = new CardsMovementThr(table, old_x, old_y, new_x, new_y, 0);
	
	public MoveSelectedCards(Table table, Engine engine, InterfaceElements iel) {
		this.table = table;
		this.engine = engine;
		this.iel = iel;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// MOVE CARD BLOCK /////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void moveCard(String dir){
			playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
			tests = table.getTests();
			card_xy = playerTurn.getMy_XY();
			old_x = card_xy.get(playerTurn.getFocusedCard_N()*10+1);
			old_y = card_xy.get(playerTurn.getFocusedCard_N()*10+2);
			switch (dir){
				case "down": doMoveDown(); break;
				
				case "up": doMoveUp(); break;
				
				case "right": doMoveRight(); break;
				
				case "left": doMoveLeft(); break;
				
				case " ": doMoveSpace(); break;
			}
			checkSpaceToFillTableCenter();
	}
	
	public void doMoveDown(){
		int fc = playerTurn.getFocusedCard_N();
		if (fc >= 4){
			for (int i = 1; i <= 2; i++) {

				//============= ���� ����� ��������� �� ���� �����, �� �� ������ ������ =============
				if (fc+i == 10 || fc+i == 7) break;	
				//============= ���� � ����������� �������� ����� ���� ������ ����� =============
				if (!playerTurn.getCardsOnTable_POWER()[fc+i].equals("n")){
					if (fc+i >= 7 && possibleToMoveCardFromCenter(fc+i)) break; 	// ���� �� � 1 ����, � ����� ����� ��������� �� ������ ���
					continue;
				}
				//============= ���� � ����������� �������� ����� ��� ������ ����� =============
				if (playerTurn.getCardsOnTable_POWER()[fc+i].equals("n")){
					if (fc <= 6 && checkSpaceOnOtherLine(fc+i)) break;	// ���� ����� �� ������ ����, � � ����������� ���� ��������� ����� � ������
					changeFocusedCardAndMoveSelectedCardOnTable(fc+i);	// ������� �����, ���� ������ �� ������
					break;
				}
			}
		}
	}
	
	public void doMoveUp(){
		int fc = playerTurn.getFocusedCard_N();
		if (fc >= 4){
			for (int i = 1; i <= 2; i++) {
				//============= ���� ����� ��������� �� ���� �����, �� �� ������ ������ =============
				if (fc-i == 6 || fc-i == 3) break;
				//============= ���� � ����������� �������� ����� ���� ������ ����� =============
				if (!playerTurn.getCardsOnTable_POWER()[fc-i].equals("n")){
					if (fc+i >= 7 && possibleToMoveCardFromCenter(fc-i)) break; 	// ���� �� � 1 ����, � ����� ����� ��������� �� ������ ���
					continue;
				}
				//============= ���� � ����������� �������� ����� ��� ������ ����� =============
				if (playerTurn.getCardsOnTable_POWER()[fc-i].equals("n")){
					if (fc <= 6 && checkSpaceOnOtherLine(fc-i)) break;			// ���� ����� �� ������ ����, � � ����������� ���� ��������� ����� � ������
					changeFocusedCardAndMoveSelectedCardOnTable(fc-i);	// ������� �����, ���� ������ �� ������
					break;
				}
			}
		}
	}
	
	public Boolean doMoveRight(){
		int fc = playerTurn.getFocusedCard_N();
		//============= ���� ������� ����� �� ��������� ������� - ��������� �� ��������� ����� =============
		if (fc <= 3 && playerTurn.getCardsOnTable_COST()[fc] <= playerTurn.getMaana()){
			findNewSpaceOnTable();
			return true;
		}
		//============= ���� �� ������� ����� - ���������� �������� =============
		if (fc <= 3 && playerTurn.getCardsOnTable_COST()[fc] > playerTurn.getMaana()){
			playerTurn.doRedFlash();
			table.getLogger().logOutOfMaana(playerTurn.getMaana(), playerTurn.getStartCardN());
			return true;
		}
		//============= ���� ����� �� ������ ���� - ���������� � ������ ����� =============
		if (fc >= 4 && fc <= 6 && possibleToSwitchCards(fc+3));
		return false;
	} 

	public Boolean doMoveLeft(){
		if (playerTurn.getFocusedCard_N() <= 3){
			playerTurn.cardToMaana();
			playerTurn.setFocusAfterAction();
		}
		else {
			if (playerTurn.getFocusedCard_N() >= 7 && possibleToSwitchCards(playerTurn.getFocusedCard_N()-3)) return true;
			endCardPos = playerTurn.getFocusedCard_N();
			changeFocusedCardAndMoveSelectedCardOnTable(startCardsPos);
			iel.setTextOnLabel(playerTurn.getMaanaPlus_Label(), "+"+playerTurn.getStartCardREFUND());
		}
		return false;
	}
	
	public void doMoveSpace(){
		if (playerTurn.getFocusedCard_N() <= 3){
			playerTurn.setUnselectOnCard();
		}
		else {
			playerTurn.setUnselectOnCard();				
			playerTurn.setUnfocusOnCard();
			playerTurn.decreaseMaanaForCard();
			playerTurn.setFocusAfterAction();
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////// SECONDARY MOVE BLOCK ////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void checkSpaceToFillTableCenter() {
		for (int i = 0; i <= 2; i++) {
			int checkPos = 7+i; // �������� ������� 7, 8 � 9
			if (playerTurn.getCardsOnTable_POWER()[checkPos].equals("n") && 
					!playerTurn.getCardsOnTable_POWER()[checkPos-3].equals("n"))
				{
					int N = playerTurn.getCardsOnTable_N()[checkPos-3];
					String power = playerTurn.getCardsOnTable_POWER()[checkPos-3];
					int hp = playerTurn.getCardsOnTable_HEALTH()[checkPos-3];
					int attack = playerTurn.getCardsOnTable_ATTACK()[checkPos-3];
					cardsMovementThr = new CardsMovementThr(table, card_xy.get((checkPos-3)*10+1), 
																	card_xy.get((checkPos-3)*10+2), 
																card_xy.get(checkPos*10+1), 
															card_xy.get(checkPos*10+2), 0);
					cardsMovementThr.start();
					moveCardProperties(checkPos, N , power, hp, attack);
					clearCardProperties(checkPos-3);
					tests.fillInCardsOnTablePOWERLabel();
					table.getLogger().logMoveCardToCenter(N, checkPos-3, checkPos);
				}
		}
	}
	
	private boolean possibleToSwitchCards(int myNewPos){
		int myOldPos = playerTurn.getFocusedCard_N();
		int myCardNumber = playerTurn.getCardsOnTable_N()[myOldPos];
		int switchCardNumber = playerTurn.getCardsOnTable_N()[myNewPos];
		if (!playerTurn.getCardsOnTable_POWER()[myNewPos].equals("n")){
			int dX = playerTurn.getSide().equals("left")? 10 : -10;
			int switchCard_x = card_xy.get(myNewPos*10+1);
			int switchCard_y = card_xy.get(myNewPos*10+2);
			cardsMovementThr = new CardsMovementThr(table, old_x, old_y, switchCard_x+dX, switchCard_y-10, 0);
			cardsMovementThr.start();
				cardsMovementThr = new CardsMovementThr(table, switchCard_x, switchCard_y, old_x, old_y, 0);
				cardsMovementThr.start();

			table.getLogger().logMoveWithSwitchCard(myCardNumber, switchCardNumber, myOldPos, myNewPos);
			int cardNumber = playerTurn.getCardsOnTable_N()[myNewPos];
			String power = playerTurn.getCardsOnTable_POWER()[myNewPos];
			int hp = playerTurn.getCardsOnTable_HEALTH()[myNewPos];
			int attack = playerTurn.getCardsOnTable_ATTACK()[myNewPos];
			moveCardProperties(playerTurn.getFocusedCard_N(), cardNumber, power, hp, attack);

			playerTurn.setFocusedCard_N(myNewPos);
				cardNumber = playerTurn.getStartCardN();
				power = playerTurn.getStartCardPOWER();
				hp = playerTurn.getStartCardHEALTH();
				attack = playerTurn.getStartCardATTACK();
				moveCardProperties(myNewPos, cardNumber, power, hp, attack);
				
			tests.fillInCardsOnTablePOWERLabel();
			
			return true;
		}
		return false;
	}

	private boolean possibleToMoveCardFromCenter(int focusedCardToSwitch_old) {
		int focusedCardToSwitch_new = focusedCardToSwitch_old-3;
			if (playerTurn.getCardsOnTable_POWER()[focusedCardToSwitch_new].equals("n"))
			{
				cardsMovementThr = new CardsMovementThr(table, card_xy.get(focusedCardToSwitch_old*10+1),
																	card_xy.get(focusedCardToSwitch_old*10+2), 
																card_xy.get(focusedCardToSwitch_new*10+1),
															card_xy.get(focusedCardToSwitch_new*10+2), 0);
				cardsMovementThr.start();
					int cardNumber = playerTurn.getCardsOnTable_N()[focusedCardToSwitch_old];
					String power = playerTurn.getCardsOnTable_POWER()[focusedCardToSwitch_old];
					int hp = playerTurn.getCardsOnTable_HEALTH()[focusedCardToSwitch_old];
					int attack = playerTurn.getCardsOnTable_ATTACK()[focusedCardToSwitch_old];
					moveCardProperties(focusedCardToSwitch_new, cardNumber, power, hp, attack);
				tests.fillInCardsOnTablePOWERLabel();
				table.getLogger().logMoveCardFromCenter(playerTurn.getStartCardN(), cardNumber, 
						playerTurn.getFocusedCard_N(), focusedCardToSwitch_old);
				changeFocusedCardAndMoveSelectedCardOnTable(focusedCardToSwitch_old);
				return true;
			}
		return false;
	}

	private boolean checkSpaceOnOtherLine(int focusedCardToCheck) {
		if (focusedCardToCheck <= 6 && playerTurn.getCardsOnTable_POWER()[focusedCardToCheck+3].equals("n")){	
			changeFocusedCardAndMoveSelectedCardOnTable(focusedCardToCheck+3);
			return true;
		}
		else return false;
	}
	
	private void changeFocusedCardAndMoveSelectedCardOnTable(int newFocusedCard){
			int oldFC = playerTurn.getFocusedCard_N();
			int dX = playerTurn.getSide().equals("left")? 10:-10;
			clearCardProperties(oldFC);
			
				playerTurn.setFocusedCard_N(newFocusedCard);

			moveCardProperties(newFocusedCard, playerTurn.getStartCardN(), 
								playerTurn.getStartCardPOWER(), playerTurn.getStartCardHEALTH(),
								playerTurn.getStartCardATTACK());
			
			tests.fillInCardsOnTablePOWERLabel();
			new_x = card_xy.get(newFocusedCard*10+1)+dX;
			new_y = card_xy.get(newFocusedCard*10+2)-10;
			cardsMovementThr = new CardsMovementThr(table, old_x, old_y, new_x, new_y, 0);
			cardsMovementThr.start();
				table.getLogger().logMoveCard(playerTurn.getStartCardN(), oldFC, newFocusedCard);
	}
	
	public void findNewSpaceOnTable(){
		startCardsPos = playerTurn.getFocusedCard_N();
		if (endCardPos==0)		
			for (int i = 2; i>=1; i--)
				for (int k = 1; k<=3; k++)
					if (!playerTurn.getCardsOnTable_POWER()[i*3+k].equals("n")) continue;
					else {
						changeFocusedCardAndMoveSelectedCardOnTable(i*3+k);
							i=0; // ����� ����� �� �������� �����
							break;
					}
		else{
			changeFocusedCardAndMoveSelectedCardOnTable(endCardPos);
			endCardPos=0;
		}
		
		playerTurn.getMaanaPlus_Label().setText("-"+Integer.toString(playerTurn.getStartCardCOST()));
	}
	/**
	 * ���������� �������� ����� "�� ����". 
	 * @param pos �������, ���� ���������� ��������: �� 1 �� 9.
	 * @param cardNumber ����� �����: �� 1 �� N_OF_CARDS.
	 * @param power ����������� �����: y, s, a, r, m, h.
	 * @param hp �������� �����: �� 1 �� 12.
	 * @param attack ���� �����.
	 */
	public void moveCardProperties(int pos, int cardNumber, String power, int hp, int attack){
		playerTurn.setCardsOnTable_N(pos, cardNumber);
		playerTurn.setCardsOnTable_POWER(pos, power);
		playerTurn.setCardsOnTable_HEALTH(pos, hp);
		playerTurn.setCardsOnTable_ATTACK(pos, attack);
	}
		public void clearCardProperties(int pos){
			playerTurn.setCardsOnTable_N(pos, 0);
			playerTurn.setCardsOnTable_POWER(pos, "n");
			playerTurn.setCardsOnTable_HEALTH(pos, 0);
			playerTurn.setCardsOnTable_ATTACK(pos, 0);
		}
	
	public CardsMovementThr getCardsMovementThr(){
		return cardsMovementThr;
	}
		
	public void setEngine(Engine engine){
		this.engine = engine;
	}
	
	public void setEndCardPos(int endCardPos){
		this.endCardPos = endCardPos;
	}
}
