package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Scoreboard{
	PriorityQueue<Score> scores;
	private final String FILE_NAME;


	public Scoreboard(int lvl) {
		FILE_NAME = "scoreboard/level"+lvl+"score.txt";
		scores = new PriorityQueue<>();
		readScore();
	}
	
	public void add(String userScore) {
		scores.add(new Score(userScore));
		writeScore();
		
	}
	

	private void readScore() {
		try {
			File scoreFile = new File(FILE_NAME);
			scoreFile.createNewFile();
			Scanner sc = new Scanner(scoreFile);
			while(sc.hasNextLine()) {
				scores.add(new Score(sc.nextLine()));

			}
			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeScore() {
		try {
			PrintWriter pw = new PrintWriter(FILE_NAME);
			
			int count = 0;
			while(scores.size() > 0 && count < 5) {
				pw.println(scores.poll());
				count ++;
			}
			
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private class Score implements Comparable<Score>{
		private String username;
		private int LevelPoints;
		private int PersonalPoints;


		public Score(String string) {
			String[] chopped = string.split("\\|");
			username = chopped[0];
			LevelPoints = Integer.parseInt(chopped[1]);
			PersonalPoints = Integer.parseInt(chopped[2]);

		}

		public int getTotalScore() {
			return LevelPoints+PersonalPoints;
		}

		@Override
		public String toString() {
			return username+"|"+LevelPoints+"|"+PersonalPoints;
		}

		@Override
		public int compareTo(Score other) {
			return other.getTotalScore() - getTotalScore();
		}
	}


}