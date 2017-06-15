package screen;

import java.util.HashMap;

import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;

public class MoveSelectedCards implements Constants {
	private Table table;
	private Engine engine;
	private PlayerTurn playerTurn;
	private int focusedCard_N;
	private int oldFocusedCard_N;
	private HashMap<Integer, Integer> card_xy;
	private String[] cardsOnTable_POWER;
	private int old_x;
	private int old_y;
	private int new_x;
	private int new_y;
	
	public MoveSelectedCards(Table table, Engine engine) {
		this.table = table;
		this.engine = engine;
	}
	
	public void moveCard(String dir){
		playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
		focusedCard_N = playerTurn.getFocusedCard_N();
		card_xy = playerTurn.getCard_XY();
		cardsOnTable_POWER = playerTurn.getCardsOnTable_POWER();
		old_x = card_xy.get(focusedCard_N*10+1);
		old_y = card_xy.get(focusedCard_N*10+2);
		switch (dir){
			case "down":{
				if (focusedCard_N >= 3){
					for (int i = 1; i <= 2; i++) {
						if (focusedCard_N+i == 10 || focusedCard_N+i == 7) break;
						if (!cardsOnTable_POWER[focusedCard_N+i].equals("n")){
							if (checkSpaceOnOtherLine(focusedCard_N+i)) break;
							continue;
						}
						if (cardsOnTable_POWER[focusedCard_N+i].equals("n")){
							if (focusedCard_N<7 && checkSpaceOnOtherLine(focusedCard_N+i)) break;	
							changeFocusedCardAndMoveCardOnTable(focusedCard_N+i);
							break;
						}
					}
				}
			} break;
			
			case "up":{
				if (focusedCard_N >= 3){
					for (int i = 1; i <= 2; i++) {
						if (focusedCard_N-i == 6 || focusedCard_N-i == 3) break;
						if (!cardsOnTable_POWER[focusedCard_N-i].equals("n")){
							if (checkSpaceOnOtherLine(focusedCard_N-i)) break;
							continue;
						}
						if (cardsOnTable_POWER[focusedCard_N-i].equals("n")){
							if (focusedCard_N<7 && checkSpaceOnOtherLine(focusedCard_N-i)) break;
							changeFocusedCardAndMoveCardOnTable(focusedCard_N-i);
							break;
						}
					}
				}
			} break;
			
			case "right":{
				if (focusedCard_N <= 3){
					oldFocusedCard_N = focusedCard_N;
					findNewSpaceOnTable();
				}
			} break;
			
			case "left": {
				if (focusedCard_N <= 3){
						playerTurn.cardToMaana();
						setFocusAfterAction();
				}
				else {
					changeFocusedCardAndMoveCardOnTable(oldFocusedCard_N);
				}
			} break;
			
			case " ":{
				if (focusedCard_N <= 3){
					playerTurn.setUnselectOnCard();
				}
				else {
					playerTurn.setUnselectOnCard();				
					playerTurn.setUnfocusOnCard();
					playerTurn.setActionsDone(playerTurn.getActionsDone()+1);
					setFocusAfterAction();
				}
			} break;
		}
	}
	
	private boolean checkSpaceOnOtherLine(int focusedCardToCheck) {
		int dN = focusedCardToCheck>6? 3:-3;
		focusedCardToCheck -= dN;
		if (focusedCardToCheck>3 && cardsOnTable_POWER[focusedCardToCheck].equals("n")){	
			changeFocusedCardAndMoveCardOnTable(focusedCardToCheck);
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
	
	public void changeFocusedCardAndMoveCardOnTable(int newFocusedCard){
			playerTurn.setFocusedCard_N(newFocusedCard);
			focusedCard_N = playerTurn.getFocusedCard_N();
			new_x = card_xy.get(focusedCard_N*10+1);
			new_y = card_xy.get(focusedCard_N*10+2);
			table.moveCardOnTable(old_x, old_y, new_x, new_y);
	}
	
	public void findNewSpaceOnTable(){
		for (int i = 2; i>=1; i--)
			for (int k = 1; k<=3; k++)
				if (!cardsOnTable_POWER[i*3+k].equals("n")) continue;
					else {
						changeFocusedCardAndMoveCardOnTable(i*3+k);
							i=0; // чтобы выйти из внешнего цикла
							break;
					}
	}
	
	public void setEngine(Engine engine){
		this.engine = engine;
	}
}
