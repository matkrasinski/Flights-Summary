package pl.ksr.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.ksr.database.DatabaseConnection;
import pl.ksr.functions.GaussianFunction;
import pl.ksr.functions.TrapezoidFunction;
import pl.ksr.functions.TriangleFunction;
import pl.ksr.lingustic.Label;
import pl.ksr.lingustic.LinguisticQuantifier;
import pl.ksr.lingustic.LinguisticVariable;
import pl.ksr.lingustic.QuantifierReader;
import pl.ksr.lingustic.VariableReader;
import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.Universe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdvancedController {

    @FXML
    private ComboBox<String> objectToAdd;
    @FXML
    private ComboBox<String> attributeQuantifierName;
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

    @FXML
    private TextField dbUrl;
    @FXML
    private TextField dbUser;
    @FXML
    private TextField dbPassword;
    @FXML
    private Text databaseInfo;
    @FXML
    private Text weightInfo;


    private List<LinguisticVariable> allVariables;
    private List<LinguisticQuantifier> relativeQuantifiers;
    private List<LinguisticQuantifier> absoluteQuantifiers;


    public static List<LinguisticQuantifier> newQuantifiers = new ArrayList<>();
    public static List<Label> newLabels = new ArrayList<>();

    public void initialize() {
        getDatabaseInfo();
        loadVariables();
        loadQuantifiers();
        setObjectToAdd();

        setDefaultWeights();
        parseWeightsField();
        initFunctionTypes();

        triangleForm.setVisible(false);
        trapezoidForm.setVisible(false);
        gaussianForm.setVisible(false);
        submitButton.setVisible(false);

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

    public void setDefaultWeights() {
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
        List<Double> weights =  List.of(
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
        );

        if (weights.stream().mapToDouble(e -> e).sum() != 1.0)
            throw new IllegalArgumentException();

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
        allVariables = VariableReader.loadVariables();

        for (var variable : allVariables) {
            String attributeName = variable.getLabels().get(0).getAttributeName();
            for (var newLabel : newLabels)
                if (Objects.equals(newLabel.getAttributeName(), attributeName))
                    variable.getLabels().add(newLabel);
        }


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
        absoluteQuantifiers = QuantifierReader.loadAbsoluteQuantifiers();
        relativeQuantifiers = QuantifierReader.loadRelativeQuantifiers();

        for (var quantifier : newQuantifiers) {
            if (quantifier.isRelative())
                relativeQuantifiers.add(quantifier);
            else
                absoluteQuantifiers.add(quantifier);
        }


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
    public void loadVariablesOrQuantifiers() {
        if (objectToAdd.getValue().equals("Quantifier")) {
            attributeQuantifierName.getItems().clear();
            attributeQuantifierName.getItems().add("relative quantifiers");
            attributeQuantifierName.getItems().add("absolute quantifiers");
        } else if (objectToAdd.getValue().equals("Summarizer/Qualifier")) {
            attributeQuantifierName.getItems().clear();
            for (var variable : allVariables) {
                attributeQuantifierName.getItems().add(variable.getVariableName());
            }
        }
    }
    public void addQuantifier() {
        String label = labelField.getText();
        String functionName = functionTypes.getSelectionModel().getSelectedItem();
        boolean isRelative = attributeQuantifierName.getValue().equals("relative quantifiers");

        Universe newUniverse;
        if (isRelative)
            newUniverse = relativeQuantifiers.get(0).getFuzzySet().getUniverseOfDiscourse();
        else
            newUniverse = absoluteQuantifiers.get(0).getFuzzySet().getUniverseOfDiscourse();


        FuzzySet newFuzzySet = null;
        switch (functionName) {
            case "TriangleFunction" -> newFuzzySet = new FuzzySet(
                    new TriangleFunction(
                            Double.parseDouble(triangleA.getText()),
                            Double.parseDouble(triangleB.getText()),
                            Double.parseDouble(triangleC.getText()),
                            newUniverse));
            case "TrapezoidFunction" -> newFuzzySet = new FuzzySet(
                    new TrapezoidFunction(
                            Double.parseDouble(trapezoidA.getText()),
                            Double.parseDouble(trapezoidB.getText()),
                            Double.parseDouble(trapezoidC.getText()),
                            Double.parseDouble(trapezoidD.getText()),
                            newUniverse));
            case "GaussianFunction" -> newFuzzySet = new FuzzySet(
                    new GaussianFunction(
                            Double.parseDouble(gaussianM.getText()),
                            Double.parseDouble(gaussianS.getText()),
                            newUniverse));
        }
        newQuantifiers.add(new LinguisticQuantifier(isRelative, label, newFuzzySet));

        loadQuantifiers();
    }
    public void addLabel() {
        String label = labelField.getText();
        String attributeName = attributeQuantifierName.getValue();
        String functionName = functionTypes.getSelectionModel().getSelectedItem();

        var optionalAttribute = allVariables.stream().filter(e -> e.getVariableName().equals(attributeName)).findFirst();
        if (optionalAttribute.isPresent()) {
            FuzzySet newFuzzySet = null;
            switch (functionName) {
                case "TriangleFunction" -> newFuzzySet = new FuzzySet(
                        new TriangleFunction(
                                Double.parseDouble(triangleA.getText()),
                                Double.parseDouble(triangleB.getText()),
                                Double.parseDouble(triangleC.getText()),
                                optionalAttribute.get().getUniverseOfDiscourse()));
                case "TrapezoidFunction" -> newFuzzySet = new FuzzySet(
                        new TrapezoidFunction(
                                Double.parseDouble(trapezoidA.getText()),
                                Double.parseDouble(trapezoidB.getText()),
                                Double.parseDouble(trapezoidC.getText()),
                                Double.parseDouble(trapezoidD.getText()),
                                optionalAttribute.get().getUniverseOfDiscourse()));
                case "GaussianFunction" -> newFuzzySet = new FuzzySet(
                        new GaussianFunction(
                                Double.parseDouble(gaussianM.getText()),
                                Double.parseDouble(gaussianS.getText()),
                                optionalAttribute.get().getUniverseOfDiscourse()));
            }
            newLabels.add(new Label(optionalAttribute.get().getLabels().get(0).getAttributeName(), label, newFuzzySet));
            loadVariables();
        }

    }

    public void addNewObject() {
        if (objectToAdd.getValue().equals("Quantifier")) {
            addQuantifier();
        } else if (objectToAdd.getValue().equals("Summarizer/Qualifier")) {
            addLabel();
        }

    }
    public void getDatabaseInfo() {
        dbUrl.setText(DatabaseConnection.DATABASE_URL);
        dbUser.setText(DatabaseConnection.DATABASE_USERNAME);
        dbPassword.setText(DatabaseConnection.DATABASE_PASSWORD);
    }
    public void changeDatabaseInfo() {
        try {
            DatabaseConnection.DATABASE_URL = dbUrl.getText();
            DatabaseConnection.DATABASE_USERNAME = dbUser.getText();
            DatabaseConnection.DATABASE_PASSWORD = dbPassword.getText();
            DatabaseConnection.getConnection();
            databaseInfo.setText("Accepted");
        } catch (Exception e) {
            databaseInfo.setText("Failed");
        }
    }
    public void resetWeights() {
        WeightsContext.resetWeights();
        setDefaultWeights();
        parseWeightsField();
    }
    public void updateWeights() {
        try {
            parseWeightsField();
            setDefaultWeights();
            weightInfo.setText("Accepted");
            parseWeightsField();
        } catch (IllegalArgumentException e) {
            weightInfo.setText("Not correct weights or weights does not sum up to 1.0");
            resetWeights();
        }
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
