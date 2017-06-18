package screen;

import java.util.HashMap;
import TESTS.Tests;
import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;

public class MoveSelectedCards implements Constants {
	private Table table;
	private Engine engine;
	private Tests tests;
	private PlayerTurn playerTurn;
	private int focusedCard_N_ToSwitch_old;
	private int focusedCard_N_ToSwitch_new;
	private int startFocusedCard_N;
	private HashMap<Integer, Integer> card_xy;
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
		tests = table.getTests();
		card_xy = playerTurn.getCard_XY();
		old_x = card_xy.get(playerTurn.getFocusedCard_N()*10+1);
		old_y = card_xy.get(playerTurn.getFocusedCard_N()*10+2);
		switch (dir){
			case "down":{
				if (playerTurn.getFocusedCard_N() >= 4){
					for (int i = 1; i <= 2; i++) {

						//============= ���� ����� ��������� �� ���� �����, �� �� ������ ������ =============
						if (playerTurn.getFocusedCard_N()+i == 10 || 
								playerTurn.getFocusedCard_N()+i == 7) break;	
						//============= ���� � ����������� �������� ����� ���� ������ ����� =============
						if (!playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()+i].equals("n")){
							if (playerTurn.getFocusedCard_N()+i >= 7 && 
									possibleToSwitchCards(playerTurn.getFocusedCard_N()+i)) break; 	// ���� �� � 1 ����, � ����� ����� ��������� �� ������ ���
							if (checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()+i)) break;		// ���� ������� ����� �� �����, �� ���� ����� �� ������ ����
							continue;
						}
						//============= ���� � ����������� �������� ����� ��� ������ ����� =============
						if (playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()+i].equals("n")){
							if (playerTurn.getFocusedCard_N() <= 6 &&
									checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()+i)) break;			// ���� ����� �� ������ ����, � � ����������� ���� ��������� ����� � ������
							changeFocusedCardAndMoveSelectedCardOnTable(playerTurn.getFocusedCard_N()+i);	// ������� �����, ���� ������ �� ������
							break;
						}
					}
				}
			} break;
			
			case "up":{
				if (playerTurn.getFocusedCard_N() >= 4){
					for (int i = 1; i <= 2; i++) {
						//============= ���� ����� ��������� �� ���� �����, �� �� ������ ������ =============
						if (playerTurn.getFocusedCard_N()-i == 6 || 
								playerTurn.getFocusedCard_N()-i == 3) break;
						//============= ���� � ����������� �������� ����� ���� ������ ����� =============
						if (!playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()-i].equals("n")){
							if (playerTurn.getFocusedCard_N()+i >= 7 && 
									possibleToSwitchCards(playerTurn.getFocusedCard_N()-i)) break; 	// ���� �� � 1 ����, � ����� ����� ��������� �� ������ ���
							if (checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()-i)) break;		// ���� ������� ����� �� �����, �� ���� ����� �� ������ ����
							continue;
						}
						//============= ���� � ����������� �������� ����� ��� ������ ����� =============
						if (playerTurn.getCardsOnTable_POWER()[playerTurn.getFocusedCard_N()-i].equals("n")){
							if (playerTurn.getFocusedCard_N() <= 6 && 
									checkSpaceOnOtherLine(playerTurn.getFocusedCard_N()-i)) break;			// ���� ����� �� ������ ����, � � ����������� ���� ��������� ����� � ������
							changeFocusedCardAndMoveSelectedCardOnTable(playerTurn.getFocusedCard_N()-i);	// ������� �����, ���� ������ �� ������
							break;
						}
					}
				}
			} break;
			
			case "right":{
				if (playerTurn.getFocusedCard_N() <= 3 && playerTurn.getCardsOnTable_COST()[playerTurn.getFocusedCard_N()] <= playerTurn.getMaana()){
					startFocusedCard_N = playerTurn.getFocusedCard_N();
					findNewSpaceOnTable();
				}
			} break;
			
			case "left": {
				if (playerTurn.getFocusedCard_N() <= 3){
						playerTurn.cardToMaana();
						setFocusAfterAction();
				}
				else {
					changeFocusedCardAndMoveSelectedCardOnTable(startFocusedCard_N);
				}
			} break;
			
			case " ":{
				if (playerTurn.getFocusedCard_N() <= 3){
					playerTurn.setUnselectOnCard();
				}
				else {
					playerTurn.setUnselectOnCard();				
					playerTurn.setUnfocusOnCard();
					playerTurn.setActionsDone(playerTurn.getActionsDone()+1);
					playerTurn.decreaseMaanaForCard();
					setFocusAfterAction();
				}
			} break;
		}
		checkSpaceToFillTableCenter();
	}
	
	private void checkSpaceToFillTableCenter() {
		int checkPos;
		//int dX = playerTurn.getSide().equals("left")? 10:-10;
		for (int i = 0; i <= 2; i++) {
			checkPos = 7+i; // �������� ������� 7, 8 � 9
			if (playerTurn.getCardsOnTable_POWER()[checkPos].equals("n") && 
					!playerTurn.getCardsOnTable_POWER()[checkPos-3].equals("n"))
				{
					table.moveCardOnTable(card_xy.get((checkPos-3)*10+1),
											card_xy.get((checkPos-3)*10+2),
												card_xy.get(checkPos*10+1),
													card_xy.get(checkPos*10+2));
					playerTurn.setCardsOnTable_POWER(checkPos, playerTurn.getCardsOnTable_POWER()[checkPos-3]);
					playerTurn.setCardsOnTable_POWER(checkPos-3, "n");
					tests.fillInCardsOnTablePOWERLabel();
				}
		}
		
	}

	private boolean possibleToSwitchCards(int focusedCardToCheck) {		
		String powerOnNewPosition = playerTurn.getCardsOnTable_POWER()[focusedCardToCheck];
			if (playerTurn.getCardsOnTable_POWER()[focusedCardToCheck-3].equals("n"))
			{
				focusedCard_N_ToSwitch_old = focusedCardToCheck;
				focusedCard_N_ToSwitch_new = focusedCard_N_ToSwitch_old-3;
				table.moveCardOnTable(card_xy.get(focusedCard_N_ToSwitch_old*10+1),
										card_xy.get(focusedCard_N_ToSwitch_old*10+2),
											card_xy.get(focusedCard_N_ToSwitch_new*10+1),
												card_xy.get(focusedCard_N_ToSwitch_new*10+2));
				playerTurn.setCardsOnTable_POWER(focusedCard_N_ToSwitch_new, powerOnNewPosition);
				playerTurn.setCardsOnTable_POWER(focusedCard_N_ToSwitch_old, "n");
				tests.fillInCardsOnTablePOWERLabel();
				changeFocusedCardAndMoveSelectedCardOnTable(focusedCardToCheck);
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
			playerTurn.setCardsOnTable_POWER(playerTurn.getFocusedCard_N(), "n");
			playerTurn.setFocusedCard_N(newFocusedCard);
			playerTurn.setCardsOnTable_POWER(newFocusedCard, playerTurn.getStartCardPOWER());
			tests.fillInCardsOnTablePOWERLabel();
			new_x = card_xy.get(playerTurn.getFocusedCard_N()*10+1)+dX;
			new_y = card_xy.get(playerTurn.getFocusedCard_N()*10+2)-10;
			table.moveCardOnTable(old_x, old_y, new_x, new_y);
	}
	
	public void findNewSpaceOnTable(){
		for (int i = 2; i>=1; i--)
			for (int k = 1; k<=3; k++)
				if (!playerTurn.getCardsOnTable_POWER()[i*3+k].equals("n")) continue;
					else {
						changeFocusedCardAndMoveSelectedCardOnTable(i*3+k);
							i=0; // ����� ����� �� �������� �����
							break;
					}
	}
	
	public void setEngine(Engine engine){
		this.engine = engine;
	}
}
