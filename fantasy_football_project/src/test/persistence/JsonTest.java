package persistence;

import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonTest {
    protected void checkPlayers(String name, String team, String pos, int points, Player player) {
        assertEquals(name, player.getName());
        assertEquals(team, player.getTeam());
        assertEquals(pos, player.getPosition());
        assertEquals(points, player.getPoints());
    }

}