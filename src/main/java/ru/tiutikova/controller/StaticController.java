package ru.tiutikova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StaticController {

    @RequestMapping(value = {"/login", "/login/"}, method = RequestMethod.GET)
    public String longin () {
        return "login/login.html";
    }

}
