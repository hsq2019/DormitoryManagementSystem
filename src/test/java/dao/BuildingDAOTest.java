package dao;

import model.DictionaryTag;
import org.junit.Test;

import java.util.List;

public class BuildingDAOTest {

    //查询寝室楼字典功能
    @Test
    public void query() {
        BuildingDAO bd=new BuildingDAO();
        List<DictionaryTag> list=bd.query();
        System.out.println(list);
    }
}