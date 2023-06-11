package pl.ksr.summary;

import pl.ksr.database.FlightsRepository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject implements Serializable {
    private String subject;

    public Map<String, List<Double>> objects;

    public Subject() {
        this.objects = new HashMap<>();
        initializeAttributes("");
    }

    public Subject(String subject) {
        this.objects = new HashMap<>();
        this.subject = subject;
        initializeAttributes(this.subject);
    }

    private void initializeAttributes(String subject) {
        System.out.println(subject);
        try {
            List<String> attributesNames = FlightsRepository.getAttributesNames();
            if (FlightsRepository.getAllSubjects().contains(subject)) {
                for (String attribute : attributesNames)
                    objects.put(attribute, FlightsRepository.findAllByNameAndSubject(attribute, subject));
                this.subject = "flights by " +  subject;
            } else {
                this.subject = "flights";
                for (String attribute : attributesNames) {
                    objects.put(attribute, FlightsRepository.findAllByName(attribute));
                }
            }
        } catch (RuntimeException e) {
            var deserialized = deserializeSubjects();
            if (deserialized != null) {
                for (var s : deserialized) {
                    if (s.getSubject().equals("flights") && subject.isEmpty()) {
                        this.subject = s.getSubject();
                        this.objects = s.objects;
                        return;
                    } else if (s.getSubject().equals(subject)) {
                        this.subject = "flights by " + subject;
                        this.objects = s.objects;
                        return;
                    }
                }
            }
        }
    }

    public void serializeSubjects() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("subjects.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            List<Subject> toSerialize = new ArrayList<>();
            for (String subject : FlightsRepository.getAllSubjects())
                toSerialize.add(new Subject(subject));

            toSerialize.add(new Subject());

            objectOutputStream.writeObject(toSerialize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> deserializeSubjects() {
        String jarFilePath = Subject.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        Path currentPath = Paths.get(jarFilePath).getParent();

        try (FileInputStream fileInputStream = new FileInputStream(currentPath + "/subjects.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (List<Subject>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("exception");
        }
        return null;
    }

    public List<Double> getObject(String attributeName) {
        return objects.get(attributeName);
    }

    public String getSubject() {
        return subject;
    }

}
