package com.denghc.springboot.groovy;

import com.denghc.springboot.groovy.engine.GroovyScriptEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootGroovyApplicationTests {

    @Autowired
    private GroovyScriptEngine scriptEngine;

    @Test
    public void contextLoads() {
        Object obj=scriptEngine.executeObject();
        System.err.println(obj);
    }

}
