package servlet;

import dao.UserDAO;
import model.User;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//用户登录服务
@WebServlet("/user/login")
public class UserLoginServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取http的请求信息，转化成用户类
        User user= JSONUtil.read(req.getInputStream(),User.class);
        User queryUser= UserDAO.query(user);
        if(queryUser==null){
            throw new RuntimeException("用户名或者密码错误");
        }
        //获取会话，如果没有获取到会话，那就建立个会话（括号中没有参数）
        HttpSession session=req.getSession();
        session.setAttribute("user",queryUser);//session设置参数
        return null;
    }


}
