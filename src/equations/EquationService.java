package equations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class EquationService {

    public void solveEquation(double[][] data, double[] result, Methods method) {
        if (method == null)
            return;
        switch (method) {
            case GAUSS_SEIDEL:
                this.solveByGaussSeidel(data, result);
                break;
            case ITERATION:
                this.solveByIteration(data, result);
                break;
        }
    }

    private void solveByIteration(double[][] data, double[] result) {
        if (data.length != result.length) {
            throw new IllegalArgumentException("Data are not consistent");
        }
        BigDecimal accuracy = new BigDecimal("0.001");

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
            for (int j = 0; j < totalX.length; j++) {
                BigDecimal e = BigDecimal.valueOf(Math.abs(totalX[j] - pastTotalX[j])).setScale(4, RoundingMode.FLOOR);
                System.out.println(String.format("Iteration = %d \t x%d = %.5f", i, j + 1, totalX[j]) + "\t accuracy = " + e);
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
        if (data.length != result.length) {
            throw new IllegalArgumentException("Data are not consistent");
        }
        BigDecimal accuracy = new BigDecimal("0.001");

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
            for (int j = 0; j < totalX.length; j++) {
                BigDecimal e = BigDecimal.valueOf(Math.abs(totalX[j] - pastTotalX[j])).setScale(4, RoundingMode.FLOOR);
                System.out.println(String.format("Iteration = %d \t x%d = %.5f", i,  j + 1, totalX[j]) + "\t accuracy = " + e);
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
}