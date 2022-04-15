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
public class Producer implements Runnable{
    
    private final BoundedBuffer buff;
    private final int maxSleepTime;
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final int splitSize;
    
    
	
    public Producer(int[][] matrixA, int[][] matrixB, int splitSize, BoundedBuffer buff, int maxSleepTime) {
	this.buff = buff;
        this.maxSleepTime = maxSleepTime;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.splitSize = splitSize;
    }
	
    @Override
    public void run() {
        int[][] subA;
        int[][] subB;
        int lowA, lowB, highA, highB;


        for(int i = 0; i < matrixA.length; i = i + splitSize){
            lowA = i;
            if (i + splitSize - 1 <= matrixA.length){   //enough rows for splitSize
                highA = i + splitSize - 1;
                subA = new int[splitSize][matrixA[0].length];
                for(int ar = 0; ar < splitSize; ar++ ){
                    System.arraycopy(matrixA[lowA + ar], 0, subA[ar], 0, matrixA[0].length);
                    //workItem.subA[ar] = matrixA[workItem.lowA + ar];
                }
            }
            else {      //not enough rows for splitSize
                highA = matrixA.length - 1;
                subA = new int[matrixA.length %splitSize][matrixA[0].length];
                for(int ar = 0; ar < (matrixA.length % splitSize); ar++ ){
                    System.arraycopy(matrixA[lowA + ar], 0, subA[ar], 0, matrixA[0].length);
                    //workItem.subA[ar] = matrixA[workItem.lowA + ar];
                }
            }


            for(int j = 0; j < matrixB[0].length; j = j + splitSize){
                lowB = j;
                if (j + splitSize - 1 <= matrixB[0].length){    //enough columns for splitSize
                    highB = j + splitSize - 1;
                    subB = new int[matrixB.length][splitSize];
                    for(int br = 0; br < matrixB.length; br++ ){    //sbuB <= matrixB
                        System.arraycopy(matrixB[br], lowB, subB[br], 0, splitSize);
                    }
                }
                else {      //not enough columns for splitSize
                    highB = matrixB[0].length - 1;
                    subB = new int[matrixB.length][matrixB[0].length % splitSize];
                    for(int br = 0; br < matrixB.length; br++ ){    //sbuB <= matrixB
                        System.arraycopy(matrixB[br], lowB, subB[br], 0, matrixB[0].length % splitSize);
                    }
                }

                WorkItem workItem = new WorkItem(subA, subB, lowA, lowB, highA, highB, false);
                buff.set(workItem);

                System.out.println("\nProducer " + Thread.currentThread().getId() + " puts A[" + workItem.lowA + "][" + workItem.highA + "] B[" + workItem.lowB + "][" + workItem.highB + "] pair into buffer");
            }
        }

        try {
            Thread.sleep((int)(Math.random() * (1 + 1 - maxSleepTime) + maxSleepTime));
        } catch(InterruptedException ignored){}
    }
}
