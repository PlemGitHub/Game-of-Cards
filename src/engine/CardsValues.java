package engine;

import java.util.HashMap;

public interface CardsValues {
	
	public HashMap<Integer, Integer> COST = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 7); put(2, 5); put(3, 4); put(4, 8); put(15, 9);
				put(6, 8); put(7, 6); put(8, 4); put(9, 7); put(10, 0);
				}};
				
	public HashMap<Integer, Integer> REFUND = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 3); put(2, 2); put(3, 2); put(4, 3); put(15, 4);
				put(6, 3); put(7, 3); put(8, 2); put(9, 3); put(10, 1);
				}};
				
	public HashMap<Integer, Integer> ATTACK = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 5); put(2, 2); put(3, 3); put(4, 5); put(15, 9);
				put(6, 7); put(7, 6); put(8, 2); put(9, 5); put(10, 0);
				}};
	
	public HashMap<Integer, Integer> HEALTH = new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
				put(1, 10); put(2, 8); put(3, 8); put(4, 9); put(15, 9);
				put(6, 8); put(7, 7); put(8, 3); put(9, 7); put(10, 3);
				}};
				
	public HashMap<Integer, String> POWER = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 1L;
		{
			put(1, "r"); put(2, "h"); put(3, " "); put(4, "a"); put(15, " ");
			put(6, "s"); put(7, " "); put(8, "r"); put(9, "a"); put(10, "m");				
				}};
}
