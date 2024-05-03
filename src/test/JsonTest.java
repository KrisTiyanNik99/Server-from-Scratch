package test;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonTest {
    public static void main(String[] args) {

        String content = "";

        try {
            // Прочитане на съдържанието на JSON файл
            String jsonContent = new String(Files.readAllBytes(Paths.get("src/data/data.json")));

            JSONArray jsonArray = new JSONArray(jsonContent);

            // Обхождане на всяка една записка в масива
            for (int i = 0; i < jsonArray.length(); i++) {

                // Взимам всеко отделно entity в json файла
                JSONObject object = jsonArray.getJSONObject(i);

                content += object.toString() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException e) {
            System.out.println("File Error!");
        }

        System.out.println(content);
    }
}
