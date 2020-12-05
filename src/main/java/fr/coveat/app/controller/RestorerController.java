package fr.coveat.app.controller;

import fr.coveat.app.model.Dish;
import fr.coveat.app.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestorerController {
    @Autowired
    private DishRepository dishRepository;

    @RequestMapping(value = {"/restorer/","/restorer/dishes"}, method = RequestMethod.GET )
    public String getDish(Model model) {
        model.addAttribute("dishes", dishRepository.findAll());
        return "dish_list";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.GET )
    public String createDish(Model model) {

        model.addAttribute("dishForm", new Dish());
        return "add_dish";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.POST )
    public String postCreateDish(@ModelAttribute("dish") Dish dish) {

        dishRepository.save(dish);

        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/edit_dish"}, method = RequestMethod.GET )
    public String editDish() {

        return "edit_dish";
    }

    @RequestMapping(value = {"/restorer/delete_dish"}, method = RequestMethod.GET )
    public String deleteDish() {

        return "redirect:/restorer/";
    }
}
