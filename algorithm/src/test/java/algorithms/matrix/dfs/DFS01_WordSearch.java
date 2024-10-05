package algorithms.matrix.dfs;

/***
 * https://leetcode.com/problems/word-search/
 * 
 * Given an m x n board and a word, find if the word exists in the grid.
 * 
 * The word can be constructed from letters of sequentially adjacent cells,
 * where "adjacent" cells are horizontally or vertically neighboring. The same
 * letter cell may not be used more than once.
 *
 * 
 * Example 1:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word
 * = "ABCCED" Output: true Example 2:
 * 
 * 
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word
 * = "SEE" Output: true Example 3:
 * 
 * 
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word
 * = "ABCB" Output: false
 * 
 * 
 * Constraints:
 * m == board.length n = board[i].length 1 <= m, n <= 200 1 <= word.length <=
 * 103 board and word consists only of lowercase and uppercase English letters.
 */
public class DFS01_WordSearch {

	public boolean exist(char[][] board, String word) {
        
        for (int row = 0; row < board.length; ++row){
            for (int col = 0; col < board[row].length; ++col){ //important to use board[row].length
                 if(board[row][col] == word.charAt(0) && backtracking(board,row, col,word, 0)){
                     return true;
                 }
            }
        }
        return false;
    }
    
    private boolean backtracking(char[][] board,int row,int col,String word, int index ){
         if (index == word.length())
            return true;
        
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length 
            || board[row][col] != word.charAt(index))
            return false;
        
        board[row][col] = '#';
        
        boolean result = 
        	backtracking(board, row+1, col, word, index + 1) ||
            backtracking(board, row-1, col, word, index + 1) ||
            backtracking(board, row, col + 1, word, index + 1) ||
            backtracking(board, row, col -1, word, index + 1);
      
        board[row][col] = word.charAt(index);
        return result;
    }
}
