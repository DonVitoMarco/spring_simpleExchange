package pl.thewalkingcode.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.thewalkingcode.model.User;
import pl.thewalkingcode.model.UserItem;
import pl.thewalkingcode.model.dto.UserAddItemDTO;
import pl.thewalkingcode.model.dto.UserBuyItemDTO;
import pl.thewalkingcode.model.dto.UserChargeWalletDTO;
import pl.thewalkingcode.service.API.UserItemService;
import pl.thewalkingcode.service.API.UserService;
import pl.thewalkingcode.utils.Utils;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@Controller
public class ProfileController {

    private final static Logger LOG = Logger.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserItemService userItemService;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        User user = userService.findUserByUsername(Utils.getUsername());
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("wallet", user.getWallet());

        List<UserItem> userItems = userItemService.findUserItemByUsername(Utils.getUsername());
        if (userItems != null) {
            model.addAttribute("userItems", userItems);
        }
        return "profile";
    }

    @RequestMapping(value = "/profile/chargewallet", method = RequestMethod.GET)
    public String chargeWallet(Model model) {
        model.addAttribute("wallet", new UserChargeWalletDTO());
        return "chargewallet";
    }

    @RequestMapping(value = "/profile/chargewallet", method = RequestMethod.POST)
    public String chargeWallet(@ModelAttribute("wallet") @Valid UserChargeWalletDTO chargeWalletDTO) {
        LOG.debug(chargeWalletDTO.toString());
        userService.chargeWallet(new BigDecimal(chargeWalletDTO.getAmount()), Utils.getUsername());
        return "redirect:/profile";
    }

    @RequestMapping(value = "/profile/additem", method = RequestMethod.GET)
    public String getAddActionForm(Model model) {
        UserAddItemDTO userAddItemDTO = new UserAddItemDTO();
        model.addAttribute("itemDTO", userAddItemDTO);
        return "additem";
    }

    @RequestMapping(value = "/profile/additem", method = RequestMethod.POST)
    public String getAddActionForm(@ModelAttribute("itemDTO") @Valid UserAddItemDTO userAddItemDTO) {
        LOG.debug(userAddItemDTO.toString());
        userItemService.createUserItem(userAddItemDTO, Utils.getUsername());
        return "redirect:/profile";
    }


//   /**********************************************/

    @RequestMapping(value = "/buy/{code}", method = RequestMethod.GET)
    public String buyCode(@PathVariable String code) {
        LOG.debug("buy " + code);
        return "redirect:/buy?" + code;
    }

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    public String buyForm(Model model) {
        UserBuyItemDTO userBuyItemDTO = new UserBuyItemDTO();
        model.addAttribute("buyItem", userBuyItemDTO);
        return "buy";
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buyForm(@ModelAttribute("item") @Valid UserBuyItemDTO userBuyItemDTO) {
        LOG.debug(userBuyItemDTO.toString());
        UserItem userItem = userItemService.buyItem(userBuyItemDTO, Utils.getUsername());
        if (userItem == null) {
            LOG.error("error buy");
            return "redirect:/exchange?errorbuy";
        }
        LOG.debug(userItem);
        return "redirect:/exchange?successbuy";
    }

    @RequestMapping(value = "/sell/{uuid}", method = RequestMethod.GET)
    public String sellForm(Model model, @PathVariable String uuid) {
        UserItem userItem = userItemService.findUserItemByUuid(uuid);
        LOG.debug("sell: " + uuid);
        LOG.debug(userItem);
        model.addAttribute("item", userItem);
        return "sell";
    }

    @RequestMapping(value = "/sell/{uuid}", method = RequestMethod.POST)
    public String sellForm(@PathVariable String uuid, @ModelAttribute("item") @Valid UserItem userItem) {
        LOG.debug("sell: " + uuid);
        LOG.debug(userItem);
        UserItem userItemResponse = userItemService.sellItem(uuid, Utils.getUsername());
        if (userItemResponse == null) {
            LOG.error("error sell");
            return "redirect:/exchange?errorsell";
        }
        LOG.debug(userItemResponse);
        return "redirect:/exchange?successsell";
    }

}
