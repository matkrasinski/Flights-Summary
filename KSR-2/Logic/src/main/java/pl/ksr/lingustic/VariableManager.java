package pl.ksr.lingustic;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ksr.functions.*;
import pl.ksr.sets.ContinuousUniverse;
import pl.ksr.sets.FuzzySet;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VariableManager {

    public static List<LinguisticVariable> loadVariables() {
        List<LinguisticVariable> variables = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(Objects.requireNonNull(VariableManager.class.getResource("/Variables.json")).getPath());

            JsonNode jsonNode = mapper.readTree(file);
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

    public static void addLabel(String attributeName, LinguisticVariable variable) {

    }


}
