package algorithms.array.TwoDimensional;

/*
 * 	https://leetcode.com/problems/word-search/
	Given an m x n board and a word, find if the word exists in the grid.

	The word can be constructed from letters of sequentially adjacent cells, where "adjacent" cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

	Example 1:
	
	
	Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
	Output: true
	Example 2:
	
	
	Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
	Output: true
	Example 3:
	
	
	Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
	Output: false
	 
	
	Constraints:
	
	m == board.length
	n = board[i].length
	1 <= m, n <= 200
	1 <= word.length <= 103
	board and word consists only of lowercase and uppercase English letters.
 */
public class TwoD05_WordSearch {

	private char[][] board;
	  private int ROWS;
	  private int COLS;
	    
	    public boolean exist(char[][] board, String word) {
	     
	         this.board = board;
	        this.ROWS = board.length;
	        this.COLS = board[0].length;
	        char[] word_chars = word.toCharArray();
	        
	        for (int row = 0; row < ROWS; ++row)
	            for (int col = 0; col < COLS; ++col)
	                 if (backtracking(row, col,word, 0))
	                     return true;
	        return false;
	    }
	    
	    private boolean backtracking(int row,int col,String word, int index ){
	         if (index >= word.length())
	            return true;
	        
	        if (row < 0 || row == this.ROWS || col < 0 || col == this.COLS
	        || this.board[row][col] != word.charAt(index))
	            return false;
	        
	        boolean ret = false;
	        
	        board[row][col] = '#';
	        
	        int[] rowOffsets = {0, 1, 0, -1};
	        int[] colOffsets = {1, 0, -1, 0};
	        for (int d = 0; d < 4; ++d) {
	          ret = backtracking(row + rowOffsets[d], col + colOffsets[d],word, index + 1);
	          if(ret)
	              break;
	        }
	      
	        
	        this.board[row][col] = word.charAt(index);
	        return ret;
	    }
}
