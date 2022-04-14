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
    private final int MAX_BUFFER_SIZE;
    private final WorkItem[] buffer;
    
    private final boolean bAvailable = false;    //flag to control the shared int variable

    public BoundedBuffer(int SIZE) {
        count = 0; in = 0; out = 0;
        MAX_BUFFER_SIZE = SIZE;
        buffer = new WorkItem[SIZE];
    }

    public synchronized WorkItem get() {
        //showbuff();
        WorkItem item;
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
        item = buffer[out];
        out = (out+1)% MAX_BUFFER_SIZE;
        count--;
        notifyAll();
        return item;
    }

    public synchronized void set(WorkItem value) {
        while (count == MAX_BUFFER_SIZE) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
        buffer[in] = value;
        in = (in+1) % MAX_BUFFER_SIZE;
        count++;
        //showbuff();
        notifyAll();  
    }
    
    private void showbuff(){
        for (int i = 0; i < MAX_BUFFER_SIZE; i++){
            if(buffer[i] != null){
                System.out.println("Buff [" + i + "] --> lowA: " + buffer[i].lowA + ", lowB: " + buffer[i].lowB);
            }
        }
    }
}
