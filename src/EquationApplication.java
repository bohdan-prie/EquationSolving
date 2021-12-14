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
       /* try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenes/MainScene.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

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
