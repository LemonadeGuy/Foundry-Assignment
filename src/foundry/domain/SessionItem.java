package foundry.domain;

public class SessionItem {
	private String talkTitle;
	private int duration; 
	
	public SessionItem(String title, int duration) {
		this.duration = duration;
		this.talkTitle = title;
	}
	
	//get and format agenda 
	public String getTitle() {
		return talkTitle.substring(0,getIndex(talkTitle, duration)).trim();
	}
	
	//format duration
	public int getDuration() {
		return duration;
	}
	
	//used for formatting string object
	private int getIndex(String title, int number) {
		String n = "" + number;
		
		int index = title.indexOf(n);
		if (index == -1){
			return title.length();
		}
		return index;
	}
	
}
