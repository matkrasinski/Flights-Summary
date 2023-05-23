package pl.ksr.view;

import java.io.FileWriter;
import java.util.List;

public class FileManager {

    public static void writeToFile(String path, List<String> lines) {
        if (!path.endsWith(".txt"))
            path += ".txt";
        try (FileWriter fileWriter = new FileWriter(path)){
            for (String line : lines)
                fileWriter.write(line + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
