public class Producer implements Runnable {
    private SharedBuffer res;
    private int id;

    public Producer(SharedBuffer res, int id) {
        this.res = res;
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            res.set(i);
            System.out.println("Producer " + id + " set " + i);
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch(InterruptedException ie){

            }
        }
    }
}

