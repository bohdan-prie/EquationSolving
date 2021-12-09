import equations.EquationService;
import javafx.application.Application;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.util.Objects;

public class EquationApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    EquationService service = new EquationService();

    @FXML
    public void start(Stage primaryStage) {
        double[][] matrix = new double[][]{
                { 4, -1, 1 },
                { 2, 6, -1 },
                { 1, 2, -3 }
        };
        double[] result = new double[]{ 4, 7, 0 };

        service.solveByGaussSeidel(matrix, result);
    }
}
