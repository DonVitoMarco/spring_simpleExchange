package pl.thewalkingcode.model.dto;

public class UserAddItemDTO {

    private String code;
    private String unit;
    private String price;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "UserAddItemDTO{" +
                "code='" + code + '\'' +
                ", unit='" + unit + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

}
