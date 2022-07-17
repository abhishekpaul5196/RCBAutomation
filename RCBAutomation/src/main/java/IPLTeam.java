import java.util.ArrayList;
import java.util.List;
/*
This is the class to maintain different properties of a IPL team
name: to store team name
location: to store team location
playerList: to store list of all players of a team
Creator: Abhishek Paul
Date: 17-07-2022
 */


public class IPLTeam {
    String name,location;
    List<Player> playerList;
    //Method to add players in a team
    public void addPlayer(String name,String country,String role,String price)
    {
        Player player=new Player(name,country,role,price);
        playerList.add(player);
    }
    public IPLTeam(String teamName,String teamLocation)
    {
        this.name=teamName;
        this.location=teamLocation;
        playerList=new ArrayList<>();
    }
}
