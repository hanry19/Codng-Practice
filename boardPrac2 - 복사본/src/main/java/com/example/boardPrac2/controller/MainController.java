package com.example.boardPrac2.controller;

import com.example.boardPrac2.service.ServiceMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    ServiceMember serviceMember;

    @RequestMapping("/")
    public String main(Model model) {


        return "/index";
    }

    @RequestMapping("/tables")
    public String tables(Model model, @RequestParam(defaultValue = "1") String pagenum, @RequestParam(defaultValue = "10") String contentnum) throws Exception{
        serviceMember.execute(model, pagenum, contentnum);
        return "/tables";
    }
}