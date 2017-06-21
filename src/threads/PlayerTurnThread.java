package threads;

import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;
import screen.Table;

public class PlayerTurnThread extends Thread implements Constants{

	private PlayerTurn playerTurn;
	private Engine engine;
	private Table table;
	private FightThr fightThr = new FightThr(table, playerTurn);
	
	public PlayerTurnThread(Table table, Engine engine) {
		this.engine = engine;
		this.table = table;
		playerTurn = new PlayerTurn(engine, table);
	}
	
	@Override
	public void run() {
		do{
			//============= ’Œƒ œ≈–¬Œ√Œ »√–Œ ¿ =============
			playerTurn.Turn("left", engine.getDeckInNumbers_left());
			checkThreads();
			playerTurn.setMaana_left(playerTurn.getMaana());

			//============= ’Œƒ ¬“Œ–Œ√Œ »√–Œ ¿ =============
			playerTurn.Turn("right", engine.getDeckInNumbers_right());
			playerTurn.setMaana_right(playerTurn.getMaana());
			checkThreads();
		
		} while (true);
	}
	
	public void checkThreads(){
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
		playerTurn.clearMaanaPlus_Label();
		try {
			playerTurn.getRedFlash().join();
			playerTurn.getGreenFlash().join();
				fightThr = new FightThr(table, playerTurn);
				fightThr.start();
				fightThr.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public PlayerTurn getPlayerTurn(){
		return this.playerTurn;
	}
}
