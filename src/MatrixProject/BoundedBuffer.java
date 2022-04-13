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
public class BoundedBuffer {
    private int count, in, out;	//the number of items, indexes of in and out pointers
    private int MAX_BUFFER_SIZE;
    private WorkItem[] buffer;
    
    private boolean bAvailable = false;    //flag to control the shared int variable

    public BoundedBuffer(int SIZE) {
        count = 0; in = 0; out = 0;
        this.MAX_BUFFER_SIZE = SIZE;
        buffer = new WorkItem[this.MAX_BUFFER_SIZE];
    }

    public synchronized WorkItem get() {
        WorkItem item;
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        item = buffer[out];
        out = (out+1)% MAX_BUFFER_SIZE;
        count--;
        notifyAll();
        return item;
    }

    public synchronized void put(WorkItem value) {
        while (count == MAX_BUFFER_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        buffer[in] = value;
        in = (in+1) % MAX_BUFFER_SIZE;
        count++;
        notifyAll();  
    }
}
