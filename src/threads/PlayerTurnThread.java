package threads;

import java.awt.Component;

import javax.swing.JOptionPane;

import engine.Constants;
import engine.Engine;
import engine.PlayerTurn;
import screen.Table;

public class PlayerTurnThread extends Thread implements Constants{

	private PlayerTurn playerTurn;
	private Engine engine;
	private Table table;
	private FightThr fightThr = new FightThr(table, playerTurn);
	private boolean noWinner=true;
	
	public PlayerTurnThread(Table table, Engine engine) {
		this.engine = engine;
		this.table = table;
		playerTurn = new PlayerTurn(engine, table);
	}
	
	@Override
	public void run() {
		table.getLogger().logNewGameStarted();
		do{
			//============= ХОД ПЕРВОГО ИГРОКА =============
			table.getMainFrame().addKeyListener(table);
			table.getLogger().logPlayerTurn("Player 1's");
				playerTurn.Turn("left");
			checkThreads();
			
			playerTurn.setMaana_left(playerTurn.getMaana());
			playerTurn.setHpLeft(playerTurn.getHpMy());
			playerTurn.setHpRight(playerTurn.getHpEnemy());
			if (checkPlayersHpToFindWinner()) break;

			//============= ХОД ВТОРОГО ИГРОКА =============
			table.getMainFrame().addKeyListener(table);
			table.getLogger().logPlayerTurn("Player 2's");
			table.getLogger().logSetFocusOnCard(1);
			playerTurn.Turn("right");
			checkThreads();
			playerTurn.setMaana_right(playerTurn.getMaana());
			playerTurn.setHpRight(playerTurn.getHpMy());
			playerTurn.setHpLeft(playerTurn.getHpEnemy());
			checkPlayersHpToFindWinner();
		
		} while (noWinner);
	}
	
	private boolean checkPlayersHpToFindWinner() {
		if (playerTurn.getHpLeft()>0 && playerTurn.getHpRight()<=0)
			return showWinner("Победил первый игрок! Начать новую игру?");
		if (playerTurn.getHpLeft()<=0 && playerTurn.getHpRight()>0)
			return showWinner("Победил второй игрок! Начать новую игру?");
		if (playerTurn.getHpLeft()<=0 && playerTurn.getHpRight()<=0)
			return showWinner("Ничья! Начать новую игру?");
		return false;
	}

	private boolean showWinner(String str) {
		int result = JOptionPane.showConfirmDialog(table.getMainFrame(), str, "Конец игры!", JOptionPane.YES_NO_OPTION);
		switch (result) {
		case JOptionPane.YES_OPTION:
			engine.newGame(); 
			break;
		case JOptionPane.NO_OPTION:
			table.windowClosing(null);
		}
		return true;
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
