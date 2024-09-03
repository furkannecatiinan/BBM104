import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    public static List<String> readFile(String filePath) throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commands.add(line);
            }
        }
        return commands;
    }

    public static void writeFile(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}