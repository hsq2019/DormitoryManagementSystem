package dao;

import model.DictionaryTag;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BuildingDAO {
    //查询寝室楼字典

    public static List<DictionaryTag> query() {
        List<DictionaryTag> list=new ArrayList<>();
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            c= DBUtil.getConnection();
            String sql="select b.id, b.building_name" +
                    "   from building b";
            ps=c.prepareStatement(sql);//预编译
            rs=ps.executeQuery();//执行sql
            while (rs.next()){
                DictionaryTag dictionaryTag=new DictionaryTag();
                //键值对的方式
                //key value
                dictionaryTag.setDictionaryTagKey(rs.getString("id"));
                dictionaryTag.setDictionaryTagValue(rs.getString("building_name"));
                list.add(dictionaryTag);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询宿舍楼字典出错",e);
        } finally {
            DBUtil.close(c,ps,rs);
        }

        return list;

    }
}
