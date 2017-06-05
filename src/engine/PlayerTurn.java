package engine;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import screen.Table;
import screen.PanelMainTable;

public class PlayerTurn implements Constants{

	private Table table;
	private PanelMainTable tablePanel;		// mainPanel
	private Engine engine;
	private int focusedCard_N=1;
	private int focusedCard_x = 0;
	private int focusedCard_y = 0;
	private HashMap<Integer, Integer> field_xy;
	
	public PlayerTurn(Table table){
		this.table = table;
	}
	
	public void Turn(String player, ArrayList<Integer> deckNumbers){
		JPanel cardPanel;
		int i;
		int cardsToBeDrawn;
		int positionToStartDrawCard = deckNumbers.size()-1;
		focusedCard_N = 1;
		int x;
		int y;
		HashMap<Integer, Integer> card_xy;
		
		if (player.equals("left")){
				card_xy = CARD_XY_LEFT;
				setFocusOnCard("left", focusedCard_N);
		} else {
				card_xy = CARD_XY_RIGHT;
				setFocusOnCard("right", focusedCard_N);		
		}

		//============= Проверка на остаток количества карт =============
		cardsToBeDrawn = deckNumbers.size()>3? 3 : deckNumbers.size();
		
		//============= Сдача cardsToBeDrawn шт. карт =============
		for (i=1; i<=cardsToBeDrawn; i++){
			x = card_xy.get(i*10+1).intValue();
			y = card_xy.get(i*10+2).intValue();
			cardPanel = new ImageImport(Integer.toString(deckNumbers.get(positionToStartDrawCard-i+1)));
			cardPanel.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
			cardPanel.setOpaque(false);
			table.cardsJPanels.add(cardPanel);			
			table.mainPanel.add(table.cardsJPanels.get(i-1));
		}
		table.mainPanel.repaint();
		
		for (i=0; i<cardsToBeDrawn; i++){
			deckNumbers.remove(positionToStartDrawCard-i);
		}
	}
	
	private void setFocusOnCard(String side, int n_of_card){
		int old_x = focusedCard_x;
		int old_y = focusedCard_y;
		int new_x;
		int new_y;
		
		if (side.equals("left")){
			field_xy = CARD_XY_LEFT;
		}			
	}
}
