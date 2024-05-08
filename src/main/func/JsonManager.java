package main.func;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonManager {

    // Path to the JSON file
    String jsonPath = "src/data/data.json";

    public void saveInfoToJsonFile(String newData) {

        try {
            // Read the content of the existing JSON file
            String jsonExitContent = new String(Files.readAllBytes(Paths.get(jsonPath)));

            // Convert the content to a JSONArray
            JSONArray exitData = new JSONArray(jsonExitContent);

            // Convert the new data string to a JSONObject
            JSONObject newIncomeData = new JSONObject(newData);

            // Append the new data to the existing JSON array
            exitData.put(newIncomeData);

            // Write the updated JSON array to the file
            FileWriter writer = new FileWriter(jsonPath);

            // Indented JSON with 4 spaces
            writer.write(exitData.toString(4));
            writer.close();

            System.out.println("Data successfully saved!");
        } catch (IOException e) {
            // Handle file-related errors
            System.out.println("File Error!");
        }
    }

    // Retrieves all information from the JSON file
    public String getInfoFromJson() {

        String jsonInfo = "";
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonPath)));
            JSONArray jsonArray = new JSONArray(jsonContent);

            // Add an opening square bracket to ensure a valid response to the page
            jsonInfo += "[\n";

            // Iterate through each note in the array
            for (int i = 0; i < jsonArray.length(); i++) {

                // Get each entity individually from the JSON file
                JSONObject object = jsonArray.getJSONObject(i);

                // Extract data from the current object and add it to the string
                if (i == jsonArray.length() - 1) {
                    jsonInfo += object.toString() + "\n";
                } else {
                    jsonInfo += object.toString() + ",\n";
                }
            }

            // Add the closing square bracket
            jsonInfo +="]";
        } catch (FileNotFoundException e) {
            // Handle file not found error
            jsonInfo = "File was not found!";

        } catch (IOException e) {
            // Handle other file-related errors
            jsonInfo = "File Error!";

        }

        /*
            To ensure proper handling of server responses, the JSON file's information must be returned
            in the same format it is stored - with "[" ",", "]" and so on.
        */

        return jsonInfo;
    }
}