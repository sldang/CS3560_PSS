import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        public void writeToFile(String fileName, JSONObject newJsonObject) {
        File file = new File(fileName);
        JSONArray jsonArray = new JSONArray();

        // Read the existing content of the file into a JSONArray
        if (file.exists() && file.length() != 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String jsonData = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData += line;
                }
                jsonArray = new JSONArray(jsonData); // Load the existing JSON array from the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add the new JSON object to the JSONArray
        jsonArray.put(newJsonObject);

        // Write the updated JSONArray back to the file
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(jsonArray.toString(4)); // Writing with indentation for readability
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

