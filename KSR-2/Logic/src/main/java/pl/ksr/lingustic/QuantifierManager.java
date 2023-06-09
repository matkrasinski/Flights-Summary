package pl.ksr.lingustic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.ksr.functions.*;
import pl.ksr.sets.ContinuousUniverse;
import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.Universe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuantifierManager {

    public static List<LinguisticQuantifier> loadRelativeQuantifiers() {
        List<LinguisticQuantifier> quantifiers = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = QuantifierManager.class.getResourceAsStream("/RelativeQuantifiers.json");

            JsonNode jsonNode = mapper.readTree(inputStream);
            JsonNode relativeQuantifiers = jsonNode.get("relativeQuantifiers");

            for (int i = 0; i < relativeQuantifiers.size(); i++) {
                LinguisticQuantifier newQuantifier;
                JsonNode current = relativeQuantifiers.get(i);
                JsonNode parameters = current.get("parameters");
                JsonNode range = jsonNode.get("range");

                if (Objects.equals(current.get("function").asText(), TrapezoidFunction.class.getSimpleName())) {
                    MembershipFunction function = new TrapezoidFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            parameters.get("d").asDouble(),
                            new ContinuousUniverse(parameters.get("a").asDouble(), parameters.get("d").asDouble()));

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                                    new FuzzySet(function));
                } else if (Objects.equals(current.get("function").asText(), TriangleFunction.class.getSimpleName())) {
                    MembershipFunction function = new TriangleFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            new ContinuousUniverse(parameters.get("a").asDouble(), parameters.get("c").asDouble()));

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(function));
                } else if (Objects.equals(current.get("function").asText(), GaussianFunction.class.getSimpleName())) {
                    MembershipFunction function = new GaussianFunction(
                            parameters.get("m").asDouble(),
                            parameters.get("s").asDouble(),
                            new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(function));
                } else {
                    Universe universe = new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble());
                    MembershipFunction function = new CompoundFunction(List.of(
                            new GaussianFunction(
                                parameters.get("m1").asDouble(),
                                parameters.get("s1").asDouble(),
                                universe),
                            new GaussianFunction(
                                parameters.get("m2").asDouble(),
                                parameters.get("s2").asDouble(),
                                    universe
                            )), universe);


                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(function));
                }
                quantifiers.add(newQuantifier);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return quantifiers;
    }

    public static List<LinguisticQuantifier> loadAbsoluteQuantifiers() {
        List<LinguisticQuantifier> quantifiers = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = QuantifierManager.class.getResourceAsStream("/AbsoluteQuantifiers.json");

            JsonNode jsonNode = mapper.readTree(inputStream);
            JsonNode relativeQuantifiers = jsonNode.get("absoluteQuantifiers");

            for (int i = 0; i < relativeQuantifiers.size(); i++) {
                LinguisticQuantifier newQuantifier;
                JsonNode current = relativeQuantifiers.get(i);
                JsonNode parameters = current.get("parameters");
                JsonNode range = jsonNode.get("range");

                if (Objects.equals(current.get("function").asText(), TrapezoidFunction.class.getSimpleName())) {
                    MembershipFunction function = new TrapezoidFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            parameters.get("d").asDouble(),
                            new ContinuousUniverse(parameters.get("a").asDouble(), parameters.get("d").asDouble()));

                    newQuantifier = new LinguisticQuantifier(false,
                            current.get("label").asText(),
                            new FuzzySet(function));
                } else if (Objects.equals(current.get("function").asText(), TriangleFunction.class.getSimpleName())) {
                    MembershipFunction function = new TriangleFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            new ContinuousUniverse(parameters.get("a").asDouble(), parameters.get("c").asDouble()));

                    newQuantifier = new LinguisticQuantifier(false,
                            current.get("label").asText(),
                            new FuzzySet(function));
                } else if (Objects.equals(current.get("function").asText(), GaussianFunction.class.getSimpleName())) {
                    MembershipFunction function = new GaussianFunction(
                            parameters.get("m").asDouble(),
                            parameters.get("s").asDouble(),
                            new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));

                    newQuantifier = new LinguisticQuantifier(false,
                            current.get("label").asText(),
                            new FuzzySet(function));
                } else {
                    Universe universe = new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble());
                    MembershipFunction function = new CompoundFunction(List.of(
                            new GaussianFunction(
                                    parameters.get("m1").asDouble(),
                                    parameters.get("s1").asDouble(),
                                    universe),
                            new GaussianFunction(
                                    parameters.get("m2").asDouble(),
                                    parameters.get("s2").asDouble(),
                                    universe
                            )), universe, false);

                    newQuantifier = new LinguisticQuantifier(false,
                            current.get("label").asText(),
                            new FuzzySet(function));
                }
                quantifiers.add(newQuantifier);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return quantifiers;
    }

    public static void addLabelToRelativeQuantifiers(LinguisticQuantifier quantifier) {
        addLabel("RelativeQuantifiers.json", quantifier);
    }
    public static void addLabelToAbsoluteQuantifiers(LinguisticQuantifier quantifier) {
        addLabel("AbsoluteQuantifiers.json", quantifier);
    }
    private static void addLabel(String fileName, LinguisticQuantifier quantifier) {
        try {
            String label = quantifier.getLabel();
            MembershipFunction function = quantifier.getFuzzySet().getMembershipFunction();
            String functionName = function.getClass().getSimpleName();

            ObjectMapper mapper = new ObjectMapper();


            File file = new File(Objects.requireNonNull(VariableManager.class.getResource("/" + fileName)).getPath());

            JsonNode jsonNode = mapper.readTree(file);
            ArrayNode array;
            if (jsonNode.has("relativeQuantifiers")) {
                array = (ArrayNode) jsonNode.get("relativeQuantifiers");
            } else {
                array = (ArrayNode) jsonNode.get("absoluteQuantifiers");
            }

            ObjectNode newObject = mapper.createObjectNode();
            newObject.put("label", label);
            newObject.put("function", functionName);
            ObjectNode parametersObject = mapper.createObjectNode();

            switch (functionName) {
                case "TriangleFunction" -> {
                    parametersObject.put("a", ((TriangleFunction) function).getA());
                    parametersObject.put("b", ((TriangleFunction) function).getB());
                    parametersObject.put("c", ((TriangleFunction) function).getC());
                }
                case "TrapezoidFunction" -> {
                    parametersObject.put("a", ((TrapezoidFunction) function).getA());
                    parametersObject.put("b", ((TrapezoidFunction) function).getB());
                    parametersObject.put("c", ((TrapezoidFunction) function).getC());
                    parametersObject.put("d", ((TrapezoidFunction) function).getD());
                }
                case "GaussianFunction" -> {
                    parametersObject.put("m", ((GaussianFunction) function).getM());
                    parametersObject.put("s", ((GaussianFunction) function).getS());
                }
            }

            newObject.putIfAbsent("parameters", parametersObject);
            array.add(newObject);

            FileWriter fileWriter = new FileWriter(file);
            mapper.writeValue(fileWriter, jsonNode);

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
