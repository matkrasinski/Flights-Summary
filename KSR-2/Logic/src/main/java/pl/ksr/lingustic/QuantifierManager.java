package pl.ksr.lingustic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ksr.functions.*;
import pl.ksr.sets.DenseUniverse;
import pl.ksr.sets.FuzzySet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuantifierManager {

    public static List<LinguisticQuantifier> loadRelativeQuantifiers() {
        List<LinguisticQuantifier> quantifiers = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(String.valueOf(QuantifierManager.class.getResource("/RelativeQuantifiers.json")).substring("file:/".length()));

            JsonNode jsonNode = mapper.readTree(file);
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
                            parameters.get("d").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                } else if (Objects.equals(current.get("function").asText(), TriangleFunction.class.getSimpleName())) {
                    MembershipFunction function = new TriangleFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                } else if (Objects.equals(current.get("function").asText(), GaussianFunction.class.getSimpleName())) {
                    MembershipFunction function = new GaussianFunction(parameters.get("m").asDouble(),
                            parameters.get("s").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                } else {
                    MembershipFunction function = new CompoundGaussian(parameters.get("m1").asDouble(),
                            parameters.get("s1").asDouble(),
                            parameters.get("m2").asDouble(),
                            parameters.get("s2").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
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
            File file = new File(String.valueOf(QuantifierManager.class.getResource("/AbsoluteQuantifiers.json")).substring("file:/".length()));

            JsonNode jsonNode = mapper.readTree(file);
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
                            parameters.get("d").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("quantifierLabel").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                } else if (Objects.equals(current.get("function").asText(), TriangleFunction.class.getSimpleName())) {
                    MembershipFunction function = new TriangleFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("quantifierLabel").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                } else if (Objects.equals(current.get("function").asText(), GaussianFunction.class.getSimpleName())) {
                    MembershipFunction function = new GaussianFunction(parameters.get("m").asDouble(),
                            parameters.get("s").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                } else {
                    MembershipFunction function = new CompoundGaussian(parameters.get("m1").asDouble(),
                            parameters.get("s1").asDouble(),
                            parameters.get("m2").asDouble(),
                            parameters.get("s2").asDouble());

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("quantifierLabel").asText(),
                            new FuzzySet(new DenseUniverse(range.get(0).asDouble(), range.get(1).asDouble()), function));
                }
                quantifiers.add(newQuantifier);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return quantifiers;
    }

    public static void addLabelToRelativeQuantifiers(LinguisticQuantifier quantifier) {

    }

    public static void addLabelToAbsoluteQuantifiers(LinguisticQuantifier quantifier) {

    }
}
