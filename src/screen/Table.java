package screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import engine.Constants;
import engine.Engine;
import engine.ImageImport;
import engine.PlayerTurn;

public class Table implements Constants, KeyListener {

	private Engine engine;
	private MoveSelectedCards msc;
	private PlayerTurn playerTurn;
	public JFrame mainFrame = new JFrame("Game of Cards");
	public JPanel mainPanel = new PanelMainTable();
	public JPanel leftDeckCard = new PanelDecks(); 	// ����� ������
	public JPanel rightDeckCard = new PanelDecks();	// ������ ������
	public JPanel leftHpSign = new ImageImport("Heart");
	public JButton newGameButton = new JButton("����� ����");  
	
	public JLabel leftTEST = new JLabel("");
	public JLabel rightTEST = new JLabel("");
	
	Table(){
		engine = new Engine(this);
		msc = new MoveSelectedCards(this, engine);
		msc.setEngine(engine);
		
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);
		leftTEST.setBounds(50, 500, 100, 20);								// �������
		rightTEST.setBounds(DISPLAY_RESOLUTION_X-50-100, 500, 100, 20);		// �������
		mainPanel.add(leftTEST);
		mainPanel.add(rightTEST);

		leftDeckCard.setBounds(X_LEFT_DECK, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		rightDeckCard.setBounds(X_RIGHT_DECK, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		newGameButton.setBounds(MIDDLE_X-50, 0, 100, 50);
		newGameButton.setFocusable(false);
		newGameButton.addActionListener(engine);
		mainPanel.add(leftDeckCard);
		mainPanel.add(rightDeckCard);
		mainPanel.add(newGameButton);
		
		mainFrame.setContentPane(mainPanel);
		
		mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);	

		mainFrame.addKeyListener(this);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
		if (e.getKeyChar()==27)
			System.exit(0);
		char c = e.getKeyChar();
		
		if (playerTurn.getSide().equals("right") && (c=='a'|c=='A'|c=='�'|c=='�'))
			c='d';
		else
			if (playerTurn.getSide().equals("right") && (c=='d'|c=='D'|c=='�'|c=='�'))
				c='a';
		
		if (playerTurn.getCardIsSelected())
			keyPressedWithSelectedCard(c);
		else
			keyPressedWithUnselectedCard(c);
	
	mainPanel.repaint();
	}
	
	private void keyPressedWithSelectedCard (char c){
		switch (c) {
			case 's':
			case 'S':
			case '�':
			case '�':
				{
					//moveSelectedCard("down");
				} break;
			case 'w':
			case 'W':
			case '�':
			case '�':
				{
					//moveSelectedCard("up");
				} break;
			case 'd':
			case 'D':
			case '�':
			case '�':
				{
					//moveSelectedCard("right");
				} break;
			case 'a':
			case 'A':
			case '�':
			case '�':
				{
					msc.moveCard("left");
				} break;
			case ' ':
			{
				if (playerTurn.getFocusedCard_N() <= 3)
						playerTurn.setUnselectOnCard();
			} break;
		}
	}

	
	/**
	 * ����� ������������ ������� ������, ���� ����� ��� �� ���� �������
	 * @param e
	 */
	private void keyPressedWithUnselectedCard (char c){
		String[] cardsOnTable = playerTurn.getCardsOnTable();
		playerTurn.setUnfocusOnCard();
		switch (c) {
			case 's':
			case 'S':
			case '�':
			case '�':
				{
					for (int i = 1; i <= 2; i++) {
						if (playerTurn.getFocusedCard_N() + i == 4) break;
						if (cardsOnTable[playerTurn.getFocusedCard_N() + i] == "n") continue;
						if (cardsOnTable[playerTurn.getFocusedCard_N() + i] == "y")
							{
								playerTurn.setFocusedCard_N(playerTurn.getFocusedCard_N() + i);
							}
					}					
				} break;
			case 'w':
			case 'W':
			case '�':
			case '�':
				{
					for (int i = 1; i <= 2; i++) {
						if (playerTurn.getFocusedCard_N() - i == 0) break;
						if (cardsOnTable[playerTurn.getFocusedCard_N() - i] == "n") continue;
						if (cardsOnTable[playerTurn.getFocusedCard_N() - i] == "y")
							{
								playerTurn.setFocusedCard_N(playerTurn.getFocusedCard_N() - i);
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
	
	public Component findComponentToRemove(int x, int y){
			Component componentToRemove = mainPanel.findComponentAt(x,y);
		return componentToRemove;
	}
	
	public void removeComponent(Component componentToRemove){
		mainPanel.remove(componentToRemove);
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


