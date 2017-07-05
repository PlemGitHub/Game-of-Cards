package screen;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Constants;
import engine.Engine;
import panels.ImageImport;

public class HelpScreen extends JPanel implements Constants{
	private static final long serialVersionUID = 1L;
	
	private Table table;
	private Engine engine;
	private JPanel sword_img = new ImageImport("iconSword");
	private JPanel axe_img = new ImageImport("iconAxe");
	private JPanel bow_img = new ImageImport("iconBow");
	private JPanel maana_img = new ImageImport("iconMaana");
	private JPanel heal_img = new ImageImport("iconHeal");
		private JLabel sword_lbl = new JLabel();
		private JLabel axe_lbl = new JLabel();
		private JLabel bow_lbl = new JLabel();
		private JLabel maana_lbl = new JLabel();
		private JLabel heal_lbl = new JLabel();
	private JButton closeHelpButton = new JButton("гюйпшрэ");
	
	
	public HelpScreen(Table table, Engine engine) {
		this.table = table;
		
		this.setLayout(null);
		this.setBackground(Color.WHITE);
			closeHelpButton.setBounds(DISPLAY_RESOLUTION_X-150, 0, 100, 50);
			closeHelpButton.setFocusable(false);
			closeHelpButton.addActionListener(engine);
			this.add(closeHelpButton);
			
			setUpImages();
			setUpLabels();
	}
	
	private void setUpLabels() {
		sword_lbl.setBounds(HELP_ICONS_X+HELP_ICONS_SIZE+15, HELP_ICONS_Y1, 400, HELP_ICONS_SIZE);
		sword_lbl.setText("йюпрю оепбни мюмняхр сдюп");
		this.add(sword_lbl);
			axe_lbl.setBounds(HELP_ICONS_X+HELP_ICONS_SIZE+15, HELP_ICONS_Y2, 700, HELP_ICONS_SIZE);
			axe_lbl.setText("<html>еякх йюпрю мюмняхр спнм анкэье, вел гднпнбэе с йюпрш опнрхбмхйю,<BR>"
							+ " рн нярюбьхияъ спнм мюмняхряъ якедсчыеи йюпре</HTML>");
			this.add(axe_lbl);
		bow_lbl.setBounds(HELP_ICONS_X+HELP_ICONS_SIZE+15, HELP_ICONS_Y3, 400, HELP_ICONS_SIZE);
		bow_lbl.setText("йюпрю лнфер мюмняхрэ спнм ян брнпни кхмхх");
		this.add(bow_lbl);
			maana_lbl.setBounds(HELP_ICONS_X+HELP_ICONS_SIZE+15, HELP_ICONS_Y4, 400, HELP_ICONS_SIZE);
			maana_lbl.setText("б йнмже ундю йюпрю цемепхпсер ндмн нвйн лююмш");
			this.add(maana_lbl);
		heal_lbl.setBounds(HELP_ICONS_X+HELP_ICONS_SIZE+15, HELP_ICONS_Y5, 400, HELP_ICONS_SIZE);
		heal_lbl.setText("<HTML>б йнмже ундю йюпрю бняярюмюбкхбюер ндмн нвйн гднпнбэъ <BR>"
						+ "боепедх ярнъыеи йюпре</HTML>");
		this.add(heal_lbl);
	}

	private void setUpImages() {
		sword_img.setBounds(HELP_ICONS_X, HELP_ICONS_Y1, HELP_ICONS_SIZE, HELP_ICONS_SIZE);
		sword_img.setOpaque(false);
		this.add(sword_img);
			axe_img.setBounds(HELP_ICONS_X, HELP_ICONS_Y2, HELP_ICONS_SIZE, HELP_ICONS_SIZE);
			axe_img.setOpaque(false);
			this.add(axe_img);
		bow_img.setBounds(HELP_ICONS_X, HELP_ICONS_Y3, HELP_ICONS_SIZE, HELP_ICONS_SIZE);
		bow_img.setOpaque(false);
		this.add(bow_img);
			maana_img.setBounds(HELP_ICONS_X, HELP_ICONS_Y4, HELP_ICONS_SIZE, HELP_ICONS_SIZE);
			maana_img.setOpaque(false);
			this.add(maana_img);
		heal_img.setBounds(HELP_ICONS_X, HELP_ICONS_Y5, HELP_ICONS_SIZE, HELP_ICONS_SIZE);
		heal_img.setOpaque(false);
		this.add(heal_img);
	}

	public void showHelpScreen(){
		table.getMainFrame().setContentPane(this);
		table.getMainFrame().revalidate();
		table.getMainFrame().repaint();
	}
	
	public void closeHelpScreen(){
		table.getMainFrame().setContentPane(table.getMainPanel());
		table.getMainFrame().revalidate();
		table.getMainFrame().repaint();
	}
	
	public JButton getCloseHelpButton(){
		return closeHelpButton;
	}
}
