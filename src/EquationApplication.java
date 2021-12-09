import equations.EquationService;
import equations.Methods;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EquationApplication extends Application {
//    private final EquationService service;
//
//    EquationApplication() {
//        service = new EquationService();
//    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/MainScene.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* double[][] matrix = new double[][]{
                { 2, 6, -1 },
                { 4, -1, 1 },
                { 1, 2, -3 }
                *//*{ 6, -1, 1 },
                { 1, 3, -1 },
                { -1, -3, 5 }*//*
        };
        double[] result = new double[]*//*{ 7, 2, 6 }*//*{ 7, 4, 0 };

        service.solveEquation(matrix, result, Methods.GAUSS_SEIDEL);*/
    }
}
