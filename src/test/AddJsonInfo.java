package test;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AddJsonInfo {
    public static void main(String[] args) {
        String newData = "{\"first_name\":\"tes3\",\"last_name\":\"tes3\",\"age\":\"tes3\"}";

        try {
            // Четене на съществуващия JSON файл
            String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/newDataJson.json")));

            JSONArray jsonArray = new JSONArray(jsonContent);
            JSONObject newDataInfo = new JSONObject(newData);

            jsonArray.put(newDataInfo);

            FileWriter fileWriter = new FileWriter("src/test/newDataJson.json");
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
