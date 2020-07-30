package servlet;

import dao.StudentDAO;
import model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/queryById")
public class StudentQueryByIdServlet extends AbstractBaseServlet{

    //查询学生详情信息，用于学生修改信息的面板中的显示
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id=req.getParameter("id");
        Student s= StudentDAO.queryById(Integer.parseInt(id));
        return s;
    }
}
