package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Rest Controller for all operations concerning the configuration file of the EUM-Server.
 */
@RestController()
@RequestMapping("/eum/")
public class EumConfigFileController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EumFileService eumFileService;

    @Autowired
    private EumServerProcessService eumServerProcessService;

    /**
     * Returns the current configuration file's content as String.
     * @return The current configuration file's content as String.
     */
    @GetMapping("file")
    public String getConfigurationFile() {
        return eumFileService.getFile();
    }

    /**
     * Returns the default configuration as String.
     * @return The default configuration as String.
     */
    @GetMapping("default")
    public String getDefaultConfigFile() {
        return eumFileService.getDefaultConfig();
    }

    /**
     * Expects a json in the format {content: **MY-FILE-CONTENT**}. The String found in the content-key will be saved
     * in the configuration file of the server.
     * @param content A json in the format {content: **MY-FILE-CONTENT**} in which the value of the content-key is a
     *                String resembling the new content of the configuration file.
     */
    @PostMapping("file")
    public void saveConfigurationFile(@RequestBody String content) throws IOException {
        String fileContent;
        if (content == null) {
            fileContent = "";
        } else {
            FileData data = objectMapper.readValue(content, FileData.class);
            fileContent = data.getContent();
        }
        eumFileService.saveFile(fileContent);
    }

    @PostMapping("apply")
    public void restartProcess(){
        eumServerProcessService.restart();
    }

    @PostMapping("start")
    public void startProcess(){
        eumServerProcessService.restart();
    }

    @PostMapping("stop")
    public void stopProcess(){
        eumServerProcessService.stop();
    }

    @GetMapping("status")
    public boolean getProcessStatus() {
        return eumServerProcessService.getStatus();
    }

    @GetMapping("logs")
    public String getLogs() {
        return eumServerProcessService.getEumLog();
    }
}
