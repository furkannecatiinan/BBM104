import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class Reader {


    public static List<Classroom> classrooms = new ArrayList<>();
    public static List<Decoration> decorations = new ArrayList<>();

    public static void readItemsFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts[0].equals("CLASSROOM")) {
                String name = parts[1];
                String shape = parts[2];
                double width = Double.parseDouble(parts[3]);
                double length = Double.parseDouble(parts[4]);
                double height = Double.parseDouble(parts[5]);


                Classroom classroom = new Classroom.Builder()
                        .setName(name)
                        .setShape(shape)
                        .setWidth(width)
                        .setLength(length)
                        .setHeight(height)
                        .build();
                classrooms.add(classroom);

            } else if (parts[0].equals("DECORATION")) {
                String name = parts[1];
                String type = parts[2];
                double price = Double.parseDouble(parts[3]);
                double area = 0;

                if (type.equals("Tile")) {
                    area = Double.parseDouble(parts[4]);
                }


                Decoration decoration = new Decoration(name, type, price, area);
                decorations.add(decoration);

            }
        }

        reader.close();
    }


    public static Map<String, String[]> classroomDecorations = new HashMap<>();

    public static void readDecorationFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");

            classroomDecorations.put(parts[0], new String[]{parts[1], parts[2]});
        }
        reader.close();
    }

}
