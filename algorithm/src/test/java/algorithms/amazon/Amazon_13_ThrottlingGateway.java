package algorithms.amazon;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Amazon_13_ThrottlingGateway {

    @Test
    public void droppedRequestsTest(){
        int[] input = {1,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,7,11,11,11,11};
        ArrayList<Integer> requestTime = new ArrayList<>();
        for (int i: input){
            requestTime.add(i);
        }

        Assertions.assertThat(droppedRequests(requestTime)).isEqualTo(7);
    }
    public int droppedRequests(ArrayList<Integer> requestTime)
    {
        ArrayList<Integer> dropped = new ArrayList<>();
        int cnt = requestTime.size() - 1;

        for(int i = 0; i < cnt; i++)
        {
            if(i + 3 <= cnt && requestTime.get(i + 3) == requestTime.get(i))
            {
                dropped.add(i + 3);
            }
            if(i + 20 <= cnt && requestTime.get(i + 20) - requestTime.get(i) < 10)
            {
                dropped.add(i + 20);
            }
            if(i + 60 <= cnt && requestTime.get(i + 60) - requestTime.get(i) < 60)
            {
                dropped.add(i + 60);
            }
        }
        return (int)dropped.stream().distinct().count();
    }
}
