package com.denghc.springboot.groovy.engine;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import groovy.lang.Binding;
import groovy.lang.Script;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class GroovyScriptEngine {

    private Log logger = LogFactory.getLog(GroovyScriptEngine.class);
    @Autowired
    private GroovyShell2 shell2;
    private final Object runlock = new Object();
    private Cache<String, Script> scriptCache = CacheBuilder.newBuilder().build();
    private static final String[] searchList = new String[]{"&apos;", "&quot;", "&gt;", "&lt;", "&nuot;", "&amp;"};
    private static final String[] replacementList = new String[]{"'", "\"", ">", "<", "\n", "&"};

    /**
     * 执行groovy代码无返回。
     * @param: script
     * @throws: Exception
     */
    public void execute(String script, Map<String, Object> vars) {
        executeObject(script, vars);
    }

    /**
     * 表达式校验
     * @param: expr
     * @param: map
     * @return:
     * @throws: Exception
     */
    public int validate(String expr, Map<String, Object> map) throws Exception {
        try {
            GroovyBinding binding = new GroovyBinding(map);
            expr = StringUtils.replaceEach(expr, searchList, replacementList);
            Script script = getScriptFromCache(expr);
            Object object = runScript(script, binding);
            binding.clearVariables();
            if (object instanceof Boolean) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (StringUtils.isNotEmpty(errorMessage) && errorMessage.contains("[校验服务异常提示]")) {
                return -1;
            } else {
                throw new Exception("校验函数异常: " + e.getMessage());
            }
        }
    }

    public Map<String, Object> executeMap(String expr, Map<String, Object> vars) throws Exception {
        try {
            GroovyBinding binding = new GroovyBinding(vars);
            expr = StringUtils.replaceEach(expr, searchList, replacementList);
            Script script = getScriptFromCache(expr);
            Object object = runScript(script, binding);
            if (object instanceof Boolean) {
                boolean flag = (boolean) object;
                if (flag) {
                    return null;
                }
            }
            Map<String, Object> map = script.getBinding().getVariables();
            binding.clearVariables();
            return map;
        } catch (Exception e) {
            throw new Exception("校验服务函数执行异常： " + e.getMessage());
        }
    }

    /**
     * 执行脚本返回对象数据。
     *
     * @param: script
     * @return:
     * @throws: Exception
     */
    public Object executeObject(String expr, Map<String, Object> vars) {
        try {
            logger.debug("执行:" + expr);
            GroovyBinding binding = new GroovyBinding(vars);
            expr = StringUtils.replaceEach(expr, searchList, replacementList);
            Script script = getScriptFromCache(expr);
            script.setBinding(binding);
            shell2.getClassLoader().clearCache();
            Object object = script.run();
            binding.clearVariables();
            return object;
        } catch (Exception e) {
            logger.error(expr + ":实例化异常", e);
            return null;
        }
    }

    public Object executeObject() {
        try {
            File file=new File("classpath:/groovy/LearnGroovy.groovy");
            Script script=shell2.parse(file);
            Object object = script.run();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Script getScriptFromCache(String expr) throws ExecutionException {
        return scriptCache.get(expr, () -> {
            logger.debug("初始化表达式:" + expr);
            return shell2.parse(expr);
        });
    }

    /**
     * @param: script
     * @param: binding
     * @return:
     */
    private Object runScript(Script script, Binding binding) {
        synchronized (runlock) {
            script.setBinding(binding);
            shell2.getClassLoader().clearCache();
            return script.run();
        }
    }
}
