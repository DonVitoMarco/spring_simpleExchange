package pl.thewalkingcode.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thewalkingcode.dao.UserDao;
import pl.thewalkingcode.model.User;
import pl.thewalkingcode.model.dto.UserRegistrationDTO;
import pl.thewalkingcode.service.API.UserService;

import java.math.BigDecimal;
import java.util.UUID;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private final static Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()));
        user.setEnabled(true);
        user.setWallet(BigDecimal.TEN);
        user.setUserRole(0);
        userDao.create(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.read(username);
    }

    @Override
    public void chargeWallet(BigDecimal amount, String username) {
        User user = userDao.read(username);
        user.setWallet(user.getWallet().add(amount));
        userDao.update(user);
    }

    @Override
    public int checkUsername(String username) {
        return userDao.checkUsername(username);
    }

}
