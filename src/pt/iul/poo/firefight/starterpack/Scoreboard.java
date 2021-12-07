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
		FILE_NAME = "level"+lvl+"score.txt";
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
			System.out.println(FILE_NAME);
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








//public class Scoreboard{
//	private List<String> scores = new ArrayList<>();
//	private File score;
//
//	public Scoreboard(String name) {
//		readScore(name);
//	}
//
//	private void readScore(String name) {
//		try {
//			score = new File(name);
//			score.createNewFile();
//			Scanner sc = new Scanner(score);
//			scores = new ArrayList<>();
//
//			while(sc.hasNextLine())
//				scores.add(sc.nextLine());
//			sc.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void writeScore(String toAdd) {
//		add(toAdd);	
//		try {
//			PrintWriter pw = new PrintWriter(score);
//			scores.forEach(s->pw.println(s));
//			pw.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private int findInsertionPosition(String n) {
//		for (String m : scores)
//			if (isBefore(n, m)) {
//				return scores.indexOf(m);
//			}
//		return scores.size();
//	}
//
//	private void add(String n) {
//		int index = findInsertionPosition(n);
//		scores.add(index,n);
//		if(scores.size() > 5) {
//			scores.remove(5);
//		}
//	}
//
//	private boolean isBefore(String n, String m) {
//		String[] strn = n.split("\\|");
//		String[] strm = m.split("\\|");
//		int scoren = Integer.parseInt(strn[1]) + Integer.parseInt(strn[2]);
//		int scorem = Integer.parseInt(strm[1]) + Integer.parseInt(strm[2]);
//		return scoren > scorem;
//	}
//
//
//	@Override
//	public String toString() {
//		String str ="";
//		for (String m : scores)
//			str += " -> " +m;
//		return str;
//	}
//
//}
