package pl.thewalkingcode.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.thewalkingcode.model.User;
import pl.thewalkingcode.model.UserItem;
import pl.thewalkingcode.service.API.UserItemService;
import pl.thewalkingcode.service.API.UserService;
import pl.thewalkingcode.utils.Utils;

import java.util.List;


@Controller
@RequestMapping(value = "/exchange")
public class ExchangeController {

    private UserItemService userItemService;
    private UserService userService;
    private final static Logger LOG = Logger.getLogger(ExchangeController.class);

    @Autowired
    public ExchangeController(UserItemService userItemService, UserService userService) {
        this.userItemService = userItemService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String exchangeMainPage(Model model) {
        User user = userService.findUserByUsername(Utils.getUsername());
        List<UserItem> userItems = userItemService.findUserItemByUsername(Utils.getUsername());
        if (userItems != null) {
            model.addAttribute("userItems", userItems);
        }
        model.addAttribute("wallet", user.getWallet().toString());
        return "exchange";
    }

}
