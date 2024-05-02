import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileSaving {

    private static FileSaving instance;

    private FileSaving() { }

    public static FileSaving getInstance() {
        if (instance == null) {
            instance = new FileSaving();
        }
        return instance;
    }

    public void writeTasksToFile(String fileName, List<Task> newTasks) {
        File file = new File(fileName);
        JSONArray existingTasks = readFromFile(fileName); // Load the existing JSON array from the file

        // Convert new tasks to JSON and append them to the existing JSON array
        for (Task task : newTasks) {
            existingTasks.put(toJSONObject(task));
        }

        // Write the updated JSON array back to the file
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(existingTasks.toString(4)); // Writing with indentation for readability
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

    private JSONObject toJSONObject(Task task) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", task.getName());
        jsonObject.put("Type", task.getType());
        jsonObject.put("StartDate", task.getStartDate());
        jsonObject.put("StartTime", task.getStartTime());
        jsonObject.put("Duration", task.getDuration());
        if (task.getEndDate() != 0) {
            jsonObject.put("EndDate", task.getEndDate());
        }
        // Add more fields if necessary, especially handling optional fields and linked tasks
        return jsonObject;
    }
}




