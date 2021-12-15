package equations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class EquationService {

    private BigDecimal accuracy = new BigDecimal("0.001");

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * Method decides what method to use to solve given equation and where to print result,
     * based on given Method param and WriteTo param
     * @param data - given matrix
     * @param result - given result array
     * @param method - how to solve
     * @param writeTo - where to write result
     */
    public void solveEquation(double[][] data, double[] result, Method method, WriteTo writeTo) {
        if (method == null || data == null || result == null)
            return;
        switch (method) {
            case GAUSS_SEIDEL:
                this.solveByGaussSeidel(data, result, writeTo);
                break;
            case ITERATION:
                this.solveByIteration(data, result, writeTo);
                break;
            case DIRECT:
                this.solveByDirect(data, result, writeTo);
                break;
        }
    }

    /**
     * Method solves given equation by direct method
     * @param data - given matrix
     * @param result - given result
     * @param writeTo - where to write result
     */
    private void solveByDirect(double[][] data, double[] result, WriteTo writeTo) {
        this.sort(data, result);

        if (data.length != result.length) {
            throw new IllegalArgumentException("Data are not consistent");
        }

        for (int i = 1; i < data.length; i++) {
            for (int k = i; k < data.length; k++) {
                double numToDivideFor = 1;
                if (data[i - 1][i - 1] != 0) {
                    numToDivideFor = data[k][i - 1] / data[i - 1][i - 1];
                }
                for (int j = 0; j < data.length; j++) {
                    data[k][j] = BigDecimal.valueOf(data[k][j] - data[i - 1][j] * numToDivideFor)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                }
                result[k] = BigDecimal.valueOf(result[k] - result[i - 1] * numToDivideFor)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
            }
        }

        double[] totalX = new double[result.length];

        for (int i = result.length - 1; i >= 0; i--) {
            double sumAfter = 0;

            for (int j = i; j < result.length; j++) {
                if (j == i) {
                    continue;
                }
                sumAfter += data[i][j] * totalX[j];
            }
            totalX[i] = (result[i] - sumAfter) / data[i][i];
        }

        if (writeTo == WriteTo.CONSOLE) {
            for (int i = 0; i < data.length; i++) {
                System.out.print("    Ax" + (i + 1));
            }
            System.out.print("    D" + "     result");
            System.out.println();

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    System.out.printf("%7.3f", data[i][j]);
                }
                System.out.printf("%7.3f   x%d = %4.3f%n", result[i], (i + 1), totalX[i]);
            }
        }
    }

    /**
     * Method solves given equation by iteration method
     * @param data - given matrix
     * @param result - given result
     * @param writeTo - where to write result
     */
    private void solveByIteration(double[][] data, double[] result, WriteTo writeTo) {
        this.sort(data, result);
        if (data.length != result.length && data.length != data[0].length) {
            throw new IllegalArgumentException("Data are not consistent");
        }

        double[] totalX = new double[data[0].length];

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            double[] pastTotalX = Arrays.copyOf(totalX, totalX.length);
            for (int j = 0; j < result.length; j++) {
                double totalResult = result[j];

                /*
                    Calculate all data before and after current number
                 */
                for (int k = 0; k < result.length; k++) {
                    if (k == j) {
                        continue;
                    }
                    totalResult += -1 * data[j][k] * pastTotalX[k];
                }

                totalX[j] = BigDecimal.valueOf(totalResult / data[j][j]).setScale(5, RoundingMode.FLOOR).doubleValue();
            }

            boolean end = true;
            if (writeTo == WriteTo.CONSOLE) {
                System.out.printf("Iteration = %d \n", i);
            }
            for (int j = 0; j < totalX.length; j++) {
                BigDecimal e = BigDecimal.valueOf(Math.abs(totalX[j] - pastTotalX[j])).setScale(4, RoundingMode.FLOOR);
                if (writeTo == WriteTo.CONSOLE) {
                    System.out.println(String.format("\t x%d = %.5f", j + 1, totalX[j]) + "\t accuracy = " + e);
                }
                if (result[j] == 0 && totalX[j] == 0) {
                    continue;
                }
                /*
                    check if current accuracy is bigger than given
                    if yes, continue evaluating equation
                 */
                if (e.compareTo(accuracy) > 0) {
                    end = false;
                }
            }
            if (writeTo == WriteTo.CONSOLE) {
                System.out.println();
            }

            if (end) {
                break;
            }
        }
    }

    /**
     * Method solves given equation by GaussSeidel method
     * @param data - given matrix
     * @param result - given result
     * @param writeTo - where to write result
     */
    private void solveByGaussSeidel(double[][] data, double[] result, WriteTo writeTo) {
        this.sort(data, result);
        if (data.length != result.length) { // check if matrix and result size are the same
            throw new IllegalArgumentException("Data are not consistent");
        }

        double[] totalX = new double[result.length];

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            double[] pastTotalX = Arrays.copyOf(totalX, totalX.length);
            for (int j = 0; j < result.length; j++) {
                double totalResult = result[j];

                /*
                    Calculate all data before and after current number
                 */
                for (int k = 0; k < result.length; k++) {
                    if (k == j) {
                        continue;
                    }
                    totalResult += -1 * data[j][k] * totalX[k];
                }

                totalX[j] = BigDecimal.valueOf(totalResult / data[j][j]).setScale(5, RoundingMode.FLOOR).doubleValue();
            }

            boolean end = true;
            if (writeTo == WriteTo.CONSOLE) {
                System.out.printf("Iteration = %d \n", i);
            }
            for (int j = 0; j < totalX.length; j++) {
                BigDecimal e = BigDecimal.valueOf(Math.abs(totalX[j] - pastTotalX[j])).setScale(4, RoundingMode.FLOOR);
                if (writeTo == WriteTo.CONSOLE) {
                    System.out.println(String.format("\t x%d = %.5f", j + 1, totalX[j]) + "\t accuracy = " + e);
                }
                if (result[j] == 0 && totalX[j] == 0) {
                    continue;
                }
                /*
                    check if current accuracy is bigger than given
                    if yes, continue evaluating equation
                 */
                if (e.compareTo(accuracy) > 0) {
                    end = false;
                }
            }
            if (writeTo == WriteTo.CONSOLE) {
                System.out.println();
            }
            if (end) {
                break;
            }
        }
    }

    /**
     * Method sorts matrix and result to max elements by main diagonal
     * @param data - given matrix
     * @param result - given result
     */
    private void sort(double[][] data, double[] result) {
        for (int j = 0; j < data.length; j++) {
            double max = Math.abs(data[j][0]);
            int maxIndex = 0;
            for (int i = 0; i < data.length; i++) {
                if (Math.abs(data[i][j]) > max) {
                    max = Math.abs(data[i][j]);
                    maxIndex = i;
                }
            }
            if (j != maxIndex) {
                double[] temp = data[j];
                data[j] = data[maxIndex];
                data[maxIndex] = temp;

                double temp1 = result[j];
                result[j] = result[maxIndex];
                result[maxIndex] = temp1;
            }
        }
    }
}