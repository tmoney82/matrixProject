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
    int lowA, lowB, highA, highB = 0;
    boolean done = false;

    public WorkItem() {}

    public WorkItem(int[][] subA, int[][] subB, int lowA, int lowB, int highA, int highB, boolean done) {
        this.subA = subA;
        this.subB = subB;
        this.lowA = lowA;
        this.lowB = lowB;
        this.highA = highA;
        this.highB = highB;
        this.done = done;
    }

}


