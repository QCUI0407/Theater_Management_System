package booking.app;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static final Logger LOGGER = (Logger) LoggerFactory.getLogger("MovieSystem.class");
    /*
     * 3.准备一些测试数据
     */
    static {
        Customer c = new Customer();
        c.setLoginName("c1");
        c.setPassWord("123456");
        c.setUserName("C-1");
        c.setGender('M');
        c.setMoney(10000);
        c.setPhone("110110");
        ALL_USERS.add(c);

        Customer c1 = new Customer();
        c1.setLoginName("c2");
        c1.setPassWord("123456");
        c1.setUserName("C-2");
        c1.setGender('F');
        c1.setMoney(2000);
        c1.setPhone("111111");
        ALL_USERS.add(c1);

        Business b = new Business();
        b.setLoginName("b1");
        b.setPassWord("123456");
        b.setUserName("B-3");
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
        b2.setLoginName("b2");
        b2.setPassWord("123456");
        b2.setUserName("B-2");
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
                    loginUser = u;// 记住登录成功的用户
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

    /* 商家操作界面 */
    private static void showBusinessMain() {
        while (true) {
            System.out.println("============Merchant===================");
            System.out
                    .println(loginUser.getUserName() + (loginUser.getGender() == 'M' ? " Mr." : " Mrs" + " Welcome! "));
            System.out.println("1.Show details:");
            System.out.println("2.On line:");
            System.out.println("3.Off line:");
            System.out.println("4.Edit movie:");
            System.out.println("5.Log-out:");

            System.out.println("Please enter number for command: ");
            String command = SYS_SC.nextLine();
            switch (command) {
                case "1":
                    // 展示全部排片信息
                    showBusinessInfos();
                    break;
                case "2":
                    // 上架电影信息
                    addMovie();
                    break;
                case "3":
                    // 下架电影信息
                    deleteMovie();
                    break;
                case "4":
                    // 修改电影信息
                    updateMovie();
                    break;
                case "5":
                    System.out.println(loginUser.getUserName() + " Bye-Bye!!");
                    return; // 干掉方法
                default:
                    System.out.println("Erro command!");
                    break;
            }
        }
    }

    /*
     * 影片修改功能
     */
    private static void updateMovie() {
        System.out.println("================Edit Movie====================");

        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        if (movies.size() == 0) {
            System.out.println("No movie can edit now");
            return;
        }
        // 2.让用户选择需要修改的电影
        while (true) {
            System.out.println("Enter the movie title need to Edit.");
            String movieName = SYS_SC.nextLine();

            // 3.查看电影对象是否存在
            Movie movie = getMovieByName(movieName);
            if (movie != null) {
                // 修改
                System.out.println("Please enter a new title: ");
                String name = SYS_SC.nextLine();
                System.out.println("Please enter the updata actor:");
                String actor = SYS_SC.nextLine();
                System.out.println("Please enter the updata duration:");
                String time = SYS_SC.nextLine();
                System.out.println("Please enter your updata fare:");
                String price = SYS_SC.nextLine();
                System.out.println("Please enter updata number of tickets:");
                String totalNumber = SYS_SC.nextLine(); // 200\n
                while (true) {
                    try {
                        System.out.println("Please enter the updata showtime:");
                        String stime = SYS_SC.nextLine();
                        movie.setName(name);
                        movie.setActor(actor);
                        movie.setPrice(Double.valueOf(price));
                        movie.setTime(time);
                        movie.setNumber(Integer.valueOf(totalNumber));
                        movie.setStartTime(sdf.parse(stime));
                        System.out.println("Movie has been edited.");
                        showBusinessInfos();
                        return; // 直接退出去
                    } catch (ParseException e) {
                        e.printStackTrace();
                        LOGGER.error("time erro!");
                    }
                }
            } else {
                System.out.println("Can not find this movie in the list.");
                System.out.println("Keep edit orther moives?[y/n]");
                String command = SYS_SC.nextLine();
                switch (command) {
                    case "y":
                        break;

                    default:
                        System.out.println("Bye from edit function.");
                        return;
                }
            }
        }
    }

    /**
     * 影片下架功能
     */
    private static void deleteMovie() {
        System.out.println("================Off-line Movie====================");

        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        if (movies.size() == 0) {
            System.out.println("No movie online now");
            return;
        }

        // 2.让用户选择需要下架的电影
        while (true) {
            System.out.println("Enter the movie title need to off-line.");
            String movieName = SYS_SC.nextLine();

            // 3.查看电影对象是否存在
            Movie movie = getMovieByName(movieName);
            if (movie != null) {
                // 下架
                movies.remove(movie);
                System.out.println(movie.getName() + " has been removed.");
                return;
            } else {
                System.out.println("Can not find this movie in the list.");
                System.out.println("Keep off-line orther moives?[y/n]");
                String command = SYS_SC.nextLine();
                switch (command) {
                    case "y":
                        break;

                    default:
                        System.out.println("Bye from off-line function.");
                        return;
                }
            }
        }
    }

    /**
     * 查询当前的排片
     * 
     * @param movieName
     * @return
     */
    public static Movie getMovieByName(String movieName) {
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        for (Movie movie : movies) {
            if (movie.getName().contains(movieName)) {
                return movie;
            }
        }
        return null;
    }

    /*
     * 商家进行影片上架
     */
    private static void addMovie() {
        System.out.println("================Online Movie====================");
        // 根据商家对象(就是登录的用户loginUser)，作为Map集合的键 提取对应的值就是其排片信息 ：Map<Business , List<Movie>>
        // ALL_MOVIES
        Business business = (Business) loginUser;
        List<Movie> movies = ALL_MOVIES.get(business);
        System.out.println("Please enter a title: ");
        String name = SYS_SC.nextLine();
        System.out.println("Please enter the actor:");
        String actor = SYS_SC.nextLine();
        System.out.println("Please enter the duration:");
        String time = SYS_SC.nextLine();
        System.out.println("Please enter your fare:");
        String price = SYS_SC.nextLine();
        System.out.println("Please enter the number of tickets:");
        String totalNumber = SYS_SC.nextLine(); // 200\n
        while (true) {
            try {
                System.out.println("Please enter the showtime:");
                String stime = SYS_SC.nextLine();
                // public Movie(String name, String actor, double time, double price, int
                // number, Date startTime) // 封装成电影对象 ，加入集合movices中去
                Movie movie = new Movie(name, actor, Double.valueOf(time), Double.valueOf(price),
                        Integer.valueOf(totalNumber), sdf.parse(stime));
                movies.add(movie);
                System.out.println("You have successfully listed:<" + movie.getName() + ">");
                return; // 直接退出去
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error("time erro!");
            }
        }
    }

    // 展示当前商家信息
    private static void showBusinessInfos() {
        System.out.println("================Merchant Detial=================");
        LOGGER.info(loginUser.getUserName() + "Merchant,checking thire account....");
        // 根据商家对象(loginUser)，作为Map集合的键 提取对应的值就是其排片信息： Map<Business, List<Movie>>
        // ALL_MOVIES
        /*
         * 向下转型是指将一个父类或接口的引用转换为子类的引用。在这段代码中，将一个loginUser引用转换为Business类型的引用。
         * 
         * 在Java中，向下转型前需要进行强制类型转换。这意味着需要在类型之前加上小括号，并在括号内填写要转换的类型。
         * 
         * 在这段代码中，loginUser是一个Object类型的引用，它被强制转换为Business类型的引用，并赋值给business变量。
         * 
         * 在向下转型之前，应确保loginUser的实际类型是Business或其子类，否则会导致运行时错误。
         * 
         * 例如，如果loginUser的实际类型是Person（假设Person是Business的父类），则会导致ClassCastException异常。
         */
        Business business = (Business) loginUser;
        System.out.println(
                business.getShopName() + "\t\tPhone: " + business.getPhone() + "\t\tAddress: " + business.getAddress());
        List<Movie> movies = ALL_MOVIES.get(business);
        if (movies.size() > 0) {
            System.out.println(
                    "Title\t\t\tactor\t\tDuration\t\tStarting\t\tFare\t\tNumber of Remaining Tickets\t\tShowtime\t\tScore");
            for (Movie movie : movies) {

                System.out.println(movie.getName() + "\t\t" + movie.getActor() + "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getPrice() + "\t\t" + movie.getNumber() + "\t\t"
                        + sdf.format(movie.getStartTime()));
            }
        } else {
            System.out.println("There are currently no films showing in your store~~~~");
        }

    }

    /* 用户操作界面 */
    private static void showCustomerMAin() {
        while (true) {
            System.out.println("============Customer===================");
            System.out.println(loginUser.getUserName() + (loginUser.getGender() == 'M' ? " Mr."
                    : " Mrs" + " Welcome! " +
                            "\tBalance: $" + loginUser.getMoney()));
            System.out.println("Please select the function you want to operate:");
            System.out.println("1.Display all video information:");
            System.out.println("2.Query movie information by movie name:");
            System.out.println("3.Rate:");
            System.out.println("4.Buy tickets:");
            System.out.println("5.Logout:");
            System.out.println("Please enter number for command:");
            String command = SYS_SC.nextLine();
            switch (command) {
                case "1":
                    // 展示全部排片信息
                    showAllMovies();
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
                    buyTicket();
                    break;
                case "5":
                    return; // 干掉方法
                default:
                    System.out.println("Erro command!");
                    break;
            }
        }
    }

    /*
     * buy ticket ALL_Movies = {b1=[p1,p2,p3,...], b2=[p3,p4,p1,....]}
     */
    private static void buyTicket() {
        showAllMovies();
        System.out.println("============Buy ticket===================");
        while (true) {
            System.out.println("Enter the store name");
            String shopName = SYS_SC.nextLine();
            // 1.查询是否存在该商家
            Business business = getUserByShopName(shopName);
            if (business == null) {
                System.out.println("Store connot find.");
            } else {
                // 2.此商家全部排片
                List<Movie> movies = ALL_MOVIES.get(business);
                // 3.判断是否存在上映的电影
                if (movies.size() > 0) {
                    // 4.购票
                    while (true) {
                        System.out.println("Enter the movie name.");
                        String movieName = SYS_SC.nextLine();

                        // 去当前商家下，查询电影对象
                        Movie movie = getMovieByShopAndName(business, movieName);
                        if (movie != null) {
                            while (true) {
                                // buy
                                System.out.println("number for tikets:  ");
                                String number = SYS_SC.nextLine();
                                int buyNumber = Integer.valueOf(number);

                                // check how many tickets left
                                if (movie.getNumber() >= buyNumber) {
                                    //start buying, check price
                                double money = BigDecimal.valueOf(movie.getPrice()).multiply(BigDecimal.valueOf(buyNumber)).doubleValue();
                                if (loginUser.getMoney() >= money) {
                                    //Done Done for buying ticket
                                    System.out.println(buyNumber + " tickets. Total is $"+money);

                                    //更新客户和商家金额，余票
                                    loginUser.setMoney(loginUser.getMoney() - money);
                                    business.setMoney(business.getMoney() + money);
                                    movie.setNumber(movie.getNumber() - buyNumber);
                                }else{
                                    //money not enough
                                    System.out.println("Do not have enough balance");
                                    System.out.println("Continue?[y/n]");
                                    String command = SYS_SC.nextLine();
                                    switch (command) {
                                        case "y":
                                            break;
                                        default:
                                            System.out.println("See you!");
                                            return;
                                    }

                                }
                                } else {
                                    // not enough
                                    System.out.println(movie.getNumber() + " tickets left.");
                                    System.out.println("Continue?[y/n]");
                                    String command = SYS_SC.nextLine();
                                    switch (command) {
                                        case "y":
                                            break;
                                        default:
                                            System.out.println("See you!");
                                            return;
                                    }
                                }
                            }
                        } else {
                            System.out.println("No movie found...");
                        }
                    }
                } else {
                    System.out.println("store colsed....");
                    System.out.println("Continue?[y/n]");
                    String command = SYS_SC.nextLine();
                    switch (command) {
                        case "y":
                            break;
                        default:
                            System.out.println("See you!");
                            return;
                    }
                }
            }
        }
    }

    public static Movie getMovieByShopAndName(Business business, String name) {
        List<Movie> movies = ALL_MOVIES.get(business);
        for (Movie movie : movies) {
            if (movie.getName().contains(name)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * 根据商家店铺名称查询商家对象
     * 
     * @param shopName
     * @return
     */
    public static Business getUserByShopName(String shopName) {
        Set<Business> businesses = ALL_MOVIES.keySet();
        for (Business business : businesses) {
            if (business.getShopName().equals(shopName)) {
                return business;
            }
        }
        return null;
    }

    /*
     * show all movies
     */
    private static void showAllMovies() {
        System.out.println("============All movies===================");
        ALL_MOVIES.forEach((business, movies) -> {
            System.out.println(
                    business.getShopName() + "\t\tPhone: " + business.getPhone() + "\t\tAddress: "
                            + business.getAddress());
            System.out.println(
                    "Title\t\t\tactor\t\tDuration\t\tStarting\t\tFare\t\tNumber of Remaining Tickets\t\tShowtime\t\tScore");
            for (Movie movie : movies) {

                System.out.println(movie.getName() + "\t\t" + movie.getActor() + "\t\t" + movie.getTime()
                        + "\t\t" + movie.getScore() + "\t\t" + movie.getPrice() + "\t\t" + movie.getNumber() + "\t\t"
                        + sdf.format(movie.getStartTime()));
            }
        });
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
