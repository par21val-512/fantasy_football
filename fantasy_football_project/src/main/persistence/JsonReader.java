package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.*;
import org.json.*;
/*
represents reader that reads team stored in JSON file
 */

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads team from file and returns it, throws IOException if error occurs reading data
    public Team read() throws IOException {
        String jsondata = readFile(source);
        JSONObject jsonObject = new JSONObject(jsondata);
        return parseTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    public Team parseTeam(JSONObject jsonObject) {
        Team t = new Team();
        addPlayers(t, jsonObject);
        return t;


    }

    // MODIFIES: t
    // EFFECTS: parses players from JSONObject and adds to team
    public void addPlayers(Team t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json: jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(t, nextPlayer);

        }
    }

    // REQUIRES: position is one of those listed
    // MODIFIES: t
    // EFFECTS: parses player fron JSONObject and adds to team
    public void addPlayer(Team t, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String team = jsonObject.getString("team");
        String position = jsonObject.getString("position");
        int points = jsonObject.getInt("points");

        Player player;

        if (position.equals("QB")) {
            player = new QB(name, team);
        } else if (position.equals("RB")) {
            player = new RB(name, team);
        } else if (position.equals("WR")) {
            player = new WR(name, team);
        } else if (position.equals("TE")) {
            player = new TE(name, team);
        } else if (position.equals("Kicker")) {
            player = new Kicker(name, team);
        } else { // if (position == "Defense")
            player = new Defense(team);
        }

        player.addPoints(points);
        t.addPlayer(player);
    }
}
