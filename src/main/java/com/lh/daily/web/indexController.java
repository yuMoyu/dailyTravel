package com.lh.daily.web;


import com.lh.daily.service.DailyService;
import com.lh.daily.service.TagService;
import com.lh.daily.service.TypeService;
import com.lh.daily.vo.DailyQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class indexController {

    @Autowired
    private DailyService dailyService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",dailyService.listDaily(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(8));
        model.addAttribute("recommendDailys",dailyService.listRecommendDailyTop(2));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page",dailyService.listDaily("%"+query+"%", pageable));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/daily/{id}")
    public String daily(@PathVariable Long id, Model model) {
        model.addAttribute("daily",dailyService.getAndConvert(id));
        return "daily";
    }
}
