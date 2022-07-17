import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;

public class VerifyRCB {

    JSONObject teamObj;
    IPLTeam rcbTeam;

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
                int countForeignPlayers=0;
                for(int i=0;i<rcbTeam.playerList.size();i++)
                {
                    String countryName=rcbTeam.playerList.get(i).playerCountry;
                    if(!countryName.equals("India"))
                        countForeignPlayers++;
                }
            if(countForeignPlayers<=4)
                System.out.println("Test Case: Passed");
            else
            {
                System.out.println("Actual count of foreign players:"+countForeignPlayers+"\nTest Case: Failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
