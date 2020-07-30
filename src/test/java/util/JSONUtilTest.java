package util;

import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
    public void write() {
        //把j2转换成一个流，可以传入到jsonutil类里面
        InputStream is=getClass().getClassLoader().getResourceAsStream("j2.json");
        HashMap hashMap=JSONUtil.read(is,HashMap.class);
        System.out.println(hashMap);
    }
}