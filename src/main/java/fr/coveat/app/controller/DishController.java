package fr.coveat.app.controller;

import fr.coveat.app.model.Dish;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DishController {

    @RequestMapping(value = {"add_dish"}, method = RequestMethod.GET )
    public String add_dish(Model model) {

        model.addAttribute("dish", new Dish());
        return "add_dish";
    }

    @RequestMapping(value = {"add_dish"}, method = RequestMethod.POST )
    public String post_add_dish(@ModelAttribute("dish") Dish dish) {
        return "dish";
    }

    @RequestMapping(value = {"dish_list"}, method = RequestMethod.GET )
    public String dish_list() {

        return "dish_list";
    }

    @RequestMapping(value = {"edit_dish"}, method = RequestMethod.GET )
    public String edit_dish() {

        return "edit_dish";
    }
}
