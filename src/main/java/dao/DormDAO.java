package dao;

import model.DictionaryTag;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DormDAO {

//查询寝室数据字典，是个键值对
    public static List<DictionaryTag> query(int id) {
        List<DictionaryTag> list=new ArrayList<>();
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            c= DBUtil.getConnection();

            String sql="select d.id, d.dorm_no" +
                    "   from dorm d" +
                    "         join building b on d.building_id = b.id" +
                    "   where b.id = ?;";
            ps=c.prepareStatement(sql);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                //字典，键值对
                DictionaryTag dictionaryTag=new DictionaryTag();
                dictionaryTag.setDictionaryTagKey(rs.getString("id"));
                dictionaryTag.setDictionaryTagValue(rs.getString("dorm_no"));
                list.add(dictionaryTag);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询寝室数据字典出错",e);
        } finally {
            DBUtil.close(c,ps,rs);
        }
        return list;
    }
}
