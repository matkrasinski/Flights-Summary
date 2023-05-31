package pl.ksr.summary;

import pl.ksr.database.FlightsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject {
    private final String subject;

    public Map<String, List<Double>> objects;

    public Subject() {
        this.objects = new HashMap<>();
        this.subject = "flights";
        initializeAttributes();
    }

    public Subject(String subject) {
        this.objects = new HashMap<>();
        if (allSubjects().contains(subject)) {
            initializeAttributes(subject);
            this.subject = "flights where manufacturer was " +  subject;
        } else {
            this.subject = "flights";
            initializeAttributes();
        }
    }

    public static List<String> allSubjects() {
        return FlightsRepository.getAllSubjects();
    }

    private void initializeAttributes() {
        List<String> attributesNames = FlightsRepository.getAttributesNames();
        for (String attribute : attributesNames) {
            objects.put(attribute, FlightsRepository.findAllByName(attribute));
        }
    }
    private void initializeAttributes(String subject) {
        List<String> attributesNames = FlightsRepository.getAttributesNames();
        for (String attribute : attributesNames) {
            objects.put(attribute, FlightsRepository.findAllByNameAndSubject(attribute, subject));
        }
    }

    public List<Double> getObject(String attributeName) {
        return objects.get(attributeName);
    }

    public String getSubject() {
        return subject;
    }

}
