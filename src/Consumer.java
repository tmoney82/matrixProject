public class Consumer implements Runnable {
    private SharedBuffer res;
    private int id;

    public Consumer(SharedBuffer res, int id) {
        this.res = res;
        this.id = id;
    }

    @Override
    public void run() {
        int val = 0;
        for(int i = 0; i < 5; i++) {
            val = res.get();
            System.out.println("Consumer " + id + " get " + val);
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch(InterruptedException ie){

            }
        }
    }
}

