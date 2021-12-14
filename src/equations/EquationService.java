package equations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class EquationService {

    private BigDecimal accuracy = new BigDecimal("0.001");

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    public void solveEquation(double[][] data, double[] result, Methods method) {
        if (method == null || data == null || result == null)
            return;
        switch (method) {
            case GAUSS_SEIDEL:
                this.solveByGaussSeidel(data, result);
                break;
            case ITERATION:
                this.solveByIteration(data, result);
                break;
            case DIRECT:
                this.solveByDirect(data, result);
                break;
        }
    }

    private void solveByDirect(double[][] data, double[] result) {
        this.sort(data, result);

        if (data.length != result.length) {
            throw new IllegalArgumentException("Data are not consistent");
        }

        for (int i = 1; i < data.length; i++) {
            for (int k = i; k < data.length; k++) {
                double numToDivideFor = data[k][i - 1] / data[i - 1][i - 1];
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

        double[] xResults = new double[result.length];

        for (int i = result.length - 1; i >= 0; i--) {
            double sumAfter = 0;

            for (int j = i; j < result.length; j++) {
                if (j == i) {
                    continue;
                }
                sumAfter += data[i][j] * xResults[j];
            }
            xResults[i] = (result[i] - sumAfter) / data[i][i];
        }

        for (int i = 0; i < data.length; i++) {
            System.out.print("    Ax" +  (i + 1));
        }
        System.out.print("    D" + "     result");
        System.out.println();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                System.out.printf("%7.3f", data[i][j]);
            }
            System.out.printf("%7.3f   x%d = %4.3f%n", result[i], (i + 1), xResults[i]);
        }
    }

    private void solveByIteration(double[][] data, double[] result) {
        this.sort(data, result);
        if (data.length != result.length && data.length != data[0].length) {
            throw new IllegalArgumentException("Data are not consistent");
        }

        double[] totalX = new double[data[0].length];

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            double[] pastTotalX = Arrays.copyOf(totalX, totalX.length);
            for (int j = 0; j < result.length; j++) {
                double totalResult = result[j];

                for (int k = 0; k < result.length; k++) {
                    if (k == j) {
                        continue;
                    }
                    totalResult += -1 * data[j][k] * pastTotalX[k];
                }

                totalX[j] = BigDecimal.valueOf(totalResult / data[j][j]).setScale(5, RoundingMode.FLOOR).doubleValue();
            }

            boolean end = true;
            System.out.printf("Iteration = %d \n", i);
            for (int j = 0; j < totalX.length; j++) {
                BigDecimal e = BigDecimal.valueOf(Math.abs(totalX[j] - pastTotalX[j])).setScale(4, RoundingMode.FLOOR);
                System.out.println(String.format("\t x%d = %.5f", j + 1, totalX[j]) + "\t accuracy = " + e);
                if (result[j] == 0 && totalX[j] == 0) {
                    continue;
                }
                if (e.compareTo(accuracy) > 0) {
                    end = false;
                }
            }
            System.out.println();

            if (end) {
                break;
            }
        }
    }

    private void solveByGaussSeidel(double[][] data, double[] result) {
        this.sort(data, result);
        if (data.length != result.length) {
            throw new IllegalArgumentException("Data are not consistent");
        }

        double[] totalX = new double[result.length];

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            double[] pastTotalX = Arrays.copyOf(totalX, totalX.length);
            for (int j = 0; j < result.length; j++) {
                double totalResult = result[j];

                for (int k = 0; k < result.length; k++) {
                    if (k == j) {
                        continue;
                    }
                    totalResult += -1 * data[j][k] * totalX[k];
                }

                totalX[j] = BigDecimal.valueOf(totalResult / data[j][j]).setScale(5, RoundingMode.FLOOR).doubleValue();
            }

            boolean end = true;
            System.out.printf("Iteration = %d \n", i);
            for (int j = 0; j < totalX.length; j++) {
                BigDecimal e = BigDecimal.valueOf(Math.abs(totalX[j] - pastTotalX[j])).setScale(4, RoundingMode.FLOOR);
                System.out.println(String.format(" \t x%d = %.5f", j + 1, totalX[j]) + "\t accuracy = " + e);
                if (result[j] == 0 && totalX[j] == 0) {
                    continue;
                }
                if (e.compareTo(accuracy) > 0) {
                    end = false;
                }
            }
            System.out.println();
            if (end) {
                break;
            }
        }
    }

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