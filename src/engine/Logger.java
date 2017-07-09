package engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import screen.Table;

public class Logger {
	private File file;
	
    private FileWriter out;	//PrintWriter обеспечит возможности записи в файл
	
	public Logger(Table table) {
		String tmp = System.getProperty("java.io.tmpdir");
		file = new File(tmp+"/goc_log.txt");

		try {
			out = new FileWriter(file.getAbsoluteFile(), true);
	        if(!file.exists())	//провер€ем, что если файл не существует то создаем его
				file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logGameOfCardsStarted(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Game of Cards starts!\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logPlayerTurn(String str){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" ========== It's "+str+" turn ==========\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logNewGameStarted(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" New game starts!\r\n");
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
	 * Ћогирует движение карты с позиции на позицию
	 * @param nCard ѕор€дковый номер карты
	 * @param oldP —тара€ позици€
	 * @param newP Ќова€ позици€
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
	 * Ћогирует движение карты вверх/вниз со смещением карты на пути на вторую линию
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
	 * Ћогирует движение карты вправо/влево со сменой местами двух карт
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
	
	public void logReadyToAttack(int nCard, int pos){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nCard+" is ready to attack"
					+ " from position #"+pos+"\r\n");
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
	
	public void closeFile(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Game of Cards closes!\r\n");
			out.append("========================================================================================\r\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}