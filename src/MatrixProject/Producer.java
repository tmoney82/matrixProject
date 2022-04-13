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
    
    private BoundedBuffer buff;
    private int maxSleepTime;
    private int[][] matrixA;
    private int[][] matrixB;
    private int splitSize;
    
    
	
    public Producer(int[][] matrixA, int[][] matrixB, int splitSize, BoundedBuffer buff, int maxSleepTime) {
	this.buff = buff;
        this.maxSleepTime = maxSleepTime;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.splitSize = splitSize;
    }
	
    @Override
    public void run() {
        WorkItem workItem = new WorkItem(null, null, null, 0 , 0, 0,  0);
        workItem.subA = new int[splitSize][matrixA[0].length]; 
        workItem.subB = new int[matrixB.length][splitSize];
        workItem.subA[0][0] = 3;
        workItem.done = false;
        for(int i = 0; i < matrixA.length; i = i + splitSize){
            workItem.lowA = i;
            if (i + splitSize - 1 <= matrixA.length){   //enough rows for splitSize
                workItem.highA = i + splitSize - 1;
                for(int ar = 0; ar < splitSize; ar++ ){
                    for(int ac = 0; ac < matrixA[0].length; ac++){
                        workItem.subA[ar][ac] = matrixA[workItem.lowA + ar][ac];
                    }
                    //workItem.subA[ar] = matrixA[workItem.lowA + ar];
                }
            }
            else {      //no enough rows for splitSize
                workItem.highA = matrixA.length - 1;
                for(int ar = 0; ar < (matrixA.length % splitSize); ar++ ){
                    for(int ac = 0; ac < matrixA[0].length; ac++){
                        workItem.subA[ar][ac] = matrixA[workItem.lowA + ar][ac];
                    }
                    //workItem.subA[ar] = matrixA[workItem.lowA + ar];
                }
            }
                    
            
            for(int j = 0; j < matrixB[0].length; j = j + splitSize){
                workItem.lowB = j;
                if (j + splitSize - 1 <= matrixB[0].length){    //enough columns for splitSize
                    workItem.highB = j + splitSize - 1;
                    for(int br = 0; br < matrixB.length; br++ ){    //sbuB <= matrixB
                        for (int bc = 0; bc < splitSize; bc++){
                            workItem.subB[br][bc] = matrixB[br][workItem.lowB + bc];
                        }                        
                    }
                }
                else {      //no enough columns for splitSize
                    workItem.highB = matrixB[0].length - 1;
                    for(int br = 0; br < matrixB.length; br++ ){    //sbuB <= matrixB
                        for (int bc = 0; bc < (matrixB[0].length % splitSize); bc++){
                            workItem.subB[br][bc] = matrixB[br][workItem.lowB + bc]; 
                        }                        
                    }
                }
                buff.set(workItem);
                System.out.println("Put A[" + workItem.lowA + "][" + workItem.highA + "] B[" + workItem.lowB + "][" + workItem.highB + "] pair into buffer");
            }
        }

        try {
            Thread.sleep((int)(Math.random() * (1 + 1 - maxSleepTime) + maxSleepTime));
        } catch(InterruptedException e){}
    }
}
