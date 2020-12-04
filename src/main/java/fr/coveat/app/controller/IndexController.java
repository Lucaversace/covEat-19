package fr.coveat.app.controller;

import fr.coveat.app.form.UserForm;
import fr.coveat.app.model.Address;
import fr.coveat.app.model.User;
import fr.coveat.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {

    private UserRepository userRepository;

    IndexController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"login"}, method = RequestMethod.GET )
    public String login() {

        //model.addAttribute("message",message);
        return "login";
    }
    @Value("${error.zipcode}")
    private String errorZipCode;

    @RequestMapping(value = {"register"}, method = RequestMethod.GET )
    public String showRegister(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("errorZipCode", errorZipCode);
        return "register";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String saveUser(Model model,
                @ModelAttribute("userForm") UserForm userForm) {

        String street = userForm.getStreet();
        Integer zipCode = userForm.getZipCode();
        String city = userForm.getCity();
        /*if (street != null && street.length() > 0
                && )*/
        Address address = new Address(zipCode, street, city);

        String lastname = userForm.getLastname();
        String firstname = userForm.getFirstname();
        String email = userForm.getEmail();
        String password = userForm.getPassword();

        //if (lastname != null && lastname.length() > 0
                //&& firstname != null && firstname.length() > 0) {
    User newUser = new User(lastname, firstname, email, password, address);

        userRepository.save(newUser);

            return "redirect:/register";
        }

}
