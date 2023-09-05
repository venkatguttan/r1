package runners;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import cucumber.api.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;



public class TestRunner 
{
	@Test
	public void testParallel() {
		File file = new File("target/cucumber-html-reports/"); 
		String myFiles[]; 
		if(file.isDirectory()){
			myFiles = file.list();
			for (int i=0; i<myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]); 
				System.out.println("Deleteing files: "+myFile);
				myFile.delete();
			}
		}
		String karateOutputPath = "target/surefire-reports/";
		long starttime = System.nanoTime();
		long endtime = System.nanoTime();
		//System.setProperty("karate.env",System.getProperty("karate.env")); 
		Results results = Runner.path("classpath:apis")
				.outputCucumberJson(true)
				.parallel(1);
		TestRunner.generateReport(results.getReportDir());
		assertTrue(results.getFailCount() == 0,results.getErrorMessages());
	}

	public static void generateReport(String karateOutputPath)
	{
		Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
		List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
		jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
		Configuration config = new Configuration(new File("target"), "Karateframeworkproj");
		ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
		reportBuilder.generateReports();
	}
}