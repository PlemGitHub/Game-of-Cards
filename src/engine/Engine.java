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
	public ArrayList<Integer> deckNumbers_1 = new ArrayList<>();	// ������ 1 ������ � ������
	public ArrayList<Integer> deckNumbers_2 = new ArrayList<>();	// ������ 2 ������ � ������
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
	 * ����� ������������ ������ "����� ����": ������� ���� �� ����, ������������ ������, 
	 * 
	 */
	public void newGame(){
		playerTurnThread = new PlayerTurnThread(this, table);
		playerTurn = playerTurnThread.getPlayerTurn();
		
		Component componentToRemove;
		//============= �������� ���� =============
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

		//============= ������� ������������ ������� ���� �� ����� =============
		for (int i = 1; i <= 9; i++) {
			cardsOnTable_left[i] = " ";
			cardsOnTable_right[i] = " ";
		}
		
		table.setTextOnLabel(table.leftTEST, ""); 		// �������
		table.setTextOnLabel(table.rightTEST, "");		// �������

		//============= ��������� ����� ������� =============
		deckNumbers_1 = RandomizeDecks(table.leftTEST);	// ����������� ������ 1 ������ �� N_OF_CARDS ����
		deckNumbers_2 = RandomizeDecks(table.rightTEST);	// ����������� ������ 2 ������ �� N_OF_CARDS ����
		
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
			for (int i: generatedNumbers){ 				// �������
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
