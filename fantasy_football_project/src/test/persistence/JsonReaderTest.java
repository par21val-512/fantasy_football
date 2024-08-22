package persistence;

import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReadNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Team t = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTeam() {
        JsonReader reader = new JsonReader("./data/testEmptyTeam.json");
        try {
            Team t = reader.read();
            assertEquals(0, t.getTotalPoints());
            assertEquals(0, t.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    @Test
    void testReaderGeneralTeam() {
        JsonReader reader = new JsonReader("./data/testGeneralTeam.json");
        try {
            Team t = reader.read();
            LinkedList players = t.getPlayers();
            assertEquals(5, players.size());
            assertEquals(100, t.getTotalPoints());
            checkPlayers("Patrick Mahomes", "Chiefs", "QB", 10, t.getPlayerFromIndex(0));
            checkPlayers("Derrick Henry", "Titans", "RB", 25, t.getPlayerFromIndex(1));
            checkPlayers("Davante Adams", "Raiders", "WR", 10, t.getPlayerFromIndex(2));
            checkPlayers("Jeff", "Pats", "TE", 54, t.getPlayerFromIndex(3));
            checkPlayers("Steelers Defense", "Steelers", "Defense", 1, t.getPlayerFromIndex(4));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderForTeamWithOneKicker() {
        JsonReader reader = new JsonReader("./data/testReaderKicker.json");
        try {
            Team t = reader.read();
            assertEquals(1, t.getTotalPoints());
            assertEquals(1, t.getPlayers().size());
            checkPlayers("Justin Tucker", "Ravens", "Kicker", 1, t.getPlayerFromIndex(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

}