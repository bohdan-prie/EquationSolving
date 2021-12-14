import equations.EquationService;
import equations.Methods;
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
        System.out.println("Write how you would like to load");
        System.out.println("1) From console");
        System.out.println("2) From modal screen");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String data;
        try {
            data = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (data.equals("1")) {
            this.loadConsole();
        } else if (data.equals("2")) {
            this.loadMainScreen(primaryStage);
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
        double[][] matrix = new double[][]{
                /*{ 4, -1, 1 },
                { 2, 6, -1 },
                { 1, 2, -3 }*/
                { -3, -1, 2 },
                { 2, 1, -1 },
                { -2, 1, 2 }
        };
        double[] result = new double[]{ -11, 8, -3 }/*{ 7, 4, 0 }*/;

        service.solveEquation(matrix, result, Methods.DIRECT);

    }
}
