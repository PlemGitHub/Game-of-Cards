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
		playerTurn.Turn("left", engine.getDeckInNumbers_left());
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
		
		playerTurn.Turn("right", engine.getDeckInNumbers_right());
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
		
		playerTurn.Turn("left", engine.getDeckInNumbers_left());
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
		
		playerTurn.Turn("right", engine.getDeckInNumbers_right());
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
	}
	
	public PlayerTurn getPlayerTurn(){
		return this.playerTurn;
	}
}
