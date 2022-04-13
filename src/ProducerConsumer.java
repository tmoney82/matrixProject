/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

import static com.sun.org.glassfish.external.amx.AMXUtil.prop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

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
            


           // printMatrix(matrix_A);
            //System.out.println();
            //printMatrix(matrix_B);


            // Not sure what this matrixTraversal is for it looks like it does the same thing as printMatrix
            //matrixTraversal(matrix_A);

        
		//Create an instance of the Bounded Buffer
	BoundedBuffer buff = new BoundedBuffer(maxBuffSize);
	//Create an instance of the Producer class with the passed instance of Bounded Buffer
	Thread t1 = new Thread(new Producer(matrix_A, matrix_B, splitSize, buff, maxProduceSleepTime));
        //Create an instance of the Consumer class with the passed instance of Bounded Buffer
	Thread t2 = new Thread(new Consumer(buff, maxConsumerSleepTime, m, p));
	t1.start();
	t2.start();
	}
    
    //generate the matrix
    public static int[][] generateMatrix(int num_row, int num_column) {
        int[][] matrix = new int[num_row][num_column];

        Random r = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = r.nextInt(10);
            }

        }
        return matrix;
    }

    //multiply the matrix
    public static int[][] multiplyMatrix(int[][] matrix_one, int[][] matrix_two) {
        int [][] result = new int [matrix_one.length][matrix_two[0].length];

        for(int i = 0; i< matrix_one.length; i++){
            for(int j = 0; j< matrix_two[0].length; j++){
                for(int k = 0; k< matrix_two.length; k++){
                    result[i][j] += matrix_one[i][k] * matrix_two[k][j];
                }
            }
        }
        return result;
    }

    public static void printMatrix(int[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");

            }
            System.out.println();
        }

    }
    public static int [][] matrixTraversal(int [][] m1){

        for( int i = 0; i < m1.length; i++){
            for(int j = 0; j < m1[i].length; j++){
                System.out.print(m1[i][j] + " ");
            }
            System.out.println();
        }
        return m1;

    }
    
    
}