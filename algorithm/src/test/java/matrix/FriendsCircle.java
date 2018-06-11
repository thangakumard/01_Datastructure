package matrix;
import org.testng.annotations.Test;
public class FriendsCircle {

	@Test
	public void Test(){
		
		int[][] input ={{1,1,0},{1,1,0},{0,0,1}};
		System.out.println(getFriendsCircle(input));
		
	}
	
	boolean[][] visited;
	
	public int getFriendsCircle(int[][] input){
		int counter = 0;
		visited = new boolean[input.length][input[0].length];
		for(int i=0; i < input.length; i++){
			for(int j=0; j < input[0].length; j++){
				if(findCircle(input, i, j) > 0){
					counter++;
				}
			}
		}
		String s ="";
	
		
		return counter;
	}
	
	public int findCircle(int[][] input, int x, int y){
		int counter = 0;
		if(x >=0 && x < input.length && 
				y >=0 && y < input[0].length &&
				!visited[x][y] &&
				input[x][y] == 1){
			visited[x][y] = true;
			counter++;
			counter += findCircle(input, x+1, y);
			counter += findCircle(input, x-1, y);
			counter += findCircle(input, x, y+1);
			counter += findCircle(input, x, y-1);			
		}			
		return counter;
	}
}
