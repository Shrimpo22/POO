package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scoreboard{
	private List<String> scores = new ArrayList<>();
	private File score;

	public Scoreboard(String name) {
		readScore(name);
	}

	private void readScore(String name) {
		try {
			score = new File(name);
			score.createNewFile();
			Scanner sc = new Scanner(score);
			scores = new ArrayList<>();

			while(sc.hasNextLine())
				scores.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeScore(String toAdd) {
		add(toAdd);	
		try {
			PrintWriter pw = new PrintWriter(score);
			scores.forEach(s->pw.println(s));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private int findInsertionPosition(String n) {
		for (String m : scores)
			if (isBefore(n, m)) {
				return scores.indexOf(m);
			}
		return scores.size();
	}

	private void add(String n) {
		int index = findInsertionPosition(n);
		scores.add(index,n);
		System.out.println("AA");
		if(scores.size() > 5) {
			System.out.println("AB");
			scores.remove(5);
			System.out.println("AB2");
			
		}
		System.out.println("BB");
	}

	private boolean isBefore(String n, String m) {
		String[] strn = n.split("\\|");
		String[] strm = m.split("\\|");
		int scoren = Integer.parseInt(strn[1]) + Integer.parseInt(strn[2]);
		int scorem = Integer.parseInt(strm[1]) + Integer.parseInt(strm[2]);
		return scoren > scorem;
	}


	@Override
	public String toString() {
		String str ="";
		for (String m : scores)
			str += " -> " +m;
		return str;
	}

}
