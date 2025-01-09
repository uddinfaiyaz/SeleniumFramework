package testRunner;

//import org.junit.platform.suite.api.IncludeEngines;
//import org.junit.platform.suite.api.SelectClasspathResource;
//import org.junit.platform.suite.api.Suite;

//@RunWith(Cucumber.class)
//@CucumberOptions(
//        features = "src/test/resources/features",
//        glue = {"com.steps"},
//        monochrome = true,
//        plugin = {
//                "pretty", "html:target/cucumber-pretty.html",
//                "json:target/cucumber.json"
//        },
//        tags = ""
//)

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("steps")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report/cucumber.html, json:target/cucumber.json" )
public class RunCucumberTest {

}
