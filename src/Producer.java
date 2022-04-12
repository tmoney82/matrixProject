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
    
    
	
    public Producer(int[][] matrixA, int[][] matrixB, BoundedBuffer buff, int maxSleepTime) {
	this.buff = buff;
        this.maxSleepTime = maxSleepTime;
    }
	
    @Override
    public void run() {
        WorkItem workItem = new WorkItem();
        workItem.done = false;
        for(int i = 0; i < matrixA.length; i = i + splitSize){
            workItem.lowA = i;
            if (i + splitSize - 1 <= matrixA.length){   //enough rows for splitSize
                workItem.highA = i + splitSize - 1;
                for(int ar = 0; ar < splitSize; ar++ ){
                    workItem.subA[ar] = matrixA[workItem.lowA + ar];
                }
            }
            else {      //no enough rows for splitSize
                workItem.highA = matrixA.length - 1;
                for(int ar = 0; ar < (matrixA.length % splitSize); ar++ ){
                    workItem.subA[ar] = matrixA[workItem.lowA + ar];
                }
            }
                    
            
            for(int j = 0; j < matrixB[0].length; j = j + splitSize){
                workItem.lowB = j;
                if (j + splitSize - 1 <= matrixB[0].length){    //enough columns for splitSize
                    workItem.highB = j + splitSize - 1;
                    for(int br = 0; br < matrixB.length; br++ ){    //sbuB <= matrixB
                        for (int bc = 0; bc < splitSize; bc++){
                            workItem.subB[br][bc] = matrixB[br][workItem.lowB + bc];
                            buff.set(workItem);
                        }                        
                    }
                }
                else {      //no enough columns for splitSize
                    workItem.highB = matrixB[0].length - 1;
                    for(int br = 0; br < matrixB.length; br++ ){    //sbuB <= matrixB
                        for (int bc = 0; bc < (matrixB[0].length % splitSize); bc++){
                            workItem.subB[br][bc] = matrixB[br][workItem.lowB + bc];
                            buff.set(workItem);
                        }                        
                    }
                }
                
            }
        }
        /*
	for(int i = 0; i < 10; i++) {
            int num = (int)(Math.random()*(10-100)+100);
            buff.set(num);
            System.out.println("Producer: put " + num + " to the buffer.");
            try {
		Thread.sleep((int)(Math.random() * (1 + 1 - 20) + 20));
            } catch(InterruptedException e){}
        }
        */
    }
}
