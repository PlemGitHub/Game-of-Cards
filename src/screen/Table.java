package screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import TESTS.Tests;
import engine.Constants;
import engine.Engine;
import engine.Logger;
import engine.PlayerTurn;
import panels.PanelMainTable;

public class Table implements Constants, KeyListener, WindowListener {

	private Engine engine;
	private MoveSelectedCards msc;
	private PlayerTurn playerTurn;
	private Tests tests = new Tests(this);
	private InterfaceElements iel;
	private Logger logger;
		private JFrame mainFrame = new JFrame("Game of Cards");
		private JPanel mainPanel = new PanelMainTable();
		private JButton newGameButton = new JButton("Новая игра");
		private JButton helpButton = new JButton("ПОМОЩЬ");
	
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

		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(this);
			newGameButton.doClick();
		logger = new Logger(this);
		logger.logGameOfCardsStarted();

	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if (!msc.getCardsMovementThr().isAlive() && mainFrame.getContentPane()==mainPanel)
			try {
				playerTurn = engine.getPlayerTurnThread().getPlayerTurn();
				int focusedCard_N = playerTurn.getFocusedCard_N();
				
				char c = e.getKeyChar();
				//============= ПЕРЕПИСЬ КНОПОК, ЕСЛИ ХОДИТ ВТОРОЙ ИГРОК =============
				if (playerTurn.getSide().equals("right") && (c=='a'|c=='A'|c=='ф'|c=='Ф'))
					c='d';
				else
					if (playerTurn.getSide().equals("right") && (c=='d'|c=='D'|c=='в'|c=='В'))
						c='a';
				
				if (playerTurn.getCardIsSelected())
					keyPressedWithSelectedCard(c, focusedCard_N);
				else
					if (e.getKeyChar()!=27)	// Иначе записывается logSetFocusOnCard
						keyPressedWithUnselectedCard(c, focusedCard_N);
			} catch (NullPointerException e1) {e1.printStackTrace();}
		
			if (e.getKeyChar()==27){	// если нажать Esc
				if (mainFrame.getContentPane()==mainPanel)	// закрыть игру, если на главной форме
					windowClosing(null);
				if (mainFrame.getContentPane()==engine.getHelpScreen()) // вернуться - если в Помощи
					engine.getHelpScreen().getCloseHelpButton().doClick();
			}
			
			mainPanel.repaint();
		
	}
	
	private void keyPressedWithSelectedCard (char c, int focusedCard_N){
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
	private void keyPressedWithUnselectedCard (char c, int focusedCard_N){
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
								logger.logSetFocusOnCard(playerTurn.getFocusedCard_N());
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
								logger.logSetFocusOnCard(playerTurn.getFocusedCard_N());
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
				Component component = mainPanel.findComponentAt(x,y);
				return component;
			}
			
	public JFrame getMainFrame(){
		return mainFrame;
	}
	public JPanel getMainPanel(){
		return mainPanel;
	}
		public JButton getNewGameButton(){
			return newGameButton;
		}
			public JButton getHelpButton(){
				return helpButton;
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
				public MoveSelectedCards getMsc(){
					return msc;
				}
					public Logger getLogger(){
						return logger;
					}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
			Table table = new Table();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		logger.closeFile();
		System.exit(0);
	}
}


