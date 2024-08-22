package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/* represents a list of players in your fantasy team
can add up to MAX_SIZE players
 */
public class Team {
    public static final int MAX_SIZE = 5;
    private LinkedList<Player> listOfPlayers;
    int totalPoints = 0;

    public Team() {
        listOfPlayers = new LinkedList<>();
    }

    // MODIFIES: listOfPlayers
    // EFFECTS: adds player to listOfPlayers of listOfPlayers has not exceeded max size
    public boolean addPlayer(Player player) {
        if (listOfPlayers.size() < MAX_SIZE) {
            listOfPlayers.add(player);
            EventLog.getInstance().logEvent(new Event("Added "
                    + player.getPosition() + ", " + player.getName() + " to team."));
            return true;
        } else {
            return false;
        }

    }

    // REQUIRES: name isn't empty string, and player named is in list
    // EFFECTS: returns total points of player with given name
    public int getPlayerPoints(String name) {
        int pos = -1;
        for (int i = 0; i < listOfPlayers.size(); i++) {
            if (listOfPlayers.get(i).getName() == name) {
                pos = listOfPlayers.get(i).getPoints();
                break;
            }
        }

        return pos;
    }

    // getters
    // EFFECTS: calculates total points
    public int getTotalPoints() {
        int points = 0;
        for (int i = 0; i < listOfPlayers.size(); i++) {
            points += listOfPlayers.get(i).getPoints();
        }
        return totalPoints + points;
    }

    public int getSize() {
        return listOfPlayers.size();
    }

    public Player getPlayerFromIndex(int i) {
        return listOfPlayers.get(i);
    }

    public LinkedList<Player> getPlayers() {
        return listOfPlayers;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("points", getTotalPoints());
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: returns players as JSON array
    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p: listOfPlayers) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: listOfPlayers
    // EFFECTS: removes first player added
    public void removePlayer() {
        listOfPlayers.removeFirst();
        EventLog.getInstance().logEvent(new Event("Removed first player from team"));
    }


}
