package pl.ksr.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.*;
import pl.ksr.summary.Subject;
import pl.ksr.summary.Summary;
import pl.ksr.summary.SummaryGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainController {

    @FXML
    private TableView<SummaryRow> summaries;
    @FXML
    private TableColumn<SummaryRow, String> summaryColumn;
    @FXML
    private TableColumn<SummaryRow, Double> tColumn;
    @FXML
    private TableColumn<SummaryRow, Double> t1Column;
    @FXML
    private TableColumn<SummaryRow, Double> t2Column;
    @FXML
    private TableColumn<SummaryRow, Double> t3Column;
    @FXML
    private TableColumn<SummaryRow, Double> t4Column;
    @FXML
    private TableColumn<SummaryRow, Double> t5Column;
    @FXML
    private TableColumn<SummaryRow, Double> t6Column;
    @FXML
    private TableColumn<SummaryRow, Double> t7Column;
    @FXML
    private TableColumn<SummaryRow, Double> t8Column;
    @FXML
    private TableColumn<SummaryRow, Double> t9Column;
    @FXML
    private TableColumn<SummaryRow, Double> t10Column;
    @FXML
    private TableColumn<SummaryRow, Double> t11Column;



    @FXML
    private ComboBox<String> summarizerBox;
    @FXML
    private ComboBox<String> labelsBox;

    @FXML
    private ListView<String> summarizersList;

    @FXML
    private TextField weightsField;

    @FXML
    private Button generateButton;

    private List<Label> currentSummarizers;
    private List<pl.ksr.summary.Summary> currentSummaries;
    private List<Double> weights;
    private List<LinguisticVariable> allVariables;
    private List<LinguisticQuantifier> allQuantifiers;
    private LinguisticVariable selectedSummarizer;
    private LinguisticVariable selectedQualifier;
    private String[] types;


    public void initialize() {
        summaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        summarizersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        getVariables();
        setCellsValuesFactors();

        allQuantifiers = new ArrayList<>();
        allQuantifiers.addAll(QuantifierManager.loadAbsoluteQuantifiers());
        allQuantifiers.addAll(QuantifierManager.loadRelativeQuantifiers());

        weightsField.setText("0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09");

        weights = parseWeightsField();
    }

    public void saveSummaries() {
        List<String> selected = summaries.getSelectionModel().getSelectedItems()
                .stream().map(SummaryRow::toString).toList();
        System.out.println(selected);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showSaveDialog(null);
        if (file != null)
            FileManager.writeToFile(file.getAbsolutePath(), selected);
    }


    public void getVariables() {
        allVariables = VariableManager.loadVariables();

        summarizerBox.getItems().addAll(allVariables.stream().map(LinguisticVariable::getVariableName).toList());
    }

    public void pickAttribute() {
        String currSummarizer = summarizerBox.getValue();

        var summarizerLabels = allVariables.stream().filter(e -> e.getVariableName().equals(currSummarizer)).toList();

        if (!labelsBox.getItems().isEmpty())
            labelsBox.getItems().clear();

        selectedSummarizer = summarizerLabels.get(0);

        labelsBox.getItems().addAll(selectedSummarizer.getLabelsNames());
    }

    public void addToAttributesList() {
        Label label = null;
        for (Label l : selectedSummarizer.getLabels())
            if (l.getLabelName().equals(labelsBox.getValue()))
                label = l;

        if (label != null) {
            if (currentSummarizers == null) {
                currentSummarizers = new ArrayList<>();
            }
            currentSummarizers.add(label);
            summarizersList.getItems().add(labelsBox.getValue());
        }
        currentSummarizers.forEach(e -> System.out.println(e.getLabelName()));
    }



    public void removeFromAttributesList() {
        for (Label label : currentSummarizers) {
            for (String item : summarizersList.getSelectionModel().getSelectedItems()) {
                if (label.getLabelName().equals(item)) {
                    currentSummarizers.remove(label);
                    break;
                }
            }
            if (currentSummarizers.isEmpty())
                break;
        }

//        currentSummarizers.removeIf(label -> label.getLabelName().equals(summarizersList.getSelectionModel().getSelectedItems()));
        currentSummarizers.forEach(e -> System.out.println(e.getLabelName()));
        summarizersList.getItems().remove(summarizersList.getSelectionModel().getSelectedItem());
    }




    public List<Double> parseWeightsField() {
        List<Double> weights = new ArrayList<>();
        for (String field : weightsField.getText().split(", ")) {
//            weights.add(Double.parseDouble(field));
            weights.add(1d / 11);
        }
        return weights;
    }

    public void generateSummaries() {
        List<Summary> summaries = new ArrayList<>();

        Subject subject = new Subject();

        for (LinguisticQuantifier quantifier : allQuantifiers) {
            Summary summary = SummaryGenerator.generateSingleSubjectFirstForm(quantifier, subject, currentSummarizers);
            summaries.add(summary);
        }

        if (currentSummaries != null)
            currentSummaries.addAll(summaries);
        else
            currentSummaries = summaries;


        loadSummaries();
    }

    public void loadSummaries() {
        parseWeightsField();

        List<SummaryRow> rows = currentSummaries.stream().map(SummaryRow::new).toList();

        rows.forEach(System.out::println);


        this.summaries.getItems().addAll(rows);
    }

    public void reset() {
        summaries.getItems().clear();
        currentSummaries.clear();
    }

    private void setCellsValuesFactors() {
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        tColumn.setCellValueFactory(new PropertyValueFactory<>("t"));
        t1Column.setCellValueFactory(new PropertyValueFactory<>("t1"));
        t2Column.setCellValueFactory(new PropertyValueFactory<>("t2"));
        t3Column.setCellValueFactory(new PropertyValueFactory<>("t3"));
        t4Column.setCellValueFactory(new PropertyValueFactory<>("t4"));
        t5Column.setCellValueFactory(new PropertyValueFactory<>("t5"));
        t6Column.setCellValueFactory(new PropertyValueFactory<>("t6"));
        t7Column.setCellValueFactory(new PropertyValueFactory<>("t7"));
        t8Column.setCellValueFactory(new PropertyValueFactory<>("t8"));
        t9Column.setCellValueFactory(new PropertyValueFactory<>("t9"));
        t10Column.setCellValueFactory(new PropertyValueFactory<>("t10"));
        t11Column.setCellValueFactory(new PropertyValueFactory<>("t11"));
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