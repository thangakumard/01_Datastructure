package algorithms.array.medium;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Array41_SubdomainVisits {

    @Test
    public void subdomainVisitsTest(){
        String[] input = new String[] {"9001 discuss.leetcode.com"};
        subdomainVisits(input);
    }

    public List<String> subdomainVisits(String[] cpdomains) {

        HashMap<String, Integer> mapResult = new HashMap<String, Integer>();

        for(String domain: cpdomains){
            String[] split1 = domain.split(" ");
            String[] split2 = split1[1].split("\\.");
            String domainKey = "";

            for(String subDomain: split2){
                domainKey = domainKey == "" ? subDomain : domainKey + "." + subDomain;
                mapResult.put(domainKey, mapResult.getOrDefault(mapResult, 0) + Integer.parseInt(split1[0]));
            }
        }
        List<String> result = new ArrayList<>();

        for(Map.Entry<String, Integer> entry: mapResult.entrySet()){
            result.add(entry.getValue() + " " + entry.getKey());
        }
        return result;
    }
}
