package persistence;

import model.Player;
import model.QB;
import model.RB;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Team t = new Team();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:Filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Team t = new Team();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTeam.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTeam.json");
            t = reader.read();
            assertEquals(0, t.getTotalPoints());
            assertEquals(0, t.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Team t = new Team();
            Player player1 = new QB("Patrick Mahomes", "Chiefs");
            Player player2 = new RB("Derrick Henry", "Titans");
            player1.addPoints(10);
            player2.addPoints(10);
            t.addPlayer(player1);
            t.addPlayer(player2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTeam.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTeam.json");
            t = reader.read();
            LinkedList players = t.getPlayers();
            assertEquals(2, players.size());
            assertEquals(20, t.getTotalPoints());
            checkPlayers("Patrick Mahomes", "Chiefs", "QB", 10, t.getPlayerFromIndex(0));
            checkPlayers("Derrick Henry", "Titans", "RB", 10, t.getPlayerFromIndex(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}