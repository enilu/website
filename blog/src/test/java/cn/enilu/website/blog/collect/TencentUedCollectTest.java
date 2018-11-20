package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on  2018/7/21 0021
 * TencentUedCollectTest
 *
 * @author enilu
 */
public class TencentUedCollectTest  extends BaseApplicationStartTest {
    @Autowired
    private TencentUedCollect tencentUedCollect;

    @Test
    public void collect() {
    }

    @Test
    public void collectAll() {
        tencentUedCollect.collectAll();
    }
}