package cn.enilu.website.blog.dao;

import cn.enilu.website.BaseApplicationStartTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * CollectorRepositoryTest
 * version 2018/7/26 0026
 *
 * @author enilu
 */
public class CollectorRepositoryTest  extends BaseApplicationStartTest {
    @Autowired
    private CollectorRepository collectorRepository;
    @Test
    public void findAllByState() {
        System.out.println("1");
        collectorRepository.findAllByState(true);
        System.out.println("2");
        collectorRepository.findAllByState(true);
        System.out.println("end");
    }
    @Test
    public void findAll() {
        System.out.println("1");
        collectorRepository.findAll();
        System.out.println("2");
        collectorRepository.findAll();
        System.out.println("end");
    }
}