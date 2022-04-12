/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

import static MatrixProject.RunMain.generateMatrix;
import static MatrixProject.RunMain.multiplyMatrix;
import static MatrixProject.RunMain.printMatrix;
import static com.sun.org.glassfish.external.amx.AMXUtil.prop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author dapen
 */
public class ProducerConsumer {
    public static void main(String[] args) throws IOException {
            
        Properties prop = new Properties();
            
        try {

            FileInputStream input_file = new FileInputStream("C:\\Users\\dapen\\Documents\\CS405\\Project\\Project2\\CS405Project2\\src\\main\\java\\hdp\\edu\\wsu\\cs405project2\\config.config"); //input the file
            prop.load(input_file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            //get the values of rows and columns
            String num_rows = prop.getProperty("M");
            String num_columns = prop.getProperty("N");
            String num_columnsB = prop.getProperty("P");
            String MaxBuffSize = prop.getProperty("MaxBuffSize");
            String SplitSize = prop.getProperty("SplitSize");
            String NumConsumer = prop.getProperty("NumConsumer");
            String MaxProduceSleepTime = prop.getProperty("MaxProduceSleepTime");
            String MaxConsumerSleepTime = prop.getProperty("MaxConsumerSleepTime");

            //convert string values to int
            /*
            m = number of rows in matrix a
            n = number of columns in matrix a and rows in matrix b
            p = number of columns in matrix b
             */
            int m = Integer.parseInt(num_rows);
            int n = Integer.parseInt(num_columns);
            int p = Integer.parseInt(num_columnsB);
            int maxBuffSize = Integer.parseInt(MaxBuffSize);
            int splitSize = Integer.parseInt(SplitSize);
            int numConsumer = Integer.parseInt(NumConsumer);
            int maxProduceSleepTime = Integer.parseInt(MaxProduceSleepTime);
            int maxConsumerSleepTime = Integer.parseInt(MaxConsumerSleepTime);


            int[][] matrix_A = generateMatrix(m, n); //matrix a
            int[][] matrix_B = generateMatrix(n, p); //matrix b
            int[][] matrix_result = multiplyMatrix(matrix_A, matrix_B, m, n, p); //matrix multiplication result

/*
            printMatrix(matrix_A);
            System.out.println();
            printMatrix(matrix_B);
            printMatrix(matrix_result);

            // Not sure what this matrixTraversal is for it looks like it does the same thing as printMatrix
            //matrixTraversal(matrix_A);
*/
        
		//Create an instance of the Bounded Buffer
	BoundedBuffer buff = new BoundedBuffer(maxBuffSize);
	//Create an instance of the Producer class with the passed instance of Bounded Buffer
	Thread t1 = new Thread(new Producer(matrix_A, matrix_B, splitSize, buff, maxProduceSleepTime));
        //Create an instance of the Consumer class with the passed instance of Bounded Buffer
	//Thread t2 = new Thread(new Consumer(buff));
	t1.start();
	//t2.start();
	}
}