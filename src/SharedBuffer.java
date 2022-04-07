public class SharedBuffer {
    /*
    This is all from the lab for now we can make changes as necessary
    */

    private int count, in, out;
    private static final int MAX_BUFFER_SIZE = 5;
    private int[] buffer;

    public SharedBuffer() {
        count = 0; in = 0; out = 0;
        buffer = new int[MAX_BUFFER_SIZE];
    }

    public synchronized int get() {
        int item;
        while(count == 0) {
            try {
                wait();

            }catch (InterruptedException e) {}
        }
        item = buffer[out];
        out = (out+1) % MAX_BUFFER_SIZE;
        count--;
        notifyAll();
        return item;
    }

    public synchronized void set(int value) {
        while(count == MAX_BUFFER_SIZE) {
            try {
                wait();

            }catch (InterruptedException e) {}
        }
        buffer[in] = value;
        in = (in+1) % MAX_BUFFER_SIZE;
        count++;
        notifyAll();
    }
}
