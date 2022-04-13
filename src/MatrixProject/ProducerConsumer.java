/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

import static MatrixProject.RunMain.generateMatrix;
import static MatrixProject.RunMain.multiplyMatrix;

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

            FileInputStream input_file = new FileInputStream("config"); //input the file
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
        int[][] matrix_result = new int[m][p]; //matrix multiplication result

        System.out.print("First Matrix:");
        printMatrix(matrix_A);
        System.out.println();
        System.out.print("Second Matrix:");
        printMatrix(matrix_B);
        System.out.println();
        //printMatrix(matrix_result);




        //Create an instance of the Bounded Buffer
        BoundedBuffer buff = new BoundedBuffer(maxBuffSize);
        //Create an instance of the Producer class with the passed instance of Bounded Buffer
        Thread t1 = new Thread(new Producer(matrix_A, matrix_B, splitSize, buff, maxProduceSleepTime));
        //Create an instance of the Consumer class with the passed instance of Bounded Buffer
        Thread t2 = new Thread(new Consumer(buff, maxConsumerSleepTime));
        t1.start();
        t2.start();


    }

    public static void printMatrix(int[][] matrix){
        System.out.println("\n[");
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix[0].length;j++)
            {
                System.out.print(matrix[i][j]+"\t");
            }

            System.out.print("\n");
        }
    }
}