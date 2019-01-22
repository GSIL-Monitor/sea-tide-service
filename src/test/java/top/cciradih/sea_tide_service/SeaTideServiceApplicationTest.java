package top.cciradih.sea_tide_service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cciradih.sea_tide_service.component.CharacterComponent;
import top.cciradih.sea_tide_service.component.ScheduledComponent;
import top.cciradih.sea_tide_service.exception.SeaTideException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeaTideServiceApplicationTest {
    @Autowired
    private ScheduledComponent scheduledComponent;
    @Autowired
    private CharacterComponent characterComponent;

    @Test
    public void contextLoads() throws SeaTideException {
//        scheduledComponent.say();
    }
}
