package fr.coveat.app.controller;

import fr.coveat.app.form.RestorerForm;
import fr.coveat.app.form.UserForm;
import fr.coveat.app.model.Address;
import fr.coveat.app.model.User;
import fr.coveat.app.model.Restaurant;
import fr.coveat.app.repository.AddressRepository;
import fr.coveat.app.repository.UserRepository;
import fr.coveat.app.repository.RestorerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private static Pattern patternStreet = Pattern.compile("^(\\d+) [a-zA-Z0-9\\s]+(.)? [a-zA-Z]+(.)?$");
    private static Matcher matcherStreet;
    private static Pattern patternZipCode = Pattern.compile("^((0[1-9])|([1-8][0-9])|(9[0-8])|(2A)|(2B))[0-9]{3}$");
    private static Matcher matcherZipCode;
    private static Pattern patternEmail = Pattern.compile("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$");
    private static Matcher matcherEmail;
    private static Pattern patternPassword = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$");
    private static Matcher matcherPassword;

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
        model.addAttribute("userForm", userForm);
        model.addAttribute("errorZipCode", errorZipCode);
        return "register";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String saveUser(Model model,
                @ModelAttribute("userForm") UserForm userForm) {

        String street = userForm.getStreet();
        String zipCode = userForm.getZipCode();
        String city = userForm.getCity();

        String lastname = userForm.getLastname();
        String firstname = userForm.getFirstname();
        String email = userForm.getEmail();
        String password = userForm.getPassword();
        String confkaka = userForm.getKaka();

        matcherStreet = patternStreet.matcher(street);
        matcherZipCode = patternZipCode.matcher(zipCode);
        matcherEmail = patternEmail.matcher(email);
        matcherPassword = patternPassword.matcher(password);

        if (!street.isEmpty() && matcherStreet.find()){
            if (!zipCode.isEmpty() && matcherZipCode.find()){
                if (!city.isEmpty() && city.length() > 1){
                    Address address = new Address(zipCode, street, city);
                    addressRepository.save(address);
                    if (!lastname.isEmpty() && lastname.length() > 1){
                        if (!firstname.isEmpty() && firstname.length() > 1){
                            if (!email.isEmpty() && matcherEmail.find()){
                                if (!password.isEmpty() && matcherPassword.find()){
                                    if (password.equals(confkaka)){
                                        String pwHash = BCrypt.hashpw(password, BCrypt.gensalt());
                                        User newUser = new User(lastname, firstname, email, pwHash, address);
                                        userRepository.save(newUser);
                                        System.out.print("compte crée");

                                    }
                                    else{
                                        System.out.print("password différent");
                                        System.out.print(password);
                                        System.out.print(confkaka);
                                    }
                                }
                                else{
                                    System.out.print("password non valide");
                                    System.out.print("password" + password);
                                    System.out.print("confpassword" + confkaka);
                                }
                            } else{
                                System.out.print("email non valide");
                            }
                        } else{
                            System.out.print("firstname non valide");
                        }
                    } else{
                        System.out.print("lastname non valide");
                    }

                } else{
                    System.out.print("city non valide");
                }
            } else{
                System.out.print("zipcode non valide");
            }
        }
        else {
            System.out.print("street non valide");
            model.addAttribute("errorStreet", errorStreet);
        }
            return "redirect:/register";
        }


    	
///////////////////////////////////////////////////////////////////////////////   
///////////////////////////////////////////////////////////////////////////////    
/////////////////////////////RESTORER REGISTER///////////////////////////////////    
///////////////////////////////////////////////////////////////////////////////    
///////////////////////////////////////////////////////////////////////////////   

    
    private RestorerRepository restorerRepository;

    	AuthController(RestorerRepository restorerRepository, AddressRepository addressRepository) {
        this.restorerRepository = restorerRepository;
        this.addressRepository = addressRepository;
    }
      
//    @RequestMapping(value = {"login"}, method = RequestMethod.GET )
//    public String login() {
//
//        //model.addAttribute("message",message);
//        return "login";
//    }
    

    @RequestMapping(value = {"register_restorer"}, method = RequestMethod.GET )
    public String showRegister_Restorer(Model model) {
        RestorerForm restorerForm = new RestorerForm();
		model.addAttribute("restorerForm", restorerForm);
        model.addAttribute("errorZipCode", errorZipCode);
        return "register";
    }

    @RequestMapping(value = { "/register_restorer" }, method = RequestMethod.POST)
    public String saveUser(Model model,
                @ModelAttribute("restorerForm") RestorerForm restorerForm) {

        String street = restorerForm.getStreet();
        String zipCode = restorerForm.getZipCode();
        String city = restorerForm.getCity();

        String name = restorerForm.getName();
        String email = restorerForm.getEmail();
        String image_url = restorerForm.getImage_url();
        String password = restorerForm.getPassword();
        String conf_password = restorerForm.getConf_password();

        matcherStreet = patternStreet.matcher(street);
        matcherZipCode = patternZipCode.matcher(zipCode);
        matcherEmail = patternEmail.matcher(email);
        matcherPassword = patternPassword.matcher(password);

        if (!street.isEmpty() && matcherStreet.find()){
            if (!zipCode.isEmpty() && matcherZipCode.find()){
                if (!city.isEmpty() && city.length() > 1){
                    Address address = new Address(zipCode, street, city);
                    addressRepository.save(address);
                    if (!name.isEmpty() && name.length() > 1){
                            if (!email.isEmpty() && matcherEmail.find()){
                                if (!password.isEmpty() && matcherPassword.find()){
                                    if (password.equals(conf_password)){
                                        String pwHash = BCrypt.hashpw(password, BCrypt.gensalt());
                                        Restaurant newRestorer = new Restaurant(name,image_url, email, address, pwHash);
                                        restorerRepository.save(newRestorer);
                                        System.out.print("compte crée");

                                    }
                                    else{
                                        System.out.print("password différent");
                                        System.out.print(password);
                                        System.out.print(conf_password);
                                    }
                                }
                                else{
                                    System.out.print("password non valide");
                                    System.out.print("password" + password);
                                    System.out.print("confpassword" + confkaka);
                                }
                            } else{
                                System.out.print("email non valide");
                            }
                        } else{
                            System.out.print("firstname non valide");
                        }
                    } else{
                        System.out.print("lastname non valide");
                    }

                } else{
                    System.out.print("city non valide");
                }
            } else{
                System.out.print("zipcode non valide");
            }
        }
        else {
            System.out.print("street non valide");
            model.addAttribute("errorStreet", errorStreet);
        }
            return "redirect:/register";
        }

    

}
