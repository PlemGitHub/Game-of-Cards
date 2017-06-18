package engine;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import TESTS.Tests;
import screen.Table;

public class Engine implements Constants, ActionListener {
	
	private Table table;
	private PlayerTurn playerTurn;
	private Tests tests;
	private PlayerTurnThread playerTurnThread;
	private ArrayList<Integer> deckInNumbers_left = new ArrayList<>();	// ������ 1 ������ � ������
	private ArrayList<Integer> deckInNumbers_right = new ArrayList<>();	// ������ 2 ������ � ������
	private String[] cardsOnTable_POWER_left = new String[13];
	private String[] cardsOnTable_POWER_right = new String[13];
	private int hp_left;
	private int hp_right;
	
	public Engine(Table table) {
		this.table = table;
	}
	
	public void actionPerformed(ActionEvent e) {
		if ((JButton)e.getSource() == table.getNewGameButton())
			newGame();
	}
	
	/**
	 * ����� ������������ ������ "����� ����": ������� ���� �� ����, ������������ ������, 
	 * 
	 */
	public void newGame(){
		playerTurnThread = new PlayerTurnThread(this, table);
		playerTurn = playerTurnThread.getPlayerTurn();
		tests = table.getTests();
		Component componentToRemove;
		//============= �������� ���� =============
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

		//============= ������� ������������ ������� ���� �� ����� =============
		for (int i = 1; i <= 9; i++) {
			cardsOnTable_POWER_left[i] = "n";
			cardsOnTable_POWER_right[i] = "n";
		}
		
		table.setTextOnLabel(table.getInterfaceElements().getHp_left_Label(), Integer.toString(START_HP));
		table.setTextOnLabel(table.getInterfaceElements().getHp_right_Label(), Integer.toString(START_HP));
		table.setTextOnLabel(table.getInterfaceElements().getMaana_left_Label(), Integer.toString(START_MAANA));
		table.setTextOnLabel(table.getInterfaceElements().getMaana_right_Label(), Integer.toString(START_MAANA));
		
		//============= ��������� ����� ������� =============
		deckInNumbers_left = RandomizeDecks(tests.getLeftTEST());	// ����������� ������ 1 ������ �� N_OF_CARDS ����
		deckInNumbers_right = RandomizeDecks(tests.getRightTEST());	// ����������� ������ 2 ������ �� N_OF_CARDS ����
		
		playerTurnThread.start();
	}
	
	private ArrayList<Integer> RandomizeDecks(JLabel jlabel){
		ArrayList<Integer> generatedNumbers = new ArrayList<>();
		Random rnd = new Random();
		tests.setTextOnLabel(jlabel, "");
		int x;
			for (int i=1; i<=N_OF_CARDS; i++){
				x = rnd.nextInt(N_OF_CARDS)+1;
				if (!generatedNumbers.contains(x))
					generatedNumbers.add(x);
				else
					i--;
			}
			for (int i: generatedNumbers){ 				// �������
				tests.setTextOnLabel(jlabel, tests.getTextOnLabel(jlabel)+" "+i);
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
	
	public PlayerTurnThread getPlayerTurnThread(){
		return playerTurnThread;
	}
	
	public int getHp_left(){
		return hp_left;
	}
	public int getHp_right(){
		return hp_right;
	}
}
