package org.interrait.utils;

import io.cucumber.java.After;
import org.interrait.PageObjects.PageObjectManager;

import java.io.IOException;

public class testContextSetup
{
    TestBase testBase;
    PageObjectManager pageObjectManager;
   //this class is used to create testbase,page object obj
    public testContextSetup() throws IOException {
        testBase = new TestBase();
        pageObjectManager = new PageObjectManager(testBase.webDriverManager());
    }

    public PageObjectManager getPageObjectManager() {
        return pageObjectManager;
    }

    public TestBase getTestBase() {
        return testBase;
    }




}
