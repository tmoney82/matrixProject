import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
public class RunMain {

    public static void main(String[] args) throws IOException {


        //create producer thread
        Thread producer_thread = new Thread();
        producer_thread.start();

        Thread consumer_thread = new Thread();
        consumer_thread.start();

        Thread main_thread = Thread.currentThread();

        //main_thread.sleep(0);

        Properties prop = new Properties();


        try {

            FileInputStream input_file = new FileInputStream("config"); //input the file
            prop.load(input_file);

            //get the values of rows and columns
            String num_rows = prop.getProperty("M");
            String num_columns = prop.getProperty("N");
            String num_columnsB = prop.getProperty("P");

            //convert string values to int
            int m = Integer.parseInt(num_rows);
            int n = Integer.parseInt(num_columns);
            int p = Integer.parseInt(num_columnsB);


            int[][] matrix_A = generateMatrix(m, n); //matrix a
            int[][] matrix_B = generateMatrix(n, p); //matrix b
            //int[][] matrix_result = multiplyMatrix(matrix_A, matrix_B); //matrix multiplication result


            printMatrix(matrix_A);
            System.out.println();
            printMatrix(matrix_B);

            // Not sure what this matrixTraversal is for it looks like it does the same thing as printMatrix
            //matrixTraversal(matrix_A);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    //print the matrix

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
//    public static int[][] multiplyMatrix(int[][] matrix_one, int[][] matrix_two) {
//        int [][] result = new int [matrix_one.length][matrix_two[0].length];
//
//        for (int i = 0; i < matrix_one.length; i++) {
//            for (int j = 0; j < matrix_two[0].length; j++) {
//                for(int k = 0; k < matrix_one[0].length; k++) {
//                    result = result + matrix_one[i][k] * matrix_two[k][j];
//                }
//
//            }
//
//        }
//        return result;
//    }
    public static void printMatrix(int[][] matrix) {
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
