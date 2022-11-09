package com.asherdrake.mp3editor;
import java.io.*;
import java.util.ArrayList;
import com.mpatric.mp3agic.*;

public class CsvReaderWriter {
	public void writeCsv(String filePath, ArrayList<Mp3File> mp3s) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath);
			
			fileWriter.append("Track, Album, Artist, File Name, File Path\n");
			
			for (int i = 0; i < mp3s.size(); i++) {
				Mp3File mp3 = mp3s.get(i);
				ID3v1 tag = null;
				if (mp3.hasId3v1Tag()) {
					tag = mp3.getId3v1Tag();			
				} else if (mp3.hasId3v2Tag()) {
					tag = mp3.getId3v2Tag();
				}

				fileWriter.append(tag.getTrack());
				fileWriter.append(",");
				
				fileWriter.append(tag.getAlbum());
				fileWriter.append(",");
				
				fileWriter.append(tag.getArtist());
				fileWriter.append(",");
				
				fileWriter.append(tag.getTitle());
				fileWriter.append(",");
				
				fileWriter.append(mp3.getFilename() + "\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void readCsv(String filePath, String mp3Path, ArrayList<Mp3File> mp3s) {
		BufferedReader reader = null;
		
		try {
			while(!mp3s.isEmpty()) {
				mp3s.remove(0);
			}
			
			String line = "";
			reader = new BufferedReader(new FileReader(filePath));
			reader.readLine();
			
			while ((line = reader.readLine()) != null) {
				String[] tags = line.split(",");
				
				if (tags.length > 0) {
					Mp3File mp3 = new Mp3File(tags[4]);
					
					ID3v1 tag = null;
					if (mp3.hasId3v1Tag()) {
						tag = mp3.getId3v1Tag();			
					} else if (mp3.hasId3v2Tag()) {
						tag = mp3.getId3v2Tag();
					}

					tag.setTrack(tags[0]);
					tag.setAlbum(tags[1]);
					tag.setArtist(tags[2]);

					mp3.save(mp3Path + "/" + tags[3] + ".mp3");
					mp3s.add(mp3);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public static void main(String[] args) {
		writeCsv("/Users/asheralacar/Coding Projects/local files csvs/Local Files Manager Directory - Sheet1 (1).csv");
	}*/
}
 