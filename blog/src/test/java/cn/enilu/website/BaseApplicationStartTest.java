package cn.enilu.website;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Name: ApplicationStartTest<br>
 * User: Yao<br>
 * Date: 2018/1/24<br>
 * Time: 16:26<br>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest
public class BaseApplicationStartTest {
    protected final Logger logger= LoggerFactory.getLogger(getClass());



}
