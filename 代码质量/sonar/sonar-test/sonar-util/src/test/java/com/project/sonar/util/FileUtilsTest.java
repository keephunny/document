package com.project.sonar.util;

import com.project.sonar.util.FileUtils;
import org.junit.Test;


public class FileUtilsTest {
    private FileUtils fileUtils = new FileUtils();

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
        fileUtils.add(1, 2);
    }

}