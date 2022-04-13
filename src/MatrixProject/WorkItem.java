/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

/**
 *
 * @author dapeng
 */
public class WorkItem {
    int[][] subA = null;
    int[][] subB = null;
    int[][] subC = null;
    int lowA = 0;
    int lowB = 0;
    int highA = 0;
    int highB = 0;
    boolean done = false;

    public WorkItem(int[][] subA, int[][] subB, int[][] subC, int lowA, int highA, int lowB, int highB) {
        this.subA = subA;
        this.subB = subB;
        this.subC = subC;
        this.lowA = lowA;
        this.highA = highA;
        this.lowB = lowB;
        this.highB = highB;
    }

    public boolean killConsumer(){
        if (subA == null || subB == null){
            return true;
        }
        return false;
    }
    
    
}


