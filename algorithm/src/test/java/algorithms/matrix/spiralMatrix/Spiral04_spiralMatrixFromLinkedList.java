package algorithms.matrix.spiralMatrix;

import algorithms.singlyLinkedList.base.ListNode;

public class Spiral04_spiralMatrixFromLinkedList {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] result = new int[m][n];
        int rowStart = 0, rowEnd = m-1;
        int colStart = 0, colEnd = n-1;
        ListNode currentNode = head;

        while (rowStart <= rowEnd && colStart <= colEnd) {
            for(int i=colStart; i <= colEnd; i++){
                result[rowStart][i] = currentNode != null ?  currentNode.value : -1;
                if(currentNode != null){
                    currentNode = currentNode.next;
                }
            }
            rowStart ++;

            for(int i=rowStart; i <= rowEnd; i++){
                result[i][colEnd] = currentNode != null ?  currentNode.value : -1;
                if(currentNode != null){
                    currentNode = currentNode.next;
                }
            }
            colEnd--;
            if(rowStart <= rowEnd){
                for(int i=colEnd; i >= colStart; i--){
                    result[rowEnd][i] = currentNode != null ?  currentNode.value : -1;
                    if(currentNode != null){
                        currentNode = currentNode.next;
                    }
                }
            }
            rowEnd--;
            if(colStart <= colEnd){
                for(int i=rowEnd; i >= rowStart; i--){
                    result[i][colStart] = currentNode != null ?  currentNode.value : -1;
                    if(currentNode != null){
                        currentNode = currentNode.next;
                    }
                }
            }
            colStart++;
        }
        return result;
    }
}
