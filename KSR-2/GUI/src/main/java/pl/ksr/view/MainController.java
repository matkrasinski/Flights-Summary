package pl.ksr.view;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckTreeView;
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
    private Button generateButton;
    @FXML
    private ScrollPane attributesPane;
    @FXML
    private ScrollPane subjectScrollPane;

    private List<Summary> currentSummaries;
    private List<LinguisticVariable> allVariables;
    private List<LinguisticQuantifier> allQuantifiers;
    private List<Label> selectedAttributes;
    private List<Subject> selectedSubjects;

    public void initialize() {
        summaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        initVariables();
        initSubjects();
        setCellsValuesFactors();

        allQuantifiers = new ArrayList<>();
        allQuantifiers.addAll(QuantifierManager.loadAbsoluteQuantifiers());
        allQuantifiers.addAll(QuantifierManager.loadRelativeQuantifiers());
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

//            selectedAttributes.forEach(e -> System.out.println(e.getLabelName()));
//            System.out.println(selectedAttributes.size());

        });

        attributesPane.setContent(checkTreeView);
    }

    public void initSubjects() {
        List<String> allSubjects = Subject.allSubjects();
        System.out.println(allSubjects);

        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>();

        CheckBoxTreeItem<String> defaultSubject = new CheckBoxTreeItem<>("All flights");

        selectedSubjects = new ArrayList<>();
        selectedSubjects.add(new Subject());

        CheckBoxTreeItem<String> multiSubjects = new CheckBoxTreeItem<>("Flights where aircraft manufacturer was");

        CheckBoxTreeItem<String> boeingItem = new CheckBoxTreeItem<>(allSubjects.get(0));
        CheckBoxTreeItem<String> airbusItem = new CheckBoxTreeItem<>(allSubjects.get(1));

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

            System.out.println(selectedSubjects.size());



        });



        subjectScrollPane.setContent(subjects);

    }

    public void generateSummaries() {
        int[] numbers = selectedAttributes.stream().mapToInt(e -> selectedAttributes.indexOf(e)).toArray();
        List<List<Integer>> indexes = generateVariations(numbers);
        System.out.println(indexes);

        if (selectedSubjects.size() == 1) {
            List<Summary> summaries = new ArrayList<>();
            for (var quantifier : allQuantifiers) {
                for (List<Integer> i : indexes) {
                    Summary summary = SummaryGenerator.generateSingleSubjectFirstForm(quantifier, selectedSubjects.get(0),
                            i.stream().map(e -> selectedAttributes.get(e)).toList());
                    if (summary != null)
                        summaries.add(summary);
                }
                // TODO second form
            }
            currentSummaries = summaries;
        } else if (selectedSubjects.size() == 2){
            List<Summary> summaries = new ArrayList<>();
            for (var quantifier : allQuantifiers) {
                for (List<Integer> i : indexes) {
                    //FIRST
                    Summary summary1 = SummaryGenerator.generateMultiSubjectFirstForm(quantifier, selectedSubjects,
                            i.stream().map(e -> selectedAttributes.get(e)).toList());
                    Summary summary2 = SummaryGenerator.generateMultiSubjectFirstForm(quantifier,
                            List.of(selectedSubjects.get(1), selectedSubjects.get(0)),
                            i.stream().map(e -> selectedAttributes.get(e)).toList());

                    summaries.add(summary1);
                    summaries.add(summary2);

                    // FOURTH
                    Summary summary3 = SummaryGenerator.generateMultiSubjectFourthForm(selectedSubjects, selectedAttributes);
                    Summary summary4 = SummaryGenerator.generateMultiSubjectFourthForm(
                            List.of(selectedSubjects.get(1), selectedSubjects.get(0)), selectedAttributes);
                    summaries.add(summary3);
                    summaries.add(summary4);
                }
                // TODO second form
                // TODO third form
                // TODO fourth form
            }
            currentSummaries = summaries;
        }





        loadSummaries();
    }


    public static List<List<Integer>> generateVariations(int[] numbers) {
        List<List<Integer>> variations = new ArrayList<>();
        for (int i = 1; i <= numbers.length; i++) {
            backtrack(numbers, new ArrayList<>(), variations, i, 0);
        }

        return variations;
    }

    private static void backtrack(int[] numbers, List<Integer> variation, List<List<Integer>> variations, int size, int startIndex) {
        if (variation.size() == size) {
            variations.add(new ArrayList<>(variation));
            return;
        }

        for (int i = startIndex; i < numbers.length; i++) {
            variation.add(numbers[i]);
            backtrack(numbers, variation, variations, size, i + 1);
            variation.remove(variation.size() - 1);
        }
    }

    public static List<List<Integer>> calculateCombinations(int n) {
        List<List<Integer>> combinations = new ArrayList<>();

        // Generowanie kombinacji
        for (int i = 1; i < (1 << n - 1); i++) {
            List<Integer> combination = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combination.add(j);
                }
            }

            combinations.add(combination);
        }
        return combinations;
    }


    public void loadSummaries() {
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