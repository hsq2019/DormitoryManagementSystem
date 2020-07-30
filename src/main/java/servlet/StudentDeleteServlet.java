package servlet;

import dao.StudentDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends AbstractBaseServlet {
    //删除学生住宿信息
    @Override
    //可以多个id请求删除
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //相同的key有多个，可以获取到value数组
        String[] ids=req.getParameterValues("ids");//servlet的api,获取多个id
        int num= StudentDAO.delete(ids);
        return null;
    }
}
