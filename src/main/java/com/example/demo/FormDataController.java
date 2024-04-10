package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FormDataController {

    @GetMapping("/formdata")
    public String formData(Model model) {
        model.addAttribute("FormDataObject", new FormDataObject());
        return "formdata";
    }

    @ResponseBody
    @PostMapping(value = "/formdata", consumes = "text/plain", produces = "text/plain")
    public String formDataSubmit(@RequestBody(required=false) String content) {
        if (content == null) {
            return "Thank You Mario, But Our Princess is in Another Castle!";
        }
        return content.toUpperCase();
    }
}
