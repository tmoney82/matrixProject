/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

/**
 *
 * @author dapen
 */
public class Consumer implements Runnable {
    
    private BoundedBuffer buff;
    private int maxConsumerSleepTime;
    private int[][] matrixC;
	
    public Consumer(BoundedBuffer buff, int maxConsumerSleepTime, int num_rows, int num_columns) {
	this.buff = buff;
        this.maxConsumerSleepTime = maxConsumerSleepTime;
        this.matrixC = new int[num_rows][num_columns];
    }
    
    private static int[][] multiplyMatrix(int[][] matrix_one, int[][] matrix_two) {
        int [][] result = new int [matrix_one.length][matrix_two[0].length];

        for(int i = 0; i< matrix_one.length; i++){
            for(int j = 0; j< matrix_two[0].length; j++){
                for(int k = 0; k< matrix_two.length; k++){
                    result[i][j] += matrix_one[i][k] * matrix_two[k][j];
                }
            }
        }
        return result;
    }
    
    private static void printMatrix(int[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");

            }
            System.out.println();
        }

    }
	
    @Override
    public void run() {
	WorkItem workItem;
        do {
            workItem = buff.get();
            //System.out.println("GET A[" + workItem.lowA + "][" + workItem.highA + "] B[" + workItem.lowB + "][" + workItem.highB + "] pair from buffer");
            workItem.subC = multiplyMatrix(workItem.subA, workItem.subB);
            //System.out.println("lowA is: " + workItem.lowA  + " and lowB is: " + workItem.lowB);
            
            for(int i = 0; i < workItem.subC.length; i++){
                for(int j = 0; j < workItem.subC[0].length; j++){
                    //System.out.println("Row is: " + workItemGeted.lowA +"+"+ i + " and Column is: " + workItemGeted.lowB + "+"+j);
                    matrixC[workItem.lowA + i][workItem.lowB + j] = workItem.subC[i][j];
                    
                }
            }
            workItem.done = true;
        
            try {
                Thread.sleep((int)(Math.random()*maxConsumerSleepTime));
            } catch(InterruptedException e){}
            
            printMatrix(matrixC);
        } while(workItem != null);
        
    }
}
