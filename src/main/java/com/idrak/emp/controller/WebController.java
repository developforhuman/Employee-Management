package com.idrak.emp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/form")
    public String showForm() {
        return "form.html";
    }

}
