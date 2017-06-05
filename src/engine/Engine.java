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
	public ArrayList<Integer> deckNumbers_1 = new ArrayList<>();	// колода 1 игрока в цифрах
	public ArrayList<Integer> deckNumbers_2 = new ArrayList<>();	// колода 2 игрока в цифрах
	
	public Engine(Table table) {
		this.table = table;
		playerTurn = new PlayerTurn(table);
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
		
		//============= Очистить поле =============
		table.cardsJPanels.clear();
		for (int i = 1; i <= 9; i++) {
			int x = CARD_XY_LEFT.get(i*10+1);
			int y = CARD_XY_LEFT.get(i*10+2);
				Component compToRemove = table.mainPanel.findComponentAt(x,y);
				table.mainPanel.remove(compToRemove);
		}
		table.mainPanel.repaint();
		
		table.leftTEST.setText(""); 		// УДАЛИТЬ
		table.rightTEST.setText("");		// УДАЛИТЬ
		

		//============= Генерация колод игроков =============
		deckNumbers_1 = RandomizeDecks(N_OF_CARDS, table.leftTEST);	// замешивание колоды 1 игрока из N_OF_CARDS карт
		deckNumbers_2 = RandomizeDecks(N_OF_CARDS, table.rightTEST);	// замешивание колоды 2 игрока из N_OF_CARDS карт

		startGame();
	}
	
	private ArrayList<Integer> RandomizeDecks(int n_of_cards, JLabel label){
		ArrayList<Integer> generatedNumbers = new ArrayList<>();
		Random rnd = new Random();
		int x;
			for (int i=1; i<=n_of_cards; i++){
				x = rnd.nextInt(n_of_cards)+1;
				if (!generatedNumbers.contains(x))
					generatedNumbers.add(x);
				else
					i--;
			}
		for (int i: generatedNumbers){ 				// УДАЛИТЬ
			label.setText(label.getText()+" "+i);
		}
		return generatedNumbers;
	}
	
	private void startGame(){
		//while (true){
			playerTurn.Turn("left", deckNumbers_1);
			playerTurn.Turn("right", deckNumbers_2);
		//}
	}	
}
