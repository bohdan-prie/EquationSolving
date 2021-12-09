import equations.EquationService;
import equations.Methods;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class EquationApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void start(Stage primaryStage) {
        EquationService service = new EquationService();

        double[][] matrix = new double[][]{
                { 2, 6, -1 },
                { 4, -1, 1 },
                { 1, 2, -3 }
                /*{ 6, -1, 1 },
                { 1, 3, -1 },
                { -1, -3, 5 }*/
        };
        double[] result = new double[]/*{ 7, 2, 6 }*/{ 7, 4, 0 };

        service.solveEquation(matrix, result, Methods.GAUSS_SEIDEL);
    }
}
