package equations;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;

public class EquationController {
    private final static float GRID_SIZE = 400F;
    private final EquationService service;
    private int size;
    private TextField[][] gridFields;

    @FXML private TextField accuracy;
    @FXML private GridPane gridMatrix;
    @FXML private TextField matrixSizeInput;

    public EquationController() {
        service = new EquationService();
    }

    @FXML
    private void enterData() {
        String textSize = matrixSizeInput.getText();

        try {
            size = Integer.parseInt(textSize);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number format error");
            alert.setContentText("Number is not valid, try again");

            alert.showAndWait();
            return;
        }
        if (size <= 2 || size >= 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Size is not in range error");
            alert.setContentText("Size is not in range 2..6, try again");

            alert.showAndWait();
            return;
        }

        gridFields = new TextField[size][size + 1];

        while (gridMatrix.getColumnConstraints().size() > 0) {
            gridMatrix.getColumnConstraints().remove(0);
        }

        gridMatrix.setPrefWidth(GRID_SIZE);
        gridMatrix.setPrefHeight(GRID_SIZE);
        double preferredSize = GRID_SIZE / (size + 1);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                TextField textField = new TextField();
                gridFields[i][j] = textField;
                textField.setPrefSize(preferredSize, preferredSize);
                gridMatrix.add(textField, j, i);
            }
        }
    }

    @FXML
    void solveByDirect() {
        double[][] data = new double[size][size];
        double[] result = new double[size];
        try {
            this.prepareData(data, result);
        } catch (NumberFormatException e) {
            return;
        }

        service.solveEquation(data, result, Method.DIRECT, WriteTo.FILE);
    }

    @FXML
    void solveByIteration() {
        double[][] data = new double[size][size];
        double[] result = new double[size];
        try {
            this.prepareData(data, result);
        } catch (NumberFormatException e) {
            return;
        }

        service.solveEquation(data, result, Method.ITERATION, WriteTo.FILE);
    }

    @FXML
    void solveByGaussSeidel() {
        double[][] data = new double[size][size];
        double[] result = new double[size];
        try {
            this.prepareData(data, result);
        } catch (NumberFormatException e) {
            return;
        }

        service.solveEquation(data, result, Method.GAUSS_SEIDEL, WriteTo.FILE);
    }

    @FXML
    void setAccuracy() {
        double value;
        try {
            value = Double.parseDouble(accuracy.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number format error");
            alert.setContentText("Number is not valid, try again");

            alert.showAndWait();
            throw e;
        }
        service.setAccuracy(BigDecimal.valueOf(value));
    }

    private void prepareData(double[][] data, double[] result) throws NumberFormatException {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                TextField textField = gridFields[i][j];
                double value;
                try {
                    value = Double.parseDouble(textField.getText());
                    if (j == (size)) {
                        result[i] = value;
                    } else {
                        data[i][j] = value;
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Number format error");
                    alert.setContentText("Number is not valid, try again");

                    alert.showAndWait();
                    throw e;
                }
            }
        }
    }
}
