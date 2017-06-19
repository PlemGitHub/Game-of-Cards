package engine;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import TESTS.Tests;
import screen.Table;
import threads.PlayerTurnThread;

public class Engine implements Constants, ActionListener {
	
	private Table table;
	private PlayerTurn playerTurn;
	private Tests tests;
	private PlayerTurnThread playerTurnThread;
	private ArrayList<Integer> deckInNumbers_left = new ArrayList<>();	// колода 1 игрока в цифрах
	private ArrayList<Integer> deckInNumbers_right = new ArrayList<>();	// колода 2 игрока в цифрах
	private String[] cardsOnTable_POWER_left = new String[10];
	private String[] cardsOnTable_POWER_right = new String[10];
		private int[] cardsOnTable_REFUND_left = new int[10];
		private int[] cardsOnTable_REFUND_right = new int[10];
			private int[] cardsOnTable_COST_left = new int[10];
			private int[] cardsOnTable_COST_right = new int[10];
				private int[] cardsOnTable_HEALTH_left = new int[10];
				private int[] cardsOnTable_HEALTH_right = new int[10];
					private int[] cardsOnTable_ATTACK_left = new int[10];
					private int[] cardsOnTable_ATTACK_right = new int[10];
	
	public Engine(Table table) {
		this.table = table;
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((JButton)e.getSource() == table.getNewGameButton())
			newGame();
	}
	
	/**
	 * Метод отрабатывает кнопку "Новая Игра": очищает поле от карт, перемешивает колоды, 
	 * 
	 */
	public void newGame(){
		playerTurnThread = new PlayerTurnThread(table, this);
		playerTurn = playerTurnThread.getPlayerTurn();
		tests = table.getTests();
		Component componentToRemove;
		//============= Очистить поле =============
		for (int i = 1; i <= 9; i++) {
			int x = CARD_XY_LEFT.get(i*10+1)+10;
			int y = CARD_XY_LEFT.get(i*10+2);
				componentToRemove = table.findComponentOnMainPanel(x, y);
				table.removeComponentFromTable(componentToRemove);
		}
		for (int i = 1; i <= 9; i++) {
			int x = CARD_XY_RIGHT.get(i*10+1)+10;
			int y = CARD_XY_RIGHT.get(i*10+2);
				componentToRemove = table.findComponentOnMainPanel(x, y);
				table.removeComponentFromTable(componentToRemove);
		}
		playerTurn.setCardIsSelected(false);

		//============= Очистка отслеживания наличия карт на полях =============
		for (int i = 1; i <= 9; i++) {
			cardsOnTable_POWER_left[i] = "n";
			cardsOnTable_POWER_right[i] = "n";
			cardsOnTable_REFUND_left[i] = 0;
			cardsOnTable_REFUND_right[i] = 0;
			cardsOnTable_COST_left[i] = 0;
			cardsOnTable_COST_right[i] = 0;
		}

		//============= Отрисовка начальных значений хп и мааны =============
		table.getInterfaceElements().getHp_left_Label().setText(Integer.toString(START_HP));
		table.getInterfaceElements().getHp_right_Label().setText(Integer.toString(START_HP));
		table.getInterfaceElements().getMaana_right_Label().setText(Integer.toString(START_MAANA));
		table.getInterfaceElements().getMaanaPlus_right_Label().setText("");
		
		//============= Генерация колод игроков =============
		deckInNumbers_left = RandomizeDecks();
		deckInNumbers_right = RandomizeDecks();

		//============= TESTS =============
		tests.getLeftTEST().setText("");
		tests.getRightTEST().setText("");
		for (int i = 0; i <= N_OF_CARDS-1; i++) {
			tests.getLeftTEST().setText(tests.getLeftTEST().getText()+" "+deckInNumbers_left.get(i));
			tests.getRightTEST().setText(tests.getRightTEST().getText()+" "+deckInNumbers_right.get(i));
		}

		//============= Запуск игры =============
		playerTurnThread.start();
	}
	
	/**
	 * Метод генерирует колоду из N_OF_CARDS шт. карт
	 * @return Возвращает ArrayList случайных чисел - номеров карт
	 */
	private ArrayList<Integer> RandomizeDecks(){
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
		return generatedNumbers;
	}
	
	public ArrayList<Integer> getDeckInNumbers_left(){
		return deckInNumbers_left;
	}
		public ArrayList<Integer> getDeckInNumbers_right(){
			return deckInNumbers_right;
		}
	
	public String[] getCardsOnTable_POWER_left(){
		return cardsOnTable_POWER_left;
	}
		public String[] getCardsOnTable_POWER_right(){
			return cardsOnTable_POWER_right;
		}
			public int[] getCardsOnTable_REFUND_left(){
				return cardsOnTable_REFUND_left;
			}
				public int[] getCardsOnTable_REFUND_right(){
					return cardsOnTable_REFUND_right;
				}
					public int[] getCardsOnTable_COST_left(){
						return cardsOnTable_COST_left;
					}
						public int[] getCardsOnTable_COST_right(){
							return cardsOnTable_COST_right;
						}
							public int[] getCardsOnTable_HEALTH_left(){
								return cardsOnTable_HEALTH_left;
							}
								public int[] getCardsOnTable_HEALTH_right(){
									return cardsOnTable_HEALTH_right;
								}
									public int[] getCardsOnTable_ATTACK_left(){
										return cardsOnTable_ATTACK_left;
									}
										public int[] getCardsOnTable_ATTACK_right(){
											return cardsOnTable_ATTACK_right;
										}
		
	public PlayerTurnThread getPlayerTurnThread(){
		return playerTurnThread;
	}	
}
