package com.denghc.springboot.groovy.engine;

import com.denghc.springboot.groovy.script.VerifyScript;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * Created by denghc on 2018-09-03.
 */
@Configuration
@ImportResource("classpath:application-bean.xml")
@Component
public class GroovyShell2 extends GroovyShell {
//    @Autowired
//    private CoreService coreServiceImpl;
//
//    public void setCoreServiceImpl(CoreService coreServiceImpl) {
//        this.coreServiceImpl = coreServiceImpl;
//    }

    public GroovyShell2(CompilerConfiguration compilerConfiguration) {
        super(new GroovyBinding(), compilerConfiguration);
    }

//    @Override
//    public Script parse(GroovyCodeSource codeSource) throws CompilationFailedException {
//        try {
//            Class<?> scriptClass = getClassLoader().parseClass(codeSource, false);
//            VerifyScript script = (VerifyScript) scriptClass.newInstance();
////            script.setCoreServiceImpl(coreServiceImpl);
//            script.setBinding(getContext());
//            return script;
//        } catch (Exception e) {
//            throw new RuntimeException("groovy 实例异常", e);
//        } finally {
//            resetLoadedClasses();
//            getClassLoader().clearCache();
//        }
//    }
}
