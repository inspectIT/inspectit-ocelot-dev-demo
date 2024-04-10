package com.example.demo;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This class contains all methods concerning the editing of the configuration file by the server's configuration UI.
 *
 */
@Component
public class EumFileService {

    /**
     * File encoding to be used by all read and write accesses.
     */
    private static final String FILE_ENCODING = "UTF-8";

    /**
     * Default file path which is used when no file path was given at server startup.
     */
    private static final String DEFAULT_FILE_PATH = "./inspectit-eum/application.properties";


    /**
     * Takes a String resembling a file content and writes it to the file present in the file path with which the class
     * was initiated.
     * @param content A String resembling the content to be written.
     */
    public void saveFile(String content) throws IOException {
        Path path = Paths.get(DEFAULT_FILE_PATH);
        Files.write(path, content.getBytes(FILE_ENCODING));
    }

    /**
     * Returns a String resembling the content of the file present in the file path with which the class was initiated.
     * @return A String resembling the content of the file.
     */
    public String getFile(){
        Scanner fileScanner = null;
        StringBuilder config = new StringBuilder();

        try {
            fileScanner = getFileScanner();
            while (fileScanner.hasNextLine()) {
                config.append(fileScanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fileScanner != null) {
                fileScanner.close();
            }
        }


        return config.toString();
    }

    /**
     * Returns a String resembling the content of the application.properties file present in the resource-folder of the
     * server.
     * @return A String resembling the content of the application.properties present in the resource-folder of the server.
     */
    public String getDefaultConfig() {
        StringBuilder config = new StringBuilder();

        BufferedReader reader = getDefaultFileReader();

        try {
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                config.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config.toString();
    }

    private File getFileObject() {
        return new File(DEFAULT_FILE_PATH);
    }

    private BufferedReader getDefaultFileReader() {
        InputStream in = getClass().getResourceAsStream(DEFAULT_FILE_PATH);
        return new BufferedReader(new InputStreamReader(in));
    }

    private Scanner getFileScanner() throws FileNotFoundException {
        File fileObject = getFileObject();
        return new Scanner(fileObject);
    }

}
