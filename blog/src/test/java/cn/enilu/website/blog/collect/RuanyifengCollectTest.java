package cn.enilu.website.blog.collect;

import cn.enilu.website.BaseApplicationStartTest;
import cn.enilu.website.blog.dao.CollectorRepository;
import cn.enilu.website.blog.entity.Collector;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on  2018/7/19 0019
 * RuanyifengCollectTest
 *
 * @author enilu
 */
public class RuanyifengCollectTest extends BaseApplicationStartTest {
    @Autowired
    private RuanyifengCollect ruanyifengCollect;
    @Autowired
    private CollectorRepository collectorRepository;

    @Test
    public void collect() {
        Collector collector = collectorRepository.findOneByAuthor("阮一峰");
        ruanyifengCollect.setCollect(collector);
        ruanyifengCollect.collect();
    }

    @Test
    public void collectAll() {
        ruanyifengCollect.collectAll();
    }
    @Test
    public void collectCategory(){
        ruanyifengCollect.collectCategory("http://www.ruanyifeng.com/blog/essays/");
    }
    @Test
    public void collectOne(){
        ruanyifengCollect.collectOne("http://www.ruanyifeng.com/blog/2017/12/qiang-tang.html");
    }
}