package servlet;

import dao.StudentDAO;
import model.Page;
import model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/student/query")
public class StudentQueryServlet extends AbstractBaseServlet{
//查询学生住宿信息

    //输入流request 只能获取到相应体中的数据，依赖程序自己去解析（json来去解析）
    //request.getParameter可以获取到url中的数据，也可以获取到请求体中的数据。格式是key1=value1&key2=value2...格式，键值对
    //如果请求体是key1=value1&key2=value2...这个样子，也就能获取到了
    //所以这个不能使用json输入流转化
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //searchText=&sortOrder=asc&pageSize=7&pageNumber=1这个是url中的数据

        Page p=Page.parse(req);
        List<Student> students= StudentDAO.query(p);

        return students;
    }
}
