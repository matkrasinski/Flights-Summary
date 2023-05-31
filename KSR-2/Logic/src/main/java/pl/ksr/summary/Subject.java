package pl.ksr.summary;

import pl.ksr.database.Flight;
import pl.ksr.database.FlightsRepository;

import java.util.List;

public class Subject {
    private final String subject;
    private final List<Flight> objects;

    public Subject() {
        this.subject = "flights";
        this.objects = FlightsRepository.findAll();
    }

    public Subject(String subject) {
        if (allSubjects().contains(subject)) {
            this.objects = FlightsRepository.findAllBySubject(subject);
            this.subject = "flights where manufacturer was " +  subject;
        } else {
            this.subject = "flights";
            this.objects = FlightsRepository.findAll();
        }
        System.out.println(this.objects.size());
    }

    public static List<String> allSubjects() {
        return FlightsRepository.getAllSubjects();
    }

    public String getSubject() {
        return subject;
    }

    public List<Flight> getObjects() {
        return objects;
    }
}
