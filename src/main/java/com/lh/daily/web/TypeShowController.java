package com.lh.daily.web;

import com.lh.daily.po.Daily;
import com.lh.daily.po.Type;
import com.lh.daily.service.DailyService;
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

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private DailyService dailyService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id,
                        Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        DailyQuery dailyQuery = new DailyQuery();
        dailyQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",dailyService.listDaily(pageable,dailyQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
