package org.interrait.cucumberOptions;

import io.cucumber.testng.*;
import org.interrait.utils.testContextSetup;


@CucumberOptions(features = "src/test/java/org/interrait/feature",
        glue = "org/interrait/StepDeffination", tags = "@BaseQualifier",
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true)
public class TestNGRunner extends AbstractTestNGCucumberTests{

}


