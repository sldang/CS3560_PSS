import org.json.JSONArray;
import org.json.JSONException;
import java.io.*;

//Used this link for help: https://www.baeldung.com/java-org-json
public class FileSaving {

    private static FileSaving instance;

    private FileSaving() {
        // Private constructor to prevent instantiation
    }

    public static FileSaving getInstance() {
        if (instance == null) {
            instance = new FileSaving();
        }
        return instance;
    }

    public void writeToFile(String fileName, JSONArray jsonArray) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            out.write(jsonArray.toString(4)); // Writing the JSONArray to file with indentation for readability
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray readFromFile(String fileName) {
        StringBuilder jsonData = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            return new JSONArray(jsonData.toString());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray(); // Return an empty JSONArray if there was an error
    }
}

