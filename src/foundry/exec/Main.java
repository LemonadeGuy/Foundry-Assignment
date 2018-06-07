package foundry.exec;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import foundry.domain.Block;
import foundry.domain.Constants;
import foundry.domain.SessionItem;

public class Main {
	
	private static ArrayList<SessionItem> sessions;
	public static void main (String[] args){
		sessions = new ArrayList<SessionItem>();
		//String path = "C:\\Users\\NkosanaM\\eclipse-workspace\\Foundry\\src\\test.txt"; //args[1]
		File file = new File(args[0]);
		executeFoundryApplication(file);
	}
	
	private static SessionTimer init (File file){
		 Pattern p = Pattern.compile("[\\d]+[A-Za-z]");
		 Matcher m;
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				int duration = 0;
				if (!line.equals("")) { //get rid of empty strings
					m = p.matcher(line);
					if (m.find())
					{
						duration = Integer.parseInt(line.replaceAll("[\\D]", ""));
						
					} else if (line.endsWith(Constants.LIGHTNING_STRING)){
						duration = 5;
					}
					SessionItem item = new SessionItem(line, duration);
					sessions.add(item);
				}				
			}
			
			scan.close();
			 
		} catch (FileNotFoundException e) {
		
			System.out.println("Cannot find the file @path: " + file.getPath());
		}
		return new SessionTimer(sessions);
	}
	
	private static void executeFoundryApplication(File file) {
		SessionTimer timer = init(file);
		int tracks = timer.calculateNumberOfTracks();
		if (!(tracks == 0)) {
			ArrayList<Block> am = timer.generateMorningSessions();
			ArrayList<Block> pm = timer.generateEveningSessions();
			
			for (int i = 0; i < tracks ; i ++) {
				System.out.println("Track " + (i+1));
				am.get(i).ToString();
				pm.get(i).ToString();
				System.out.println("\n");
			}
		} else {
			throw new Error ("Whoopss... Looks like you didnt meet the session requirements there");
		}
	}
}
