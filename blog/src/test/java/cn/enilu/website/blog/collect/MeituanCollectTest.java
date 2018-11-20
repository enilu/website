package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created  on  2018/7/22 0022
 * MeituanCollectTest
 *
 * @author enilu
 */
public class MeituanCollectTest extends BaseApplicationStartTest {
    @Autowired
    private MeituanCollect meituanCollect;

    @Test
    public void collect() {
    }

    @Test
    public void collectAll() {
        meituanCollect.collectAll();
    }
}