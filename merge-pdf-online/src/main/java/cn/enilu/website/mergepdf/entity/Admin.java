package cn.enilu.website.mergepdf.entity;

import java.io.Serializable;


/**
 * Created  on  2018/7/16 0016
 * Admin
 *
 * @author enilu
 */
public class Admin     implements Serializable {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
