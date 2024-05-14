package main;

import main.func.JsonManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5678)) {
            System.out.println("Server is start on port " + 5678);

            while (true) {
                // Accept connection from client
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                // Read and print the request
                String request = in.readLine();
                System.out.println(request);

                // Check HTTP requests from the client
                if (request.startsWith("GET")) {
                    // Checking which GET address the request is sent to
                    if (request.equals("GET / HTTP/1.1")) {

                        String urlHtml = "src/templates/index.html";
                        GetHandler(out, urlHtml);
                    } else if (request.equals("GET /dict.html HTTP/1.1")) {

                        String urlHtml = "src/templates/dict.html";
                        GetHandler(out, urlHtml);
                    } else if (request.equals("GET /test.html HTTP/1.1")){

                        String urlHtml = "src/templates/test.html";
                        GetHandler(out, urlHtml);
                    }
                    // Get Css Files
                    else if (request.equals("GET /style.css HTTP/1.1")) {

                        String urlCss = "src/templates/css/style.css";
                        GetHandlerCss(out, urlCss);
                    } else if (request.equals("GET /sideboard.css HTTP/1.1")) {

                        String urlCss = "src/templates/css/sideboard.css";
                        GetHandlerCss(out, urlCss);
                    }
                    // Get JavaScript Files
                    else if (request.equals("GET /dict.js HTTP/1.1")) {

                        // application/javascript
                        String urlJs = "src/templates/js/dict.js";
                        GetHandler(out, urlJs);
                    }else if (request.equals("GET /changer.js HTTP/1.1")){

                        String urlJs = "src/templates/js/changer.js";
                        GetHandler(out, urlJs);
                    }else if (request.equals("GET /index.js HTTP/1.1")){

                        String urlJs = "src/templates/js/index.js";
                        GetHandler(out, urlJs);
                    }else if (request.equals("GET /dialog.js HTTP/1.1")){

                        String urlJs = "src/templates/js/dialog.js";
                        GetHandler(out, urlJs);
                    }
                    // Get Info
                    else if (request.equals("GET /dict/info HTTP/1.1")) {

                        // Handling GET request for dictionary info
                        GetInfoHandler(out);
                    } else if (request.equals("GET /logo.png HTTP/1.1")) {

                        String imgUrl = "src/resource/images/logo.png";
                        GetHandlerImg(imgUrl, clientSocket);
                    } else {

                        // Handling 404 Not Found
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

                    // Splitting the request into 2 parts to separate the body from the rest of the information
                    String[] data = requestBody.toString().split("\\n\\s*\\n");
                    String singleEntity = data[1];

                    // Saving the information to the JSON file
                    PostHandler(singleEntity);

                    // Returning status and response in JSON format that the request was received successfully
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: application/json");
                    out.println();
                } else {

                    // Handling other HTTP methods
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                }

                out.flush();
                // Closing the connection with the client
                clientSocket.close();
            }
        } catch (IOException e) {
            // Handling IO exceptions
            e.printStackTrace();
        }
    }

    // Method for handling GET requests that return HTML response
    private static void GetHandler(PrintWriter output, String urlHtml) throws IOException {

        // Sending HTTP header for a successful response
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html");
        output.println();

        // Reading HTML file and sending its content to the client
        File htmlFile = new File(urlHtml);
        BufferedReader readHtml = new BufferedReader(new FileReader(htmlFile));
        String line;

        while ((line = readHtml.readLine()) != null) {
            output.println(line);
        }

        readHtml.close();
    }

    // Method for handling CSS requests
    private static void GetHandlerCss(PrintWriter output, String urlCss) throws IOException {

        // Sending HTTP header for a successful response
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/css");
        output.println();

        // Reading CSS file and sending its content to the client
        File cssFile = new File(urlCss);
        BufferedReader readCss = new BufferedReader(new FileReader(cssFile));
        String line;

        while ((line = readCss.readLine()) != null) {
            output.println(line);
        }

        readCss.close();
    }

    private static void GetHandlerImg(String urlImage, Socket clientSocket) throws IOException {

        OutputStream out = clientSocket.getOutputStream();
        FileInputStream fileIn = new FileInputStream(urlImage);
        byte[] buffer = new byte[1024];
        int bytesRead;

        out.write("HTTP/1.1 200 OK\\r\\n".getBytes());
        out.write("Content-Type: image/png\r\n".getBytes());
        out.write("\r\n".getBytes());

        while ((bytesRead = fileIn.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        out.close();
    }

    // Method for handling GET requests that return a JSON response
    private static void GetInfoHandler(PrintWriter output) {

        // Sending HTTP header for a successful JSON response
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: application/json");
        output.println();

        // Retrieving JSON data and sending it as the response
        JsonManager infoManager = new JsonManager();
        String data = infoManager.getInfoFromJson();
        output.println(data);
    }

    // Method for handling and saving information from a POST request
    private static void PostHandler(String singleEntity) {

        // Creating an instance of JsonManager to save data to the repository
        JsonManager saveDataToRepo = new JsonManager();

        // Saving the received data to the JSON file
        saveDataToRepo.saveInfoToJsonFile(singleEntity);
    }
}
