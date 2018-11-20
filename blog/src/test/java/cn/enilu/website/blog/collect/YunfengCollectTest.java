package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on  2018/7/24 0024
 * YunfengCollectTest
 *
 * @author enilu
 */
public class YunfengCollectTest extends BaseApplicationStartTest {
    @Autowired
    private YunfengCollect yunfengCollect;
    @Test
    public void collect() {
        yunfengCollect.collect();
    }

    @Test
    public void collectAll() {
        yunfengCollect.collectAll();
    }
}