package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created  on  2018/7/23 0023
 * TencentIsuxCollectTest
 *
 * @author enilu
 */
public class TencentIsuxCollectTest extends BaseApplicationStartTest {

        @Autowired
        private TencentIsuxCollect tencentIsuxCollect;
    @Test
    public void collect() {
    }

    @Test
    public void collectAll() {
        tencentIsuxCollect.collectAll();
    }
}