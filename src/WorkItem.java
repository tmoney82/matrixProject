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
    int[][] subA;
    int[][] subB;
    int[][] subC;
    int lowA, lowB, highA, highB;
    boolean done;

    public WorkItem() {
    }

    public WorkItem(int[][] subA, int[][] subB, int lowA, int lowB, int highA, int highB, boolean done) {
        this.subA = subA;
        this.subB = subB;
        this.lowA = lowA;
        this.lowB = lowB;
        this.highA = highA;
        this.highB = highB;
        this.done = done;
    }
    
    
    
    public WorkItem(int[][] subA, int[][] subB, int[][] subC, int lowA, int lowB, int highA, int highB, boolean done) {
        this.subA = subA;
        this.subB = subB;
        this.subC = subC;
        this.lowA = lowA;
        this.lowB = lowB;
        this.highA = highA;
        this.highB = highB;
        this.done = done;
    }
}


