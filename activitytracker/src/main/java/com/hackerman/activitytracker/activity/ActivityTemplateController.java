package com.hackerman.activitytracker.activity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActivityTemplateController {

    @GetMapping("/home")
    public String home(){
        return "home";//apparently this returns a template with filename home.html
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
