package main;

import main.func.JsonManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5679)) {
            System.out.println("Server is start on port " + 5679);

            while (true) {

                // Приемане на връзка от клиент
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                // Четем заявката и си я принтираме
                String request = in.readLine();
                System.out.println(request);

                // Проверка на HTTP заявките от клиента
                if (request.startsWith("GET")) {

                    // Проверка към кой GET адрес се изпраща заявка
                    if (request.equals("GET / HTTP/1.1")) {

                        String urlHtml = "src/templates/index.html";
                        GetHandler(out, urlHtml);
                    } else if (request.equals("GET /style.css HTTP/1.1")) {

                        String urlCss = "src/templates/css/style.css";
                        GetHandler(out, urlCss);
                    } else if (request.equals("GET /web.js HTTP/1.1")) {

                        String urlJs = "src/templates/js/web.js";
                        GetHandler(out, urlJs);
                    } else {

                        out.println("HTTP/1.1 404 Not Found");
                        out.println("Content-Type: text/html");
                        out.println();
                        out.println("<h1>404 Not Found</h1>");
                    }

                } else if (request.startsWith("POST")) {

                    StringBuilder requestBody = new StringBuilder();
                    while (in.ready()) {
                        requestBody.append((char) in.read());
                    }

                    // Разделяме заявката на 2 части за да отделим тялото и от останалата информация
                    String[] data = requestBody.toString().split("\\n\\s*\\n");
                    String singleEntity = data[1];

                    // Запазване на информацията към json файла
                    PostHandler(singleEntity);

                    // Връщане на статус и отговор в json формат, че заявката е получена успешно
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: application/json");
                    out.println();
                } else {

                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                }

                out.flush();
                // Затваряне на връзката с клиента
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод за обработка на GET заявката, която връща HTML отговор
    private static void GetHandler(PrintWriter output, String urlHtml) throws IOException {

        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html");
        output.println();

        File htmlFile = new File(urlHtml);

        BufferedReader readHtml = new BufferedReader(new FileReader(htmlFile));
        String line;

        while ((line = readHtml.readLine()) != null) {
            output.println(line);
        }

        readHtml.close();
    }

    // Метод за обработка на GET заявката, която връща JSON отговор
    private static void GetInfoHandler(PrintWriter output) {

        // Връщане на статус и отговор в json формат
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: application/json");
        output.println();

        JsonManager infoManager = new JsonManager();
        String data = infoManager.getInfoFromJson();
        output.println(data);
    }

    // Метод за обработка и запазване на информация от Post заявката
    private static void PostHandler(String singleEntity) {

        JsonManager saveDataToRepo = new JsonManager();
        saveDataToRepo.saveInfoToJsonFile(singleEntity);
    }
}
