import java.util.Scanner;

public class ProducerConsumer {
    public static void main(String[] args) {
        // Create the shared resource between producers and consumers
        SharedBuffer res = new SharedBuffer();
        Scanner in = new Scanner(System.in);
        //create each thread for each producer and consumer and then start
        int m, n, x;
        System.out.print("Enter the number of rows for first matrix: ");
        m = in.nextInt();
        System.out.print("Enter the number of columns for first matrix (will be number of rows for " +
                "second matrix): ");
        n = in.nextInt();
        System.out.print("Enter the number of columns for second matrix: ");
        x = in.nextInt();

        Thread myDriver = new Thread(new Driver(m, n, x));
        Thread t1 = new Thread(new Producer(res, 1));
        Thread t2 = new Thread(new Consumer(res, 1));
        myDriver.start();
        t1.start();
        t2.start();
    }
}

