package com.deary.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src/test/resources" },
        glue = { "com.deary.steps" },
        dryRun = false,
        monochrome = true,
        tags = { "@test","not @initialtest" },
        plugin = { "pretty", "html:report" }
)
public class CukesTest { }
