import equations.EquationService;
import equations.Method;
import equations.WriteTo;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class EquationApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void start(Stage primaryStage) {
        while (true) {
            System.out.println("\nWrite what you would like to do");
            System.out.println("1) Load from console");
            System.out.println("2) Load from modal screen");
            System.out.println("3) Exit");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String data;
            try {
                data = reader.readLine();
            } catch (IOException e) {
                System.out.println("Number is not valid, try again");
                continue;
            }
            if (data.equals("1")) {
                this.loadConsole();
            } else if (data.equals("2")) {
                this.loadMainScreen(primaryStage);
            } else if (data.equals("3")) {
                break;
            }
        }
    }

    private void loadMainScreen(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/MainScene.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConsole() {
        EquationService service = new EquationService();
        double[][] matrix = {
                { 2, 0, 0 },
                { 0, 0, -1 },
                { 0, 1, 0 }
        };
        double[][] matrix1 = new double[][]{
                { 4, -1, 1 },
                { 2, 6, -1 },
                { 1, 2, -3 }
        };
        double[] result = { 1, 8, -3 };
        double[] result1 = { 7, 4, 0 };

        service.solveEquation(matrix, result, Method.GAUSS_SEIDEL, WriteTo.CONSOLE);
    }
}
