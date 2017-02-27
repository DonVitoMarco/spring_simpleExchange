package pl.thewalkingcode.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.thewalkingcode.model.User;
import pl.thewalkingcode.model.dto.UserRegistrationDTO;
import pl.thewalkingcode.service.API.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;
    private final static Logger LOG = Logger.getLogger(RegistrationController.class);

    @Autowired
    private RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        model.addAttribute("user", userRegistrationDTO);
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("user") @Valid UserRegistrationDTO userRegistrationDTO, BindingResult result, Errors errors) {
        if (result.hasErrors()) {
            LOG.error("Passwords do not match");
            return "redirect:registration?error";
        }
        if(userService.checkUsername(userRegistrationDTO.getUsername()) != 0) {
            LOG.error("Username exist");
            return "redirect:registration?validUsername";
        }
        userService.createUser(userRegistrationDTO);
        return "redirect:/login?success";
    }

}
