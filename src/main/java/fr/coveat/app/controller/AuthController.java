package fr.coveat.app.controller;

import fr.coveat.app.form.UserForm;
import fr.coveat.app.model.Address;
import fr.coveat.app.model.User;
import fr.coveat.app.repository.AddressRepository;
import fr.coveat.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private static Pattern patternStreet;
    private static Matcher matcherStreet;
    private static Pattern patternZipCode;
    private static Matcher matcherZipCode;
    private Object String;

    AuthController(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }
    @RequestMapping(value = {"login"}, method = RequestMethod.GET )
    public String login() {

        //model.addAttribute("message",message);
        return "login";
    }
    @Value("${error.zipcode}")
    private String errorZipCode;

    @Value("${error.street}")
    private String errorStreet;

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

        patternStreet = Pattern.compile("(\\d{1,}) [a-zA-Z0-9\\s]+(.)? [a-zA-Z]");
        patternZipCode = Pattern.compile("/((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}/");

        String street = userForm.getStreet();
        String zipCode = userForm.getZipCode();
        String city = userForm.getCity();

        String lastname = userForm.getLastname();
        String firstname = userForm.getFirstname();
        String email = userForm.getEmail();
        String password = userForm.getPassword();

        matcherStreet = patternStreet.matcher(street);
        matcherZipCode = patternZipCode.matcher(zipCode);

        if (!street.isEmpty() && matcherStreet.find()){
            if (!zipCode.isEmpty() && matcherZipCode.find()){
                if (!city.isEmpty() && city.length() > 2){
                    Address address = new Address(zipCode, street, city);
                    addressRepository.save(address);

                    User newUser = new User(lastname, firstname, email, password, address);
                    userRepository.save(newUser);
                }
            }
        }
        else {
            model.addAttribute("errorStreet", errorStreet);
        }




        //if (lastname != null && lastname.length() > 0
                //&& firstname != null && firstname.length() > 0) {


            return "redirect:/register";
        }

}
