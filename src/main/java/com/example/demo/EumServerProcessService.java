package com.example.demo;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class provides functionality to restart the current Java application with all parameters it was initially
 * started with.
 */
@Component
public class EumServerProcessService {

    private Process eumServerProcess;

    private String eumLog;

    public void start() {
        boolean error = false;
        System.out.println("Starting EUM-Server.");
        try {
            // java binary
            Path javaPath = Paths.get(System.getProperty("java.home"), "bin", "java");

            // init the command to execute, add the vm args
            final StringBuffer cmd = new StringBuffer(javaPath.toString() + " -jar " + "inspectit-ocelot-eum-server-SNAPSHOT.jar");
            System.out.println(cmd.toString());

            ProcessBuilder processBuilder = new ProcessBuilder(javaPath.toString(), "-Xmx256m", "-jar", "inspectit-ocelot-eum-server-SNAPSHOT.jar");
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new File("eum"));
            eumServerProcess = processBuilder.start();

            new Thread(this::readOutput).start();

            // prevent orphan processes
            Runtime.getRuntime().addShutdownHook(new Thread(() -> eumServerProcess.destroy()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            error = true;
        }
        if (!error) {
            System.out.println("EUM-Server started successfully!");
        }
    }

    private void readOutput() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(eumServerProcess.getInputStream()));
            String line = null;
            eumLog = "--- LOG-BEGIN ---";
            while ((line = reader.readLine()) != null) {
                eumLog += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("EUM-Process log stream closed.");
        }
        eumLog += "\n--- LOG-END ---";
        System.out.println("EUM output stream closed!");
    }

    public void stop() {
        System.out.println("Stopping EUM-Server.");

        if (eumServerProcess != null) {
            eumServerProcess.destroy();
        }
    }

    public void restart() {
        System.out.println("Restarting EUM-Server.");

        this.stop();
        this.start();
    }

    public String getEumLog() {
        return eumLog;
    }

    public boolean getStatus() {
        if (eumServerProcess == null) {
            return false;
        }
        try {
            eumServerProcess.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

}
