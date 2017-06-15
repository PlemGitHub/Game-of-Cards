package engine;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

import TESTS.Tests;
import screen.Table;

public class PlayerTurn implements Constants, CardsValues{

	private Table table;
	private Engine engine;
	private String side;
	private Tests tests;
	private HashMap<Integer, Integer> card_xy;
	private int actionsDone;
	private int focusedCard_N;
	private boolean cardIsSelected = false;
	private String[] cardsOnTable_POWER;
	private int cardsToBeDrawn;
	private String selectedCardType;
	
	public PlayerTurn(Engine engine, Table table){
		this.table = table;
		this.engine = engine;
		tests = table.getTests();
	}
	
	public void Turn(String playerSide, ArrayList<Integer> deckNumbers){
		this.side = playerSide;
		JPanel cardPanel;
		int i;
		int x;
		int y;
		int positionToStartDrawCard = deckNumbers.size()-1;
		cardsOnTable_POWER = side.equals("left")? engine.getCardsOnTable_POWER_left() : 
												engine.getCardsOnTable_POWER_right();
		focusedCard_N = 1;
		tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+focusedCard_N);
		actionsDone = 0;

		card_xy = side.equals("left")? CARD_XY_LEFT : CARD_XY_RIGHT;
		
		//============= Проверка на остаток количества карт =============
		cardsToBeDrawn = deckNumbers.size()>3? 3 : deckNumbers.size();
		
		//============= Сдача cardsToBeDrawn шт. карт =============
		for (i=1; i<=cardsToBeDrawn; i++){
			x = card_xy.get(i*10+1).intValue();
			y = card_xy.get(i*10+2).intValue();
			cardPanel = new ImageImport(Integer.toString(deckNumbers.get(positionToStartDrawCard-i+1)));
			cardPanel.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
			cardPanel.setOpaque(false);		
			table.addCardPanelToMainPanel(cardPanel);
			cardsOnTable_POWER[i] = POWER.get(deckNumbers.get(positionToStartDrawCard-i+1));
		}
		tests.fillInCardsOnTablePOWERLabel();
		setFocusOnCard();
		table.getMainPanel().repaint();
		
		//============= Удаление из конца колоды cardsToBeDrawn шт. карт =============
		for (i=0; i<cardsToBeDrawn; i++)
			deckNumbers.remove(positionToStartDrawCard-i);
	}
	
	/*
	 * Устанавливают или снимают визуальное выделение при выборе карты
	 */
	public void setUnfocusOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
		int dX = side.equals("left")? 10: -10;
			Component selectedCard = table.findComponentOnMainPanel(x+10, y);
			if (selectedCard != table.getMainPanel())
				selectedCard.setLocation(selectedCard.getX()-dX, selectedCard.getY());
	}
	public void setFocusOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
		int dX = side.equals("left")? 10: -10;
			Component selectedCard = table.findComponentOnMainPanel(x+10, y);
			if (selectedCard != table.getMainPanel())
				selectedCard.setLocation(selectedCard.getX()+dX, selectedCard.getY());
	}
	
	/*
	 * Устанавливают или снимают выбор карты для дальнейшего действия
	 */
	public void setSelectOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCardPanel = table.findComponentOnMainPanel(x+10, y);
			if (selectedCardPanel != table.getMainPanel()){
				selectedCardPanel.setLocation(selectedCardPanel.getX(), selectedCardPanel.getY()-10);
				cardIsSelected = true;
				selectedCardType = cardsOnTable_POWER[focusedCard_N];
				cardsOnTable_POWER[focusedCard_N] = "n";
				tests.fillInCardsOnTablePOWERLabel();
			}
	}
	public void setUnselectOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCardPanel = table.findComponentOnMainPanel(x+10, y);
			if (selectedCardPanel != table.getMainPanel()){
				selectedCardPanel.setLocation(selectedCardPanel.getX(), selectedCardPanel.getY()+10);
				cardIsSelected = false;
				cardsOnTable_POWER[focusedCard_N] = selectedCardType;
				tests.fillInCardsOnTablePOWERLabel();
			}
	}
	
	public void cardToMaana(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCard = table.findComponentOnMainPanel(x+10, y);
			table.getMainPanel().remove(selectedCard);
			actionsDone++;
			cardIsSelected = false;
			cardsOnTable_POWER[focusedCard_N] = "n";
			tests.fillInCardsOnTablePOWERLabel();
	}
	
	public void setCardsOnTable(int i, String str){
		cardsOnTable_POWER[i] = str;
	}	
		public String[] getCardsOnTable_POWER(){
			return cardsOnTable_POWER;
		}
	
	public void setEngine(Engine engine){
		this.engine = engine;
	}
	
	public void setCardIsSelected(boolean cardIsSelected){
		this.cardIsSelected = cardIsSelected;
	}
		public boolean getCardIsSelected(){
			return cardIsSelected;
	}
	
	public String getSide(){
		return side;
	}
	
	public void setFocusedCard_N(int focusedCard_N){
		this.focusedCard_N = focusedCard_N;
	}
		public int getFocusedCard_N(){
			return focusedCard_N;
		}
		
	public void setActionsDone(int actionsDone){
		this.actionsDone = actionsDone;
	}	
		public int getActionsDone(){
			return actionsDone;
		}
	
	public int getCardsToBeDrawn(){
		return cardsToBeDrawn;
	}
	
	public HashMap<Integer, Integer> getCard_XY (){
		return card_xy;
	}
	
	public void setSelectedCardType(String selectedCardType){
		this.selectedCardType = selectedCardType;
	}
		public String getSelectedCardType(){
			return selectedCardType;
		}
}
