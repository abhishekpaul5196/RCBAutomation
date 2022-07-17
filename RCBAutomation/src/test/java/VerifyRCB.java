import com.mongodb.util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;

public class VerifyRCB {

    JSONObject teamObj;
    IPLTeam iplTeam;
    @BeforeTest
    public void addRCBTeam() {
        try {
            teamObj = new JSONObject();
            JSONParser parser=new JSONParser();
            FileReader reader=new FileReader("src/main/resources/RCBTeam.json");
            teamObj=(JSONObject)parser.parse(reader);
            String teamName=teamObj.get("name").toString();
            String teamLocation=teamObj.get("location").toString();
            iplTeam=new IPLTeam(teamName,teamLocation);
            JSONArray players=(JSONArray) teamObj.get("player");
            for(int i=0;i<players.size();i++)
            {
                JSONObject playerObj=(JSONObject)players.get(i);
                String playerName=(String) playerObj.get("name");
                String playerCounty=(String) playerObj.get("country");
                String playerRole=(String) playerObj.get("role");
                String playerPrice=(String) playerObj.get("price-in-crores");
                iplTeam.addPlayer(playerName,playerCounty,playerRole,playerPrice);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
