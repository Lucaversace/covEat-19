package fr.coveat.app.controller;

import fr.coveat.app.repository.DishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private DishRepository dishRepository;

    IndexController (DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET )
    public String home(Model model) {
        model.addAttribute("dishes", dishRepository.findAll());
        return "home";
    }

    @RequestMapping(value = {"dish/{id}"}, method = RequestMethod.GET )
    public String getDish(@PathVariable("id") Long id, Model model) {
        if (dishRepository.existsById(id)) {
            model.addAttribute("dish", dishRepository.getOne(id));
            return "dish";
        }
        return "redirect:/";
    }
}
