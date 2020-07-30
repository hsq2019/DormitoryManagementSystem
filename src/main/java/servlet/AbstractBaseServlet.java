package servlet;

import model.Response;
import util.JSONUtil;
import util.ThreadLocalHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractBaseServlet extends HttpServlet {
    //抽象类


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");//设置请求数据的编码格式
        resp.setCharacterEncoding("UTF-8");//设置相应数据编码格式
        resp.setContentType("application/json");//设置响应数据格式
        Response response=new Response();

        try {
            Object o=process(req,resp);
            response.setSuccess(true);
            response.setCode("200");
            response.setMessage("操作成功");
            //从左往右看
            //先获取到ThreadLocal 这个变量(第一个get方法),之后是调用ThreadLocal中的一个方法，得到之前保存的变量
            response.setTotal(ThreadLocalHolder.get().get());//获取当前线程的count变量
            response.setData(o);
        } catch (Exception e) {
            response.setCode("500");
            response.setMessage(e.getMessage());
            StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace=sw.toString();
            System.err.println(stackTrace);
            response.setStackTrance(stackTrace);
        } finally {
            //切记使用完ThreadLocal在线程结束之前一定要删掉变量
            //防止内存泄漏
            ThreadLocalHolder.get().remove();//把里面变量的值删掉
        }
        //响应数据，是json数据
        PrintWriter pw=resp.getWriter();
        pw.println(JSONUtil.write(response));
        pw.flush();
    }
    public abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
