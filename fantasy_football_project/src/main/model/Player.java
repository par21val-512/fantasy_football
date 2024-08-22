package model;

import org.json.JSONObject;

/*
abstract clas for one player, contains abstract methods for getting points and adding stats
 */
public abstract class Player {

    protected String playerName;
    protected String playerTeam;
    protected String playerPosition;
    protected int points;

    // REQUIRES: name and team aren't empty strings
    // EFFECTS: instantiates new player with their name and team
    public Player(String name, String team, String position) {
        this.playerName = name;
        this.playerTeam = team;
        this.playerPosition = position;
        this.points = 0;
    }

    // EFFECTS: instantiates new player, default constructor
    public Player() {}

    // REQUIRES: newTeam isn't empty string
    // MODIFIES: this
    // EFFECTS: changes player's team
    public void changeTeam(String newTeam) {
        this.playerTeam = newTeam;
    }

    // getters
    public String getName() {
        return playerName;
    }

    public String getTeam() {
        return playerTeam;
    }

    public String getPosition() {
        return playerPosition;
    }

    // EFFECTS: adds number of points to player (used for JSON reload)
    public void addPoints(int points) {
        this.points += points;
    }

    public abstract int getPoints();

    // MODIFIES: this
    // EFFECTS: adds statistic of specified type
    public abstract boolean addStat(String statType, int num);

    // EFFECTS: returns this as JSON
    protected JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("team", getTeam());
        json.put("position", getPosition());
        json.put("points", getPoints());

        return json;
    }
}
