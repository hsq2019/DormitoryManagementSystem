package servlet;

import dao.BuildingDAO;
import model.DictionaryTag;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/building/queryAsDict")
public class BuildingQueryServlet extends AbstractBaseServlet{
    //寝室楼管理
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<DictionaryTag> list= BuildingDAO.query();
        return list;
    }


}
