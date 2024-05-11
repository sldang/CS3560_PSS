import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.Iterator;
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
        JSONArray existingTasks = readJSONArrayFromFile(fileName); // Load the existing JSON array from the file

        // Convert new tasks to JSON and append them to the existing JSON array
        for (Task task : newTasks) {
            existingTasks.put(task.toJSONObject());
        }

        // Write the updated JSON array back to the file
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.write(existingTasks.toString(4)); // Writing with indentation for readability
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> readFromFile(String fileName) {
        JSONArray readArray = readJSONArrayFromFile(fileName);
        List<Task> task = new LinkedList<>();
        Iterator<Object> arrayIterator = readArray.iterator();
        while (arrayIterator.hasNext()){
            JSONObject working = (JSONObject) arrayIterator.next();

            /**
             * @FIXME There should be a better way to do this rather than extracting data and applying operations on it
             * Either the Factory or the Task should handle what type it is - Alex
            **/
            String type = working.getString("Type");
            String translatedType = TaskFactory.getTranslation(type);

            Task newTask = TaskFactory.getInstance().createTask(translatedType);
            newTask.fromJSONObject(working);

            task.add(newTask); //@FIXME Are tasks saved sequentially? - Alex
        }
        return task;
    }

    // new method (works)
    private JSONArray readJSONArrayFromFile(String fileName) {
        StringBuilder jsonData = new StringBuilder();
        String line;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            String jsonString = jsonData.toString().trim();
            if (!jsonString.startsWith("[")) {
                // If JSON data doesn't start with '[', add it to make it an array
                jsonString = "[" + jsonString + "]";
            }
            return new JSONArray(jsonString);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray(); // Return an empty JSONArray if there was an error
    }
    
}




