package servlet;

import dao.DormDAO;
import model.DictionaryTag;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/dorm/queryAsDict")
public class DormQueryServlet extends AbstractBaseServlet{
    //宿舍管理
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String key=req.getParameter("dictionaryKey");
        List<DictionaryTag> list= DormDAO.query(Integer.parseInt(key));
        return list;
    }


}
