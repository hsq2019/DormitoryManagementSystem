package servlet;

import dao.StudentDAO;
import model.Student;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/apply")
public class StudentApplyServlet extends AbstractBaseServlet {
    //分配学生宿舍，多个id一起选中，之后分配
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Student s= JSONUtil.read(req.getInputStream(),Student.class);
        int num= StudentDAO.apply(s);
        return null;
    }
}
