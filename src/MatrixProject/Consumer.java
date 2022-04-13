package MatrixProject;

public class Consumer implements Runnable {
    private BoundedBuffer buff;
    private int maxConsumerSleepTime;
    private int[][] subC;
    private int id;
    // TODO: Finish the Consumer class.

    public Consumer(int maxConsumerSleepTime, BoundedBuffer buff, int id){
        this.maxConsumerSleepTime = maxConsumerSleepTime;
        this.buff = buff;
        this.id = id;
    }

    @Override
    public void run() {
        WorkItem val;
        id = 1;
        for(int i = 0; i < 5; i++) {
            val = buff.get();
            val.subC = new int[val.subA.length][val.subB.length];
            System.out.println("Consumer " + id + " getting item from buffer");

            //multiply matrix and put into the sub c matrix
            for(int p = 0; i< val.subA.length; i++){
              for(int j = 0; j< val.subA[0].length; j++){
                for(int k = 0; k< val.subB.length; k++){
                  val.subC[p][j] += val.subA[p][k] * val.subB[k][j];
                }
              }
            }
            for(int l=0;i<val.subC.length;i++)
            {
                for(int j=0;j<val.subC[0].length;j++)
                {
                    System.out.print(val.subC[l][j]+"\t");
                }

                System.out.print("\n");
            }
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch(InterruptedException ie){

            }
        }
    }
}

