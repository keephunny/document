package com.project.sonar.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilsTest {
    private FileUtils fileUtils=new FileUtils();
    @Test
    public void test1() throws Exception {
        fileUtils.test1();
    }

    @Test
    public void test2() throws Exception {
        fileUtils.test2();
    }

    @Test
    public void add() throws Exception {
        fileUtils.add(1,2);
    }
}