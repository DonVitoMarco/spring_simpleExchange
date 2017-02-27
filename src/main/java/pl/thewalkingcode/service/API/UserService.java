package pl.thewalkingcode.service.API;

import pl.thewalkingcode.model.User;
import pl.thewalkingcode.model.dto.UserRegistrationDTO;

import java.math.BigDecimal;


public interface UserService {

    void createUser(UserRegistrationDTO userRegistrationDTO);

    void updateUser(User user);

    User findUserByUsername(String username);

    void chargeWallet(BigDecimal amount, String username);

    int checkUsername(String username);

}
