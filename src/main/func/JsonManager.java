package main.func;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonManager {

    String jsonPath = "src/data/data.json";

    public void saveInfoToJsonFile(String newData) {

        try {
            String jsonExitContent = new String(Files.readAllBytes(Paths.get(jsonPath)));

            JSONArray exitData = new JSONArray(jsonExitContent);
            JSONObject newIncomeData = new JSONObject(newData);

            exitData.put(newIncomeData);

            FileWriter writer = new FileWriter(jsonPath);
            writer.write(exitData.toString(4));
            writer.close();

            System.out.println("Data successfully saved!");
        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }

    // Взимаме цялата информация от json файла
    public String getInfoFromJson() {

        String jsonInfo = "";
        try {
            // Прочитане на съдържанието на JSON файл
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonPath)));
            JSONArray jsonArray = new JSONArray(jsonContent);

            // Слагаме отваряща скоба, защото иначе без тях отговора към страницата няма да е валиден
            jsonInfo += "[\n";

            // Обхождане на всяка една записка в масива
            for (int i = 0; i < jsonArray.length(); i++) {

                // Взимам всеко отделно entity в json файла
                JSONObject object = jsonArray.getJSONObject(i);

                // Извличане на данни от текущия обект и добавянето им в стринг
                if (i == jsonArray.length() - 1) {
                    jsonInfo += object.toString() + "\n";
                } else {
                    jsonInfo += object.toString() + ",\n";
                }
            }

            // Слагаме затварящата скоба
            jsonInfo +="]";
        } catch (FileNotFoundException e) {
            jsonInfo = "File was not found!";
        } catch (IOException e) {
            jsonInfo = "File Error!";
        }

        /*
            За да може да работи коректно сървърния отговор, трябва на файла "json" информацията да се връща
            по същия начин по който е записана - с "[" със "," , "]" и т.н.
         */

        return jsonInfo;
    }
}