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
	        if(!file.exists())	//проверяем, что если файл не существует то создаем его
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
			out.append(timeStamp+" Focused card is # "+focusedCard+"\r\n");
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
	
	public void logCardToMaana(int fc, int nCard, int plusMaana, int maana){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nCard+" goes to Maana from position #"+fc+"\r\n");
			out.append(timeStamp+" Card #"+nCard+" refunds "+plusMaana+" Maana. It is "+maana+" Maana now.\r\n");
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
	
	public void logMoveCard(int nCard, int oldP, int newP){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Card #"+nCard+" moves from position #"+oldP+
					" to position #"+newP+" \r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile(){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			out.append(timeStamp+" Game of Cards closed!\r\n");
			out.append("========================================================================================\r\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}