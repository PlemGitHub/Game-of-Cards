package TESTS;

import javax.swing.JLabel;
import engine.Constants;
import engine.PlayerTurn;
import screen.Table;

public class Tests implements Constants{
	private Table table;
	private PlayerTurn playerTurn;
	private JLabel leftCardsLabel = new JLabel();
	private JLabel rightCardsLabel = new JLabel();
	private JLabel focusedCardLabel = new JLabel();
	private JLabel cardsOnTablePOWERLabel = new JLabel();
	
	public Tests(Table table) {
		this.table = table;
	}
	
	public void setUpTESTSComponents(){
		leftCardsLabel.setBounds(50, 500, 100, 20);								
		rightCardsLabel.setBounds(DISPLAY_RESOLUTION_X-50-100, 500, 100, 20);		
		focusedCardLabel.setBounds(50, 50, 200, 20);
		cardsOnTablePOWERLabel.setBounds(300, 50, 200, 20);
		
		table.getMainPanel().add(leftCardsLabel);
		table.getMainPanel().add(rightCardsLabel);
		table.getMainPanel().add(focusedCardLabel);
		table.getMainPanel().add(cardsOnTablePOWERLabel);
	}
	
	public void setTextOnLabel(JLabel jlabel, String str){
		jlabel.setText(str);
	}
		public String getTextOnLabel(JLabel jlabel){
			return jlabel.getText();		
	}
		
	public JLabel getLeftTEST(){
		return leftCardsLabel;
	}
	public JLabel getRightTEST(){
		return rightCardsLabel;
	}
	
	public JLabel getFocusedCardTEST(){
		return focusedCardLabel;
	}	
	
	public void fillInCardsOnTablePOWERLabel(){
		playerTurn = table.getEngine().getPlayerTurnThread().getPlayerTurn();
		cardsOnTablePOWERLabel.setText("");
		int d=0;
		for (String str: playerTurn.getCardsOnTable_POWER()){
				String div = d==0|d==3|d==6|d==9? "|||" : "";
				cardsOnTablePOWERLabel.setText(cardsOnTablePOWERLabel.getText()+" "+str+div);
				d++;
				}
	}
}
