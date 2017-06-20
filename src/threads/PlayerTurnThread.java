package threads;

import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;
import screen.Table;

public class PlayerTurnThread extends Thread implements Constants{

	private PlayerTurn playerTurn;
	private Engine engine;
	
	public PlayerTurnThread(Table table, Engine engine) {
		this.engine = engine;
		playerTurn = new PlayerTurn(engine, table);
	}
	
	@Override
	public void run() {
		do{
			//============= ÕÎÄ ÏÅĞÂÎÃÎ ÈÃĞÎÊÀ =============
			playerTurn.Turn("left", engine.getDeckInNumbers_left());
				checkThreads();
			playerTurn.setMaana_left(playerTurn.getMaana());
			playerTurn.clearMaanaPlus_Label();

			//============= ÕÎÄ ÂÒÎĞÎÃÎ ÈÃĞÎÊÀ =============
			playerTurn.Turn("right", engine.getDeckInNumbers_right());
				checkThreads();
			playerTurn.setMaana_right(playerTurn.getMaana());
			playerTurn.clearMaanaPlus_Label();
		
		} while (true);
	}
	
	public void checkThreads(){
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
		try {
			playerTurn.getRedFlash().join();
			playerTurn.getGreenFlash().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public PlayerTurn getPlayerTurn(){
		return this.playerTurn;
	}
}
