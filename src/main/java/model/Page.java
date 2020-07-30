package model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@ToString
public class Page {
    //searchText=&sortOrder=asc&pageSize=7&pageNumber=1
    private String searchText;//搜索内容
    private String sortOrder;//排序方式：升序asc/降序
    private Integer pageSize;//每页的数量
    private Integer pageNumber;//第几页

    public static Page parse(HttpServletRequest req){//不能使用json格式
        Page p=new Page();
        p.searchText=req.getParameter("searchText");//获取参数
        p.sortOrder=req.getParameter("sortOrder");
        p.pageSize=Integer.parseInt(req.getParameter("pageSize"));//Integer.parseInt()把括号里的内容转化成整数
        p.pageNumber=Integer.parseInt(req.getParameter("pageNumber"));
        return p;
    }
}
