package pl.ksr.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;
import pl.ksr.lingustic.QuantifierManager;
import pl.ksr.lingustic.VariableManager;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class MainController {

    @FXML
    private ListView<String> summaries;
    @FXML
    private RadioButton quantifierRadio;
    @FXML
    private ComboBox<String> quantifiersBox;
    @FXML
    private ComboBox<String> summaryTypes;
    @FXML
    private ComboBox<String> summarizerBox;
    @FXML
    private ComboBox<String> labelsBox1;
    @FXML
    private ComboBox<String> labelsBox2;
    @FXML
    private ComboBox<String> qualifierBox;
    @FXML
    private ListView<String> summarizersList;
    @FXML
    private ListView<String> qualifiersList;
    @FXML
    private TextField weightsField;
    @FXML
    private Menu menu;
    @FXML
    private Button generateButton;


    public void initialize() {
        summaries.getItems().add("Majority of flights with minor delays took place in spring [0.6]");
        summaries.getItems().add("Majority of flights took place in spring [0.4]");
        summaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        getQuantifiersNames();
        initSummaryTypes();
        getVariables();

        weightsField.setText("0.07, 0.07, 0.07, 0.07, 0.07, 0.07, 0.07, 0.07, 0.07, 0.3");
    }

    public void saveSummaries() {
        List<String> selected = summaries.getSelectionModel().getSelectedItems();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showSaveDialog(null);
        if (file != null)
            FileManager.writeToFile(file.getAbsolutePath(), selected);
    }

    public void getQuantifiersNames() {
        quantifiersBox.getItems().clear();
        if (quantifierRadio.isSelected()) {
            quantifiersBox.getItems().addAll(QuantifierManager.loadRelativeQuantifiers().stream().map(LinguisticQuantifier::getLabel).toList());
        } else {
            quantifiersBox.getItems().addAll(QuantifierManager.loadAbsoluteQuantifiers().stream().map(LinguisticQuantifier::getLabel).toList());
        }
    }

    public void getVariables() {
        summarizerBox.getItems().addAll(VariableManager.loadVariables().stream().map(LinguisticVariable::getVariableName).toList());
        qualifierBox.getItems().addAll(VariableManager.loadVariables().stream().map(LinguisticVariable::getVariableName).toList());
    }

    public void getLabels() {
//        System.out.println(summarizerBox.getSelectionModel().getSelectedItem());

//        String selectedItem = summarizerBox.getSelectionModel().getSelectedItem();
//        System.out.println(VariableManager.loadVariables()
//                .stream()
//                .filter(e -> Objects.equals(e.getVariableName(), selectedItem))
//                .map(LinguisticVariable::getLabelsNames).toList());

        List<String> labels1 = List.of(
                "spring", "summer", "fall", "winter"
        );
//        List<String> labels2 = List.of(
//                "morning", "forenoon", "noon", "afternoon", "evening", "night", "midnight"
//        );

        List<String> labels2 = List.of(
                "early", "normal", "minor delays", "major delays", "postponed"
        );

        labelsBox1.getItems().addAll(labels1);
        labelsBox2.getItems().addAll(labels2);
    }

    public void addToSummarizers() {
        summarizersList.getItems().add(summarizerBox.getValue() + " - " + labelsBox1.getValue());
    }

    public void addToQuantifiers() {
        qualifiersList.getItems().add(qualifierBox.getValue() + " - " + labelsBox2.getValue());
    }

    public void removeFromSummarizers() {
        summarizersList.getItems().remove(summarizersList.getSelectionModel().getSelectedItem());
    }
    public void removeFromQuantifiers() {
        qualifiersList.getItems().remove(qualifiersList.getSelectionModel().getSelectedItem());
    }

    public void initSummaryTypes() {
        String[] types = {
                "Single-subject", "Multi-subject"
        };
        summaryTypes.getItems().addAll(types);
    }

    @FXML
    public void switchToAdvanced() throws IOException {
        Stage primaryStage = (Stage) generateButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("advanced-view.fxml"));

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}