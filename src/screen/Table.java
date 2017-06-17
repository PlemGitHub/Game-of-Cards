package screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import TESTS.Tests;
import engine.Constants;
import engine.Engine;
import engine.ImageImport;
import engine.PlayerTurn;

public class Table implements Constants, KeyListener {

	private Engine engine;
	private MoveSelectedCards msc;
	private PlayerTurn playerTurn;
	private Tests tests = new Tests(this);
	private JFrame mainFrame = new JFrame("Game of Cards");
	private JPanel mainPanel = new PanelMainTable();
	private JPanel deckCard_left = new PanelDecks(); 	// левая колода
	private JPanel deckCard_right = new PanelDecks();	// правая колода
	private JPanel hpSign_left_img = new ImageImport("Heart");
	private JLabel hpSign_left_Lable = new JLabel();
	private JPanel hpSign_right_img = new ImageImport("Heart");
	private JLabel hpSign_right_Lable = new JLabel();
	private JButton newGameButton = new JButton("Новая игра");
	private int focusedCard_N;
	
	Table(){
		engine = new Engine(this);
		msc = new MoveSelectedCards(this, engine);
		
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);

		tests.setUpTESTSComponents();
		setUpDecksAndButton();
		setUpHpSigns();
		
		mainFrame.setContentPane(mainPanel);
		
		mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);	

		mainFrame.addKeyListener(this);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setUpHpSigns() {
		hpSign_left_img.setBounds(FIELD_XY_LEFT.get(01), DECK_Y+DECK_HEIGTH+40, HP_SIGN_WIDTH, HP_SIGN_HEIGHT);
		hpSign_left_img.setOpaque(false);
		hpSign_left_Lable.setBounds(FIELD_XY_LEFT.get(01)+HP_SIGN_WIDTH+5, DECK_Y+DECK_HEIGTH+40, HP_SIGN_WIDTH, HP_SIGN_HEIGHT);
		hpSign_left_Lable.setFont(new Font("Segoe Script", Font.BOLD, 33));
		hpSign_left_Lable.setForeground(Color.RED);
		hpSign_right_img.setBounds(FIELD_XY_RIGHT.get(01), DECK_Y+DECK_HEIGTH+40, HP_SIGN_WIDTH, HP_SIGN_HEIGHT);
		hpSign_right_img.setOpaque(false);
		mainPanel.add(hpSign_left_img);
		mainPanel.add(hpSign_left_Lable);
		mainPanel.add(hpSign_right_img);
	}

	private void setUpDecksAndButton() {
		deckCard_left.setBounds(X_LEFT_DECK, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		deckCard_right.setBounds(X_RIGHT_DECK, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		mainPanel.add(deckCard_left);
		mainPanel.add(deckCard_right);
			newGameButton.setBounds(MIDDLE_X-50, 0, 100, 50);
			newGameButton.setFocusable(false);
			newGameButton.addActionListener(engine);
			mainPanel.add(newGameButton);
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
					tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case 'w':
			case 'W':
			case 'ц':
			case 'Ц':
				{
					if (focusedCard_N >= 4)
						msc.moveCard("up");
					tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case 'd':
			case 'D':
			case 'в':
			case 'В':
				{
						msc.moveCard("right");
						tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+playerTurn.getFocusedCard_N());
				} break;
			case 'a':
			case 'A':
			case 'ф':
			case 'Ф':
				{
					msc.moveCard("left");
					tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+playerTurn.getFocusedCard_N());
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
								tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+playerTurn.getFocusedCard_N());
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
								tests.setTextOnLabel(tests.getFocusedCardTEST(), "focusedCard_N="+playerTurn.getFocusedCard_N());
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
	
	public Component findComponentOnMainPanel(int x, int y){
			Component componentToRemove = mainPanel.findComponentAt(x,y);
		return componentToRemove;
	}
	
	public void removeComponentFromTable(Component componentToRemove){
		mainPanel.remove(componentToRemove);
	}
	
	public void addCardPanelToMainPanel(JPanel cardPanel){
		mainPanel.add(cardPanel);
	}
	
	public JPanel getMainPanel(){
		return mainPanel;
	}
	
	public void moveCardOnTable(int old_x, int old_y, int new_x, int new_y){
		mainPanel.findComponentAt(old_x+10, old_y).setLocation(new_x, new_y);
	}
	
	public JButton getNewGameButton(){
		return newGameButton;
	}
	public JLabel getHpSign_Label_left(){
		return hpSign_left_Lable;
	}
	public JLabel getHpSign_Label_right(){
		return hpSign_right_Lable;
	}
	
	public Tests getTests(){
		return tests;
	}
	
	public Engine getEngine(){
		return engine;
	}
	
	public void setTextOnLabel(JLabel jlabel, String str){
		jlabel.setText(str);
	}
		public String getTextOnLabel(JLabel jlabel){
			return jlabel.getText();		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Table table = new Table();
	}
}


