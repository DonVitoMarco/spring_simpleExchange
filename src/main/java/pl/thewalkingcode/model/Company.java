package pl.thewalkingcode.model;

import java.io.Serializable;

public class Company implements Serializable {

    private String code;
    private Long sumUnits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSumUnits() {
        return sumUnits;
    }

    public void setSumUnits(Long sumUnits) {
        this.sumUnits = sumUnits;
    }

    @Override
    public String
    toString() {
        return "Company{" +
                "code='" + code + '\'' +
                ", sumUnits=" + sumUnits +
                '}';
    }

}