package engine;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import screen.Table;

public class Engine implements Constants, ActionListener {
	
	private Table table;
	private PlayerTurn playerTurn;
	private PlayerTurnThread playerTurnThread;
	public ArrayList<Integer> deckNumbers_1 = new ArrayList<>();	// колода 1 игрока в цифрах
	public ArrayList<Integer> deckNumbers_2 = new ArrayList<>();	// колода 2 игрока в цифрах
	public String[] cardsOnTable_left = new String[10];
	public String[] cardsOnTable_right = new String[10];
	
	public Engine(Table table) {
		this.table = table;
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((JButton)e.getSource() == table.newGameButton)
			newGame();
	}
	
	/**
	 * Метод отрабатывает кнопку "Новая Игра": очищает поле от карт, перемешивает колоды, 
	 * 
	 */
	public void newGame(){
		playerTurnThread = new PlayerTurnThread(this, table);
		playerTurn = playerTurnThread.getPlayerTurn();
		
		Component componentToRemove;
		//============= Очистить поле =============
		for (int i = 1; i <= 9; i++) {
			int x = CARD_XY_LEFT.get(i*10+1)+10;
			int y = CARD_XY_LEFT.get(i*10+2);
				componentToRemove = table.findComponentToRemove(x, y);
				table.removeComponent(componentToRemove);
		}
		for (int i = 1; i <= 9; i++) {
			int x = CARD_XY_RIGHT.get(i*10+1)+10;
			int y = CARD_XY_RIGHT.get(i*10+2);
				componentToRemove = table.findComponentToRemove(x, y);
				table.removeComponent(componentToRemove);
		}
		playerTurn.setCardIsSelected(false);

		//============= Очистка отслеживания наличия карт на полях =============
		for (int i = 1; i <= 9; i++) {
			cardsOnTable_left[i] = " ";
			cardsOnTable_right[i] = " ";
		}
		
		table.setTextOnLabel(table.leftTEST, ""); 		// УДАЛИТЬ
		table.setTextOnLabel(table.rightTEST, "");		// УДАЛИТЬ

		//============= Генерация колод игроков =============
		deckNumbers_1 = RandomizeDecks(table.leftTEST);	// замешивание колоды 1 игрока из N_OF_CARDS карт
		deckNumbers_2 = RandomizeDecks(table.rightTEST);	// замешивание колоды 2 игрока из N_OF_CARDS карт
		
		playerTurnThread.start();
	}
	
	private ArrayList<Integer> RandomizeDecks(JLabel jlabel){
		ArrayList<Integer> generatedNumbers = new ArrayList<>();
		Random rnd = new Random();
		int x;
			for (int i=1; i<=N_OF_CARDS; i++){
				x = rnd.nextInt(N_OF_CARDS)+1;
				if (!generatedNumbers.contains(x))
					generatedNumbers.add(x);
				else
					i--;
			}
			for (int i: generatedNumbers){ 				// УДАЛИТЬ
				table.setTextOnLabel(jlabel, table.getTextOnLabel(jlabel)+" "+i);
			}
		return generatedNumbers;
	}
	
	public ArrayList<Integer> getDeckNumber_1(){
		return deckNumbers_1;
	}
	public ArrayList<Integer> getDeckNumber_2(){
		return deckNumbers_2;
	}
	
	public PlayerTurnThread getPlayerTurnThread(){
		return playerTurnThread;
	}
}
