package pl.ksr.view;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckTreeView;
import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.*;
import pl.ksr.summary.Subject;
import pl.ksr.summary.Summary;
import pl.ksr.summary.SummaryFactory;

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
    private Button generateButton;
    @FXML
    private ScrollPane attributesPane;
    @FXML
    private ScrollPane subjectScrollPane;
    @FXML
    private Text summaryCounterText;

    private List<Summary> currentSummaries;
    private List<LinguisticVariable> allVariables;
    private List<LinguisticQuantifier> relativeQuantifiers;
    private List<LinguisticQuantifier> absoluteQuantifiers;
    private List<Label> selectedAttributes;
    private List<Subject> selectedSubjects;

    public void initialize() {
        summaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        initVariables();
        initSubjects();
//        setCellsValuesFactorsSingle();

        relativeQuantifiers = new ArrayList<>();
        absoluteQuantifiers = new ArrayList<>();
        absoluteQuantifiers.addAll(QuantifierManager.loadAbsoluteQuantifiers());
        relativeQuantifiers.addAll(QuantifierManager.loadRelativeQuantifiers());
    }
    public void saveSummaries() {
        List<String> selected = summaries.getSelectionModel().getSelectedItems()
                .stream().map(SummaryRow::toString).toList();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showSaveDialog(null);
        if (file != null)
            FileManager.writeToFile(file.getAbsolutePath(), selected);
    }
    public void initVariables() {
        allVariables = VariableManager.loadVariables();
        CheckBoxTreeItem<String> attributes = new CheckBoxTreeItem<>();

        for (var variable : allVariables) {
            CheckBoxTreeItem<String> newAttribute = new CheckBoxTreeItem<>(variable.getVariableName());
            for (var label : variable.getLabels()) {
                newAttribute.getChildren().add(new CheckBoxTreeItem<>(label.getLabelName()));
            }
            attributes.getChildren().add(newAttribute);
        }

        CheckTreeView<String> checkTreeView = new CheckTreeView<>(attributes);
        checkTreeView.setShowRoot(false);

        checkTreeView.getCheckModel().getCheckedItems().addListener((ListChangeListener<TreeItem<String>>) change -> {
            selectedAttributes = new ArrayList<>();

            for (var checked : checkTreeView.getCheckModel().getCheckedItems()) {
                for (LinguisticVariable variable : allVariables) {
                    for (Label label : variable.getLabels()) {
                        if (label.getLabelName().equals(checked.getValue()))
                            selectedAttributes.add(label);
                    }
                }
            }
        });

        attributesPane.setContent(checkTreeView);
    }
    public void initSubjects() {
        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>();

        CheckBoxTreeItem<String> defaultSubject = new CheckBoxTreeItem<>("All flights");

        selectedSubjects = new ArrayList<>();
        selectedSubjects.add(new Subject());

        CheckBoxTreeItem<String> multiSubjects = new CheckBoxTreeItem<>("Flights where aircraft manufacturer was");

        CheckBoxTreeItem<String> boeingItem = new CheckBoxTreeItem<>("Boeing");
        CheckBoxTreeItem<String> airbusItem = new CheckBoxTreeItem<>("Airbus");

        multiSubjects.getChildren().add(boeingItem);
        multiSubjects.getChildren().add(airbusItem);

        rootItem.getChildren().add(defaultSubject);
        rootItem.getChildren().add(multiSubjects);
        CheckTreeView<String> subjects = new CheckTreeView<>(rootItem);
        subjects.setShowRoot(false);

        subjects.getCheckModel().getCheckedItems().addListener((ListChangeListener<TreeItem<String>>) change -> {
            selectedSubjects = new ArrayList<>();
            for (var item : subjects.getCheckModel().getCheckedItems()) {
                if (item != null) {
                    if (item.getValue().equals("All flights")) {
                        selectedSubjects.add(new Subject());
                        break;
                    }
                    if (item.getValue().equals("Boeing")) {
                        selectedSubjects.add(new Subject("Boeing"));
                    }
                    if (item.getValue().equals("Airbus")) {
                        selectedSubjects.add(new Subject("Airbus"));
                    }
                }
            }

        });

        subjectScrollPane.setContent(subjects);
    }
    public void generateSummaries() {
        if (selectedSubjects.size() == 1) {
            currentSummaries = SummaryFactory.generateAllSingleSubject(absoluteQuantifiers, relativeQuantifiers,
                    selectedAttributes, selectedSubjects.get(0));
            setCellsValuesFactorsSingle();
            loadSingleSubjectSummaries();
        } else if (selectedSubjects.size() == 2) {
            currentSummaries = SummaryFactory.generateAllMultiSubject(
                    relativeQuantifiers,
                    selectedAttributes,
                    selectedSubjects
            );
            setCellsValuesFactorsMulti();
            loadMultiSubjectSummaries();
        }
    }
    public void loadSingleSubjectSummaries() {
        List<SummaryRow> rows = currentSummaries.stream().map(SummaryRow::new).toList();
        summaryCounterText.setText("Current summaries counter: "  + rows.size());
        this.summaries.getItems().clear();
        this.summaries.getItems().addAll(rows);
    }

    public void loadMultiSubjectSummaries() {
        List<SummaryRow> rows = currentSummaries.stream()
                .map(e -> new SummaryRow(e.getSummary(), e.getQualityMeasures().getT_1())).toList();
        summaryCounterText.setText("Current summaries counter: "  + rows.size());
        this.summaries.getItems().clear();
        this.summaries.getItems().addAll(rows);
    }

    public void reset() {
        summaries.getItems().clear();
        currentSummaries.clear();
        summaryCounterText.setText("");
    }

    private void setCellsValuesFactorsSingle() {
        summaries.getColumns().clear();

        summaryColumn = new TableColumn<>("Summary");
        tColumn = new TableColumn<>("T");
        t1Column = new TableColumn<>("T1");
        t2Column = new TableColumn<>("T2");
        t3Column = new TableColumn<>("T3");
        t4Column = new TableColumn<>("T4");
        t5Column = new TableColumn<>("T5");
        t6Column = new TableColumn<>("T6");
        t7Column = new TableColumn<>("T7");
        t8Column = new TableColumn<>("T8");
        t9Column = new TableColumn<>("T9");
        t10Column = new TableColumn<>("T10");
        t11Column = new TableColumn<>("T11");

        summaryColumn.setPrefWidth(320);
        tColumn.setPrefWidth(50);
        t1Column.setPrefWidth(50);
        t2Column.setPrefWidth(50);
        t3Column.setPrefWidth(50);
        t4Column.setPrefWidth(50);
        t5Column.setPrefWidth(50);
        t6Column.setPrefWidth(50);
        t7Column.setPrefWidth(50);
        t8Column.setPrefWidth(50);
        t9Column.setPrefWidth(50);
        t10Column.setPrefWidth(50);
        t11Column.setPrefWidth(50);

        summaries.getColumns().addAll(List.of(summaryColumn, tColumn, t1Column, t2Column, t3Column,
                t4Column, t5Column, t6Column, t7Column, t8Column, t9Column, t10Column, t11Column));

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

    private void setCellsValuesFactorsMulti() {
        summaries.getColumns().clear();

        summaryColumn = new TableColumn<>("Summary");
        t1Column = new TableColumn<>("T1");
        summaryColumn.setPrefWidth(820);
        t1Column.setPrefWidth(50);


        summaries.getColumns().add(summaryColumn);
        summaries.getColumns().add(t1Column);

        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        t1Column.setCellValueFactory(new PropertyValueFactory<>("t1"));
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