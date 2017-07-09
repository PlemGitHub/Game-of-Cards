package engine;

import java.awt.Toolkit;
import java.util.HashMap;

public interface Constants {
	public int N_OF_CARDS = 60;
	public int START_HP = 30;
	public int START_MAANA = 30;
	public int BIG_FONT_SIZE = 34;
	public int NORMAL_FONT_SIZE = 30;
	public int CARDS_MOVEMENT_TIME_MS = 100;
	public int CARDS_MOVEMENT_DIV = 15;
	public int CARDS_SHIFT_DELAY = 25;
	public int dA = 5; //коэффициент для скругления углов обводки
	public int dXY = 7; //смещение для отрисовки обводки карт
	public int dXY2 = 2*dXY;
	
	public int DISPLAY_RESOLUTION_X = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int DISPLAY_RESOLUTION_Y = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public int MIDDLE_X = DISPLAY_RESOLUTION_X/2;
	public int MIDDLE_Y = DISPLAY_RESOLUTION_Y/2;
	
	public int CARD_WIDTH = 130;					// ширина карты
	public int CARD_HEIGHT = 180;					// высота карты
	public int DECK_WIDTH = 110;					// ширина колоды
	public int DECK_HEIGTH = 164;					// высота колоды
	public int FIELD_WIDTH = CARD_WIDTH+dXY2;		// ширина обводки
	public int FIELD_HEIGHT = CARD_HEIGHT+dXY2;		// высота обводки
	public int HP_MAANA_WIDTH = 50;
	public int HP_MAANA_HEIGHT = 50;
	public int HELP_ICONS_SIZE = 55;
	public int HELP_ICONS_X = DISPLAY_RESOLUTION_X/2-300;
	public int HELP_ICONS_Y1 = DISPLAY_RESOLUTION_Y/2-HELP_ICONS_SIZE*9/2;
	public int HELP_ICONS_Y2 = DISPLAY_RESOLUTION_Y/2-HELP_ICONS_SIZE*5/2;
	public int HELP_ICONS_Y3 = DISPLAY_RESOLUTION_Y/2-HELP_ICONS_SIZE*1/2;
	public int HELP_ICONS_Y4 = DISPLAY_RESOLUTION_Y/2+HELP_ICONS_SIZE*3/2;
	public int HELP_ICONS_Y5 = DISPLAY_RESOLUTION_Y/2+HELP_ICONS_SIZE*7/2;
	
	public int FIELD_TO_DECK_WIDTH = (FIELD_WIDTH-DECK_WIDTH)/2; 	// переход от Обводки к Колоде
	public int FIELD_TO_DECK_HEIGHT = (FIELD_HEIGHT-DECK_HEIGTH)/2; // переход от Обводки к Колоде
	public int FIELD_TO_CARD_XY = dXY; 							// переход от Обводки к Карте
	public int F_dXY2 = FIELD_WIDTH + dXY2; 						// Обводка + промежуток между ними

	public int X_LEFT_0 = MIDDLE_X - 4*(F_dXY2);	// колода
	public int X_LEFT_1 = MIDDLE_X - 3*(F_dXY2); 	// розыгрыш первого игрока	
	public int X_LEFT_2 = MIDDLE_X - 2*(F_dXY2);	// задний ряд
	public int X_LEFT_3 = MIDDLE_X - (F_dXY2);		// передний ряд

	public int X_RIGHT_0 = MIDDLE_X + dXY2 + 3*(F_dXY2);	// колода
	public int X_RIGHT_1 = MIDDLE_X + dXY2 + 2*(F_dXY2);	// розыгрыш второго игрока						
	public int X_RIGHT_2 = MIDDLE_X + dXY2 + (F_dXY2);		// задний ряд
	public int X_RIGHT_3 = MIDDLE_X + dXY2; 				// передний ряд
	
	public int Y1 = MIDDLE_Y-FIELD_HEIGHT*3/2-dXY2;		// Y первого ряда
	public int Y2 = MIDDLE_Y-FIELD_HEIGHT/2;				// Y второго ряда
	public int Y3 = MIDDLE_Y+FIELD_HEIGHT/2+dXY2;				// Y третьего ряда
	
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
				
		public int DECK_X_LEFT = X_LEFT_0 + FIELD_TO_DECK_WIDTH;	// Х колоды левого игрока
		public int DECK_X_RIGHT = X_RIGHT_0 + FIELD_TO_DECK_WIDTH;	// Х колоды правого игрока
		public int DECK_Y = Y1 + FIELD_TO_DECK_HEIGHT;				// верхний край колоды
		
		public int CARD_TO_NOTHING_X_LEFT = -300-CARD_WIDTH;
		public int CARD_TO_NOTHING_X_RIGHT = DISPLAY_RESOLUTION_X+300;
		public int CARD_TO_NOTHING_Y = Y2;
}