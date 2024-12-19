package myInterviews;

import org.junit.Test;
import org.junit.internal.runners.TestMethod;
import org.junit.runners.model.TestClass;
import org.testng.remote.strprotocol.TestMessage;

import java.util.*;

/***
 * Write a method to get Kth winner from a Game.
 * The method will take 3 arguments.
 *      1st argument is the list of players
 *      2nd argument is the corresponding players list of scores Lis<List<Integer>>.
 *      3rd argument is k
 * Based on their total score, the top score person will be announced as the winner.
 * Return the Kth Winner
 */
public class Meta_KthWinner {

    @Test
    public void getKthWinner(){
        HashMap<String, List<Integer>> mapPlayerScore = new HashMap<>();
        List<String> lstPlayers = new ArrayList<>();
        lstPlayers.add("Player1");
        lstPlayers.add("Player2");
        lstPlayers.add("Player3");

        List<Integer> scores1 = new ArrayList<>();
        scores1.add(10);
        scores1.add(10);
        mapPlayerScore.put("Player1", scores1);

        List<Integer> scores2 = new ArrayList<>();
        scores2.add(10);
        scores2.add(40);
        mapPlayerScore.put("Player2", scores2);

        List<Integer> scores3 = new ArrayList<>();
        scores3.add(10);
        scores3.add(20);
        mapPlayerScore.put("Player3", scores3);

        List<Integer> scores4 = new ArrayList<>();
        scores4.add(10);
        scores4.add(20);
        mapPlayerScore.put("Player4", scores4);

        List<String> lstWinners = getKthWinner(mapPlayerScore, 2);

        for(String winner: lstWinners){
            System.out.println(winner);
        }
    }

    public List<String> getKthWinner(HashMap<String, List<Integer>> mapPlayerScore, int k){
        int sum = 0;
        HashMap<Integer, List<String>> mapScore = new HashMap<>();

        for(Map.Entry<String, List<Integer>> entry : mapPlayerScore.entrySet()){
            sum = 0;
            for(Integer x: entry.getValue()) {
                sum += x;
            }
            List<String> lstPaylers = mapScore.getOrDefault(sum, new ArrayList<>());
            lstPaylers.add(entry.getKey());
            mapScore.put(sum, lstPaylers);
        }

        PriorityQueue<Integer> maxQueue = new PriorityQueue<>();

        for (Integer score: mapScore.keySet()){
            while (maxQueue.size() > k){
                maxQueue.poll();
            }
            maxQueue.add(score);
        }
        while (maxQueue.size() > k){
            maxQueue.poll();
        }

        return mapScore.get(maxQueue.poll());
    }
}
