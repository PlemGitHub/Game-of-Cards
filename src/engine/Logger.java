package engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements Constants, CardsValues{
	private File file;
	
    private FileWriter out;	//PrintWriter обеспечит возможности записи в файл
	
	public Logger() {
		String tmp = System.getProperty("java.io.tmpdir");
		file = new File(tmp+"/goc_log.txt");

		try {
			out = new FileWriter(file.getAbsoluteFile(), true);
	        if(!file.exists())	//проверяем, что если файл не существует то создаем его
				file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logGameOfCardsStarted(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" GAME OF CARDS STARTS!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logPlayerTurn(String str){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" =============== It's "+str+" turn ===============\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logNewGameStarted(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append("\r\n"+timeStamp+" NEW GAME STARTS!\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logSetFocusOnCard(int focusedCard){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Focused card is #"+focusedCard+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logSelectOnCard(int nCard){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Select card #"+nCard+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logSetUnselectOnCard(int nCard){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Unselect card #"+nCard+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logDecreaseMaanaForCard(String player, int nCard, int pos, int cardCost, int maana){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+player+" sets up card #"+nCard+" on position #"+pos+" paying "+cardCost+" Maana\r\n");
			out.append(timeStamp+" "+player+" has "+maana+" Maana left\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logActionsDone(int actionsDone){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" ----------Actions done = "+actionsDone+" ----------\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logCardToMaana(int fc, int nCard, int plusMaana, int maana){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nCard+" goes to Maana from position #"+fc+"\r\n");
			out.append(timeStamp+" Card #"+nCard+" refunds "+plusMaana+" Maana. It is "+maana+" Maana now.\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logMaanaIncrement(int maana){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Maana increments by 1. It is "+maana+" Maana now.\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logOutOfMaana(int maana, int nCard){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+maana+" Maana is not enough to place card #"+nCard+" on table\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Логирует движение карты с позиции на позицию
	 * @param nCard Порядковый номер карты
	 * @param oldP Старая позиция
	 * @param newP Новая позиция
	 */
	public void logMoveCard(int nCard, int oldP, int newP){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nCard+" moves from position #"+oldP+
					" to position #"+newP+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Логирует движение карты вверх/вниз со смещением карты на пути на вторую линию
	 * @param nMyCard
	 * @param nSecondCard
	 * @param myOldPos
	 * @param myNewPos
	 */
	public void logMoveCardFromCenter(int nMyCard, int nSecondCard, int myOldPos, int myNewPos){
		int scNewPos = myNewPos-3;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nMyCard+" moves card #"+nSecondCard+
					" to second line from position #"+myNewPos+" to position #"+scNewPos+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Логирует движение карты вправо/влево со сменой местами двух карт
	 * @param nMyCard
	 * @param nSecondCard
	 * @param myOldPos
	 * @param myNewPos
	 */
	public void logMoveWithSwitchCard(int nMyCard, int nSecondCard, int myOldPos, int myNewPos){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nMyCard+" switches with card #"+nSecondCard+
					" from position #"+myOldPos+" to position #"+myNewPos+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logMoveCardToCenter(int nMyCard, int myOldPos, int myNewPos){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nMyCard+" automoves to center from "
					+ "position #"+myOldPos+" to position #"+myNewPos+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logFightStarts(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Fight begins!\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logCardCanDoDamage(String attacker, int nCard, int pos, String power){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+attacker+": card #"+nCard+" can do damage"
					+ " from position #"+pos+" with "+power.toUpperCase()+" power\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logDoDamageToHp(int nCard, int damage, String attacker, String defender, int defHP){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" No more card counters "+attacker+"'s attack!\r\n"); // ПЕРЕДЕЛАТЬ ФРАЗУ
			out.append(timeStamp+" "+attacker+": card #"+nCard+" does "+damage+" damage to "+defender+"\r\n");
			out.append(timeStamp+" "+defender+" has "+defHP+"/"+START_HP+" hp now\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logDoDamageToCard(int nCard, int nEnemyCard, int damage, int defHP, String attacker, String defender){
		int baseEnemyCardHp = HEALTH.get(nEnemyCard);
		if (defHP < 0)
				defHP = 0;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+attacker+": card #"+nCard+" does "+damage+" damage to "+defender+"'s card"
					+ " #"+nEnemyCard+"\r\n");
			out.append(timeStamp+" "+defender+"'s card #"+nEnemyCard+" has "+defHP+"/"+baseEnemyCardHp+" hp now\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logCardIsDead(String player, int nCard, int pos){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+player+": card #"+nCard+" has 0 hp and already DEAD at position #"+pos+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logCardAutomoveToFirstLine(String player, int nCard, int pos){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+player+": card #"+nCard+" automoves from second line to position #"+pos+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logAxeDamageRemaining(int nCard, int damage,String attacker){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+attacker+": card #"+nCard+" has "+damage+" more damage\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logCardGivesMaana(String player, int nCard, int maana){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+player+": card #"+nCard+" increases Maana by 1. "+player+" has "+maana+" Maana now\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logCardHealsAnotherCard(String player, int nCard, int nToBeHealed, int healerPos, int injuredPos, int newHP){
		int baseEnemyCardHp = HEALTH.get(nToBeHealed);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+player+": card #"+nCard+" at position #"+healerPos+" heals 1 hp to card #"
							+nToBeHealed+" at position #"+injuredPos+"\r\n");
			out.append(timeStamp+" "+player+": card #"+nToBeHealed+" has "+newHP+"/"+baseEnemyCardHp+" hp now\r\n");		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logNoHealNeeded(String player, int nCard, int healerPos){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+player+": card #"+nCard+" at position #"+healerPos+" tries to heal someone. No injuries detected!\r\n");	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logFightEnds(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Fight ends!\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logShowHelpScreen(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Help screen opens\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logHideHelpScreen(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Help screen hides\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logSomeoneWinsTheGame(String strToLog){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" "+strToLog+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" GAME OF CARDS CLOSES!\r\n");
			out.append("========================================================================================\r\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}