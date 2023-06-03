package pl.ksr.summary;

import pl.ksr.database.FlightsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject {
    private String subject;

    public Map<String, List<Double>> objects;

    public Subject() {
        this.objects = new HashMap<>();
        this.subject = "flights";
        initializeAttributes(this.subject);
    }

    public Subject(String subject) {
        this.objects = new HashMap<>();
        this.subject = subject;
        initializeAttributes(this.subject);
    }

    public static List<String> allSubjects() {
        return FlightsRepository.getAllSubjects();
    }

    private void initializeAttributes(String subject) {
        try {
            List<String> attributesNames = FlightsRepository.getAttributesNames();
            if (allSubjects().contains(subject)) {
                for (String attribute : attributesNames) {
                    objects.put(attribute, FlightsRepository.findAllByName(attribute));
                }
                this.subject = "flights where aircraft manufacturer was " +  subject;
            } else {
                this.subject = "flights";
                for (String attribute : attributesNames) {
                    objects.put(attribute, FlightsRepository.findAllByNameAndSubject(attribute, subject));
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Something went wrong while loading subjects");
        }
    }

    public List<Double> getObject(String attributeName) {
        return objects.get(attributeName);
    }

    public String getSubject() {
        return subject;
    }

}
