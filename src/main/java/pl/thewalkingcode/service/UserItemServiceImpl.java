package pl.thewalkingcode.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thewalkingcode.dao.UserItemDao;
import pl.thewalkingcode.model.Company;
import pl.thewalkingcode.model.User;
import pl.thewalkingcode.model.UserItem;
import pl.thewalkingcode.model.dto.UserAddItemDTO;
import pl.thewalkingcode.model.dto.UserBuyItemDTO;
import pl.thewalkingcode.service.API.CompanyService;
import pl.thewalkingcode.service.API.UserItemService;
import pl.thewalkingcode.service.API.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class UserItemServiceImpl implements UserItemService {

    private final static Logger LOG = Logger.getLogger(UserItemServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserItemDao userItemDao;

    @Autowired
    private CompanyService companyService;

    public void createUserItem(UserAddItemDTO addUserItemDTO, String username) {
        UserItem userItem = new UserItem();
        userItem.setUuid(UUID.randomUUID().toString());
        userItem.setUsername(username);
        userItem.setCode(addUserItemDTO.getCode());
        userItem.setPrice(addUserItemDTO.getPrice());
        userItem.setUnit(Long.parseLong(addUserItemDTO.getUnit()));
        userItem.setAmount(new BigDecimal(addUserItemDTO.getPrice()).multiply(new BigDecimal(addUserItemDTO.getUnit())).toString());
        userItemDao.create(userItem);
    }

    @Override
    public UserItem findUserItemByUuid(String uuid) {
        return userItemDao.read(uuid);
    }

    @Override
    public List<UserItem> findUserItemByUsername(String username) {
        return userItemDao.readAllItemsByUsername(username);
    }

//    public List<UserItem> getAllActionByUsername(String username) {
//        return userItemDao.findAllActionByUsername(username);
//    }
//
//    public UserItem findItemByUuid(String username, String uuid) {
//        List<UserItem> userItems = userItemDao.findAllActionByUsername(username);
//        UserItem userItem = null;
//        for (UserItem u : userItems) {
//            if (u.getUuid().equals(uuid)) {
//                userItem = u;
//            }
//        }
//        return userItem;
//    }

    public UserItem buyItem(UserBuyItemDTO userBuyItemDTO, String username) {
        UserItem userItem = new UserItem();
        userItem.setUuid(UUID.randomUUID().toString());
        userItem.setUsername(username);
        userItem.setUnit(Long.parseLong(userBuyItemDTO.getUnit()));
        userItem.setCode(userBuyItemDTO.getCode());
        userItem.setPrice(userBuyItemDTO.getPrice());
        BigDecimal newAmount = new BigDecimal(userItem.getPrice()).multiply(new BigDecimal(userItem.getUnit()));
        userItem.setAmount(newAmount.toString());

        Company company = companyService.findCompany(userBuyItemDTO.getCode());
        if (company == null) {
            LOG.error("Company is null");
            return null;
        }
        if (company.getSumUnits() < Long.parseLong(userBuyItemDTO.getUnit())) {
            LOG.error("Empty unit");
            return null;
        }

        User user = userService.findUserByUsername(username);
        if (user == null) {
            LOG.error("User is null");
            return null;
        }
        if (newAmount.compareTo(user.getWallet()) != -1) {
            LOG.error("Empty wallet");
            return null;
        }

        //Update user
        BigDecimal newCash = user.getWallet().subtract(newAmount);
        user.setWallet(newCash);
        userService.updateUser(user);

        //Update company
        Long newUnits = company.getSumUnits() - Long.parseLong(userBuyItemDTO.getUnit());
        companyService.updateUnit(newUnits, company.getCode());

        //Create item
        userItemDao.create(userItem);
        return userItemDao.read(userItem.getUuid());
    }

    public UserItem sellItem(String uuid, String username) {
        UserItem userItem = userItemDao.read(uuid);
        if (userItem == null) {
            return null;
        }

        User user = userService.findUserByUsername(username);
        if (user == null) {
            LOG.error("User is null");
            return null;
        }

        Company company = companyService.findCompany(userItem.getCode());
        if (company == null) {
            LOG.error("Company is null");
            return null;
        }

        //Update user
        BigDecimal newCash = user.getWallet().add(new BigDecimal(userItem.getAmount()));
        user.setWallet(newCash);
        userService.updateUser(user);

        //Update company
        Long newAmount = company.getSumUnits() + userItem.getUnit();
        companyService.updateUnit(newAmount, company.getCode());

        //Update item
        userItemDao.delete(userItem.getUuid());
        userItem.setUsername(null);
        return userItem;
    }

}
