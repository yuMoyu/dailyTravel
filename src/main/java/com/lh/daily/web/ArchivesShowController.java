package com.lh.daily.web;

import com.lh.daily.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesShowController {

    @Autowired
    private DailyService dailyService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap",dailyService.archiveDaily());
        model.addAttribute("dailyCount",dailyService.countDaily());
        return "archives";
    }
}
