package pl.ksr.lingustic;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ksr.functions.*;
import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.FuzzySet;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VariableManager {

    public static List<LinguisticVariable> loadVariables() {
        List<LinguisticVariable> variables = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(String.valueOf(QuantifierManager.class.getResource("/Variables.json")).substring("file:/".length()));

            JsonNode jsonNode = mapper.readTree(file);
            JsonNode linguisticVariables = jsonNode.get("linguisticVariables");

            for (int i = 0; i < linguisticVariables.size(); i++) {
                LinguisticVariable newVariable;
                JsonNode current = linguisticVariables.get(i);

                String attributeName = current.get("attributeName").asText();
                String variableName = current.get("variableName").asText();
                JsonNode range = current.get("range");

                JsonNode labels = current.get("labels");

                Map<String, FuzzySet> labelsFuzzSet = new HashMap<>();

                for (int j = 0; j < labels.size(); j++) {
                    String label = labels.get(i).get("label").asText();
                    MembershipFunction function;
                    JsonNode parameters = labels.get(j).get("parameters");
                    if (Objects.equals(labels.get(j).get("function").asText(), TrapezoidFunction.class.getSimpleName())) {
                        function = new TrapezoidFunction(parameters.get("a").asDouble(), parameters.get("b").asDouble(),
                                parameters.get("c").asDouble(), parameters.get("d").asDouble());
                    } else if (Objects.equals(labels.get(j).get("function").asText(), TriangleFunction.class.getSimpleName())) {
                        function = new TriangleFunction(parameters.get("a").asDouble(), parameters.get("b").asDouble(),
                                parameters.get("c").asDouble());
                    } else if (Objects.equals(labels.get(j).get("function").asText(), GaussianFunction.class.getSimpleName())) {
                        function = new GaussianFunction(parameters.get("m").asDouble(), parameters.get("s").asDouble());
                    } else {
                        function = new CompoundGaussian(parameters.get("m1").asDouble(), parameters.get("s1").asDouble(),
                                parameters.get("m2").asDouble(), parameters.get("s2").asDouble());
                    }
                    labelsFuzzSet.put(label, new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                }

                newVariable = new LinguisticVariable(attributeName, variableName, labelsFuzzSet,
                        new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()));
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
