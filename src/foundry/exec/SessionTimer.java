package foundry.exec;

import java.util.ArrayList;

import foundry.domain.*;
import foundry.domain.Constants.DayTime;

public class SessionTimer {

	private int tracks;; // 4 x 60 @ PM && 3 x 120

	private ArrayList<SessionItem> allSessions, workingSessions;

	// initialize items
	public SessionTimer(ArrayList<SessionItem> allSessions) {
		workingSessions = new ArrayList<SessionItem>();
		this.allSessions = allSessions;
		this.workingSessions.addAll(allSessions);
		this.tracks = calculateNumberOfTracks();
	}

	// get the total time of all sessions
	public int getTotalTime() {
		int x = 0;
		for (int i = 0; i < allSessions.size(); i++) {
			x += allSessions.get(i).getDuration();
		}
		return x;
	}

	public int calculateNumberOfTracks() {
		int totalTime = getTotalTime(); //There's no inputs 
		if (totalTime == 0) {
			return totalTime;
		}

		// spec says that each session will have a morning and an afternoon so do we
		// dont need to worry about the afternoon ending at >2PM -__- <4PM

		/*
		 * } else if (total == 0 && getTotalTime() > 0) { return 1; } else if (total > 0
		 * && totalTime % Constants.DAILY_LITMIT > 0) { return total + 1; //integer
		 * division gives you one if > 360 but also }
		 */
		return getTotalTime() / Constants.DAILY_LITMIT;
	}
	/**
	 * Generates the Block Items for the morning session enforcing the Must 
	 * start at 9am and end @ 12pm rule
	 * @return ArrayList<Block>
	 */
	public ArrayList<Block> generateMorningSessions() {
		int total = 0, count = 0; // 3 hours

		ArrayList<SessionItem> sessions = new ArrayList<SessionItem>();
		ArrayList<Block> morning = new ArrayList<Block>(); // create the necessary blocks

		while (count < tracks) {
			for (int i = 0; i < workingSessions.size(); i++) { 
				sessions = new ArrayList<SessionItem>();
				int start = workingSessions.get(i).getDuration(); 
				sessions.add(workingSessions.get(i)); // :(
				total = start;
				//TODO consider using a binary search to update running total
				for (int j = i + 1; j < workingSessions.size(); j++) { 
					SessionItem item = workingSessions.get(j);
					int d = item.getDuration();
					if (total + d <= Constants.MORNING_REQUIREMENT) {
						total += d;
						sessions.add(item);
						if (total == Constants.MORNING_REQUIREMENT) { // enforce size limit here
							workingSessions.removeAll(sessions);
							Block morningBlock = new Block(sessions, Constants.MORNING_START, DayTime.AM);
							morning.add(morningBlock);
							count++;
							break;
						}
					}
				}
			}
		}
		return morning;
	}
	
	/** 
	 * Generates the block items for the evening session
	 * This method will always execute post the morning schedule
	 * @return
	 */

	public ArrayList<Block> generateEveningSessions() {
		int total = 0, count = 0; // 3 hours
		ArrayList<SessionItem> sessions = new ArrayList<SessionItem>();
		ArrayList<Block> afternoon = new ArrayList<Block>();
		// get the number of tracks

		while (count < tracks) {
			System.out.println("Starting with " + workingSessions.size());
			for (int i = 0; i < workingSessions.size(); i++) { // iterate and
				sessions = new ArrayList<SessionItem>();
				sessions.add(workingSessions.get(i));
				int start = workingSessions.get(i).getDuration();
				total = start;
				for (int j = i + 1; j < workingSessions.size(); j++) {
					SessionItem item = workingSessions.get(j);
					int d = item.getDuration();
					if ((total + d <= Constants.AFTERNOON_UPPER_BOUND)) {
						total += d;
						sessions.add(item);
						if (total >= Constants.AFTERNOON_REQUIRMENT && total <= Constants.AFTERNOON_UPPER_BOUND) {
							workingSessions.removeAll(sessions); // whack them all from list item
							Block afternoonBlock = new Block(sessions, Constants.AFTERNOON_START, DayTime.PM);
							afternoon.add(afternoonBlock);
							count++;
							break;
						}
					}

				}
			}
			// System.out.println("Remaining items " + workingSessions.size());
		}
		return afternoon;
	}

}
