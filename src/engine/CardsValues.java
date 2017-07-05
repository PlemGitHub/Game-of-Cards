package engine;

import java.util.HashMap;

public interface CardsValues {				
	public HashMap<Integer, String> POWER = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 1L;
		{
			put(1, "r"); put(2, "h"); put(3, "y"); put(4, "a"); put(5, "y");
			put(6, "s"); put(7, "y"); put(8, "r"); put(9, "a"); put(10, "m");	
			put(11, "m"); put(12, "a"); put(13, "r"); put(14, "m"); put(15, "y");
			put(16, "s"); put(17, "a"); put(18, "r"); put(19, "m"); put(20, "y");	
			put(21, "m"); put(22, "r"); put(23, "a"); put(24, "r"); put(25, "r");
			put(26, "r"); put(27, "a"); put(28, "s"); put(29, "s"); put(30, "m");
			put(31, "s"); put(32, "r"); put(33, "r"); put(34, "a"); put(35, "a");
			put(36, "a"); put(37, "r"); put(38, "r"); put(39, "a"); put(40, "a");
			put(41, "m"); put(42, "m"); put(43, "s"); put(44, "a"); put(45, "s");
			put(46, "y"); put(47, "y"); put(48, "y"); put(49, "r"); put(50, "y");
			put(51, "y"); put(52, "y"); put(53, "a"); put(54, "r"); put(55, "s");
			put(56, "a"); put(57, "r"); put(58, "y"); put(59, "y"); put(60, "s");
				}};
				
	public HashMap<Integer, Integer> COST = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 7); put(2, 5); put(3, 4); put(4, 8); put(5, 9);
				put(6, 8); put(7, 6); put(8, 4); put(9, 7); put(10, 0);
				put(11, 3); put(12, 9); put(13, 6); put(14, 2); put(15, 6);
				put(16, 1); put(17, 1); put(18, 1); put(19, 2); put(20, 2);	
				put(21, 0); put(22, 5); put(23, 7); put(24, 6); put(25, 6);
				put(26, 9); put(27, 7); put(28, 8); put(29, 9); put(30, 4);
				put(31, 7); put(32, 7); put(33, 7); put(34, 6); put(35, 4);
				put(36, 10); put(37, 7); put(38, 2); put(39, 4); put(40, 3);
				put(41, 1); put(42, 0); put(43, 4); put(44, 5); put(45, 4);
				put(46, 1); put(47, 2); put(48, 2); put(49, 4); put(50, 3);
				put(51, 4); put(52, 1); put(53, 3); put(54, 2); put(55, 2);
				put(56, 4); put(57, 4); put(58, 3); put(59, 3); put(60, 5);
				}};
								
	public HashMap<Integer, Integer> REFUND = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 3); put(2, 2); put(3, 2); put(4, 3); put(5, 4);
				put(6, 3); put(7, 3); put(8, 2); put(9, 3); put(10, 1);
				put(11, 2); put(12, 4); put(13, 3); put(14, 1); put(15, 3);
				put(16, 1); put(17, 1); put(18, 1); put(19, 1); put(20, 1);	
				put(21, 1); put(22, 2); put(23, 3); put(24, 3); put(25, 3);
				put(26, 4); put(27, 3); put(28, 3); put(29, 4); put(30, 2);
				put(31, 3); put(32, 3); put(33, 3); put(34, 3); put(35, 2);
				put(36, 4); put(37, 3); put(38, 1); put(39, 2); put(40, 2);
				put(41, 1); put(42, 1); put(43, 2); put(44, 2); put(45, 2);
				put(46, 1); put(47, 1); put(48, 1); put(49, 2); put(50, 2);
				put(51, 2); put(52, 1); put(53, 2); put(54, 1); put(55, 1);
				put(56, 2); put(57, 2); put(58, 2); put(59, 2); put(60, 2);
				}};		
				
	public HashMap<Integer, Integer> ATTACK = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 5); put(2, 2); put(3, 3); put(4, 5); put(5, 9);
				put(6, 7); put(7, 6); put(8, 2); put(9, 5); put(10, 0);
				put(11, 0); put(12, 12); put(13, 5); put(14, 0); put(15, 5);
				put(16, 0); put(17, 0); put(18, 0); put(19, 1); put(20, 2);	
				put(21, 1); put(22, 4); put(23, 5); put(24, 4); put(25, 4);
				put(26, 10); put(27, 5); put(28, 6); put(29, 8); put(30, 5);
				put(31, 5); put(32, 5); put(33, 5); put(34, 6); put(35, 3);
				put(36, 9); put(37, 7); put(38, 1); put(39, 2); put(40, 3);
				put(41, 0); put(42, 0); put(43, 3); put(44, 4); put(45, 2);
				put(46, 1); put(47, 2); put(48, 3); put(49, 4); put(50, 2);
				put(51, 1); put(52, 1); put(53, 2); put(54, 1); put(55, 1);
				put(56, 3); put(57, 3); put(58, 0); put(59, 1); put(60, 5);
				}};
	
	public HashMap<Integer, Integer> HEALTH = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 10); put(2, 8); put(3, 8); put(4, 9); put(5, 9);
				put(6, 8); put(7, 7); put(8, 3); put(9, 7); put(10, 3);
				put(11, 12); put(12, 2); put(13, 3); put(14, 10); put(15, 7);
				put(16, 2); put(17, 2); put(18, 2); put(19, 6); put(20, 2);	
				put(21, 1); put(22, 5); put(23, 8); put(24, 6); put(25, 6);
				put(26, 3); put(27, 8); put(28, 10); put(29, 7); put(30, 5);
				put(31, 8); put(32, 7); put(33, 7); put(34, 5); put(35, 4);
				put(36, 9); put(37, 7); put(38, 2); put(39, 3); put(40, 2);
				put(41, 6); put(42, 4); put(43, 3); put(44, 5); put(45, 5);
				put(46, 1); put(47, 1); put(48, 1); put(49, 2); put(50, 5);
				put(51, 10); put(52, 2); put(53, 2); put(54, 2); put(55, 2);
				put(56, 3); put(57, 3); put(58, 11); put(59, 8); put(60, 1);
				}};

}
