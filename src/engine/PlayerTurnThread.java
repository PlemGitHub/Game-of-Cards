package engine;

import screen.Table;

public class PlayerTurnThread extends Thread implements Constants{

	private PlayerTurn playerTurn;
	private Engine engine;
	
	public PlayerTurnThread(Engine engine, Table table) {
		this.engine = engine;
		playerTurn = new PlayerTurn(engine, table);
	}
	
	@Override
	public void run() {
		playerTurn.Turn("left", engine.getDeckNumber_1());
		while (playerTurn.getActionsDone() != 3)
			Thread.yield();
		
		playerTurn.Turn("right", engine.getDeckNumber_2());
		while (playerTurn.getActionsDone() != 3)
			Thread.yield();
		
		playerTurn.Turn("left", engine.getDeckNumber_1());
		while (playerTurn.getActionsDone() != 3)
			Thread.yield();
		
		playerTurn.Turn("right", engine.getDeckNumber_2());
		while (playerTurn.getActionsDone() != 3)
			Thread.yield();
	}
	
	public PlayerTurn getPlayerTurn(){
		return this.playerTurn;
	}
}
