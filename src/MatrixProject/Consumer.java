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
    private final int[][] matrixA;
    private final int[][] matrixB;
    private int[][] originalResult;
	
    public Consumer(BoundedBuffer buff, int maxConsumerSleepTime, int num_rows, int num_columns, int[][] matrixA,
                    int[][] matrixB, int[][] originalResult) {
	this.buff = buff;
        this.maxConsumerSleepTime = maxConsumerSleepTime;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = new int[num_rows][num_columns];
        this.num_rows = num_rows;
        this.num_columns = num_columns;
        this.originalResult = originalResult;
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
            if (i == 0) {
                System.out.print("[ [");
            } else {
                System.out.print("[");
            }
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            if (i == matrix.length - 1) {
                System.out.print("] ]");
            } else {
                System.out.print("],");
                System.out.println();
            }
        }
    }
	
    @Override
    public void run() {
	    WorkItem workItem;
        synchronized (buff) {
            try{
                buff.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            while (true) {
                System.out.println("\nBuffer size: " + buff.getSize());
                workItem = buff.get();
                System.out.println("\nConsumer " + Thread.currentThread().getId() + " gets A[" + workItem.lowA + "][" + workItem.highA + "] B[" + workItem.lowB + "][" + workItem.highB + "] pair from buffer");
                workItem.subC = multiplyMatrix(workItem.subA, workItem.subB);
                //System.out.println("lowA is: " + workItem.lowA  + " and lowB is: " + workItem.lowB);

                // Prints out the subA x subB => subC
                System.out.println("\nConsumer " + Thread.currentThread().getId() + " finishes calculating");
                printMatrix(workItem.subA);
                System.out.print("\nx");
                printMatrix(workItem.subB);
                System.out.print("\n=>");
                printMatrix(workItem.subC);

                for (int i = 0; i < workItem.subC.length; i++) {
                    //System.out.println("Row is: " + workItemGeted.lowA +"+"+ i + " and Column is: " + workItemGeted.lowB + "+"+j);
                    System.arraycopy(workItem.subC[i], 0, matrixC[workItem.lowA + i], workItem.lowB, workItem.subC[0].length);
                }
                if (workItem.highA == num_rows - 1 && workItem.highB == num_columns - 1) {
                    break;
                }

                try {
                    Thread.sleep((int) (Math.random() * maxConsumerSleepTime));
                } catch (InterruptedException ignored) {
                }

                //printMatrix(matrixC);
            }
        }
        System.out.println("\nProducer successfully assembly all the results from consumer threads");
        System.out.println("------------------------------------------------");
        System.out.print("Final result of parallel matrix multiplication:");
        printMatrix(matrixC);
        System.out.print("\n\nVerified result of sequential matrix multiplication:");
        originalResult = multiplyMatrix(matrixA, matrixB);
        printMatrix(originalResult);
        System.out.println("\n------------------------------------------------");
        System.out.println("The number of times the buffer was full: " + buff.getNumOfTimesFull());
        System.out.println("The number of times the buffer was empty: " + buff.getNumOfTimesEmpty());
        System.out.println("Number of items produced: " + buff.getNumItemsProduced());
        System.out.println("Number of items consumed: " + buff.getNumItemsConsumed());
    }
}
