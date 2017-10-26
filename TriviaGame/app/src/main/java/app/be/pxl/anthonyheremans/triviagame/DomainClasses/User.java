package app.be.pxl.anthonyheremans.triviagame.DomainClasses;

/**
 * Created by 11401671 on 17/10/2017.
 */

public class User {
    private int USERID;
    private String USERNAME;
    private String PASSWORD;
    private int COINS;

    public User(int USERID, String USERNAME, String PASSWORD, int COINS) {
        this.USERID = USERID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.COINS = COINS;
    }

    public int getUSERID() {
        return USERID;
    }

    public void setUSERID(int USERID) {
        this.USERID = USERID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public int getCOINS() {
        return COINS;
    }

    public void setCOINS(int COINS) {
        this.COINS = COINS;
    }
}

