package pl.ksr.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;
import pl.ksr.lingustic.QuantifierManager;
import pl.ksr.lingustic.VariableManager;
import pl.ksr.summary.Subject;
import pl.ksr.summary.Summary;

import pl.ksr.lingustic.Label;
import pl.ksr.summary.SummaryGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    @FXML
    private Button resetButton;
    @FXML
    private ComboBox<String> measuresBox;
    private LinguisticQuantifier currentQuantifier;
    private List<Label> currentSummarizers;
    private List<Label> currentQualifiers;
    private List<Summary> currentSummaries;
    private List<Double> weights;
    private List<LinguisticVariable> allVariables;
    private List<LinguisticQuantifier> allQuantifiers;
    private LinguisticVariable selectedSummarizer;
    private LinguisticVariable selectedQualifier;
    private String[] types;
    private String[] measures;


    public void initialize() {
        summaries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        summarizersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        qualifiersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        getQuantifiersNames();
        initSummaryTypes();
        initMeasures();
        getVariables();

        measuresBox.getItems().addAll(measures);
        weightsField.setText("0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09, 0.09");

        weights = parseWeightsField();
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
            allQuantifiers = QuantifierManager.loadRelativeQuantifiers();

            quantifiersBox.getItems().addAll(allQuantifiers.stream().map(e -> e.getLabel().getLabelName()).toList());
        } else {
            allQuantifiers = QuantifierManager.loadAbsoluteQuantifiers();
            quantifiersBox.getItems().addAll(allQuantifiers.stream().map(e -> e.getLabel().getLabelName()).toList());
        }
    }

    public void getVariables() {
        allVariables = VariableManager.loadVariables();

        summarizerBox.getItems().addAll(allVariables.stream().map(LinguisticVariable::getVariableName).toList());
        qualifierBox.getItems().addAll(allVariables.stream().map(LinguisticVariable::getVariableName).toList());
    }

    public void pickSummarizer() {
        String currSummarizer = summarizerBox.getValue();

        var summarizerLabels = allVariables.stream().filter(e -> e.getVariableName().equals(currSummarizer)).toList();

        if (!labelsBox1.getItems().isEmpty())
            labelsBox1.getItems().clear();

        selectedSummarizer = summarizerLabels.get(0);

        labelsBox1.getItems().addAll(selectedSummarizer.getLabelsNames());
    }
    public void pickQualifier() {
        String currQualifier = qualifierBox.getValue();

        var qualifierLabels = allVariables.stream().filter(e -> e.getVariableName().equals(currQualifier)).toList();

        if (!labelsBox2.getItems().isEmpty())
            labelsBox2.getItems().clear();

        selectedQualifier = qualifierLabels.get(0);

        labelsBox2.getItems().addAll(selectedQualifier.getLabelsNames());
    }

    public void setCurrentQuantifier() {
        var curr = allQuantifiers.stream()
                .filter(e -> e.getLabel().getLabelName().equals(quantifiersBox.getValue())).findAny();
        curr.ifPresent(quantifier -> currentQuantifier = quantifier);
    }


    public void addToSummarizers() {
        Label label = null;
        for (Label l : selectedSummarizer.getLabels())
            if (l.getLabelName().equals(labelsBox1.getValue()))
                label = l;

        if (label != null) {
            if (currentSummarizers == null) {
                currentSummarizers = new ArrayList<>();
            }
            currentSummarizers.add(label);
            summarizersList.getItems().add(labelsBox1.getValue());
        }
        currentSummarizers.forEach(e -> System.out.println(e.getLabelName()));
    }

    public void addToQualifiers() {
        Label label = null;
        for (Label l : selectedQualifier.getLabels())
            if (l.getLabelName().equals(labelsBox2.getValue()))
                label = l;

        if (label != null) {
            if (currentQualifiers == null) {
                currentQualifiers = new ArrayList<>();
            }
            currentQualifiers.add(label);
            qualifiersList.getItems().add(labelsBox2.getValue());
        }
        currentQualifiers.forEach(e -> System.out.println(e.getLabelName()));
    }

    public void removeFromSummarizers() {
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
    public void removeFromQualifiers() {
        for (Label label : currentQualifiers) {
            for (String item : qualifiersList.getSelectionModel().getSelectedItems()) {
                if (label.getLabelName().equals(item)) {
                    currentQualifiers.remove(label);
                    break;
                }
            }
            if (currentQualifiers.isEmpty())
                break;
        }

//        currentSummarizers.removeIf(label -> label.getLabelName().equals(summarizersList.getSelectionModel().getSelectedItems()));
//        currentSummarizers.forEach(e -> System.out.println(e.getLabelName()));
        qualifiersList.getItems().remove(qualifiersList.getSelectionModel().getSelectedItem());
    }

    public void initSummaryTypes() {
        types = new String[] {
                "Single-subject I form",
                "Single-subject II form",
                "Multi-subject I form",
                "Multi-subject II form",
                "Multi-subject III form",
                "Multi-subject IV form"

        };
        summaryTypes.getItems().addAll(types);
    }

    public void initMeasures() {
        measures = new String[] {
                "An extended concept of the optimal summary",
                "Degree of truth",
                "Degree of imprecision",
                "Degree of covering",
                "Degree of appropriateness",
                "Length of summary",
                "Degree of quantifier imprecision",
                "Degree of quantifier cardinality",
                "Degree of summarizer cardinality",
                "Degree of qualifier imprecision",
                "Degree of qualifier cardinality",
                "Length of qualifier"
        };
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
        if (currentSummaries == null)
            currentSummaries = new ArrayList<>();
        if (currentSummarizers == null || currentSummarizers.isEmpty()) {
            List<Summary> summaries = new ArrayList<>();
            for (LinguisticQuantifier quantifier : allQuantifiers) {
                for (int i = 0; i < allVariables.size(); i++) {
                    for (Label summarizer : allVariables.get(i).getLabels()) {
                        for (int j = i + 1; j < allVariables.size(); j++) {
                            for (Label qualifier : allVariables.get(j).getLabels()) {
                                Summary summary2 = SummaryGenerator.generateSingleSubjectSecondForm(
                                        quantifier, new Subject(), List.of(summarizer), List.of(qualifier));
                                summaries.add(summary2);
                            }
                        }
                    }
                }
            }
            currentSummaries.addAll(summaries);
        } else if (summaryTypes.getValue().equals(types[0])) {
            List<Summary> summaries = new ArrayList<>();
            if (currentQuantifier == null) {
                for (LinguisticQuantifier quantifier : allQuantifiers) {
                    Summary summary = SummaryGenerator.generateSingleSubjectFirstForm(quantifier, new Subject(), currentSummarizers);
                    summaries.add(summary);
                }
            } else {
                Summary summary = SummaryGenerator.generateSingleSubjectFirstForm(currentQuantifier, new Subject(), currentSummarizers);
                summaries.add(summary);
            }

            currentSummaries.addAll(summaries);
        } else if (summaryTypes.getValue().equals(types[1])) {
            List<Summary> summaries = new ArrayList<>();
            for (LinguisticQuantifier quantifier : allQuantifiers) {
                Summary summary = SummaryGenerator.generateSingleSubjectSecondForm(quantifier,
                        new Subject(),
                        currentSummarizers,
                        currentQualifiers);
                summaries.add(summary);
            }
            currentSummaries.addAll(summaries);

        }  else if (summaryTypes.getValue().equals(types[2])) {

        }  else if (summaryTypes.getValue().equals(types[3])) {

        }  else if (summaryTypes.getValue().equals(types[4])) {

        } else if (summaryTypes.getValue().equals(types[5])) {

        }

        loadSummaries();
    }

    public void loadSummaries() {
        parseWeightsField();
        List<String> summariesWithMeasures = currentSummaries.stream().map(e -> e.getSummary() + "[ " +
                (double) Math.round(e.getQualityMeasures().calculateExtendedOptimalSummary(weights) * 100) / 100 + " ]"
                + e.getQualityMeasures().getAll().stream().map(e2 -> (double) Math.round(e2 * 100) / 100).toList()
        ).toList();

        this.summaries.getItems().addAll(summariesWithMeasures);
    }

    public void sort() {
        parseWeightsField();
        if (measuresBox.getValue().equals(measures[0])) {
            currentSummaries.sort((o1, o2) ->
                 Double.compare(o2.getQualityMeasures().calculateExtendedOptimalSummary(weights),
                         o1.getQualityMeasures().calculateExtendedOptimalSummary(weights))
            );
        } else if (measuresBox.getValue().equals(measures[1])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_1(), o1.getQualityMeasures().getT_1())
            );
        } else if (measuresBox.getValue().equals(measures[2])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_2(), o1.getQualityMeasures().getT_2())
            );
        } else if (measuresBox.getValue().equals(measures[3])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_3(), o1.getQualityMeasures().getT_3())
            );
        } else if (measuresBox.getValue().equals(measures[4])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_4(), o1.getQualityMeasures().getT_4())
            );
        } else if (measuresBox.getValue().equals(measures[5])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_5(), o1.getQualityMeasures().getT_5())
            );
        } else if (measuresBox.getValue().equals(measures[6])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_6(), o1.getQualityMeasures().getT_6())
            );
        } else if (measuresBox.getValue().equals(measures[7])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_7(), o1.getQualityMeasures().getT_7())
            );
        } else if (measuresBox.getValue().equals(measures[8])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_8(), o1.getQualityMeasures().getT_8())
            );
        } else if (measuresBox.getValue().equals(measures[9])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_9(), o1.getQualityMeasures().getT_9())
            );
        } else if (measuresBox.getValue().equals(measures[10])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_10(), o1.getQualityMeasures().getT_10())
            );
        } else if (measuresBox.getValue().equals(measures[11])) {
            currentSummaries.sort((o1, o2) ->
                    Double.compare(o2.getQualityMeasures().getT_11(), o1.getQualityMeasures().getT_11())
            );
        }
        if (!summaries.getItems().isEmpty())
            summaries.getItems().clear();
        loadSummaries();
    }

    public void reset() {
        summaries.getItems().clear();
        currentSummaries.clear();
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