package engine;

import java.awt.Toolkit;
import java.util.HashMap;

public interface Constants {
	public int N_OF_CARDS = 6;
	public int dA = 5; //����������� ��� ���������� ����� �������
	public int dXY = 7; //�������� ��� ��������� ������� ����
	public int dXY2 = 2*dXY;
	
	public int DISPLAY_RESOLUTION_X = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int DISPLAY_RESOLUTION_Y = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public int MIDDLE_X = DISPLAY_RESOLUTION_X/2;
	public int MIDDLE_Y = DISPLAY_RESOLUTION_Y/2;
	
	public int CARD_WIDTH = 130;					// ������ �����
	public int CARD_HEIGHT = 180;					// ������ �����
	public int DECK_WIDTH = 110;					// ������ ������
	public int DECK_HEIGTH = 164;					// ������ ������
	public int FIELD_WIDTH = CARD_WIDTH+dXY2;		// ������ �������
	public int FIELD_HEIGHT = CARD_HEIGHT+dXY2;		// ������ �������
	
	public int FIELD_TO_DECK_WIDTH = (FIELD_WIDTH-DECK_WIDTH)/2; 	// ������� �� ������� � ������
	public int FIELD_TO_DECK_HEIGHT = (FIELD_HEIGHT-DECK_HEIGTH)/2; // ������� �� ������� � ������
	public int FIELD_TO_CARD_XY = dXY; 							// ������� �� ������� � �����
	public int F_dXY2 = FIELD_WIDTH + dXY2; 						// ������� + ���������� ����� ����
		
	public int X_LEFT_1 = MIDDLE_X - 3*(F_dXY2); 	// �������� ������� ������	
	public int X_LEFT_2 = MIDDLE_X - 2*(F_dXY2);	// ������ ���
	public int X_LEFT_3 = MIDDLE_X - (F_dXY2);		// �������� ���
	public int X_LEFT_0 = MIDDLE_X - 4*(F_dXY2);	// ������
	
	public int X_RIGHT_1 = MIDDLE_X + dXY2 + 2*(F_dXY2);	// �������� ������� ������						
	public int X_RIGHT_2 = MIDDLE_X + dXY2 + (F_dXY2);		// ������ ���
	public int X_RIGHT_3 = MIDDLE_X + dXY2; 				// �������� ���
	public int X_RIGHT_0 = MIDDLE_X + dXY2 + 3*(F_dXY2);	// ������
	
	public int Y1 = MIDDLE_Y-FIELD_HEIGHT*3/2-dXY2;		// Y ������� ����
	public int Y2 = MIDDLE_Y-FIELD_HEIGHT/2;				// Y ������� ����
	public int Y3 = MIDDLE_Y+FIELD_HEIGHT/2+dXY2;				// Y �������� ����
	
	public int DECK_Y = Y1 + FIELD_TO_DECK_HEIGHT;		// ������� ���� ������
	
	public HashMap<Integer, Integer> FIELD_XY_LEFT = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(01, X_LEFT_0); put(02, Y1);
				put(11, X_LEFT_1); put(12, Y1);
				put(21, X_LEFT_1); put(22, Y2);
				put(31, X_LEFT_1); put(32, Y3);
				
				put(41, X_LEFT_2); put(42, Y1);
				put(51, X_LEFT_2); put(52, Y2);
				put(61, X_LEFT_2); put(62, Y3);
				
				put(71, X_LEFT_3); put(72, Y1);
				put(81, X_LEFT_3); put(82, Y2);
				put(91, X_LEFT_3); put(92, Y3);
				}};
				
	public HashMap<Integer, Integer> FIELD_XY_RIGHT = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(01, X_RIGHT_0); put(02, Y1);
				put(11, X_RIGHT_1); put(12, Y1);
				put(21, X_RIGHT_1); put(22, Y2);
				put(31, X_RIGHT_1); put(32, Y3);
				
				put(41, X_RIGHT_2); put(42, Y1);
				put(51, X_RIGHT_2); put(52, Y2);
				put(61, X_RIGHT_2); put(62, Y3);
				
				put(71, X_RIGHT_3); put(72, Y1);
				put(81, X_RIGHT_3); put(82, Y2);
				put(91, X_RIGHT_3); put(92, Y3);
				}};
				
	public HashMap<Integer, Integer> CARD_XY_LEFT = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(11, X_LEFT_1+FIELD_TO_CARD_XY); put(12, Y1+FIELD_TO_CARD_XY);
				put(21, X_LEFT_1+FIELD_TO_CARD_XY); put(22, Y2+FIELD_TO_CARD_XY);
				put(31, X_LEFT_1+FIELD_TO_CARD_XY); put(32, Y3+FIELD_TO_CARD_XY);
				
				put(41, X_LEFT_2+FIELD_TO_CARD_XY); put(42, Y1+FIELD_TO_CARD_XY);
				put(51, X_LEFT_2+FIELD_TO_CARD_XY); put(52, Y2+FIELD_TO_CARD_XY);
				put(61, X_LEFT_2+FIELD_TO_CARD_XY); put(62, Y3+FIELD_TO_CARD_XY);
				
				put(71, X_LEFT_3+FIELD_TO_CARD_XY); put(72, Y1+FIELD_TO_CARD_XY);
				put(81, X_LEFT_3+FIELD_TO_CARD_XY); put(82, Y2+FIELD_TO_CARD_XY);
				put(91, X_LEFT_3+FIELD_TO_CARD_XY); put(92, Y3+FIELD_TO_CARD_XY);
				}};
				
	public HashMap<Integer, Integer> CARD_XY_RIGHT = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(11, X_RIGHT_1+FIELD_TO_CARD_XY); put(12, Y1+FIELD_TO_CARD_XY);
				put(21, X_RIGHT_1+FIELD_TO_CARD_XY); put(22, Y2+FIELD_TO_CARD_XY);
				put(31, X_RIGHT_1+FIELD_TO_CARD_XY); put(32, Y3+FIELD_TO_CARD_XY);
				
				put(41, X_RIGHT_2+FIELD_TO_CARD_XY); put(42, Y1+FIELD_TO_CARD_XY);
				put(51, X_RIGHT_2+FIELD_TO_CARD_XY); put(52, Y2+FIELD_TO_CARD_XY);
				put(61, X_RIGHT_2+FIELD_TO_CARD_XY); put(62, Y3+FIELD_TO_CARD_XY);
				
				put(71, X_RIGHT_3+FIELD_TO_CARD_XY); put(72, Y1+FIELD_TO_CARD_XY);
				put(81, X_RIGHT_3+FIELD_TO_CARD_XY); put(82, Y2+FIELD_TO_CARD_XY);
				put(91, X_RIGHT_3+FIELD_TO_CARD_XY); put(92, Y3+FIELD_TO_CARD_XY);
				}};
		public int X_LEFT_DECK = X_LEFT_0 + FIELD_TO_DECK_WIDTH;	// � ������ ������ ������
		public int X_RIGHT_DECK = X_RIGHT_0 + FIELD_TO_DECK_WIDTH;	// � ������ ������� ������
	}