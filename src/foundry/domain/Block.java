package foundry.domain;

import java.util.ArrayList;

import foundry.domain.Constants.DayTime;

public class Block {
	private ArrayList<SessionItem> sessionList;
	private int startTime , currentTime;
	public Block(ArrayList<SessionItem> sessionList, int startTime, Constants.DayTime time) {
		this.sessionList = sessionList;
		this.startTime = startTime;
		this.currentTime = startTime;
		prepareList(time); 
	}
	
	public String convertTime(){
		int hours = startTime / Constants.DIVISOR_MINUTES;
		int minutes = startTime % Constants.DIVISOR_MINUTES;
		
		String timeString = (String.format("%02d", hours) + ": " + String.format("%02d", minutes));
		if (hours < 12) {
			return timeString + "AM";
		}
		return  timeString + "PM";
	}
	
	
	public void incrementTimeCount(int input) {
		this.startTime += input;
	}
	
	private void prepareList (Constants.DayTime time) {
		if (time == DayTime.AM) {
			sessionList.add(Constants.LUNCH);
		} else if  (time == DayTime.PM){
			sessionList.add(Constants.NETWOKRING);
		}	
	}
	
	public void ToString() {
		for (int i = 0; i < sessionList.size() ; i ++) {
			SessionItem item = sessionList.get(i);
			System.out.println(convertTime() + " " + item.getTitle());
			incrementTimeCount(item.getDuration());
		}
	}
}
