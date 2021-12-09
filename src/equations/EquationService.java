package equations;

import java.util.Arrays;

public class EquationService {

    public void solveByIteration(double[][] data, double[] result) {

    }

    public void solveByGaussSeidel(double[][] data, double[] result) {
        if (data.length != result.length) {
            throw new IllegalArgumentException("Data are not consistent");
        }
        double accuracy = Math.pow(10, -3);

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

                totalX[j] = totalResult / data[j][j];
            }

            double currentAccuracy = 0;
            boolean end = false;
            for (int j = 0; j < totalX.length; j++) {
                double e = Math.abs(Math.pow(totalX[j], i) - Math.pow(pastTotalX[j], i - 1));
                if (e < accuracy) {
                    currentAccuracy = e;
                    end = false;
                    break;
                }
                end = true;
            }

            this.printX(totalX);
            System.out.printf("Accuracy = %f.7\n%n", currentAccuracy);
            if (end) {
                break;
            }
        }
    }

    private void printX(double[] xValues) {
        for (int i = 0; i < xValues.length; i++) {
            String value = String.format("x%d = %.10f", i + 1, xValues[i]);
            System.out.println(value);
        }
    }
}
