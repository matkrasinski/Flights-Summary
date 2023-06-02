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
    private TextField weightsField;

    @FXML
    private Button generateButton;

    @FXML
    private ScrollPane attributesPane;

    private List<Label> currentSummarizers;
    private List<Summary> currentSummaries;
    private List<Double> weights;
    private List<LinguisticVariable> allVariables;
    private List<LinguisticQuantifier> allQuantifiers;
    private List<Label> selectedAttributes;
    private String[] types;


    public void initialize() {
        summaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        initVariables();
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

            selectedAttributes.forEach(e -> System.out.println(e.getLabelName()));
            System.out.println(selectedAttributes.size());

        });

        attributesPane.setContent(checkTreeView);
    }

    public void generateSummaries() {
        int[] numbers = selectedAttributes.stream().mapToInt(e -> selectedAttributes.indexOf(e)).toArray();
        Subject subject = new Subject();
        List<List<Integer>> indexes = generateVariations(numbers);
        List<List<Integer>> combinations = calculateCombinations(selectedAttributes.size());
        List<Summary> summaries = new ArrayList<>();
        for (var quantifier : allQuantifiers) {
//            for (List<Integer> i : indexes) {
//                Summary summary = SummaryGenerator.generateSingleSubjectFirstForm(quantifier, subject,
//                        i.stream().map(e -> selectedAttributes.get(e)).toList());
//                if (summary != null)
//                    summaries.add(summary);
//            }

            for (List<Integer> combination : combinations) {
                List<Integer> group1 = new ArrayList<>();
                List<Integer> group2 = new ArrayList<>();

                for (int i = 0; i < selectedAttributes.size(); i++) {
                    if (combination.contains(i)) {
                        group1.add(i);
                    } else {
                        group2.add(i);
                    }
                }
                Summary summary = SummaryGenerator.generateSingleSubjectSecondForm(quantifier,
                        subject, group1.stream().map(e -> selectedAttributes.get(e)).toList(),
                        group2.stream().map(e -> selectedAttributes.get(e)).toList());
                if (summary != null)
                    summaries.add(summary);

            }


        }
        currentSummaries = summaries;
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

        for (int i = 1; i < (1 << (n - 1)); i++) {
            List<Integer> combination = new ArrayList<>();

            for (int j = 0; j < n - 1; j++) {
                if ((i & (1 << j)) > 0) {
                    combination.add(j + 1);
                }
            }

            combinations.add(combination);
        }

        return combinations;
    }

    public List<Double> parseWeightsField() {
        List<Double> weights = new ArrayList<>();
        for (String field : weightsField.getText().split(", ")) {
//            weights.add(Double.parseDouble(field));
            weights.add(1d / 11);
        }
        return weights;
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