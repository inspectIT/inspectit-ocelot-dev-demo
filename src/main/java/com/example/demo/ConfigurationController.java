package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
public class ConfigurationController {

    @Value("${spring.thymeleaf.templates_root:}")
    private String templatesRoot;

    @GetMapping("/configuration")
    public String configuration(Model model) {
        model.addAttribute("title", "Configuration Template");
        return "configuration";
    }

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public String saveFile(@RequestParam String sitePath, @RequestBody String content) throws IOException {
        Path path = Paths.get(templatesRoot + sitePath + ".html");
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        return "configuration";
    }

    @GetMapping(value = "/reset")
    public String resetFile(@RequestParam String sitePath) throws IOException {
        Path currentPath = Paths.get(templatesRoot + sitePath + ".html");
        Files.copy(Objects.requireNonNull(DemoApplication.class.getClassLoader()
                .getResourceAsStream("templates/" + sitePath + ".html")), currentPath, StandardCopyOption.REPLACE_EXISTING);
        return "configuration";
    }
}
