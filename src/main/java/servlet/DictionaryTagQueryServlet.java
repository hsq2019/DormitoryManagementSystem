package servlet;

import dao.DictionaryTagDAO;
import model.DictionaryTag;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/dict/tag/query")
public class DictionaryTagQueryServlet extends AbstractBaseServlet {

    //查询毕业年份字典，查询专业字典
    //这个看的是传入的key，000001(毕业年份)和000002(专业)
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取用户表单中提交的数据
        String key=req.getParameter("dictionaryKey");
        List<DictionaryTag> tags= DictionaryTagDAO.query(key);
        return tags;
    }
}
