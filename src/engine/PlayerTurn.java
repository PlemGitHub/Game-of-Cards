package engine;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import TESTS.Tests;
import screen.InterfaceElements;
import screen.Table;

public class PlayerTurn implements Constants, CardsValues{

	private Table table;
	private Engine engine;
	private String side;
	private Tests tests;
	private InterfaceElements iel;
	private HashMap<Integer, Integer> card_xy;
	private int actionsDone;
	private int focusedCard_N;
	private boolean cardIsSelected = false;
	private String[] cardsOnTable_POWER;
	private int[] cardsOnTable_REFUND;
	private int[] cardsOnTable_COST;
	private int cardsToBeDrawn;
	private String startCardPOWER;
	private int startCardCOST;
	private int hp;
	private int hp_left;
	private int hp_right;
	private int maana;
	private int maana_left;
	private int maana_right;
	private JLabel maana_Label;
	private JLabel maanaPlus_Label;
	
	public PlayerTurn(Engine engine, Table table){
		this.table = table;
		this.engine = engine;
		tests = table.getTests();
	}
	
	public void Turn(String playerSide, ArrayList<Integer> deckNumbers){
		this.side = playerSide;
		iel = table.getInterfaceElements();
		JPanel cardPanel;
		int i;
		int x;
		int y;
		int positionToStartDrawCard = deckNumbers.size()-1;
		card_xy = side.equals("left")? CARD_XY_LEFT : CARD_XY_RIGHT;
		hp = side.equals("left")? hp_left : hp_right;
		maana = side.equals("left")? maana_left : maana_right;
		maana_Label = side.equals("left")? iel.getMaana_left_Label() : iel.getMaana_right_Label();
		maanaPlus_Label = side.equals("left")? iel.getMaanaPlus_left_Label() : iel.getMaanaPlus_right_Label();
		
		cardsOnTable_POWER = side.equals("left")? engine.getCardsOnTable_POWER_left() : engine.getCardsOnTable_POWER_right();
			cardsOnTable_REFUND = side.equals("left")? engine.getCardsOnTable_REFUND_left() : engine.getCardsOnTable_REFUND_right();
				cardsOnTable_COST = side.equals("left")? engine.getCardsOnTable_COST_left() : engine.getCardsOnTable_COST_right();
		actionsDone = 0;
		tests.getFocusedCardTEST().setText("focusedCard_N="+focusedCard_N);

		
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
			cardsOnTable_REFUND[i] = REFUND.get(deckNumbers.get(positionToStartDrawCard-i+1));
			cardsOnTable_COST[i] = COST.get(deckNumbers.get(positionToStartDrawCard-i+1));
		}
		focusedCard_N = 1;
		setFocusOnCard();
		
		tests.fillInCardsOnTablePOWERLabel();
		
		//============= Удаление из конца колоды cardsToBeDrawn шт. карт =============
		for (i=0; i<cardsToBeDrawn; i++)
			deckNumbers.remove(positionToStartDrawCard-i);

		//============= Ежеходный инкремент количества мааны =============
		maana++;
		iel.setTextMaanaLabel(maana_Label, Integer.toString(maana));
		
		table.getMainPanel().repaint();
		
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
				iel.setTextMaanaLabel(maanaPlus_Label, "+"+Integer.toString(cardsOnTable_REFUND[focusedCard_N]));
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
				startCardPOWER = cardsOnTable_POWER[focusedCard_N];
				startCardCOST = cardsOnTable_COST[focusedCard_N];
			}
	}
	public void setUnselectOnCard(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCardPanel = table.findComponentOnMainPanel(x+10, y);
			if (selectedCardPanel != table.getMainPanel()){
				selectedCardPanel.setLocation(selectedCardPanel.getX(), selectedCardPanel.getY()+10);
				cardIsSelected = false;
			}
	}
	
	public void cardToMaana(){
		int x = card_xy.get(focusedCard_N*10+1);
		int y = card_xy.get(focusedCard_N*10+2);
			Component selectedCard = table.findComponentOnMainPanel(x+10, y);
			table.getMainPanel().remove(selectedCard);
			cardIsSelected = false;
			cardsOnTable_POWER[focusedCard_N] = "n";
			tests.fillInCardsOnTablePOWERLabel();
			maana += cardsOnTable_REFUND[focusedCard_N];
			iel.setTextMaanaLabel(maana_Label, Integer.toString(maana));
			actionsDone++;
	}
	
	public void clearMaanaPlus_Label(){
		iel.setTextMaanaLabel(maanaPlus_Label, "");
	}
	
	
	public void decreaseMaanaForCard(){
		maana -= startCardCOST;
		iel.setTextMaanaLabel(maana_Label, Integer.toString(maana));
	}
	
	public void setCardsOnTable_POWER(int i, String str){
		cardsOnTable_POWER[i] = str;
	}	
		public String[] getCardsOnTable_POWER(){
			return cardsOnTable_POWER;
		}

	public int[] getCardsOnTable_COST(){
		return cardsOnTable_COST;
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
	
	public String getStartCardPOWER(){
		return startCardPOWER;
	}
	
	public void setMaana(int maana){
		this.maana = maana;
	}
		public int getMaana(){
			return maana;
		}
			public void setMaana_left(int maana){
				maana_left = maana;
			}
				public void setMaana_right(int maana){
					maana_right = maana;
				}
}
