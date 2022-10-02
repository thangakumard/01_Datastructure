package algorithms.graph.easy;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/***
 * You are given the array paths, where paths[i] = [cityAi, cityBi] means there exists a direct path going from cityAi to cityBi.
 * Return the destination city, that is, the city without any path outgoing to another city.
 * It is guaranteed that the graph of paths forms a line without any loop, therefore, there will be exactly one destination city.
 *
 *
 *
 * Example 1:
 * Input: paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
 * Output: "Sao Paulo"
 * Explanation: Starting at "London" city you will reach "Sao Paulo" city which is the destination city. Your trip consist of: "London" -> "New York" -> "Lima" -> "Sao Paulo".

 * Example 2:
 * Input: paths = [["B","C"],["D","B"],["C","A"]]
 * Output: "A"
 * Explanation: All possible trips are:
 * "D" -> "B" -> "C" -> "A".
 * "B" -> "C" -> "A".
 * "C" -> "A".
 * "A".
 * Clearly the destination city is "A".

 * Example 3:
 * Input: paths = [["A","Z"]]
 * Output: "Z"
 *
 *
 * Constraints:
 *
 * 1 <= paths.length <= 100
 * paths[i].length == 2
 * 1 <= cityAi.length, cityBi.length <= 10
 * cityAi != cityBi
 * All strings consist of lowercase and uppercase English letters and the space character.
 */
public class Graph01_DestinationCity {

    @Test
    public void destCityTest(){
        List<List<String>> paths = new ArrayList<>();
        List<String> set1 = new ArrayList<>();
        set1.add("London");
        set1.add("New York");
        paths.add(set1);

        List<String> set2 = new ArrayList<>();
        set2.add("New York");
        set2.add("Lima");
        paths.add(set2);

        List<String> set3 = new ArrayList<>();
        set3.add("Lima");
        set3.add("Sao Paulo");
        paths.add(set3);

       Assertions.assertThat(destCity(paths)).isEqualTo("Sao Paulo");
    }
    public String destCity(List<List<String>> paths) {
        List<String> cities = new ArrayList<>();
        for(List<String> path: paths){
            cities.add(path.get(1));
        }
        for(List<String> path: paths){
            cities.remove(path.get(0));
        }
        return cities.iterator().next();
    }
}
