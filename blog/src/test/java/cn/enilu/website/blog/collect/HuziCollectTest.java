package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import cn.enilu.website.blog.entity.Collector;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * HuziCollectTest
 *
 * @author zt
 * @version 2018/8/6 0006
 */
public class HuziCollectTest  extends BaseApplicationStartTest {
    @Autowired
    private HuziCollect huziCollect;
    @Test
    public void collect() {
        Collector collector = new Collector("barretlee","http://www.barretlee.com/entry",
                "小胡子哥的博客","huziCollect",true);
        huziCollect.setCollect(collector);
        huziCollect.collect();
    }

    @Test
    public void collectAll() {
        Collector collector = new Collector("barretlee","http://www.barretlee.com/entry",
                "小胡子哥的博客","huziCollect",true);
        huziCollect.setCollect(collector);
        huziCollect.collectAll();
    }
}