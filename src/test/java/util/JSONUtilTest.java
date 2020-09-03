package util;

import model.Student;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JSONUtilTest {

    @Test
    public void read() {
        Map<String,String> map=new HashMap<>();
        map.put("1","A");
        map.put("2","B");
        map.put("3","C");
        map.put("4","D");
        System.out.println(JSONUtil.write(map));
    }

    @Test
    public void read1() {
        int i=1111;
        System.out.println(JSONUtil.write(i));
    }
    @Test
    public void write() {
        //把j2转换成一个流，可以传入到jsonutil类里面
        InputStream is=getClass().getClassLoader().getResourceAsStream("j2.json");
        HashMap hashMap=JSONUtil.read(is,HashMap.class);
        System.out.println(hashMap);
    }
    @Test
    public void write1() {
        //把j2转换成一个流，可以传入到jsonutil类里面
        InputStream is=getClass().getClassLoader().getResourceAsStream("j2.json");
        Student i=JSONUtil.read(is,Student.class);
        System.out.println(i);
    }

}