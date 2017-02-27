package pl.thewalkingcode.service.API;

import pl.thewalkingcode.model.UserItem;
import pl.thewalkingcode.model.dto.UserAddItemDTO;
import pl.thewalkingcode.model.dto.UserBuyItemDTO;

import java.util.List;


public interface UserItemService {

    void createUserItem(UserAddItemDTO userAddItemDTO, String username);

    UserItem findUserItemByUuid(String uuid);

    List<UserItem> findUserItemByUsername(String username);

    UserItem buyItem(UserBuyItemDTO userBuyItemDTO, String username);

    UserItem sellItem(String uuid, String username);

}
