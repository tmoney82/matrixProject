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
    
    private final BoundedBuffer buff;
    private final int maxConsumerSleepTime;
    private final int[][] matrixC;
    private final int num_rows;
    private final int num_columns;
	
    public Consumer(BoundedBuffer buff, int maxConsumerSleepTime, int num_rows, int num_columns) {
	this.buff = buff;
        this.maxConsumerSleepTime = maxConsumerSleepTime;
        this.matrixC = new int[num_rows][num_columns];
        this.num_rows = num_rows;
        this.num_columns = num_columns;
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
        for (int[] ints : matrix) {

            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
	
    @Override
    public void run() {
	WorkItem workItem;
        while (true) {
            workItem = buff.get();
            System.out.println("\nConsumer " + Thread.currentThread().getId() + " gets A[" + workItem.lowA + "][" + workItem.highA + "] B[" + workItem.lowB + "][" + workItem.highB + "] pair from buffer");
            workItem.subC = multiplyMatrix(workItem.subA, workItem.subB);
            //System.out.println("lowA is: " + workItem.lowA  + " and lowB is: " + workItem.lowB);
            
            for(int i = 0; i < workItem.subC.length; i++){
                //System.out.println("Row is: " + workItemGeted.lowA +"+"+ i + " and Column is: " + workItemGeted.lowB + "+"+j);
                System.arraycopy(workItem.subC[i], 0, matrixC[workItem.lowA + i], workItem.lowB, workItem.subC[0].length);
            }
            if (workItem.highA == num_rows -1 && workItem.highB == num_columns -1){
                break;
            }

            try {
                Thread.sleep((int)(Math.random()*maxConsumerSleepTime));
            } catch(InterruptedException ignored){}

            //printMatrix(matrixC);
        }
        printMatrix(matrixC);
    }
}
