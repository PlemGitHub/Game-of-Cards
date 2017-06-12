package screen;

import java.util.HashMap;

import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;

public class MoveSelectedCards implements Constants {
	private Table table;
	private PlayerTurn playerTurn;
	private Engine engine;
	
	public MoveSelectedCards(Table table, Engine engine) {
		this.table = table;
		this.engine = engine;
	}
	
	public void moveCard(String dir){
		playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
		int focusedCard_N = playerTurn.getFocusedCard_N();
		HashMap<Integer, Integer> card_xy = playerTurn.getCard_XY();
		switch (dir){
			case "down":{
				
			}
			case "up":{
				
			}
			case "right":{
				int x = card_xy.get(focusedCard_N*10+1);
				int y = card_xy.get(focusedCard_N*10+2);
			}
			case "left": {
				if (focusedCard_N <= 3){
						playerTurn.cardToMaana(); 
						setFocusAfterAction();
				}
			} break;
		}
	}
	
	public void setFocusAfterAction(){
		playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
		String[] cardsOnTable = playerTurn.getCardsOnTable();
		for (int i = 1; i <= 3; i++)
			if (cardsOnTable[i] == "y"){
				playerTurn.setFocusedCard_N(i);
				playerTurn.setFocusOnCard();
				break;
			}
	}
	
	public void setEngine(Engine engine){
		this.engine = engine;
	}
}
