package ru.temansky.tempcard.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewSensorsData {
    @GetMapping("/viewSensorsData")
    public String home(Model model) {
        //model.addAttribute("title", "Главная страница");
        return "viewSensorsData";
    }
}
