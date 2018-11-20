package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created  on  2018/7/22 0022
 * Team75CollectTest
 *
 * @author enilu
 */
public class Team75CollectTest extends BaseApplicationStartTest {
    @Autowired
    private Team75Collect team75Collect;
    @Test
    public void collect() {
    }

    @Test
    public void collectAll() {
        team75Collect.collectAll();
    }
}