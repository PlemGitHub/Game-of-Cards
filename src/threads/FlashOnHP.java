package threads;

import java.awt.Font;
import javax.swing.JLabel;
import engine.Constants;

public class FlashOnHP extends Thread implements Constants{
	private JLabel hp_Label;
	
	public FlashOnHP(JLabel hp_Label) {
		this.hp_Label = hp_Label;
	}
	
	@Override
	public void run(){
			hp_Label.setFont(new Font("Segoe Script", Font.ITALIC, BIG_FONT_SIZE));
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			hp_Label.setFont(new Font("Segoe Script", Font.BOLD, NORMAL_FONT_SIZE));
	}
}
