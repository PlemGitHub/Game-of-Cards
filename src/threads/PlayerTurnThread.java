package threads;

import java.awt.Component;
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
			table.getMainFrame().addKeyListener(table);
				playerTurn.Turn("left");
			checkThreads();
			
			playerTurn.setMaana_left(playerTurn.getMaana());
			playerTurn.setHpLeft(playerTurn.getHpMy());
			playerTurn.setHpRight(playerTurn.getHpEnemy());

			//============= ’Œƒ ¬“Œ–Œ√Œ »√–Œ ¿ =============
			table.getMainFrame().addKeyListener(table);
			playerTurn.Turn("right");
			checkThreads();
			playerTurn.setMaana_right(playerTurn.getMaana());
			playerTurn.setHpRight(playerTurn.getHpMy());
			playerTurn.setHpLeft(playerTurn.getHpEnemy());
		
		} while (true);
	}
	
	public void checkThreads(){
		while (playerTurn.getActionsDone() != playerTurn.getCardsToBeDrawn())
			Thread.yield();
		
		table.getMainFrame().removeKeyListener(table);
		table.getNewGameButton().removeActionListener(engine);
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
		clearCardsOutOfTable();
		table.getNewGameButton().addActionListener(engine);
	}
	
	private void clearCardsOutOfTable(){
		for (Component c: table.getMainPanel().getComponents()){
			if (c.getX()<0 || c.getX()>DISPLAY_RESOLUTION_X || c.getY()<0)
				table.removeComponentFromTable(c);
		}
	}
	
	public PlayerTurn getPlayerTurn(){
		return this.playerTurn;
	}
		public FightThr getFightThr(){
			return fightThr;
		}
}
