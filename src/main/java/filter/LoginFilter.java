package filter;

import model.Response;
import util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//过滤器
//客户端和服务器之间的
//http的url匹配过滤器的路径规则，才会过滤(调用filter中的方法)
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化的方法
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //进行过滤的方法
        //servlet有很多请求，这个是http请求
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse resp=(HttpServletResponse)response;
        //需要处理的敏感资源
        //1首页：/public/page/main.html 如果没有登录的话，就重定向到登陆页面
        //重定向就是服务器收到请求，但是因为某些原因不让访问url，之后告诉客户端重新访问的路径客户端再访问
        //2后端服务资源，除登录接口user/login以外，其他接口没有登录，返回没有登录的json信息
        HttpSession session=req.getSession(false);//如果没有会话，那就直接返回null（括号中有参数）

        if(session==null){
            String uri=req.getServletPath();//获取当前http请求的路径
            if("/public/page/main.html".equals(uri)){//没有登录
                //重定向
                String schema=req.getScheme();//从请求获得架构(http)
                String host=req.getServerName();//服务器域名或ip
                int port=req.getServerPort();//获得服务器端口号
                String contextPath=req.getContextPath();//项目部署名
                String basePath=schema+"://"+host+":"+port+contextPath;
                resp.sendRedirect(basePath+"/public/index.html");//重定向
                return;
            }else if(!"/user/login".equals(uri)&&!uri.startsWith("/public")
                    &&!uri.startsWith("/static")){
                //不是这些就说明是敏感资源了，得返回一个没有登录的json信息
                //登录接口的uri是user/login，登录的servlet上面有
                req.setCharacterEncoding("UTF-8");//设置请求数据的编码格式
                resp.setCharacterEncoding("UTF-8");//设置响应数据的编码格式
                resp.setContentType("application/json");//设置响应数据格式
                Response r=new Response();
                r.setCode("301");//这个是响应体，不是响应状态码
                r.setMessage("未授权的http请求");
                PrintWriter pw=resp.getWriter();
                pw.println(JSONUtil.write(r));
                pw.flush();
                return;

            }
        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        //销毁的方法
    }
}
