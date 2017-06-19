package screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import TESTS.Tests;
import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;
import panels.PanelMainTable;

public class Table implements Constants, KeyListener {

	private Engine engine;
	private MoveSelectedCards msc;
	private PlayerTurn playerTurn;
	private Tests tests = new Tests(this);
	private InterfaceElements iel;
		private JFrame mainFrame = new JFrame("Game of Cards");
		private JPanel mainPanel = new PanelMainTable();
		private JButton newGameButton = new JButton("Новая игра");
		private int focusedCard_N;
	
	Table(){
		engine = new Engine(this);
		iel = new InterfaceElements(this, engine);
		msc = new MoveSelectedCards(this, engine, iel);
		
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);

		tests.setUpTESTSComponents();
		
		mainFrame.setContentPane(mainPanel);
		
		mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);	

		mainFrame.addKeyListener(this);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			newGameButton.doClick();
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		
		try {
			playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
			focusedCard_N = playerTurn.getFocusedCard_N();
			
			char c = e.getKeyChar();
			
			if (playerTurn.getSide().equals("right") && (c=='a'|c=='A'|c=='ф'|c=='Ф'))
				c='d';
			else
				if (playerTurn.getSide().equals("right") && (c=='d'|c=='D'|c=='в'|c=='В'))
					c='a';
			if (playerTurn.getCardIsSelected())
				keyPressedWithSelectedCard(c);
			else
				keyPressedWithUnselectedCard(c);
		} catch (NullPointerException e1) {}
	
		if (e.getKeyChar()==27)
			System.exit(0);
		
	mainPanel.repaint();
	}
	
	private void keyPressedWithSelectedCard (char c){
		switch (c) {
			case 's':
			case 'S':
			case 'ы':
			case 'Ы':
				{
					if (focusedCard_N >= 4)
						msc.moveCard("down");
					tests.getFocusedCardTEST().setText("focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case 'w':
			case 'W':
			case 'ц':
			case 'Ц':
				{
					if (focusedCard_N >= 4)
						msc.moveCard("up");
					tests.getFocusedCardTEST().setText("focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case 'd':
			case 'D':
			case 'в':
			case 'В':
				{
						msc.moveCard("right");
						tests.getFocusedCardTEST().setText("focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case 'a':
			case 'A':
			case 'ф':
			case 'Ф':
				{
					msc.moveCard("left");
					tests.getFocusedCardTEST().setText("focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case ' ':
			{
					msc.moveCard(" ");
			} break;
		}
	}

	
	/**
	 * Метод отрабатывает нажатия клавиш, если карта еще не была выбрана
	 * @param e
	 */
	private void keyPressedWithUnselectedCard (char c){
		String[] cardsOnTable_POWER = playerTurn.getCardsOnTable_POWER();
		playerTurn.setUnfocusOnCard();
		switch (c) {
			case 's':
			case 'S':
			case 'ы':
			case 'Ы':
				{
					for (int i = 1; i <= 2; i++) {
						if (focusedCard_N + i == 4) break;
						if (cardsOnTable_POWER[focusedCard_N + i].equals("n")) continue;
						if (!cardsOnTable_POWER[focusedCard_N + i].equals("n"))
							{
								playerTurn.setFocusedCard_N(focusedCard_N + i);
								tests.getFocusedCardTEST().setText("focusedCard_N="+playerTurn.getFocusedCard_N());
								break;
							}
					}					
				} break;
			case 'w':
			case 'W':
			case 'ц':
			case 'Ц':
				{
					for (int i = 1; i <= 2; i++) {
						if (focusedCard_N - i == 0) break;
						if (cardsOnTable_POWER[focusedCard_N - i].equals("n")) continue;
						if (!cardsOnTable_POWER[focusedCard_N - i].equals("n"))
							{
								playerTurn.setFocusedCard_N(focusedCard_N - i);
								tests.getFocusedCardTEST().setText("focusedCard_N="+playerTurn.getFocusedCard_N());
								break;
							}
					}					
				} break;
			case ' ':
			{						
					playerTurn.setSelectOnCard();
			} break;
		}
		playerTurn.setFocusOnCard();
	}
	
	
	public void addCardPanelToMainPanel(JPanel cardPanel){
		mainPanel.add(cardPanel);
	}
		public void removeComponentFromTable(Component componentToRemove){
			mainPanel.remove(componentToRemove);
		}	
			public Component findComponentOnMainPanel(int x, int y){
				Component componentToRemove = mainPanel.findComponentAt(x,y);
				return componentToRemove;
			}
				public void moveCardOnTable(int old_x, int old_y, int new_x, int new_y){
					mainPanel.findComponentAt(old_x+10, old_y).setLocation(new_x, new_y);
				}
			
	public JPanel getMainPanel(){
		return mainPanel;
	}
		public JButton getNewGameButton(){
			return newGameButton;
		}



	public Tests getTests(){
		return tests;
	}
		public Engine getEngine(){
			return engine;
		}
			public InterfaceElements getInterfaceElements(){
				return iel;
			}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Table table = new Table();
	}
}


