package sudoku;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import ventana.Ventana;

public class Sudoku extends Ventana {

    public Sudoku(String titulo) {
        super(titulo);
    }

    public int[][] initSudoku(int filasCol) {
        int[][] matrix = new int[9][9];
        for (int rowCounter = 0; rowCounter < filasCol; rowCounter++) {
            for (int colCounter = 0; colCounter < filasCol; colCounter++) {
                int number = (int) (colCounter / 1 + 1 + (rowCounter * 3) + Math.floor(rowCounter / 3) % 3);
                if (number > filasCol) {
                    number = number % filasCol;
                }
                if (number == 0) {
                    number = filasCol;
                }
                matrix[rowCounter][colCounter] = number;
            }
        }

        // Switching rows
        int row1;
        int row2;
        for (int no = 0; no < filasCol; no += 3) {

            for (int no2 = 0; no2 < 3; no2++) {
                row1 = (int) Math.floor(Math.random() * 3);
                row2 = (int) Math.floor(Math.random() * 3);
                while (row2 == row1) {
                    row2 = (int) Math.floor(Math.random() * 3);
                }
                row1 = row1 + no;
                row2 = row2 + no;
                int[] tmpMatrix = matrix[row1];
                matrix[row1] = matrix[row2];
                matrix[row2] = tmpMatrix;
            }
        }

        // Switching columns
        int col1;
        int col2;
        for (int no = 0; no < filasCol; no += 3) {
            for (int no2 = 0; no2 < 3; no2++) {
                col1 = (int) Math.floor(Math.random() * 3);
                col2 = (int) Math.floor(Math.random() * 3);
                while (col2 == col1) {
                    col2 = (int) Math.floor(Math.random() * 3);
                }
                col1 = col1 + no;
                col2 = col2 + no;

                int tmpMatrixValue;
                for (int[] matrix1 : matrix) {
                    tmpMatrixValue = matrix1[col1];
                    matrix1[col1] = matrix1[col2];
                    matrix1[col2] = tmpMatrixValue;
                }
            }
        }
        return matrix;
    }

    /*public static void main(String[] args) {
        Sudoku sudoku = new Sudoku("Sudoku");
        int[][] matrizSudoku = sudoku.initSudoku(9);
        //sudoku.pintarSudoku(matrizSudoku);
        for (int[] is : matrizSudoku) {
            System.out.println("");
            for (int i : is) {
                System.out.print(i + ",");
            }
        }

    }*/

    private void pintarSudoku(int[][] matrizSudoku) {
        Container contentPane = this.getContentPane();
        JPanel panelSudoku = new JPanel(new GridLayout(3, 3, 2, 2));
        JPanel panel = new JPanel(new GridLayout(3, 3, 2, 2));
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                panel.add(new Button("" + matrizSudoku[y][x]));
            }
        }
        panelSudoku.add(panel);
        panel = new JPanel(new GridLayout(3, 3, 2, 2));
        for (int y = 3; y < 6; y++) {
            for (int x = 3; x < 6; x++) {
                panel.add(new Button("" + matrizSudoku[y][x]));
            }
        }
        panelSudoku.add(panel);
        contentPane.add(panelSudoku);
        this.setVisible(true);

    }

}
