package fr.coveat.app.controller;

import fr.coveat.app.form.DishForm;
import fr.coveat.app.model.Dish;
import fr.coveat.app.model.Restaurant;
import fr.coveat.app.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RestorerController {
    @Autowired
    private DishRepository dishRepository;

    @RequestMapping(value = {"/restorer/","/restorer"}, method = RequestMethod.GET )
    public String getDish(Model model) {
        model.addAttribute("dishes", dishRepository.findAll());
        return "restorer/dish_list";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.GET )
    public String createDish(Model model) {

        model.addAttribute("dishForm", new Dish());
        return "restorer/add_dish";
    }

    @RequestMapping(value = {"/restorer/add_dish"}, method = RequestMethod.POST )
    public String postCreateDish(@ModelAttribute("dish") Dish dish) {
    	// TODO : A MODIFIER APRES LES SESSIONS

    	Restaurant restaurant=new Restaurant(); 
    	restaurant.setId(1);

    	dish.setRestaurant(restaurant);
        dishRepository.save(dish);

        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/edit_dish"}, method = RequestMethod.GET )
    public String editDish(@PathVariable("id") Long id, Model model) {

        model.addAttribute("dishForm", dishRepository.findById(id));
        return "restorer/edit_dish";
    }

    @RequestMapping(value = {"/restorer/{id}/edit_dish"}, method = RequestMethod.POST )
    public String editDish(@PathVariable("id") Long id, Model model, @ModelAttribute("dishForm") DishForm dishForm) {
        Dish dish = dishRepository.getOne(id);

        dish.setName(dishForm.getName());
        dish.setPrice(dishForm.getPrice());
        dish.setDescription(dishForm.getDescription());
        dish.setImageUrl(dishForm.getImageUrl());
//        String name = dishForm.getName();
//        Double price = dishForm.getPrice();
//        String description = dishForm.getDescription();
//        String imageUrl = dishForm.getImageUrl();

        dishRepository.save(dish);

        return "redirect:/restorer/";
    }

    @RequestMapping(value = {"/restorer/{id}/delete_dish"}, method = RequestMethod.GET )
    public String deleteDish() {

        return "redirect:/restorer/";
    }
}
