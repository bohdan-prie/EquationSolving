package equations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EquationController {
    private final static float GRID_SIZE = 800F;
    private final EquationService service;
    private int size;
    private TextField[][] gridFields;

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
        if (size <= 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Size is too short error");
            alert.setContentText("Size is too short, try again");

            alert.showAndWait();
            return;
        }

        gridFields = new TextField[size][size + 1];

        while (gridMatrix.getColumnConstraints().size() > 0) {
            gridMatrix.getColumnConstraints().remove(0);
        }

        gridMatrix.setPrefWidth(GRID_SIZE);
        gridMatrix.setPrefHeight(GRID_SIZE);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                TextField textField = new TextField();
                gridFields[i][j] = textField;
                gridMatrix.add(textField, j, i);
            }
        }
    }

    @FXML
    void solveByIteration() {
        double[][] data = new double[size][size];
        double[] result = new double[size];
        this.prepareData(data, result);

        service.solveEquation(data, result, Methods.ITERATION);
    }

    @FXML
    void solveByGaussSeidel() {
        double[][] data = new double[size][size];
        double[] result = new double[size];
        this.prepareData(data, result);

        service.solveEquation(data, result, Methods.GAUSS_SEIDEL);
    }

    private void loadScreen() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/InfectionScene.fxml")));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) matrixSizeInput.getScene().getWindow();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Some error");
            alert.setContentText("Some error happened");

            alert.showAndWait();
        }
    }

    private void prepareData(double[][] data, double[] result) {
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
                    return;
                }
            }
        }
    }
}
