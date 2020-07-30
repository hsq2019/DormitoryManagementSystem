package servlet;

import dao.StudentDAO;
import model.Student;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//插入学生信息

@WebServlet("/student/add")
public class StudentAddServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //JSON请求数据输入流(读入read)进行读取,
        //read读取的是请求的输入流(req.getInputStream),读出来解析成Student类型
        Student s= JSONUtil.read(req.getInputStream(),Student.class);
        int num= StudentDAO.insert(s);
        return null;
    }
}
