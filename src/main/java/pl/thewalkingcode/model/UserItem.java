package pl.thewalkingcode.model;

import javax.persistence.Entity;


@Entity
public class UserItem {

    private String uuid;
    private String username;
    private String code;
    private Long unit;
    private String price;
    private String amount;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UserItem{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", code='" + code + '\'' +
                ", unit=" + unit +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

}
