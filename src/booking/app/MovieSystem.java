package booking.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import booking.bean.Business;
import booking.bean.Customer;
import booking.bean.Movie;
import booking.bean.User;

public class MovieSystem {

    /*
     * 定义系统的数据容器存储数据
     * 1.存储多用户（客人对象，片商对象）
     */
    // 用户final修饰把其变为常量(constant),地址不变，不能把这个容器变为其他容器（比如null），但是可以添加数据，
    public static final List<User> ALL_USERS = new ArrayList<>();

    /*
     * 2.存储商家信息和其排片信息
     */
    public static final Map<Business, List<Movie>> ALL_MOVIES = new HashMap<>();

    /*
     * Scanner
     */
    public static final Scanner SYS_SC = new Scanner(System.in);

    /*
     * 定义一个静态的User类型的变量记住当前登录成功的用户
     */
    public static User loginUser;
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
        ALL_MOVIES.put(b, movies); // b = []

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
        ALL_MOVIES.put(b2, movies3); // b2 = []
    }

    // -------------------------------------------------------------------------------
    public static void main(String[] args) {
        showMain();
    }

    // -------------------------------------------------------------------------------
    /*
     * mian page
     */
    private static void showMain() {
        while (true) {
            System.out.println("==============Home Page==============");
            System.out.println("1.Login");
            System.out.println("2.User Register");
            System.out.println("3.Merchant Register");
            System.out.println("Please enter number for command");
            String command = SYS_SC.nextLine();
            switch (command) {
                case "1":
                    // login
                    login();
                    break;
                case "2":
                    // User Register
                    break;
                case "3":
                    // Merchant Register
                    break;
                default:
                    System.out.println("Wrong command number!");
            }
        }
    }

    /*
     * login function
     */
    private static void login() {
        while (true) {
            System.out.println("Please enter your login name");
            String loginName = SYS_SC.nextLine();
            System.out.println("Please enter your Password");
            String passWord = SYS_SC.nextLine();

            // 1.根据登录名称查询用户对象（做成方法）
            User u = getUserByLoginName(loginName);
            // 2.判断用户名是否存在，存在说明登录名称正确
            if (u != null) {
                // 3.比对密码是否正确
                if (u.getPassWord().equals(passWord)) {
                    // 登录成功
                    loginUser = u;//记住登录成功的用户
                    // 判断是用户还是商家在登录
                    if (u instanceof Customer) {
                        // 普通用户登录
                        showCustomerMAin();
                    } else {
                        // 商家用户登录
                        showBusinessMain();
                    }
                } else {
                    System.out.println("Password is werong!");
                }
            } else {
                System.out.println("Login name is wrong!!");
            }
        }
    }
/*商家操作界面 */
    private static void showBusinessMain() {
        while (true) {
            System.out.println("============Merchant===================");
            System.out.println(loginUser.getUserName() + (loginUser.getGender()=='M'? "Mr.":"Mrs" + " Welcome!"));
            System.out.println("1、Show details:");
            System.out.println("2、On line:");
            System.out.println("3、Off line:");
            System.out.println("4、Edit movie:");
            System.out.println("5、Log-out:");
 
            System.out.println("Please enter number for command: ");
            String command = SYS_SC.nextLine();
            switch (command){
                case "1":
                    // 展示全部排片信息
                    //showBusinessInfos();
                    break;
                case "2":
                    // 上架电影信息
                    //addMovie();
                    break;
                case "3":
                    // 下架电影信息
                   // deleteMovie();
                    break;
                case "4":
                    // 修改电影信息
                   // updateMovie();
                    break;
                case "5":
                    System.out.println(loginUser.getUserName() +"Bye-Bye!!");
                    return; // 干掉方法
                default:
                    System.out.println("Erro command!");
                    break;
            }
        }
    }
/*用户操作界面 */
    private static void showCustomerMAin() {
        while (true) {
            System.out.println("============Customer===================");
            System.out.println(loginUser.getUserName() + (loginUser.getGender()=='M'? "Mr.":"Mrs" + " Welcome!" +
                    "\tBalance: $" + loginUser.getMoney()));
            System.out.println("Please select the function you want to operate:");
            System.out.println("1、Display all video information:");
            System.out.println("2、Query movie information by movie name:");
            System.out.println("3、Rate:");
            System.out.println("4、Buy tickets:");
            System.out.println("5、Logout:");
            System.out.println("Please enter number for command:");
            String command = SYS_SC.nextLine();
            switch (command){
                case "1":
                    // 展示全部排片信息
                    //showAllMovies();
                    break;
                case "2":
                    break;
                case "3":
                    // 评分功能
                   // scoreMovie();
                   // showAllMovies();
                    break;
                case "4":
                    // 购票功能
                   // buyMovie();
                    break;
                case "5":
                    return; // 干掉方法
                default:
                    System.out.println("Erro command!");
                    break;
            }
        }
    }

    public static User getUserByLoginName(String loginName) {
        for (User user : ALL_USERS) {
            // 判断用户名称是不是我们想要的
            if (user.getLoginName().equals(loginName)) {
                return user;
            }
        }
        return null;// 查无此用户
    }
}
