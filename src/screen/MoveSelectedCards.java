package screen;

import java.util.HashMap;
import javax.swing.JLabel;
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
	private int focusedCard_N_ToSwitch_old;
	private int focusedCard_N_ToSwitch_new;
	private int startFocusedCard_N;
	private HashMap<Integer, Integer> card_xy;
	private int old_x;
	private int old_y;
	private int new_x;
	private int new_y;
	private CardsMovementThr cardsMovementThr;
	
	public MoveSelectedCards(Table table, Engine engine, InterfaceElements iel) {
		this.table = table;
		this.engine = engine;
		this.iel = iel;
	}
	
	public void moveCard(String dir){
		playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
		tests = table.getTests();
		card_xy = playerTurn.getCard_XY();
		old_x = card_xy.get(playerTurn.getFocusedCard_N()*10+1);
		old_y = card_xy.get(playerTurn.getFocusedCard_N()*10+2);
		switch (dir){
			case "down":{
				if (playerTurn.getFocusedCard_N() >= 4){
					for (int i = 1; i <= 2; i++) {

						//============= Если карта находится НА КРАЮ СТОЛА, то не делать ничего =============
						if (playerTurn.getFocusedCard_N()+i == 10 || 
								playerTurn.getFocusedCard_N()+i == 7) break;	
						//============= Если в направлении движения карты ЕСТЬ другая карта =============
						if (!playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()+i].equals("n")){
							if (playerTurn.getFocusedCard_N()+i >= 7 && 
									possibleToMoveCardFromCenter(playerTurn.getFocusedCard_N()+i)) break; 	// если мы в 1 ряду, и карту НУЖНО подвинуть во второй ряд
							if (checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()+i)) break;		// если двигать карту НЕ НУЖНО, но есть место во втором ряду
							continue;
						}
						//============= Если в направлении движения карты НЕТ другой карты =============
						if (playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()+i].equals("n")){
							if (playerTurn.getFocusedCard_N() <= 6 &&
									checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()+i)) break;			// если карта во втором ряду, а в направлении есть свободное место в первом
							changeFocusedCardAndMoveSelectedCardOnTable(playerTurn.getFocusedCard_N()+i);	// двигать карту, если ничего не мешает
							break;
						}
					}
				}
			} break;
			
			case "up":{
				if (playerTurn.getFocusedCard_N() >= 4){
					for (int i = 1; i <= 2; i++) {
						//============= Если карта находится НА КРАЮ СТОЛА, то не делать ничего =============
						if (playerTurn.getFocusedCard_N()-i == 6 || 
								playerTurn.getFocusedCard_N()-i == 3) break;
						//============= Если в направлении движения карты ЕСТЬ другая карта =============
						if (!playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()-i].equals("n")){
							if (playerTurn.getFocusedCard_N()+i >= 7 && 
									possibleToMoveCardFromCenter(playerTurn.getFocusedCard_N()-i)) break; 	// если мы в 1 ряду, и карту НУЖНО подвинуть во второй ряд
							if (checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()-i)) break;		// если двигать карту НЕ НУЖНО, но есть место во втором ряду
							continue;
						}
						//============= Если в направлении движения карты НЕТ другой карты =============
						if (playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()-i].equals("n")){
							if (playerTurn.getFocusedCard_N() <= 6 && 
									checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()-i)) break;			// если карта во втором ряду, а в направлении есть свободное место в первом
							changeFocusedCardAndMoveSelectedCardOnTable(playerTurn.getFocusedCard_N()-i);	// двигать карту, если ничего не мешает
							break;
						}
					}
				}
			} break;
			
			case "right":{
				if (playerTurn.getFocusedCard_N() <= 3 && playerTurn.getCardsOnTable_COST()[playerTurn.getFocusedCard_N()] <= playerTurn.getMaana()){
					startFocusedCard_N = playerTurn.getFocusedCard_N();
					findNewSpaceOnTable();
					break;
				}
					if (playerTurn.getFocusedCard_N() <= 3 && playerTurn.getCardsOnTable_COST()[playerTurn.getFocusedCard_N()] > playerTurn.getMaana()){
						playerTurn.doRedFlash();
						break;
					}
				if (playerTurn.getFocusedCard_N() >= 4 && 
						playerTurn.getFocusedCard_N() <= 6 &&
							possibleToSwitchCards(playerTurn.getFocusedCard_N()+3)) break;
			} break;
			
			case "left": {
				if (playerTurn.getFocusedCard_N() <= 3){
						playerTurn.cardToMaana();
						setFocusAfterAction();
				}
				else {
					if (playerTurn.getFocusedCard_N() >= 7 && possibleToSwitchCards(playerTurn.getFocusedCard_N()-3)) break;
					changeFocusedCardAndMoveSelectedCardOnTable(startFocusedCard_N);
					iel.setTextOnLabel(playerTurn.getMaanaPlus_Label(), "+"+playerTurn.getStartCardREFUND());
				}
			} break;
			
			case " ":{
				if (playerTurn.getFocusedCard_N() <= 3){
					playerTurn.setUnselectOnCard();
				}
				else {
					playerTurn.setUnselectOnCard();				
					playerTurn.setUnfocusOnCard();
					playerTurn.decreaseMaanaForCard();
					playerTurn.setActionsDone(playerTurn.getActionsDone()+1);
					setFocusAfterAction();
				}
			} break;
		}
		checkSpaceToFillTableCenter();
	}

	private void checkSpaceToFillTableCenter() {
		int checkPos;
		for (int i = 0; i <= 2; i++) {
			checkPos = 7+i; // проверка позиций 7, 8 и 9
			if (playerTurn.getCardsOnTable_POWER()[checkPos].equals("n") && 
					!playerTurn.getCardsOnTable_POWER()[checkPos-3].equals("n"))
				{
					cardsMovementThr = new CardsMovementThr(table, card_xy.get((checkPos-3)*10+1), 
																	card_xy.get((checkPos-3)*10+2), 
																card_xy.get(checkPos*10+1), 
															card_xy.get(checkPos*10+2));
					cardsMovementThr.start();
					moveCardProperties(checkPos, playerTurn.getCardsOnTable_N()[checkPos-3], playerTurn.getCardsOnTable_POWER()[checkPos-3]);
					moveCardProperties(checkPos-3, 0, "n");
					tests.fillInCardsOnTablePOWERLabel();
				}
		}
		
	}
	
	private boolean possibleToSwitchCards(int focusedCardToCheck){
		int dX = playerTurn.getSide().equals("left")? 10 : -10;
		if (!playerTurn.getCardsOnTable_POWER()[focusedCardToCheck].equals("n")){
			int switchCard_x = card_xy.get(focusedCardToCheck*10+1);
			int switchCard_y = card_xy.get(focusedCardToCheck*10+2);
			cardsMovementThr = new CardsMovementThr(table, old_x, old_y, 
																	switchCard_x+dX, switchCard_y-10);
			cardsMovementThr.start();
				cardsMovementThr = new CardsMovementThr(table, switchCard_x, switchCard_y, old_x, old_y);
				cardsMovementThr.start();
			playerTurn.setCardsOnTable_POWER(playerTurn.getFocusedCard_N(), playerTurn.getCardsOnTable_POWER()[focusedCardToCheck]);
			playerTurn.setCardsOnTable_POWER(focusedCardToCheck, playerTurn.getStartCardPOWER());
			playerTurn.setFocusedCard_N(focusedCardToCheck);
			tests.fillInCardsOnTablePOWERLabel();
			
			return true;
		}
		return false;
	}

	private boolean possibleToMoveCardFromCenter(int focusedCardToMove) {		
		String powerOnNewPosition = playerTurn.getCardsOnTable_POWER()[focusedCardToMove];
			if (playerTurn.getCardsOnTable_POWER()[focusedCardToMove-3].equals("n"))
			{
				focusedCard_N_ToSwitch_old = focusedCardToMove;
				focusedCard_N_ToSwitch_new = focusedCard_N_ToSwitch_old-3;
				cardsMovementThr = new CardsMovementThr(table, card_xy.get(focusedCard_N_ToSwitch_old*10+1),
																	card_xy.get(focusedCard_N_ToSwitch_old*10+2), 
																card_xy.get(focusedCard_N_ToSwitch_new*10+1),
															card_xy.get(focusedCard_N_ToSwitch_new*10+2));
				cardsMovementThr.start();
				playerTurn.setCardsOnTable_POWER(focusedCard_N_ToSwitch_new, powerOnNewPosition);
				playerTurn.setCardsOnTable_POWER(focusedCard_N_ToSwitch_old, "n");
				tests.fillInCardsOnTablePOWERLabel();
				changeFocusedCardAndMoveSelectedCardOnTable(focusedCardToMove);
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

	public void setFocusAfterAction(){
		for (int i = 1; i <= 3; i++)
			if (!playerTurn.getCardsOnTable_POWER()[i].equals("n")){
				playerTurn.setFocusedCard_N(i);
				playerTurn.setFocusOnCard();
				break;
			}
	}
	
	public void changeFocusedCardAndMoveSelectedCardOnTable(int newFocusedCard){
			int dX = playerTurn.getSide().equals("left")? 10:-10;
			moveCardProperties(playerTurn.getFocusedCard_N(), 0, "n");
			
				playerTurn.setFocusedCard_N(newFocusedCard);

			moveCardProperties(newFocusedCard, playerTurn.getStartCardN(), playerTurn.getStartCardPOWER());
			
			tests.fillInCardsOnTablePOWERLabel();
			new_x = card_xy.get(playerTurn.getFocusedCard_N()*10+1)+dX;
			new_y = card_xy.get(playerTurn.getFocusedCard_N()*10+2)-10;
			cardsMovementThr = new CardsMovementThr(table, old_x, old_y, new_x, new_y);
			cardsMovementThr.start();
	}
	
	public void findNewSpaceOnTable(){
		for (int i = 2; i>=1; i--)
			for (int k = 1; k<=3; k++)
				if (!playerTurn.getCardsOnTable_POWER()[i*3+k].equals("n")) continue;
					else {
						JLabel maanaPlus = playerTurn.getSide().equals("left")? iel.getMaanaPlus_left_Label():iel.getMaanaPlus_right_Label();
						maanaPlus.setText("-"+Integer.toString(playerTurn.getStartCardCOST()));
						changeFocusedCardAndMoveSelectedCardOnTable(i*3+k);
							i=0; // чтобы выйти из внешнего цикла
							break;
					}
	}
	/**
	 * Перемещает свойства карты "по полю". 
	 * @param pos позиция, куда записывать значения: от 1 до 9.
	 * @param cardNumber номер карты: от 1 до N_OF_CARDS.
	 * @param power сила карты: y, s, a, r, m, h.
	 */
	public void moveCardProperties(int pos, int cardNumber, String power){
		playerTurn.setCardsOnTable_N(playerTurn.getFocusedCard_N(), cardNumber);
		playerTurn.setCardsOnTable_POWER(pos, power);
	}
	
	public void setEngine(Engine engine){
		this.engine = engine;
	}
}
