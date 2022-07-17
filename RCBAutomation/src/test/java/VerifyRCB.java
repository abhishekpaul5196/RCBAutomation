import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;

public class VerifyRCB {

    JSONObject teamObj;
    IPLTeam rcbTeam;
    ExtentSparkReporter reporter;
    ExtentReports extentReports;
    ExtentTest test;
    @BeforeSuite
    public void beforeSuiteReporting()
    {
        reporter=new ExtentSparkReporter("target/ExecutionResult.html");
        extentReports=new ExtentReports();
        extentReports.attachReporter(reporter);
    }
    @AfterSuite
    public void afterSuiteReportCereation()
    {
        extentReports.flush();
    }
    @BeforeTest
    public void addRCBTeam() {
        try {
            teamObj = new JSONObject();
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("src/main/resources/RCBTeam.json");
            teamObj = (JSONObject) parser.parse(reader);
            String teamName = teamObj.get("name").toString();
            String teamLocation = teamObj.get("location").toString();
            rcbTeam = new IPLTeam(teamName, teamLocation);
            JSONArray players = (JSONArray) teamObj.get("player");
            for (int i = 0; i < players.size(); i++) {
                JSONObject playerObj = (JSONObject) players.get(i);
                String playerName = (String) playerObj.get("name");
                String playerCounty = (String) playerObj.get("country");
                String playerRole = (String) playerObj.get("role");
                String playerPrice = (String) playerObj.get("price-in-crores");
                rcbTeam.addPlayer(playerName, playerCounty, playerRole, playerPrice);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyForeignPlayers() {
        try {
            test= extentReports.createTest("Verify Foreign Players");
            int countForeignPlayers = 0;
            for (int i = 0; i < rcbTeam.playerList.size(); i++) {
                String countryName = rcbTeam.playerList.get(i).playerCountry;
                if (!countryName.equals("India"))
                    countForeignPlayers++;
            }
            test.log(Status.INFO,"Total count of foreign players:"+countForeignPlayers);
            if (countForeignPlayers <= 4) {
                System.out.println("Test Case: Passed");
                test.pass("PASSED");
            }
            else {
                System.out.println("Test Case:Failed\n"+"Actual count of foreign players:" + countForeignPlayers);
                test.fail("FAILED");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void verifyWicketKeeper() {
        try {
            test= extentReports.createTest("Verify Wicket Keeper");
            int wicketKeeperCount = 0;
            for (int i = 0; i < rcbTeam.playerList.size(); i++) {
                String playerRole = rcbTeam.playerList.get(i).playerRole;
                if (playerRole.equals("Wicket-keeper"))
                    wicketKeeperCount++;
            }
            test.log(Status.INFO,"Total wicket-keeper count:"+wicketKeeperCount);
            if (wicketKeeperCount >0)
            {
                System.out.println("Test Case: Passed");
                test.pass("PASSED");
            }
            else {
                System.out.println("Test Case:Failed\n"+"No wicket-keeper found");
                test.fail("FAILED");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
