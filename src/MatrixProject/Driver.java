/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author dapen
 */

public class Driver {
    public static void main(String[] args) throws IOException {
            
        Properties prop = new Properties();
        Scanner in = new Scanner(System.in);

        // Get the name of configuration file from user
        System.out.print("Enter the name of the configuration file you want to use: ");
        String configFile = in.nextLine();
            
        try {
            FileInputStream input_file = new FileInputStream(configFile); //input the file
            prop.load(input_file);
        } catch (FileNotFoundException e) {
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

        // Make sure there is a configuration data provides valid parameters
        if (m == 0 || n == 0 || p == 0 || maxBuffSize != 5 || splitSize == 0 || numConsumer == 0 ||
            maxProduceSleepTime != 20 || maxConsumerSleepTime != 80){
            System.out.println("Check the config file and try again.");
            System.exit(0);
        }

        int[][] matrix_A = generateMatrix(m, n); // matrix a
        int[][] matrix_B = generateMatrix(n, p); // matrix b
        int[][] matrix_C = new int[m][p];  // This will be the result matrix for sequential matrix multiplication


        printMatrix(matrix_A, 1);
        System.out.println();
        printMatrix(matrix_B, 2);
        System.out.println();

		//Create an instance of the Bounded Buffer
	    BoundedBuffer buff = new BoundedBuffer(maxBuffSize);
	    //Create an instance of the Producer class with the passed instance of Bounded Buffer
	    Thread t1 = new Thread(new Producer(matrix_A, matrix_B, splitSize, buff, maxProduceSleepTime));
        //Create an instance of the Consumer class with the passed instance of Bounded Buffer
	    Thread t2 = new Thread(new Consumer(buff, maxConsumerSleepTime, m, p, matrix_A, matrix_B, matrix_C));
	    t1.start();
	    t2.start();
    }
    
    // Generate the matrix
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

    // Multiply the matrix
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

    public static void printMatrix(int[][] matrix, int matrix_num) {
        System.out.println();
        if (matrix_num == 1){
            System.out.println("First matrix (A- " + matrix.length + "x" + matrix[0].length + "):");
            for (int i = 0; i < matrix.length; i++) {
                if (i == 0) {
                    System.out.print("[ [");
                }else {
                    System.out.print("[");
                }
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                if (i == matrix.length -1){
                    System.out.print("] ]");
                }else {
                    System.out.print("],");
                    System.out.println();
                }
            }
        }else {
            System.out.println("Second matrix (B- " + matrix.length + "x" + matrix[0].length + "):");
            for (int i = 0; i < matrix.length; i++) {
                if (i == 0) {
                    System.out.print("[ [");
                }else {
                    System.out.print("[");
                }
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                if (i == matrix.length -1){
                    System.out.print("] ]");
                }else {
                    System.out.print("],");
                    System.out.println();
                }
            }
        }
    }
}