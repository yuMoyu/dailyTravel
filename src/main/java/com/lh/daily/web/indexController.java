package com.lh.daily.web;

import com.lh.daily.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class indexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/daily")
    public String daily() {
        return "daily";
    }
}
