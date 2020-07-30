package dao;

import model.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    //查询
    public static User query(User user) {
        User u=null;
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            c=DBUtil.getConnection();
            String sql="select id,nickname,email from user where username=? and password=?";
            ps=c.prepareStatement(sql);//预执行sql
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            rs=ps.executeQuery();
            while (rs.next()){
                u=user;
                u.setNickname(rs.getString("nickname"));
                u.setEmail(rs.getString("email"));
                u.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            throw new RuntimeException("登录校验用户名密码出错",e);
        } finally {
            DBUtil.close(c,ps,rs);
        }
        return u;
    }
}
