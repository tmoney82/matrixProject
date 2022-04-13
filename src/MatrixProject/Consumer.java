package MatrixProject;
import java.util.Random;

public class Consumer implements Runnable{
    final int maxConsumerSleepTime;
    final BoundedBuffer buff;
    private long consumerID = 0;
    private WorkItem workItem;
    private int consumedItems = 0;
    private int consumerSleepTime = 0;
    private long consumerSleepStart, consumerSleepEnd, consumerSleepTimeTotal;
    static long consumerSleepTimeSum;
    Random r = new Random();
    ProducerConsumer result;


    public Consumer(BoundedBuffer buff, int maxConsumerSleepTime){
        this.buff = buff;
        this.maxConsumerSleepTime = maxConsumerSleepTime;
    }

    public long getConsumerID(){
        return consumerID;
    }
    @Override
    public void run() {
        consumerID = Thread.currentThread().getId();
        while (true) {
            workItem = buff.get();
            if(workItem.killConsumer()){
                break;
            }
            workItem.subC = new int[workItem.subA.length][workItem.subB[0].length];
            for(int i = 0; i< workItem.subA.length; i++){
                for(int j = 0; j< workItem.subB[0].length; j++){
                    for(int k = 0; k< workItem.subA[0].length; k++){
                        workItem.subC[i][j] += workItem.subA[i][k] * workItem.subB[k][j];
                    }
                }
            }
            System.out.println("\nC[");
            for(int i=0;i<workItem.subC.length;i++)
            {
                for(int j=0;j<workItem.subC[0].length;j++)
                {
                    System.out.print(workItem.subC[i][j]+"\t");
                }

                System.out.print("\n");
            }
            System.out.println("]");

            consumerSleepTime = r.nextInt(maxConsumerSleepTime + 1);

            consumerSleepStart = System.nanoTime();
            try {
                Thread.sleep(consumerSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            consumerSleepEnd = System.nanoTime();
            consumerSleepTimeTotal = consumerSleepStart - consumerSleepEnd;
            consumerSleepTimeSum += consumerSleepTimeTotal;
        }
    }
}
