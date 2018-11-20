package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created  on  2018/7/22 0022
 * BaiduUedCollectTest
 *
 * @author enilu
 */
public class BaiduUedCollectTest extends BaseApplicationStartTest {
    @Autowired
    private BaiduUedCollect baiduUedCollect;

    @Test
    public void collect() {
    }

    @Test
    public void collectAll() {
        baiduUedCollect.collectAll();
    }
}