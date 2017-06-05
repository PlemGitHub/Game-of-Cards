package screen;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Constants;
import engine.Engine;
import engine.ImageImport;

public class Table implements Constants, KeyListener {

	public JFrame mainFrame = new JFrame("Game of Cards");
	public JPanel mainPanel = new PanelMainTable();
	public JPanel leftDeckCard = new PanelDecks(); 	// левая колода
	public JPanel rightDeckCard = new PanelDecks();	// правая колода
	public JPanel leftHpSign = new ImageImport("Heart");
	public JButton newGameButton = new JButton("Новая игра");  
	
	public JLabel leftTEST = new JLabel("");
	public JLabel rightTEST = new JLabel("");
	public ArrayList<JPanel> cardsJPanels = new ArrayList<>();
	
	Table(){
		Engine engine = new Engine(this);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);
		
		leftTEST.setBounds(50, 500, 100, 20);
		rightTEST.setBounds(DISPLAY_RESOLUTION_X-50-100, 500, 100, 20);
		mainPanel.add(leftTEST);
		mainPanel.add(rightTEST);

		leftDeckCard.setBounds(X_LEFT_DECK, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		rightDeckCard.setBounds(X_RIGHT_DECK, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		newGameButton.setBounds(MIDDLE_X-50, DISPLAY_RESOLUTION_Y/20, 100, 50);
		newGameButton.setFocusable(false);
		newGameButton.addActionListener(engine);
		mainPanel.add(rightDeckCard);
		mainPanel.add(leftDeckCard);
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
		if (e.getKeyChar()==27)
			System.exit(0);		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Table table = new Table();
		
	}
}
