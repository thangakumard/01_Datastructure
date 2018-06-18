package myInterviews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/********
 * 
 * @author THANGAKUMAR
 * /*
 * Please come up with Class and Data Structure Design for a "metric" system to determine the top song of a
 * band. Two Web Service calls
 
  void play(String bandname, String songname);
  String topSong(String bandname);

  play("Lady Gaga", "Pokerface");
  play("Lady Gaga", "Pokerface");
  play("Lady Gaga", "Alejandro");
  play("Bruno Mars", "Treasure");
  topSong("Lady Gaga") -> "Pokerface"

 */

class songs{
	HashMap<String, Integer> songsMap = new HashMap<String, Integer>();
	TreeMap<Integer, List<String>> playCountMap = new TreeMap<Integer, List<String>>();
}

public class amazon05_playlist {

	HashMap<String, songs> albumMap = new HashMap<String, songs>();
	
	public void play(String album, String songName){
		songs s = albumMap.get(album); //Read Album
		int counter = s.songsMap.get(songName); // Read the song	
		s.songsMap.put(songName, counter+1); // update the song's play counter
		
		//update the play counter treeMap
		//1.Remove from previous counter
		List<String> songList1 = s.playCountMap.get(counter);
		songList1.remove(songName);
		s.playCountMap.put(counter, songList1);
		
		//2.Add to the new counter
		List<String> songList2 = s.playCountMap.getOrDefault(counter+1, new ArrayList<>());
		songList2.add(songName);
		s.playCountMap.put(counter+1, songList2);
	}
	
	public void topSong(String album){
		songs s = albumMap.get(album);
		int counter = s.playCountMap.lastKey();
		List<String> songList1 = s.playCountMap.get(counter);
		Random rand = new Random();
		String songName = songList1.get(rand.nextInt(songList1.size()));
		
		//update the play counter treeMap
		//1.Remove from previous counter
		songList1.remove(songName);
		s.playCountMap.put(counter, songList1);
		
		//2.Add to the new counter
		List<String> songList2 = s.playCountMap.getOrDefault(counter+1, new ArrayList<>());
		songList2.add(songName);
		s.playCountMap.put(counter+1, songList2);
		
		//update the songsMap
		int payCount = s.songsMap.get(songName);
		s.songsMap.put(songName, payCount+1);
	}
	
}

