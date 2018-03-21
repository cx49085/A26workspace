package com.work26.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ChenJY on 2018/3/20.
 */

@Controller
public class Controller_index {

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }
}
