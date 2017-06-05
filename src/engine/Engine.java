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
	public ArrayList<Integer> deckNumbers_1 = new ArrayList<>();	// ������ 1 ������ � ������
	public ArrayList<Integer> deckNumbers_2 = new ArrayList<>();	// ������ 2 ������ � ������
	
	public Engine(Table table) {
		this.table = table;
		playerTurn = new PlayerTurn(table);
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
		
		//============= �������� ���� =============
		table.cardsJPanels.clear();
		for (int i = 1; i <= 9; i++) {
			int x = CARD_XY_LEFT.get(i*10+1);
			int y = CARD_XY_LEFT.get(i*10+2);
				Component compToRemove = table.mainPanel.findComponentAt(x,y);
				table.mainPanel.remove(compToRemove);
		}
		table.mainPanel.repaint();
		
		table.leftTEST.setText(""); 		// �������
		table.rightTEST.setText("");		// �������
		

		//============= ��������� ����� ������� =============
		deckNumbers_1 = RandomizeDecks(N_OF_CARDS, table.leftTEST);	// ����������� ������ 1 ������ �� N_OF_CARDS ����
		deckNumbers_2 = RandomizeDecks(N_OF_CARDS, table.rightTEST);	// ����������� ������ 2 ������ �� N_OF_CARDS ����

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
		for (int i: generatedNumbers){ 				// �������
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
