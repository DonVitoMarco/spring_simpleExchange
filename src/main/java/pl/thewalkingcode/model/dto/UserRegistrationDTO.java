package pl.thewalkingcode.model.dto;

import org.hibernate.validator.constraints.NotEmpty;
import pl.thewalkingcode.validation.ValidPassword;

import javax.validation.constraints.NotNull;


@ValidPassword
public class UserRegistrationDTO {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String machingPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMachingPassword() {
        return machingPassword;
    }

    public void setMachingPassword(String machingPassword) {
        this.machingPassword = machingPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", machingPassword='" + machingPassword + '\'' +
                '}';
    }

}
