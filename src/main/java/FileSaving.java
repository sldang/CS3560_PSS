import java.io.*;

public class FileSaving {

    private static FileSaving instance;

    private FileSaving() {
        //constructor
    }

    public static FileSaving getInstance() {
        if (instance == null) {
            instance = new FileSaving();
        }
        return instance;
    }

    public void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine(); // Ensures each content addition is on a new line
        } catch (IOException e) {
            // Handle exceptions properly in your application context
            e.printStackTrace();
        }
    }

    public void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            // Handle exception, perhaps return an error or throw a custom exception
        } catch (IOException e) {
            // Handle exceptions properly in your application context
            e.printStackTrace();
        }
    }
 }
