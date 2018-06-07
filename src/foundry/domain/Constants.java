package foundry.domain;

public class Constants {

	public enum DayTime {
	    AM,
	    PM
	}
	
	public final static int DAILY_LITMIT = 360;
	public final static int MORNING_START =  540;
	public final static int AFTERNOON_START = 780;
	public final static int DIVISOR_MINUTES = 60;
	public final static int MORNING_REQUIREMENT = 180;
	public final static int AFTERNOON_REQUIRMENT = 180;
	public final static int AFTERNOON_UPPER_BOUND = 240;
	
	public static SessionItem LUNCH = new SessionItem("LUNCH", 60);
	public static SessionItem NETWOKRING = new SessionItem("NETWORKING", 60);
	public final static String LIGHTNING_STRING = "lightning";
	
	
}
