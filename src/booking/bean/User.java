package booking.bean;
/*
 * 用户类（客人和片商父类）
 */
public class User {
    private String loginName;//登录名 不能重复
    private String userName;
    private String passWord;
    private char gender;
    private String phone;
    private double money;
    public User() {
    }
    public User(String loginName, String userName, String passWord, char gender, String phone, double money) {
        this.loginName = loginName;
        this.userName = userName;
        this.passWord = passWord;
        this.gender = gender;
        this.phone = phone;
        this.money = money;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }


    
}
