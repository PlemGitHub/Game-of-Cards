package screen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelDecks extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	
	public PanelDecks() {
		try {  
			img = ImageIO.read(this.getClass().getResourceAsStream("/Cards/Deck.png"));
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
	     super.paintComponent(g);
	     g.drawImage(img, 0, 0, this); 
	 }
}
