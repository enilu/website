package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created  on  2018/7/19 0019
 * SoucheCollectTest
 *
 * @author enilu
 */
public class SoucheCollectTest extends BaseApplicationStartTest {
    @Autowired
    private SoucheCollect soucheCollect;
    @Test
    public void collectAll() {
        soucheCollect.collectAll();
    }
}