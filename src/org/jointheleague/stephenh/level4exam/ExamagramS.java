package org.jointheleague.stephenh.level4exam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ExamagramS {
	List<String> anagrams;
	
	public static void main(String[] args) {
		new ExamagramS().run();
	}

	private void run() {
		try {
			System.out.println("Loading Anagrams");
			speak("Loading Anagrams");
			anagrams = loadAnagrams();
			System.out.println("Load Complete");
			speak("The angrams have been loaded");
		} catch (IOException e) {
			System.out.println("ERROR! Closing Program");
			speak("An error has occured in the loading process. This program must close");
			return;
		}
		
		boolean running = true;
		while (running == true) {
			System.out.println();
			speak("Enter the word you would like to find anagrams for!");
			String finder = JOptionPane.showInputDialog("Enter Word");
			if (finder == null) {
				System.out.println("Cancelled");
				speak("Operation cancelled");
				return;
			}
			System.out.println("Finding anagrams for " + finder);
			speak("Thank you! Please wait while I find the anagrams for " + finder);
			List<String> findings = new ArrayList<String>();
			for (String word : anagrams) {
				if (isAnagram(finder, word)) findings.add(word);
			}
			if (findings.isEmpty()) {
				System.out.println("No Anagrams for" + finder);
				speak("There were no anagrams to be found for " + finder);
			} else {
				System.out.println("Anagrams for " + finder);
				speak("Here are the anagrams for " + finder);
				for (String found : findings) {
					System.out.println(found);
					speak(found);
				}
			}
			speak("Would you like to find more anagrams?");
			int repeat = JOptionPane.showConfirmDialog(null, "Find more anagrams?", "Again?", JOptionPane.YES_NO_OPTION);
			running = repeat == JOptionPane.YES_OPTION;
		}
	}

	private List<String> loadAnagrams() throws IOException {
		List<String> anagrams = new ArrayList<String>();
		try (BufferedReader readMe = new BufferedReader(new FileReader("src/org/jointheleague/stephenh/level4exam/Dictionary.txt"))) {
			boolean end = false;
			while (!end) {
				String word = readMe.readLine();
				if (word == null) end = true;
				else anagrams.add(word);
			}
		}
		return anagrams;
	}
	
	public boolean isAnagram(String a, String b) {
		if (a.equals(b)) return false;
		if (a.length() != b.length()) return false;
		char[] aChars = new char[a.length()];
		a.toLowerCase().getChars(0, a.length(), aChars, 0);
		char[] bChars = new char[b.length()];
		b.toLowerCase().getChars(0, b.length(), bChars, 0);
		for (int intA = 0; intA < aChars.length; intA++) {
			boolean match = false;
			for (int intB = 0; intB < bChars.length; intB++) {
				if (aChars[intA] == bChars[intB]) {
					match = true;
					bChars[intB] = ' ';
					break;
				}
			}
			if (!match) return false;
		}
		return true;
	}
	
	private void speak(String words) {
		try {
			Runtime.getRuntime().exec("say " + words).waitFor();
		} catch (Exception e) {
		 	e.printStackTrace();
		}
	}
}
