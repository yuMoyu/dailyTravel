package com.lh.daily.web.admin;

import com.lh.daily.po.Type;
import com.lh.daily.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        //根据前端构造好的参数，自动封装到pageable里面，实现分页
        //每页十条数据，根据id倒序排序
        //分页数据放入前端展示模型内
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("types/{id}/input") //@PathVariable接收路径中 id
    public String editInput(@PathVariable Long id, Model model) {
        //查询到数据放到model内，返回到页面
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @GetMapping("/types/input") //新增
    public String input(Model model) {
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types") //post和get同名不会冲突
    //@Valid Type type：校验type对象
    //BindingResult 接受校验后的结果
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //新增类型存在
        if(type1 != null) {
            //验证值  错误 最终返回的消息
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            //未保存成功
            attributes.addFlashAttribute("message","新增失败");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    //BindingResult一定要紧跟在Type后，不然校验没有效果
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            //验证值  错误 最终返回的消息
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null) {
            //未保存成功
            attributes.addFlashAttribute("message","更新失败");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
