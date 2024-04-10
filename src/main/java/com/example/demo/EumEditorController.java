package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EumEditorController {

    @Value("${spring.thymeleaf.templates_root:}")
    private String templatesRoot;

    @GetMapping("/eum-editor")
    public String configuration(Model model) {
        model.addAttribute("title", "Configuration Template");
        return "eumeditor";
    }
}
