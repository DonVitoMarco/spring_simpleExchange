package pl.thewalkingcode.model.dto;


public class UserChargeWalletDTO {

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UserChargeWalletDTO{" +
                "amount='" + amount + '\'' +
                '}';
    }

}
