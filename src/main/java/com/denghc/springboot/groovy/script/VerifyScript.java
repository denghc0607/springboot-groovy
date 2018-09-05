package com.denghc.springboot.groovy.script;

import groovy.lang.Binding;
import groovy.lang.Script;

/**
 * Created by denghc on 2018-08-27.
 */
public class VerifyScript extends Script implements IScript {
    @Override
    public Object run() {
        return null;
    }

    @Override
    public void test() {
        System.err.println("hello groovy denghc!");
    }

    @Override
    public void setProperty(String property, Object newValue) {
        super.setProperty(property, newValue);
    }

    @Override
    public void setBinding(Binding binding) {
        super.setBinding(binding);
    }
}
