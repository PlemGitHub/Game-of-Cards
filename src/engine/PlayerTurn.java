package engine;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import screen.Table;

public class PlayerTurn implements Constants{

	private Table table;
	private Engine engine;
	private String side;
	private HashMap<Integer, Integer> card_xy;
	private int actionsDone;
	private int focusedCard_N=1;
	private boolean cardIsSelected = false;
	private String[] cardsOnTable;
	
	public PlayerTurn(Engine engine, Table table){
		this.table = table;
		this.engine = engine;
	}
	
	public void Turn(String playerSide, ArrayList<Integer> deckNumbers){
		this.side = playerSide;
		JPanel cardPanel;
		int i;
		int x;
		int y;
		int cardsToBeDrawn;
		int positionToStartDrawCard = deckNumbers.size()-1;
		cardsOnTable = side.equals("left")? engine.cardsOnTable_left : engine.cardsOnTable_right;
		focusedCard_N = 1;
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
			table.mainPanel.add(cardPanel);
			cardsOnTable[i] = "y";
		}
		setFocusOnCard();
		table.mainPanel.repaint();
		
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
			Component selectedCard = table.mainPanel.findComponentAt(x+10, y);
			if (selectedCard != table.mainPanel)
				selectedCard.setLocation(selectedCard.getX()-dX, selectedCard.getY());
	}
	public void setFocusOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
		int dX = side.equals("left")? 10: -10;
			Component selectedCard = table.mainPanel.findComponentAt(x+10, y);
			if (selectedCard != table.mainPanel)
				selectedCard.setLocation(selectedCard.getX()+dX, selectedCard.getY());
	}
	
	/*
	 * Устанавливают или снимают выбор карты для дальнейшего действия
	 */
	public void setSelectOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCard = table.mainPanel.findComponentAt(x+10, y);
			if (selectedCard != table.mainPanel){
				selectedCard.setLocation(selectedCard.getX(), selectedCard.getY()-10);
				cardIsSelected = true;
			}
	}
	public void setUnselectOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCard = table.mainPanel.findComponentAt(x+10, y);
			if (selectedCard != table.mainPanel){
				selectedCard.setLocation(selectedCard.getX(), selectedCard.getY()+10);
				cardIsSelected = false;
			}
	}
	
	public void cardToMaana(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCard = table.mainPanel.findComponentAt(x+10, y);
			table.mainPanel.remove(selectedCard);
			actionsDone++;
			cardIsSelected = false;
			cardsOnTable[focusedCard_N] = "n";
	}
	
	public String[] getCardsOnTable(){
		return cardsOnTable;
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
		
	public int getActionsDone(){
		return actionsDone;
	}
	
	public HashMap<Integer, Integer> getCard_XY (){
		return card_xy;
	}
}
