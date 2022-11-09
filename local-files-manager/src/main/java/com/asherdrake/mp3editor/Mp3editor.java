package com.asherdrake.mp3editor;

import com.mpatric.mp3agic.*;
import java.util.*;
import java.io.*;

public class Mp3editor {
	private static ArrayList<Mp3File> mp3s = new ArrayList<Mp3File>();
	public static void main(String[] args) throws InvalidDataException, UnsupportedTagException, IOException, NotSupportedException{
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the path to your Mp3 folder: ");
		String folderPath = input.nextLine();
		
		System.out.println();
		
		System.out.print("Please enter the path to where you want your csv file to be created/existing csv file to be edited: ");
		String csvPath = input.nextLine();
		
		System.out.println();
		//String folderPath = "/Users/asheralacar/EditedMp3s";
		//String csvPath = "/Users/asheralacar/Coding Projects/local files csvs/Local Files Manager Directory - Sheet1 (1).csv";
		readFolder(folderPath);
		System.out.println();
		System.out.println("Printing Mp3 Info...");
		printMp3Info();
		System.out.println("Printing done.");
		System.out.println();
		CsvReaderWriter csv = new CsvReaderWriter();
		
		System.out.println("Writing to csv file...");
		csv.writeCsv(csvPath, mp3s);
		System.out.println("Csv file done.");
		System.out.println();
		System.out.println("Please make your desired changes to the csv file.");
		System.out.println("Please type 'Continue' once you are done making changes.");
		String cont = input.nextLine();
		while(!cont.equals("Continue")) {
			System.out.println("Please type 'Continue' once you are done making changes.");
			cont = input.nextLine();
		}

		System.out.println("Please enter the path of the folder you want the new Mp3s to be output. (Leave out the '/' at the end)");
		String mp3Path = input.nextLine();
		System.out.println("Reading the csv file...");
		csv.readCsv(csvPath, mp3Path, mp3s);
		//csv.readCsv("/Users/asheralacar/Coding Projects/local files csvs/LCM-post-edit - Local Files Manager Directory - Sheet1 (1).csv");
		System.out.println("Printing final Mp3 info...");
		printMp3Info();
		System.out.println("Mp3 info printed.");
	}
	
	public static void readFolder(String folderPath) throws InvalidDataException, UnsupportedTagException, IOException { 
		
		File folder = new File(folderPath);
 		System.out.println("Directory: " + folder.getAbsolutePath());
		File[] files = folder.listFiles();
		System.out.print("Mp3s: ");
		System.out.println(files.length);
		for (int i = 0; i < files.length; i++) {
			if(files[i].getAbsolutePath().contains(".DS_Store")) {
				continue;
			}
			String filePath = files[i].getAbsolutePath();
			Mp3File mp3file = new Mp3File(filePath);
			mp3s.add(mp3file);
			if (mp3file.hasId3v1Tag()) {
				ID3v1 tag = mp3file.getId3v1Tag();
				System.out.println(tag.getTitle());
			} else if (mp3file.hasId3v2Tag()) {
				ID3v2 tag = mp3file.getId3v2Tag();
				System.out.println(tag.getTitle());
			}
		}	
	}
	
	public static void printMp3Info() {
		System.out.println("=============");
		for (int i = 0; i < mp3s.size(); i++) {
			Mp3File mp3 = mp3s.get(i);
			ID3v1 tag = null;
			if (mp3.hasId3v1Tag()) {
				tag = mp3.getId3v1Tag();
			} else if (mp3.hasId3v2Tag()) {
				tag = mp3.getId3v2Tag();
			}
			
			System.out.println("Track: " + tag.getTrack());
			System.out.println("Album: " + tag.getAlbum());
			System.out.println("Artist: " + tag.getArtist());	
			
			if (i < mp3s.size() - 1) {
				System.out.println("--------");
			}
		}
		System.out.println("=============");
	}
}
		