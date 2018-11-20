package cn.enilu.website.blog.service;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created  on  2018/7/23 0023
 * TagServiceTest
 *
 * @author enilu
 */
public class TagServiceTest extends BaseApplicationStartTest {
    @Autowired
    private TagService tagService;

    @Test
    public void findAll() {
    }

    @Test
    public void findHotTag() {
        List list = tagService.findHotTag();
        System.out.println(list.size());
    }
}