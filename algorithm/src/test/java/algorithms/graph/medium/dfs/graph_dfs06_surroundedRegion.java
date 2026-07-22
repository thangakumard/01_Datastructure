package algorithms.graph.medium.dfs;

/**
 * https://leetcode.com/problems/surrounded-regions/description
 * You are given an m x n matrix board containing letters 'X' and 'O', capture regions that are surrounded:
 *
 * Connect: A cell is connected to adjacent cells horizontally or vertically.
 * Region: To form a region connect every 'O' cell.
 * Surround: A region is surrounded if none of the 'O' cells in that region are on the edge of the board. Such regions are completely enclosed by 'X' cells.
 * To capture a surrounded region, replace all 'O's with 'X's in-place within the original board. You do not need to return anything.
 *
 * Example 1:
 *===========
 * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 * Explanation:
 * In the above diagram, the bottom region is not captured because it is on the edge of the board and cannot be surrounded.
 *
 * Example 2:
 * Input: board = [["X"]]
 * Output: [["X"]]
 *
 * important example
 * =================
 *input :
 * [["X","X","X","X","X","X"],["X","O","X","O","O","X"],["X","X","X","X","X","X"],["X","O","O","X","O","X"],["X","X","O","O","O","O"],["X","X","X","X","X","X"]]
 ** Output:
 * [["X","X","X","X","X","X"],["X","X","X","X","X","X"],["X","X","X","X","X","X"],["X","O","O","X","O","X"],["X","X","O","O","O","O"],["X","X","X","X","X","X"]]
 *
 * Constraints:
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] is 'X' or 'O'.
 */
public class graph_dfs06_surroundedRegion {
    /**
     * Time : O(rows, cols)
     * Space: O(rows, cols)
     */
    public void solve(char[][] board) {
        if(board == null || board.length == 0) return;

        int rows = board.length;
        int cols = board[0].length;

        //Mark all the column edge cells that has O with #
        for(int r=0; r < rows; r++){
            if(board[r][0] == 'O') dfs(board, r , 0);
            if(board[r][cols - 1] == 'O') dfs(board, r, cols-1);
        }
        //Mark all the row edge cells that has O with #
        for(int c=0; c < cols; c++){
            if(board[0][c] == 'O') dfs(board, 0 , c);
            if(board[rows-1][c] == 'O') dfs(board, rows-1, c);
        }

        //Mark all the remaining cells that has O with X
        //Revert the cells with value # to O
        for(int r=0; r < rows; r++){
            for(int c=0; c < cols; c++){
                if(board[r][c] == 'O') board[r][c] = 'X';
                else if(board[r][c] == '#') board[r][c] = 'O';
            }
        }
    }

    private void dfs(char[][] board, int r, int c){
        if(r < 0 || c < 0 ||
                r >= board.length || c >= board[0].length ||
                board[r][c] != 'O') return;

        board[r][c] = '#';

        dfs(board, r+1, c);
        dfs(board, r-1, c);
        dfs(board, r, c+1);
        dfs(board, r, c-1);
    }
}