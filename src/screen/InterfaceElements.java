package screen;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import engine.Constants;
import engine.Engine;
import panels.ImageImport;
import panels.PanelDecks;

public class InterfaceElements implements Constants{
	private Table table;
	private Engine engine;
	private JPanel deckCard_left = new PanelDecks(); 	// левая колода
	private JPanel deckCard_right = new PanelDecks();	// правая колода
		private JPanel hp_left_img = new ImageImport("Heart");
		private JLabel hp_left_Lable = new JLabel();
		private JPanel hp_right_img = new ImageImport("Heart");
		private JLabel hp_right_Lable = new JLabel();
	private JPanel maana_left_img = new ImageImport("Maana");
	private JLabel maana_left_Label = new JLabel();
	private JPanel maana_right_img = new ImageImport("Maana");
	private JLabel maana_right_Label = new JLabel();
		private JLabel maanaPlus_left_Label = new JLabel();
		private JLabel maanaPlus_right_Label = new JLabel();
	private JPanel mainPanel;
	
	public InterfaceElements(Table table, Engine engine) {
		this.table = table;
		this.engine = engine;
		
		mainPanel = table.getMainPanel();
		
		setUpDecksAndButton();
		setUpHpSigns();
		setUpMaanaSigns();
	}
	
	private void setUpMaanaSigns() {
		maana_left_img.setBounds(FIELD_XY_LEFT.get(01), DECK_Y+DECK_HEIGTH+40+HP_MAANA_HEIGHT+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
		maana_left_img.setOpaque(false);
		mainPanel.add(maana_left_img);
			maana_left_Label.setBounds(FIELD_XY_LEFT.get(01)+HP_MAANA_WIDTH+5, DECK_Y+DECK_HEIGTH+40+HP_MAANA_HEIGHT+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
			maana_left_Label.setFont(new Font("Segoe Script", Font.BOLD, 30));
			maana_left_Label.setForeground(Color.BLUE);
			maana_left_Label.setHorizontalAlignment(SwingConstants.RIGHT);
			mainPanel.add(maana_left_Label);
			
		maana_right_img.setBounds(FIELD_XY_RIGHT.get(01)+FIELD_WIDTH-HP_MAANA_WIDTH, DECK_Y+DECK_HEIGTH+40+HP_MAANA_HEIGHT+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
		maana_right_img.setOpaque(false);
		mainPanel.add(maana_right_img);
			maana_right_Label.setBounds(FIELD_XY_RIGHT.get(01)+FIELD_WIDTH-HP_MAANA_WIDTH*2-5, DECK_Y+DECK_HEIGTH+40+HP_MAANA_HEIGHT+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
			maana_right_Label.setFont(new Font("Segoe Script", Font.BOLD, 30));
			maana_right_Label.setForeground(Color.BLUE);
			mainPanel.add(maana_right_Label);
			
		maanaPlus_left_Label.setBounds(FIELD_XY_LEFT.get(01)+HP_MAANA_WIDTH+5, DECK_Y+DECK_HEIGTH+40+(HP_MAANA_HEIGHT+40)*3/2, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
		maanaPlus_left_Label.setFont(new Font("Segoe Script", Font.BOLD, 30));
		maanaPlus_left_Label.setForeground(Color.BLUE);
		maanaPlus_left_Label.setHorizontalTextPosition(SwingConstants.RIGHT);
		mainPanel.add(maanaPlus_left_Label);
			maanaPlus_right_Label.setBounds(FIELD_XY_RIGHT.get(01)+FIELD_WIDTH-HP_MAANA_WIDTH*2-5, DECK_Y+DECK_HEIGTH+40+(HP_MAANA_HEIGHT+40)*3/2, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
			maanaPlus_right_Label.setFont(new Font("Segoe Script", Font.BOLD, 30));
			maanaPlus_right_Label.setForeground(Color.BLUE);
			mainPanel.add(maanaPlus_right_Label);
	}

	private void setUpHpSigns() {	// x = колода, HP_SIGN_WIDTH, промежуток 5,  HP_SIGN_WIDTH
		hp_left_img.setBounds(FIELD_XY_LEFT.get(01), DECK_Y+DECK_HEIGTH+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
		hp_left_img.setOpaque(false);
		mainPanel.add(hp_left_img);		
			hp_left_Lable.setBounds(FIELD_XY_LEFT.get(01)+HP_MAANA_WIDTH+5, DECK_Y+DECK_HEIGTH+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
			hp_left_Lable.setFont(new Font("Segoe Script", Font.BOLD, 33));
			hp_left_Lable.setForeground(Color.RED);
			hp_left_Lable.setHorizontalAlignment(SwingConstants.RIGHT);
			mainPanel.add(hp_left_Lable);
		
		hp_right_img.setBounds(FIELD_XY_RIGHT.get(01)+FIELD_WIDTH-HP_MAANA_WIDTH, DECK_Y+DECK_HEIGTH+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
		hp_right_img.setOpaque(false);
		mainPanel.add(hp_right_img);		
			hp_right_Lable.setBounds(FIELD_XY_RIGHT.get(01)+FIELD_WIDTH-HP_MAANA_WIDTH*2-5, DECK_Y+DECK_HEIGTH+40, HP_MAANA_WIDTH, HP_MAANA_HEIGHT);
			hp_right_Lable.setFont(new Font("Segoe Script", Font.BOLD, 33));
			hp_right_Lable.setForeground(Color.RED);
			mainPanel.add(hp_right_Lable);
	}

	private void setUpDecksAndButton() {
		deckCard_left.setBounds(DECK_X_LEFT, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		deckCard_right.setBounds(DECK_X_RIGHT, DECK_Y, DECK_WIDTH, DECK_HEIGTH);
		mainPanel.add(deckCard_left);
		mainPanel.add(deckCard_right);
			table.getNewGameButton().setBounds(MIDDLE_X-50, 0, 100, 50);
			table.getNewGameButton().setFocusable(false);
			table.getNewGameButton().addActionListener(engine);
			mainPanel.add(table.getNewGameButton());
	}
	
	public JLabel getHp_left_Label(){
		return hp_left_Lable;
	}
		public JLabel getHp_right_Label(){
				return hp_right_Lable;
		}
		
	public JLabel getMaana_left_Label(){
		return maana_left_Label;
	}
		public JLabel getMaana_right_Label(){
			return maana_right_Label;
		}
			public JLabel getMaanaPlus_left_Label(){
				return maanaPlus_left_Label;
			}
				public JLabel getMaanaPlus_right_Label(){
					return maanaPlus_right_Label;
				}
	
	public void setTextOnLabel(JLabel label, String str){
		label.setText(str);
	}
	
}
