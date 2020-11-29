package fr.coveat.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = {"/","login"}, method = RequestMethod.GET )
    public String login() {

        return "login";
    }
    
    @RequestMapping(value = {"add_dish"}, method = RequestMethod.GET )
    public String add_dish() {

        return "add_dish";
    }
    
    @RequestMapping(value = {"dish_list"}, method = RequestMethod.GET )
    public String dish_list() {

        return "dish_list";
    }
    
    @RequestMapping(value = {"edit_dish"}, method = RequestMethod.GET )
    public String edit_dish() {

        return "edit_dish";
    }
    
    @RequestMapping(value = {"home"}, method = RequestMethod.GET )
    public String home() {

        return "home";
    }
}
