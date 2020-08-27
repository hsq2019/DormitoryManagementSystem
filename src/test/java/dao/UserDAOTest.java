package dao;
import model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOTest {
    @Test
    public void query(){
        //测试从数据库查找用户信息
        UserDAO userDAO=new UserDAO();
        User u=new User();
        u.setUsername("abc");
        u.setPassword("123");
        User queryUser=userDAO.query(u);
        System.out.println(queryUser);
    }


    @Test
    public void query2(){
        //测试从数据库查找用户信息
        //输入正确的用户名，错误的密码，查出来的用户为null
        UserDAO userDAO=new UserDAO();
        User u=new User();
        u.setUsername("abc");
        u.setPassword("1234");
        User queryUser=userDAO.query(u);
        System.out.println(queryUser);
    }



}