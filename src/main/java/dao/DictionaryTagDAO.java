package dao;

import model.DictionaryTag;
import util.DBUtil;
/*
* JQuery javascript Ajax
* Ajax ajax = new Ajax();
* ajax.post(......)
*
* */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DictionaryTagDAO {
    //查询数据字典

    public static List<DictionaryTag> query(String key) {
        //使用jdbc
        List<DictionaryTag> list=new ArrayList<>();
        Connection c=null;//数据量连接
        PreparedStatement ps=null;//执行sql，可以执行带参数的sql
        ResultSet rs=null;//如果是查询，会有结果

        try {
            c= DBUtil.getConnection();
            String sql="select concat(d.dictionary_key,dt.dictionary_tag_key) dictionary_tag_key," +
                    "       dt.dictionary_tag_value" +
                    "   from dictionary d" +
                    "         join dictionary_tag dt on d.id = dt.dictionary_id" +
                    "   where d.dictionary_key=?";
            ps=c.prepareStatement(sql);
            ps.setString(1,key);
            rs=ps.executeQuery();
            while (rs.next()){
                DictionaryTag tag=new DictionaryTag();
                //键值对的方式
                //key value
                tag.setDictionaryTagValue(rs.getString("dictionary_tag_value"));
                tag.setDictionaryTagKey(rs.getString("dictionary_tag_key"));
                list.add(tag);
            }

        } catch (Exception e) {
            throw new RuntimeException("查询数据字典标签出错",e);
        }finally {
            DBUtil.close(c,ps,rs);
        }

        return list;
    }
}
