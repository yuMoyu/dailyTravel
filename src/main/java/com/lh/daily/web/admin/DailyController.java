package com.lh.daily.web.admin;

import com.lh.daily.po.Daily;
import com.lh.daily.po.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class DailyController {

    private static final String INPUT = "admin/dailys-input";
    private static final String LIST = "admin/dailys";
    private static final String REDIRECT_LIST = "redirect:/admin/dailys";
    @Autowired
    private DailyService dailyService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/dailys")
    public String list(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       DailyQuery daily, Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", dailyService.listDaily(pageable, daily));
        return LIST;
    }

    @PostMapping("/dailys/search") //用于局部刷新 渲染  如果直接提交给表单的话会把筛选条件覆盖掉
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         DailyQuery daily, Model model) {
        model.addAttribute("page", dailyService.listDaily(pageable, daily));
        //返回dailys下的dailyList片段
        return "admin/dailys :: dailyList";
    }

    @GetMapping("/dailys/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("daily", new Daily());
        return INPUT;
    }
    private void setTypeAndTag(Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/dailys/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Daily daily = dailyService.getDaily(id);
        //将tagIds变成字符串
        daily.init();
        model.addAttribute("daily", daily);
        return INPUT;
    }
    @PostMapping("/dailys")
    public String post(Daily daily, RedirectAttributes attributes, HttpSession session) {
        daily.setUser((User)session.getAttribute("user"));
        daily.setType(typeService.getType(daily.getType().getId()));
        daily.setTags(tagService.listTag(daily.getTagIds()));
        Daily d;
        if (daily.getId() == null) {
            d = dailyService.saveDaily(daily);
        } else {
            d = dailyService.updateDaily(daily.getId(),daily);
        }
        if (d == null) {
            attributes.addFlashAttribute("message","操作失败");
        } else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/dailys/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        dailyService.deleteDaily(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }
}
