package pl.ksr.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdvancedController {

    @FXML
    private ComboBox<String> objectToAdd;
    @FXML
    private ComboBox<String> linguisticVariableField;
    @FXML
    private Text pointA;
    @FXML
    private Text pointB;
    @FXML
    private Text pointC;
    @FXML
    private Text pointD;
    @FXML
    private Text averageM;
    @FXML
    private Text standardDeviation;
    @FXML
    private TextField pointAField;
    @FXML
    private TextField pointBField;
    @FXML
    private TextField pointCField;
    @FXML
    private TextField pointDField;
    @FXML
    private TextField averageMField;
    @FXML
    private TextField standardDeviationField;

    @FXML
    private Menu menu;
    @FXML
    private Button submitButton;

    public void initialize() {
        setObjectToAdd();
        setInvisible();
        linguisticVariableField.setValue("flight start");
    }

    public void setObjectToAdd() {
        objectToAdd.getItems().addAll(List.of("Summarizer", "Quantifier", "Qualifier"));
    }

    public void setInvisible() {
        averageM.setVisible(false);
        averageMField.setVisible(false);

        standardDeviation.setVisible(false);
        standardDeviationField.setVisible(false);
    }
    @FXML
    public void switchToMainView() throws IOException {
        Stage primaryStage = (Stage) submitButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}