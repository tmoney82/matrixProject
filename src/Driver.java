/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatrixProject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

/**
 *
 * @author dapen
 */
public class Driver {
    public static void main(String[] args) throws IOException {
        
        Properties prop = new Properties();
        
        String propFileName = "C:\\Users\\dapen\\Documents\\CS405\\Project\\Project2\\CS405Project2\\src\\main\\java\\hdp\\edu\\wsu\\cs405project2\\config.config";
        
        FileInputStream inputStream = new FileInputStream(propFileName);

        //InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        }

        
        
        
        Scanner in = new Scanner(System.in);
        Random r = new Random();
        /*
        m = number of rows in the first matrix
        n = number of columns in the first matrix and number of rows in the second matrix
        x = number of columns in the second matrix
         */
        int m, n, x;
        // Prompt user for size of matrix
        System.out.print("Enter the number of rows for first matrix: ");
        m = in.nextInt();
        System.out.print("Enter the number of columns for first matrix (will be number of rows for " +
                "second matrix): ");
        n = in.nextInt();
        System.out.print("Enter the number of columns for second matrix: ");
        x = in.nextInt();
        int[][] a = new int[m][n];  // First matrix
        int[][] b = new int[n][x];  // Second matrix
        int[][] c = new int[m][x];  // Answer matrix

        // Create and print contents of "a" matrix
        System.out.println("A[");
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                a[i][j]=r.nextInt(10);
                System.out.print(a[i][j]+"\t");
            }

            System.out.print("\n");
        }
        System.out.println("]");

        // Create and print contents of "b" matrix
        System.out.println("\nB[");
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<x;j++)
            {
                b[i][j]=r.nextInt(10);
                System.out.print(b[i][j]+"\t");
            }

            System.out.print("\n");
        }
        System.out.println("]");

        //multiply matrix and put into the c matrix
        for(int i = 0; i< m; i++){
            for(int j = 0; j< x; j++){
                for(int k = 0; k< n; k++){
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }


        System.out.println("\nC[");
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<x;j++)
            {
                System.out.print(c[i][j]+"\t");
            }

            System.out.print("\n");
        }
        System.out.println("]");
    }
} //end Driver class

