package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;
import engine.Constants;

public class PanelMainTable extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;

	 @Override
	 protected void paintComponent(Graphics g) {
	     super.paintComponent(g);
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(MIDDLE_X, 0, MIDDLE_X, DISPLAY_RESOLUTION_Y);									// ����� ����������
			g.drawRoundRect(X_LEFT_0, Y1, FIELD_WIDTH, FIELD_HEIGHT, FIELD_WIDTH/dA, FIELD_HEIGHT/dA); 	// ������� ����� ������
			g.drawRoundRect(X_RIGHT_0, Y1, FIELD_WIDTH, FIELD_HEIGHT, FIELD_WIDTH/dA, FIELD_HEIGHT/dA); // ������� ������ ������
			paintCells("left", g);																		// ������� ������ ������
			paintCells("right", g); 
	}
	
	/**
	 * ���� ����� ������������ ������� ������
	 * @param str ����� ��� ������?
	 * @param g
	 */
	private void paintCells(String str, Graphics g){
		HashMap<Integer, Integer> hm = str.equals("left")? FIELD_XY_LEFT:FIELD_XY_RIGHT;
		for (int i=1; i<=9; i++){
				int x = hm.get(i*10+1);
				int y = hm.get(i*10+2);
				g.drawRoundRect(x, y, FIELD_WIDTH, FIELD_HEIGHT, FIELD_WIDTH/dA, FIELD_HEIGHT/dA);
		}
	}
}
