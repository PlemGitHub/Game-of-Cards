package engine;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import TESTS.Tests;
import panels.ImageImport;
import screen.InterfaceElements;
import screen.Table;
import threads.CardsMovementThr;
import threads.FlashOnMaana_GREEN;
import threads.FlashOnMaana_RED;

public class PlayerTurn implements Constants, CardsValues{

	private Table table;
	private Engine engine;
	private String side;
	private Tests tests;
	private InterfaceElements iel;
	private HashMap<Integer, Integer> my_XY;
	private HashMap<Integer, Integer> enemy_XY;
	private int actionsDone;
	private int focusedCard_N;
	private boolean cardIsSelected = false;
	private ArrayList<Integer> deckInNumbers;
	private ArrayList<Integer> discardPile;
	private int[] cardsOnTable_N;
	private String[] cardsOnTable_POWER;
	private int[] cardsOnTable_REFUND;
	private int[] cardsOnTable_COST;
	private int[] cardsOnTable_HEALTH;
	private int[] cardsOnTable_ATTACK;
	private int cardsToBeDrawn;
	private int startCardN;
	private String startCardPOWER;
	private int startCardREFUND;
	private int startCardCOST;
	private int startCardHealth;
	private int startCardAttack;
	private int hpMy;
	private int hpEnemy;
	private JLabel hpMy_Label;
	private JLabel hpEnemy_Label;
	private int hp_left = START_HP;
	private int hp_right = START_HP+10;
	private int maana;
	private int maana_left = START_MAANA;
	private int maana_right = START_MAANA;
	private JLabel maana_Label;
	private JLabel maanaPlus_Label;
	private int card_to_nothing_X; // Х положения карточки, куда улетают в Маану и убитые с поля
		
		// если не создать new, то может быть null в PlayerTurnThread.class при проверке checkThreads();
		private FlashOnMaana_RED redFlash = new FlashOnMaana_RED(this); 
		private FlashOnMaana_GREEN greenFlash = new FlashOnMaana_GREEN(this);
	
	public PlayerTurn(Engine engine, Table table){
		this.table = table;
		this.engine = engine;
		tests = table.getTests();
	}
	
	public void Turn(String playerSide){
		side = playerSide;
		iel = table.getInterfaceElements();
		JPanel cardPanel;
		int i;
		int x;
		int y;
		deckInNumbers = side.equals("left")? engine.getDeckInNumbers_left() : engine.getDeckInNumbers_right();
		discardPile = side.equals("left")? engine.getDiscardPile_left() : engine.getDiscardPile_right();
		my_XY = side.equals("left")? CARD_XY_LEFT : CARD_XY_RIGHT;
		enemy_XY = side.equals("left")? CARD_XY_RIGHT : CARD_XY_LEFT;
		hpMy = side.equals("left")? hp_left : hp_right;
		hpMy_Label = side.equals("left")? iel.getHp_left_Label() : iel.getHp_right_Label();
		hpEnemy = side.equals("left")? hp_right : hp_left;
		hpEnemy_Label = side.equals("left")? iel.getHp_right_Label() : iel.getHp_left_Label();
		maana = side.equals("left")? maana_left : maana_right;
		maana_Label = side.equals("left")? iel.getMaana_left_Label() : iel.getMaana_right_Label();
		maanaPlus_Label = side.equals("left")? iel.getMaanaPlus_left_Label() : iel.getMaanaPlus_right_Label();
		card_to_nothing_X = side.equals("left")? CARD_TO_NOTHING_X_LEFT : CARD_TO_NOTHING_X_RIGHT;
		
		cardsOnTable_N = side.equals("left")? engine.getCardsOnTable_N_left() : engine.getCardsOnTable_N_right();
			cardsOnTable_POWER = side.equals("left")? engine.getCardsOnTable_POWER_left() : engine.getCardsOnTable_POWER_right();
				cardsOnTable_REFUND = side.equals("left")? engine.getCardsOnTable_REFUND_left() : engine.getCardsOnTable_REFUND_right();
					cardsOnTable_COST = side.equals("left")? engine.getCardsOnTable_COST_left() : engine.getCardsOnTable_COST_right();
						cardsOnTable_HEALTH = side.equals("left")? engine.getCardsOnTable_HEALTH_left() : engine.getCardsOnTable_HEALTH_right();
							cardsOnTable_ATTACK = side.equals("left")? engine.getCardsOnTable_ATTACK_left() : engine.getCardsOnTable_ATTACK_right();
		actionsDone = 0;
		table.getMsc().setEndCardPos(0);	
		tests.getFocusedCardTEST().setText("focusedCard_N="+focusedCard_N);

		
		//============= Проверка на остаток количества карт =============
		if (deckInNumbers.size()<3 && discardPile.size()!=0)
				deckInNumbers.addAll(deckInNumbers.size(), discardPile);
		cardsToBeDrawn = deckInNumbers.size()>3? 3 : deckInNumbers.size();
		int positionToStartDrawCard = deckInNumbers.size()-1;
		
		//============= Сдача cardsToBeDrawn шт. карт =============
		for (i=1; i<=cardsToBeDrawn; i++){
			x = my_XY.get(i*10+1).intValue();
			y = my_XY.get(i*10+2).intValue();
			cardPanel = new ImageImport(Integer.toString(deckInNumbers.get(positionToStartDrawCard-i+1)));
			cardPanel.setBounds(x, y, CARD_WIDTH, CARD_HEIGHT);
			cardPanel.setOpaque(false);		
			table.addCardPanelToMainPanel(cardPanel);
			cardsOnTable_POWER[i] = POWER.get(deckInNumbers.get(positionToStartDrawCard-i+1));
			cardsOnTable_REFUND[i] = REFUND.get(deckInNumbers.get(positionToStartDrawCard-i+1));
			cardsOnTable_COST[i] = COST.get(deckInNumbers.get(positionToStartDrawCard-i+1));
			cardsOnTable_HEALTH[i] = HEALTH.get(deckInNumbers.get(positionToStartDrawCard-i+1));
			cardsOnTable_ATTACK[i] = ATTACK.get(deckInNumbers.get(positionToStartDrawCard-i+1));
			cardsOnTable_N[i] = deckInNumbers.get(positionToStartDrawCard-i+1);
		}
		focusedCard_N = 1;
		setFocusOnCard();
		
		tests.fillInCardsOnTablePOWERLabel();
		
		//============= Удаление из конца колоды cardsToBeDrawn шт. карт =============
		for (i=0; i<cardsToBeDrawn; i++)
			deckInNumbers.remove(positionToStartDrawCard-i);

		//============= Ежеходный инкремент количества мааны =============
		maana++;
		iel.setTextOnLabel(maana_Label, Integer.toString(maana));
		doGreenFlash();
		table.getLogger().logMaanaIncrement(maana);
		table.getLogger().logSetFocusOnCard(1);
		
		table.getMainPanel().repaint();
		
	}
	
	/*
	 * Устанавливают или снимают визуальное выделение при выборе карты
	 */
	public void setUnfocusOnCard(){
		int x = my_XY.get(focusedCard_N*10+1);
		int y = my_XY.get(focusedCard_N*10+2);
		int dX = side.equals("left")? 10: -10;
			Component selectedCard = table.findComponentOnMainPanel(x+10, y);
				selectedCard.setLocation(selectedCard.getX()-dX, selectedCard.getY());
	}
		public void setFocusOnCard(){
			int x = my_XY.get(focusedCard_N*10+1);
			int y = my_XY.get(focusedCard_N*10+2);
			int dX = side.equals("left")? 10: -10;
				Component selectedCard = table.findComponentOnMainPanel(x+10, y);
					selectedCard.setLocation(selectedCard.getX()+dX, selectedCard.getY());
				iel.setTextOnLabel(maanaPlus_Label, "+"+Integer.toString(cardsOnTable_REFUND[focusedCard_N]));
		}
			public void setFocusAfterAction(){
				for (int i = 1; i <= 3; i++)
					if (!cardsOnTable_POWER[i].equals("n")){
						setFocusedCard_N(i);
						setFocusOnCard();
						table.getLogger().logSetFocusOnCard(i);
						break;
					}
			}
	/*
	 * Устанавливают или снимают выбор карты для дальнейшего действия
	 */
	public void setSelectOnCard(){
		int x = my_XY.get(focusedCard_N*10+1);
		int y = my_XY.get(focusedCard_N*10+2);
			Component selectedCardPanel = table.findComponentOnMainPanel(x+10, y);
//			if (selectedCardPanel != table.getMainPanel()){
			selectedCardPanel.setLocation(selectedCardPanel.getX(), selectedCardPanel.getY()-10);
		cardIsSelected = true;
		startCardN = cardsOnTable_N[focusedCard_N];
		startCardPOWER = cardsOnTable_POWER[focusedCard_N];
		startCardCOST = cardsOnTable_COST[focusedCard_N];
		startCardREFUND = cardsOnTable_REFUND[focusedCard_N];
		startCardHealth = cardsOnTable_HEALTH[focusedCard_N];
		startCardAttack = cardsOnTable_ATTACK[focusedCard_N];
			table.getLogger().logSelectOnCard(startCardN);
//			}
	}
	public void setUnselectOnCard(){
		int x = my_XY.get(focusedCard_N*10+1);
		int y = my_XY.get(focusedCard_N*10+2);
			Component selectedCardPanel = table.findComponentOnMainPanel(x+10, y);
				selectedCardPanel.setLocation(selectedCardPanel.getX(), selectedCardPanel.getY()+10);
				cardIsSelected = false;
		table.getLogger().logSetUnselectOnCard(startCardN);
	}
	
	public void cardToMaana(){
		int x = my_XY.get(focusedCard_N*10+1);
		int y = my_XY.get(focusedCard_N*10+2);
		int newX = card_to_nothing_X;
		int newY = CARD_TO_NOTHING_Y;
			CardsMovementThr cmt = new CardsMovementThr(table, x, y, newX, newY, 10);
			cmt.start();

		cardIsSelected = false;
		
		cardsOnTable_POWER[focusedCard_N] = "n"; // нужно для setFocusAfterAction()
		
		//============= Сброшенная карта замешивается в колоду сброса =============
		Random rnd = new Random();
			int n_to_DiscardPile = rnd.nextInt(1+discardPile.size());
		discardPile.add(n_to_DiscardPile, cardsOnTable_N[focusedCard_N]);
		
		tests.fillInCardsOnTablePOWERLabel();
		int refund = cardsOnTable_REFUND[focusedCard_N];
		maana += refund;
		iel.setTextOnLabel(maana_Label, Integer.toString(maana));
		doGreenFlash();
		actionsDone++;
			
			//ДЛЯ НАГЛЯДНОСТИ ОТЛАДКИ
			cardsOnTable_ATTACK[focusedCard_N]=0;
			cardsOnTable_HEALTH[focusedCard_N]=0;
			cardsOnTable_N[focusedCard_N]=0;
			cardsOnTable_COST[focusedCard_N]=0;
			cardsOnTable_REFUND[focusedCard_N]=0;
		table.getLogger().logCardToMaana(focusedCard_N, startCardN, refund, maana);
		table.getLogger().logActionsDone(actionsDone);
	}
	
	public void clearMaanaPlus_Label(){
		iel.setTextOnLabel(maanaPlus_Label, "");
	}
	
	public void decreaseMaanaForCard(){
		maana -= startCardCOST;
		iel.setTextOnLabel(maana_Label, Integer.toString(maana));
		doRedFlash();
	}
	
	public void setCardsOnTable_POWER(int i, String str){
		cardsOnTable_POWER[i] = str;
	}	
	public String[] getCardsOnTable_POWER(){
		return cardsOnTable_POWER;
	}
	public void setEnemyCardsOnTable_POWER(int i, String n){
		String[] cards = cardsOnTable_POWER==engine.getCardsOnTable_POWER_left()? 
						engine.getCardsOnTable_POWER_right() : engine.getCardsOnTable_POWER_left();
		cards[i] = n;
	}	
	public String[] getEnemyCardsOnTable_POWER(){
		String[] cards = cardsOnTable_POWER==engine.getCardsOnTable_POWER_left()? 
				engine.getCardsOnTable_POWER_right() : engine.getCardsOnTable_POWER_left();
		return cards;
	}
		public void setCardsOnTable_N(int i, int n){
			cardsOnTable_N[i] = n;
		}	
		public int[] getCardsOnTable_N(){
			return cardsOnTable_N;
		}
		public void setEnemyCardsOnTable_N(int i, int n){
			int[] cards = cardsOnTable_N==engine.getCardsOnTable_N_left()? 
							engine.getCardsOnTable_N_right() : engine.getCardsOnTable_N_left();
			cards[i] = n;
		}	
		public int[] getEnemyCardsOnTable_N(){
			int[] cards = cardsOnTable_N==engine.getCardsOnTable_N_left()? 
					engine.getCardsOnTable_N_right() : engine.getCardsOnTable_N_left();
			return cards;
		}
			public void setCardsOnTable_HEALTH(int i, int n){
				cardsOnTable_HEALTH[i] = n;
			}	
			public int[] getCardsOnTable_HEALTH(){
				return cardsOnTable_HEALTH;
			}
			public void setEnemyCardsOnTable_HEALTH(int i, int n){
				int[] cards = cardsOnTable_HEALTH==engine.getCardsOnTable_HEALTH_left()? 
								engine.getCardsOnTable_HEALTH_right() : engine.getCardsOnTable_HEALTH_left();
				cards[i] = n;
			}	
			public int[] getEnemyCardsOnTable_HEALTH(){
				int[] cards = cardsOnTable_HEALTH==engine.getCardsOnTable_HEALTH_left()? 
								engine.getCardsOnTable_HEALTH_right() : engine.getCardsOnTable_HEALTH_left();
				return cards;
			}
				public void setCardsOnTable_ATTACK(int i, int n){
					cardsOnTable_ATTACK[i] = n;
				}	
				public int[] getCardsOnTable_ATTACK(){
					return cardsOnTable_ATTACK;
				}
				public void setEnemyCardsOnTable_ATTACK(int i, int n){
					int[] cards = cardsOnTable_ATTACK==engine.getCardsOnTable_ATTACK_left()? 
									engine.getCardsOnTable_ATTACK_right() : engine.getCardsOnTable_ATTACK_left();
					cards[i] = n;
				}	
				public int[] getEnemyCardsOnTable_ATTACK(){
					int[] cards = cardsOnTable_ATTACK==engine.getCardsOnTable_ATTACK_left()? 
									engine.getCardsOnTable_ATTACK_right() : engine.getCardsOnTable_ATTACK_left();
					return cards;
				}
	public int getStartCardN(){
		return startCardN;
	}			
		public String getStartCardPOWER(){
			return startCardPOWER;
		}
			public int getStartCardCOST(){
				return startCardCOST;
			}
				public int getStartCardREFUND(){
					return startCardREFUND;
				}			
					public int getStartCardHEALTH(){
						return startCardHealth;
					}
						public int getStartCardATTACK(){
							return startCardAttack;
						}	

	public int[] getCardsOnTable_COST(){
		return cardsOnTable_COST;
	}
	
	public void setEngine(Engine engine){
		this.engine = engine;
	}
	
	
	public void setCardIsSelected(boolean cardIsSelected){
		this.cardIsSelected = cardIsSelected;
	}
		public boolean getCardIsSelected(){
			return cardIsSelected;
	}
	
	public String getSide(){
		return side;
	}
	
	public void setFocusedCard_N(int focusedCard_N){
		this.focusedCard_N = focusedCard_N;
	}
		public int getFocusedCard_N(){
			return focusedCard_N;
		}
		
	public void setActionsDone(int actionsDone){
		this.actionsDone = actionsDone;
	}	
		public int getActionsDone(){
			return actionsDone;
		}
	
	public int getCardsToBeDrawn(){
		return cardsToBeDrawn;
	}
	
	public HashMap<Integer, Integer> getMy_XY (){
		return my_XY;
	}
		public HashMap<Integer, Integer> getEnemy_XY (){
			return enemy_XY;
		}
	
	public int getCardToNothingX(){
		return card_to_nothing_X;
	}
	
	public void setHpMy(int hpMy){
		this.hpMy = hpMy;
	}
	public void setHpEnemy(int hpEnemy){
		this.hpEnemy = hpEnemy;
	}
		public int getHpMy(){
			return hpMy;
		}
		public int getHpEnemy(){
			return hpEnemy;
		}
			public int getHpLeft(){
				return hp_left;
			}
			public void setHpLeft(int hp_left){
				this.hp_left = hp_left;
			}
				public int getHpRight(){
					return hp_right;
				}
				public void setHpRight(int hp_right){
					this.hp_right = hp_right;
				}
	public JLabel getHpMy_Label(){
		return hpMy_Label;
	}
		public JLabel getHpEnemy_Label(){
			return hpEnemy_Label;
		}
	public void setMaana(int maana){
		this.maana = maana;
	}
		public int getMaana(){
			return maana;
		}
			public void setMaana_left(int maana){
				maana_left = maana;
			}
				public void setMaana_right(int maana){
					maana_right = maana;
				}
	public JLabel getMaana_Label(){
		return maana_Label;
	}
		public JLabel getMaanaPlus_Label(){
			return maanaPlus_Label;
		}
	
	public void doRedFlash(){
		redFlash = new FlashOnMaana_RED(this);
		redFlash.start();
	}
		public FlashOnMaana_RED getRedFlash(){
			return redFlash;
		}
		
	public void doGreenFlash(){
		greenFlash = new FlashOnMaana_GREEN(this);
		greenFlash.start();
	}
		public FlashOnMaana_GREEN getGreenFlash(){
			return greenFlash;
		}
}
