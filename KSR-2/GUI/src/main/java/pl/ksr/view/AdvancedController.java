package pl.ksr.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;
import pl.ksr.lingustic.QuantifierManager;
import pl.ksr.lingustic.VariableManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedController {

    @FXML
    private ComboBox<String> objectToAdd;
    @FXML
    private ComboBox<String> linguisticVariableField;
    @FXML
    private ComboBox<String> functionTypes;

    @FXML
    private Button submitButton;

    @FXML
    private GridPane triangleForm;
    @FXML
    private GridPane trapezoidForm;
    @FXML
    private GridPane gaussianForm;
    @FXML
    private GridPane compoundForm;

    @FXML
    private TextField triangleA;
    @FXML
    private TextField triangleB;
    @FXML
    private TextField triangleC;

    @FXML
    private TextField trapezoidA;
    @FXML
    private TextField trapezoidB;
    @FXML
    private TextField trapezoidC;
    @FXML
    private TextField trapezoidD;

    @FXML
    private TextField gaussianM;
    @FXML
    private TextField gaussianS;


    @FXML
    private ScrollPane attributesPane;
    @FXML
    private ScrollPane quantifiersPane;

    @FXML
    private TextField t1Field;
    @FXML
    private TextField t2Field;
    @FXML
    private TextField t3Field;
    @FXML
    private TextField t4Field;
    @FXML
    private TextField t5Field;
    @FXML
    private TextField t6Field;
    @FXML
    private TextField t7Field;
    @FXML
    private TextField t8Field;
    @FXML
    private TextField t9Field;
    @FXML
    private TextField t10Field;
    @FXML
    private TextField t11Field;

    @FXML
    private TextField labelField;


    public void initialize() {
        setObjectToAdd();

        linguisticVariableField.setValue("flight start");
        setDefault();
        parseWeightsField();
        initFunctionTypes();

        triangleForm.setVisible(false);
        trapezoidForm.setVisible(false);
        gaussianForm.setVisible(false);
        submitButton.setVisible(false);

        numericOnly(triangleA);
        numericOnly(triangleB);
        numericOnly(triangleC);

        numericOnly(trapezoidA);
        numericOnly(trapezoidB);
        numericOnly(trapezoidC);
        numericOnly(trapezoidD);

        numericOnly(gaussianM);
        numericOnly(gaussianS);

        loadVariables();
        loadQuantifiers();
    }

    public void setObjectToAdd() {
        objectToAdd.getItems().addAll(List.of("Summarizer/Qualifier", "Quantifier"));
    }

    public void onFunctionChange() {
        if (functionTypes.getSelectionModel().getSelectedItem().equals("TriangleFunction")) {
            triangleForm.setVisible(true);
            trapezoidForm.setVisible(false);
            gaussianForm.setVisible(false);
        } else if (functionTypes.getSelectionModel().getSelectedItem().equals("TrapezoidFunction")) {
            triangleForm.setVisible(false);
            trapezoidForm.setVisible(true);
            gaussianForm.setVisible(false);
        } else if (functionTypes.getSelectionModel().getSelectedItem().equals("GaussianFunction")) {
            triangleForm.setVisible(false);
            trapezoidForm.setVisible(false);
            gaussianForm.setVisible(true);
        }
        submitButton.setVisible(true);
    }

    public void setDefault() {
        var weights = WeightsContext.getWeights();
        t1Field.setText(String.valueOf(weights.get(0)));
        t2Field.setText(String.valueOf(weights.get(1)));
        t3Field.setText(String.valueOf(weights.get(2)));
        t4Field.setText(String.valueOf(weights.get(3)));
        t5Field.setText(String.valueOf(weights.get(4)));
        t6Field.setText(String.valueOf(weights.get(5)));
        t7Field.setText(String.valueOf(weights.get(6)));
        t8Field.setText(String.valueOf(weights.get(7)));
        t9Field.setText(String.valueOf(weights.get(8)));
        t10Field.setText(String.valueOf(weights.get(9)));
        t11Field.setText(String.valueOf(weights.get(10)));
    }

    public void parseWeightsField() {
        WeightsContext.setWeights(List.of(
                parseTextField(t1Field),
                parseTextField(t2Field),
                parseTextField(t3Field),
                parseTextField(t4Field),
                parseTextField(t5Field),
                parseTextField(t6Field),
                parseTextField(t7Field),
                parseTextField(t8Field),
                parseTextField(t9Field),
                parseTextField(t10Field),
                parseTextField(t11Field)
        ));
    }

    public double parseTextField(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    public void initFunctionTypes() {
        functionTypes.getItems().add("TriangleFunction");
        functionTypes.getItems().add("TrapezoidFunction");
        functionTypes.getItems().add("GaussianFunction");
    }

    public void loadVariables() {
        List<LinguisticVariable> allVariables = VariableManager.loadVariables();
        TreeItem<String> attributes = new CheckBoxTreeItem<>();

        for (var variable : allVariables) {
            TreeItem<String> newAttribute = new TreeItem<>(variable.getVariableName());
            for (var label : variable.getLabels()) {
                newAttribute.getChildren().add(new TreeItem<>(label.getLabelName()));
            }
            attributes.getChildren().add(newAttribute);
        }

        TreeView<String> treeView = new TreeView<>(attributes);
        treeView.setShowRoot(false);


        attributesPane.setContent(treeView);
    }

    public void loadQuantifiers() {
        List<LinguisticQuantifier> relativeQuantifiers = QuantifierManager.loadRelativeQuantifiers();
        List<LinguisticQuantifier> absoluteQuantifiers = QuantifierManager.loadAbsoluteQuantifiers();

        TreeItem<String> quantifiers = new CheckBoxTreeItem<>();

        TreeItem<String> relativeItem = new CheckBoxTreeItem<>("Relative quantifiers");
        for (var quantifier : relativeQuantifiers)
            relativeItem.getChildren().add(new TreeItem<>(quantifier.getLabel()));

        TreeItem<String> absoluteItem = new CheckBoxTreeItem<>("Absolute quantifiers");
        for (var quantifier : absoluteQuantifiers)
            absoluteItem.getChildren().add(new TreeItem<>(quantifier.getLabel()));

        quantifiers.getChildren().add(relativeItem);
        quantifiers.getChildren().add(absoluteItem);

        TreeView<String> treeView = new TreeView<>(quantifiers);
        treeView.setShowRoot(false);

        quantifiersPane.setContent(treeView);
    }

    public void numericOnly(TextField field) {
        // TODO
    }

    public void addQuantifier() {
        String label = labelField.getText();
        String functionName = functionTypes.getSelectionModel().getSelectedItem();
        List<Double> parameters = new ArrayList<>();
        switch (functionName) {
            case "TriangleFunction" -> {
                parameters.add(Double.parseDouble(triangleA.getText()));
                parameters.add(Double.parseDouble(triangleB.getText()));
                parameters.add(Double.parseDouble(triangleC.getText()));
            }
            case "TrapezoidFunction" -> {
                parameters.add(Double.parseDouble(trapezoidA.getText()));
                parameters.add(Double.parseDouble(trapezoidB.getText()));
                parameters.add(Double.parseDouble(trapezoidC.getText()));
                parameters.add(Double.parseDouble(trapezoidD.getText()));
            }
            case "GaussianFunction" -> {
                parameters.add(Double.parseDouble(gaussianM.getText()));
                parameters.add(Double.parseDouble(gaussianS.getText()));
            }
        }

        QuantifierManager.addLabelToRelativeQuantifiers(label, functionName, parameters);

        loadQuantifiers();
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
