package fr.coveat.app.controller;

import fr.coveat.app.repository.DishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private DishRepository dishRepository;

    IndexController (DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET )
    public String home(Model model) {
        model.addAttribute("dishes", dishRepository.findAll());
        System.out.println(dishRepository.findAll());
        return "home";
    }
    
}
