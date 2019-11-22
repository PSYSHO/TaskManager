package Lab1.Entities;

import java.io.Serializable;

public class Contacts implements Serializable {
    private String name;
    private String phone;

    public Contacts(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String name) {
        this.phone = phone;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(" ")
                .append(name + "\t")
                .append(phone + "");
        return str.toString();
    }
}
