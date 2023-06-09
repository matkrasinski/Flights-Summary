package pl.ksr.lingustic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.ksr.functions.*;
import pl.ksr.sets.ContinuousUniverse;
import pl.ksr.sets.FuzzySet;
import pl.ksr.sets.Universe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuantifierReader {

    public static List<LinguisticQuantifier> loadRelativeQuantifiers() {
        List<LinguisticQuantifier> quantifiers = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = QuantifierReader.class.getResourceAsStream("/RelativeQuantifiers.json");

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
                            new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));

                    newQuantifier = new LinguisticQuantifier(true,
                            current.get("label").asText(),
                                    new FuzzySet(function));
                } else if (Objects.equals(current.get("function").asText(), TriangleFunction.class.getSimpleName())) {
                    MembershipFunction function = new TriangleFunction(
                            parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));

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

            InputStream inputStream = QuantifierReader.class.getResourceAsStream("/AbsoluteQuantifiers.json");
            JsonNode jsonNode = mapper.readTree(inputStream);
            JsonNode absoluteQuantifiers = jsonNode.get("absoluteQuantifiers");

            for (int i = 0; i < absoluteQuantifiers.size(); i++) {
                LinguisticQuantifier newQuantifier;
                JsonNode current = absoluteQuantifiers.get(i);
                JsonNode parameters = current.get("parameters");
                JsonNode range = jsonNode.get("range");

                if (Objects.equals(current.get("function").asText(), TrapezoidFunction.class.getSimpleName())) {
                    MembershipFunction function = new TrapezoidFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            parameters.get("d").asDouble(),
                            new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));

                    newQuantifier = new LinguisticQuantifier(false,
                            current.get("label").asText(),
                            new FuzzySet(function));
                } else if (Objects.equals(current.get("function").asText(), TriangleFunction.class.getSimpleName())) {
                    MembershipFunction function = new TriangleFunction(parameters.get("a").asDouble(),
                            parameters.get("b").asDouble(),
                            parameters.get("c").asDouble(),
                            new ContinuousUniverse(range.get(0).asDouble(), range.get(1).asDouble()));

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
                            )), universe);

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



}
