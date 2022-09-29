package algorithms.amazon;

import java.util.*;

public class Amazon_11_TransactionLogs {
    /*
     * Complete the 'processLogs' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING_ARRAY logs
     *  2. INTEGER threshold
     */

    public static List<String> processLogs(List<String> logs, int threshold) {
        List<String> result = new ArrayList<>();
        HashMap<String, Integer> mapCounter = new HashMap<>();
        String sender_user_id, recipient_user_id;
        for(String log: logs){
            String[] log_info = log.split("\\s");
            sender_user_id =  log_info[0];
            recipient_user_id =  log_info[1];
            mapCounter.put(sender_user_id, mapCounter.getOrDefault(sender_user_id, 0) + 1);
            if(!sender_user_id.equals(recipient_user_id)){
                mapCounter.put(recipient_user_id, mapCounter.getOrDefault(recipient_user_id, 0) + 1);
            }
        }

        PriorityQueue<String> queueLog = new PriorityQueue<>((a, b) -> mapCounter.get(b) - mapCounter.get(a));
        queueLog.addAll(mapCounter.keySet());

        while(!queueLog.isEmpty()){
            String user_id = queueLog.poll();
            int transaction_count = mapCounter.get(user_id);
            if(transaction_count >= threshold){
                result.add(user_id);
            }else{
                break;
            }
        }

        //It is mandatory to use Integer.parseInt. If we miss this step few tests were failed
        Collections.sort(result, (s1, s2) -> Integer.parseInt(s1) - Integer.parseInt(s2));
        return result;
    }
}
