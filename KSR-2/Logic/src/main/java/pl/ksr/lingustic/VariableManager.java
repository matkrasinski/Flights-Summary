package pl.ksr.lingustic;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import pl.ksr.functions.*;
import pl.ksr.sets.ContinuousUniverse;
import pl.ksr.sets.FuzzySet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VariableManager {

    public static List<LinguisticVariable> loadVariables() {
        List<LinguisticVariable> variables = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = VariableManager.class.getResourceAsStream("/Variables.json");

            JsonNode jsonNode = mapper.readTree(inputStream);
            JsonNode linguisticVariables = jsonNode.get("linguisticVariables");

            for (int i = 0; i < linguisticVariables.size(); i++) {
                LinguisticVariable newVariable;
                JsonNode current = linguisticVariables.get(i);

                String attributeName = current.get("attributeName").asText();
                String variableName = current.get("variableName").asText();
                JsonNode range = current.get("range");

                JsonNode labels = current.get("labels");

                List<Label> newLabels = new ArrayList<>();

                for (int j = 0; j < labels.size(); j++) {
                    String label = labels.get(j).get("label").asText();
                    MembershipFunction function;
                    JsonNode parameters = labels.get(j).get("parameters");
                    if (Objects.equals(labels.get(j).get("function").asText(), TrapezoidFunction.class.getSimpleName())) {
                        function = new TrapezoidFunction(
                                parameters.get("a").asDouble(),
                                parameters.get("b").asDouble(),
                                parameters.get("c").asDouble(),
                                parameters.get("d").asDouble(),
                                new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));
                    } else if (Objects.equals(labels.get(j).get("function").asText(), TriangleFunction.class.getSimpleName())) {
                        function = new TriangleFunction(
                                parameters.get("a").asDouble(),
                                parameters.get("b").asDouble(),
                                parameters.get("c").asDouble(),
                                new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));
                    } else if (Objects.equals(labels.get(j).get("function").asText(), GaussianFunction.class.getSimpleName())) {
                        function = new GaussianFunction(
                                parameters.get("m").asDouble(),
                                parameters.get("s").asDouble(),
                                new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));
                    } else {
                        function = new CompoundFunction(
                                List.of(
                                        new GaussianFunction(
                                                parameters.get("m1").asDouble(),
                                                parameters.get("s1").asDouble(),
                                                new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble())),
                                        new GaussianFunction(
                                                parameters.get("m2").asDouble(),
                                                parameters.get("s2").asDouble(),
                                                new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble())
                                        )), new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()), false);
                    }
                    newLabels.add(new Label(attributeName, label, new FuzzySet(function)));
                }

                newVariable = new LinguisticVariable(variableName, newLabels,
                        new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));
                variables.add(newVariable);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return variables;
    }

    public static void addLabel(Label label) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File file = new File(Objects.requireNonNull(VariableManager.class.getResource("/Variables.json")).getPath());
            JsonNode jsonNode = mapper.readTree(file);
            ArrayNode arrayNode = (ArrayNode) jsonNode.get("linguisticVariables");

            JsonNode node = null;
            for (var s : arrayNode) {
                System.out.println(s.get("variableName").textValue() + " " + label.getAttributeName());
                if (s.get("variableName").textValue().equals(label.getAttributeName())) {
                    node = s;
                    break;
                }
            }

            assert node != null;
            ArrayNode labelsArrayNode = (ArrayNode) node.get("labels");
            String functionName = label.getFuzzySet().getMembershipFunction().getClass().getSimpleName();

            var newLabel = mapper.createObjectNode();
            newLabel.put("label", label.getLabelName());
            newLabel.put("function", label.getFuzzySet().getMembershipFunction().getClass().getSimpleName());

            var parametersObject = mapper.createObjectNode();
            switch (functionName) {
                case "TriangleFunction" -> {
                    parametersObject.put("a",
                            ((TriangleFunction) label.getFuzzySet().getMembershipFunction()).getA());
                    parametersObject.put("b",
                            ((TriangleFunction) label.getFuzzySet().getMembershipFunction()).getB());
                    parametersObject.put("c",
                            ((TriangleFunction) label.getFuzzySet().getMembershipFunction()).getC());
                }
                case "TrapezoidFunction" -> {
                    parametersObject.put("a",
                            ((TrapezoidFunction) label.getFuzzySet().getMembershipFunction()).getA());
                    parametersObject.put("b",
                            ((TrapezoidFunction) label.getFuzzySet().getMembershipFunction()).getB());
                    parametersObject.put("c",
                            ((TrapezoidFunction) label.getFuzzySet().getMembershipFunction()).getC());
                    parametersObject.put("d",
                            ((TrapezoidFunction) label.getFuzzySet().getMembershipFunction()).getD());
                }
                case "GaussianFunction" -> {
                    parametersObject.put("m",
                            ((GaussianFunction) label.getFuzzySet().getMembershipFunction()).getM());
                    parametersObject.put("s",
                            ((GaussianFunction) label.getFuzzySet().getMembershipFunction()).getS());
                }
            }

            newLabel.putIfAbsent("parameters", parametersObject);
            labelsArrayNode.add(newLabel);
            System.out.println(jsonNode);
            FileWriter fileWriter = new FileWriter(file);
            mapper.writeValue(fileWriter, jsonNode);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
