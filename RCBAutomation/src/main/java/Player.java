/*
This is the class to maintain different properties of a player
Creator: Abhishek Paul
Date: 17-07-2022
 */
public class Player {
    String playerName;
    String playerCountry;
    String playerRole;
    String playerPrice;

    public Player(String name, String country, String role, String price) {
        this.playerName=name;
        this.playerCountry=country;
        this.playerRole=role;
        this.playerPrice=price;
    }
}
