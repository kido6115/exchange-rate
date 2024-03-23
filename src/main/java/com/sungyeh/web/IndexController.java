package com.sungyeh.web;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sungyeh.domain.ExchangeRate;
import com.sungyeh.model.QueryContext;
import com.sungyeh.service.ExchangeRateService;

import jakarta.annotation.Resource;


@Controller
@RequestMapping("/home")
public class IndexController {

    @Resource
    private ExchangeRateService service;

    @RequestMapping({"","/index","/"})
    public String index(Model model, @ModelAttribute("queryContext") QueryContext queryContext){
        Page<ExchangeRate> page=service.findAllByCondition(queryContext);
        model.addAttribute("data",page);
        return "home/index";
    }
    @GetMapping("add")
    public String add(Model model){
        model.addAttribute("data", new ExchangeRate());
        return "home/add";
    }
    @PostMapping("add")
    public String save(ExchangeRate exchangeRate){
        exchangeRate.setId(null);
        service.save(exchangeRate);
        return "redirect:/home";
    }
    @GetMapping("update/{id}")
    public String edit(@PathVariable("id")String id,Model model){
       ExchangeRate exchangeRate=service.findOne(id);
       model.addAttribute("data", exchangeRate);
        return "home/update";
    }

    @PostMapping("update")
    public String update(ExchangeRate exchangeRate){
        service.update(exchangeRate);
        return "redirect:/home";
    }
    
    @GetMapping("delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id")String id){
        service.delete(id);

    }

    

}
