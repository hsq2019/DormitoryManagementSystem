package util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBUtilTest {
    @Test
    public void test(){
        Assert.assertNotNull(DBUtil.getConnection());
    }

}