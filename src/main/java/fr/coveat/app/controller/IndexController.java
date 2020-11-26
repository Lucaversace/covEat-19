package fr.coveat.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = {"/","add_dish"}, method = RequestMethod.GET )
    public String add_dish() {

        //model.addAttribute("message",message);

        return "login";

    }
}
