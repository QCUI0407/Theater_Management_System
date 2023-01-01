package booking.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import booking.bean.Business;
import booking.bean.Customer;
import booking.bean.Movie;
import booking.bean.User;

public class MovieSystem {

    /*
     * 定义系统的数据容器存储数据
     * 1.存储多用户（客人对象，片商对象）
     */
    //用户final修饰把其变为常量(constant),地址不变，不能把这个容器变为其他容器（比如null），但是可以添加数据，
    public static final List<User> ALL_USERS = new ArrayList<>();

    /*
     * 2.存储商家信息和其排片信息
     */
    public static final Map<Business, List<Movie>> ALL_MOVIES = new HashMap<>();

    /*
     * 3.准备一些测试数据
     */
    static {
        Customer c = new Customer();
        c.setLoginName("zyf888");
        c.setPassWord("123456");
        c.setUserName("P-1");
        c.setGender('M');
        c.setMoney(10000);
        c.setPhone("110110");
        ALL_USERS.add(c);
 
        Customer c1 = new Customer();
        c1.setLoginName("gzl888");
        c1.setPassWord("123456");
        c1.setUserName("P-2");
        c1.setGender('F');
        c1.setMoney(2000);
        c1.setPhone("111111");
        ALL_USERS.add(c1);
 
        Business b = new Business();
        b.setLoginName("baozugong888");
        b.setPassWord("123456");
        b.setUserName("P-3");
        b.setMoney(0);
        b.setGender('M');
        b.setPhone("110110");
        b.setAddress("BLUD-1");
        b.setShopName("ROOM-@@@");
        ALL_USERS.add(b);
        // 注意，商家一定需要加入到店铺排片信息中去
        List<Movie> movies = new ArrayList<>();
        ALL_MOVIES.put(b , movies); // b = []
 
        Business b2 = new Business();
        b2.setLoginName("baozupo888");
        b2.setPassWord("123456");
        b2.setUserName("P-4");
        b2.setMoney(0);
        b2.setGender('F');
        b2.setPhone("110110");
        b2.setAddress("BLUD-2");
        b2.setShopName("ROOM-$$$");
        ALL_USERS.add(b2);
        // 注意，商家一定需要加入到店铺排片信息中去
        List<Movie> movies3 = new ArrayList<>();
        ALL_MOVIES.put(b2 , movies3); // b2 = []
    }
    
    public static void main(String[] args) {
        
    }
}
