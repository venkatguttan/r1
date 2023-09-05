package runners;

import com.intuit.karate.testng.KarateRunner;

import cucumber.api.CucumberOptions;

@CucumberOptions(features={"src\\test\\java\\apis\\login.feature"},
                  monochrome=true,
                  plugin={"pretty","html:target/myresults1","rerun:target/failed1.txt"}
                  )
public class TestRunner extends KarateRunner
{
}