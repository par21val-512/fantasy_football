package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyModelTest {
    // delete or rename this class!

    QB quarterback;
    Flex runningBack;
    Flex tightEnd;
    Flex wideReceiver;
    Kicker kicker;
    Defense defense;
    Defense defense2;
    Defense defense3;
    Defense defense4;
    Defense defense5;
    Team myTeam;
    @BeforeEach
    void runBefore() {
        quarterback = new QB("Patrick Mahomes", "Chiefs");
        runningBack = new RB("Derrick Henry", "Titans");
        tightEnd = new TE("Travis Kelce", "Chiefs");
        wideReceiver = new WR("Justin Jefferson", "Vikings");
        kicker = new Kicker("Justin Tucker", "Ravens");
        defense = new Defense("Steelers");
        defense2 = new Defense("Bills");
        defense3 = new Defense("Patriots");
        defense4 = new Defense("Giants");
        defense5 = new Defense("Rams");

        myTeam = new Team();


    }

    @Test
    void testGetPosition() {
        assertEquals("QB", quarterback.getPosition());
        assertEquals("RB", runningBack.getPosition());
        assertEquals("WR", wideReceiver.getPosition());
        assertEquals("TE", tightEnd.getPosition());
        assertEquals("Kicker", kicker.getPosition());
        assertEquals("Defense", defense.getPosition());
        assertEquals("Defense", defense2.getPosition());
    }

    @Test
    void testConstructorQB() {
        assertEquals(0, quarterback.getPoints());
        assertEquals(0, quarterback.getPassYards());
        assertEquals(0, quarterback.getConversions());
        assertEquals(0, quarterback.getRushAndRecTouchdowns());
        assertEquals(0, quarterback.getPassTouchdowns());
        assertEquals(0, quarterback.getRushAndRecYards());
        assertEquals(0, quarterback.getFumblesLost());
    }

    @Test
    void testConstructorFlex() {
        assertEquals(0, runningBack.getRushYards());
        assertEquals(0, runningBack.getReceptions());
        assertEquals(0, runningBack.getRecYards());
        assertEquals(0, runningBack.getTouchdowns());
        assertEquals(0, runningBack.getFumbles());
        assertEquals(0, runningBack.getPoints());
    }

    @Test
    void testConstructorKicker() {
        assertEquals(0, kicker.getLongest());
        assertEquals(0, kicker.getPoints());
    }

    @Test
    void testConstructorDefense() {
        assertEquals(0, defense.getPoints());
        assertEquals(0, defense.getSacks());
        assertEquals(0, defense.getSafeties());
        assertEquals(0, defense.getTouchdowns());
        assertEquals(0, defense.getForcedFumbles());
        assertEquals(0, defense.getPointsAllowed());
        assertEquals(0, defense.getInterceptions());
    }

    @Test
    void testAddPlayer() {
        assertTrue(myTeam.addPlayer(quarterback));
        assertEquals(0, myTeam.getPlayerPoints("Patrick Mahomes"));
    }

    @Test
    void testAddStatsToOnePlayerOnTeamMultipleTimes() {
        myTeam.addPlayer(runningBack);
        runningBack.addRushYards(104);
        assertEquals(10, myTeam.getTotalPoints());
        runningBack.addRecYards(16);
        assertEquals(12, myTeam.getTotalPoints());
    }

    @Test
    void testAddingTooManyPlayers() {
        assertTrue(myTeam.addPlayer(quarterback));
        assertTrue(myTeam.addPlayer(runningBack));
        assertTrue(myTeam.addPlayer(wideReceiver));
        assertTrue(myTeam.addPlayer(tightEnd));
        assertTrue(myTeam.addPlayer(defense));
        assertFalse(myTeam.addPlayer(kicker));

    }

    @Test
    void testAddingStatsForMultiplePlayers() {
        assertTrue(myTeam.addPlayer(quarterback));
        assertTrue(myTeam.addPlayer(kicker));
        assertEquals(2, myTeam.getSize());
        assertEquals(quarterback, myTeam.getPlayerFromIndex(0));
        quarterback.addPassingTouchdowns(4);
        kicker.addFieldGoal(55);
        assertEquals(16, myTeam.getPlayerPoints("Patrick Mahomes"));
        assertEquals(5, myTeam.getPlayerPoints("Justin Tucker"));
        assertEquals(21, myTeam.getTotalPoints());
    }

    @Test
    void testGetters() {
        assertEquals("Patrick Mahomes", quarterback.getName());
        assertEquals("Chiefs", quarterback.getTeam());
        assertEquals("Derrick Henry", runningBack.getName());
        assertEquals("Justin Tucker", kicker.getName());
        assertEquals("QB", quarterback.getPosition());
        assertEquals("Defense", defense.getPosition());
    }

    @Test
    void testAddPassingYards() {
        quarterback.addPassingYards(10);
        assertEquals(10, quarterback.getPassYards());
        assertEquals(0, quarterback.getPoints());
    }

    @Test
    void testAddPassingYardsMultipleTimes() {
        quarterback.addPassingYards(10);
        assertEquals(10, quarterback.getPassYards());
        quarterback.addPassingYards(10);
        assertEquals(20, quarterback.getPassYards());
        assertEquals(1, quarterback.getPoints());
    }

    @Test
    void testAddPassingYardsForMultiplePoints() {
        quarterback.addPassingYards(69);
        assertEquals(3, quarterback.getPoints());
    }

    @Test
    void testAddingPassingAndRushingYardsToQB() {
        quarterback.addPassingYards(10);
        quarterback.addRushAndRecYards(10);
        assertEquals(20, quarterback.getTotalYards());
        assertEquals(1, quarterback.getPoints()); // Adding 10 passing yards shouldn't result in points
    }

    @Test
    void testAddingPassingAndRushingTouchdownsToQB() {
        quarterback.addPassingTouchdowns(1);
        assertEquals(4, quarterback.getPoints());
        quarterback.addRushAndRecTouchdowns(1);
        assertEquals(10, quarterback.getPoints());
    }

    @Test
    void testAddingMultipleTouchdownsToQB() {
        quarterback.addPassingTouchdowns(4);
        assertEquals(16, quarterback.getPoints());
    }

    @Test
    void testAddInterceptions() {
        quarterback.addInts(3);
        assertEquals(-6, quarterback.getPoints());
    }

    @Test
    void testAddFumbles() {
        quarterback.addFumbles(3);
        assertEquals(-6, quarterback.getPoints());
    }

    @Test
    void testAdd2PtConversion() {
        quarterback.addTwoPointConversion(2);
        assertEquals(4, quarterback.getPoints());
    }

    @Test
    void testChangeTeam() {
        quarterback.changeTeam("Saints");
        assertEquals("Saints", quarterback.getTeam());
    }

    @Test
    void testAddYardsToFlex() {
        runningBack.addRushYards(1);
        runningBack.addRecYards(1);
        assertEquals(1, runningBack.getRushYards());
        assertEquals(1, runningBack.getRecYards());
        assertEquals(2, runningBack.getTotalYards());
        assertEquals(0, runningBack.getPoints());

    }

    @Test
    void testAddUpperLimitYardsToFlex() {
        runningBack.addRushYards(9);
        runningBack.addRecYards(9);
        assertEquals(18, runningBack.getTotalYards());
        assertEquals(1, runningBack.getPoints());
    }

    @Test
    void testAddOneOneYardFieldGoal() {
        assertEquals(0, kicker.getPoints());
        kicker.addFieldGoal(1);
        assertEquals(3, kicker.getPoints());
        assertEquals(1, kicker.getLongest());
    }

    @Test
    void testAddOneLongFieldGoal() {
        kicker.addFieldGoal(50);
        assertEquals(5, kicker.getPoints());
        assertEquals(50, kicker.getLongest());
    }

    @Test
    void testAddMultipleFieldGoals() {
        kicker.addFieldGoal(35);
        kicker.addFieldGoal(56);
        kicker.addFieldGoal(22);
        assertEquals(11, kicker.getPoints());
        assertEquals(56, kicker.getLongest());
    }

    @Test
    void testAddExtraPoints() {
        kicker.addExtraPoints(5);
        assertEquals(5, kicker.getPoints());
    }

    @Test
    void testGetNameAndGetTeamDefense() {
        assertEquals("Steelers Defense", defense.getName());
        assertEquals("Steelers", defense.getTeam());
    }

    @Test
    void testAddStatsToTeam() {
        assertEquals(0, defense.getPoints());
        defense.addSacks(1);
        defense.addInterceptions(1);
        defense.addSafeties(1);
        defense.addTouchdowns(1);
        defense.addFumbles(1);
        assertEquals(1, defense.getSacks());
        assertEquals(1, defense.getInterceptions());
        assertEquals(1, defense.getForcedFumbles());
        assertEquals(1, defense.getSafeties());
        assertEquals(1, defense.getTouchdowns());
        assertEquals(1+2+2+2+6, defense.getPoints());

    }

    @Test
    void testAddShutoutTODefense() {
        defense.addPointsAllowed(0);
        assertEquals(0, defense.getPointsAllowed());
        assertEquals(10, defense.getPoints());
    }

    @Test
    void testAddPointsAllowedToDefense() {
        defense.addPointsAllowed(10);
        assertEquals(4, defense.getPoints());
    }

    @Test
    void testAddingOnePointAllowedToDefense() {
        defense.addPointsAllowed(1);
        assertEquals(7, defense.getPoints());
    }

    @Test
    void testBoundaryCasesForPointsAllowed() {
        defense.addPointsAllowed(7);
        assertEquals(4, defense.getPoints());
        defense2.addPointsAllowed(14);
        assertEquals(1, defense2.getPoints());
        defense3.addPointsAllowed(21);
        assertEquals(0, defense3.getPoints());
        defense4.addPointsAllowed(28);
        assertEquals(-1, defense4.getPoints());
        defense5.addPointsAllowed(35);
        assertEquals(-4, defense5.getPoints());
    }

    @Test
    void testAddStatsAndPointsAllowedToDefense() {
        defense.addPointsAllowed(65);
        defense.addSafeties(3);
        assertEquals(2, defense.getPoints());
    }

    @Test
    void testAddStatsInDifferentOrder() {
        defense.addSafeties(3);
        defense.addPointsAllowed(65);
        assertEquals(2, defense.getPoints());
    }

    @Test
    void testAddStatQB() {
        assertTrue(quarterback.addStat("py", 20));
        assertTrue(quarterback.addStat("ry", 10));
        assertTrue(quarterback.addStat("rtd", 1));
        assertTrue(quarterback.addStat("ptd", 3));
        assertTrue( quarterback.addStat("int", 1));
        assertTrue(quarterback.addStat("fumble", 1));
        assertTrue(quarterback.addStat("two", 1));
        assertEquals(3, quarterback.getPassTouchdowns());
        assertEquals(20, quarterback.getPassYards());
        assertEquals(10, quarterback.getRushAndRecYards());
        assertEquals(1, quarterback.getRushAndRecTouchdowns());
        assertEquals(1, quarterback.getInts());
        assertEquals(1, quarterback.getFumblesLost());
        assertEquals(1, quarterback.getConversions());
        assertEquals(18, quarterback.getPoints());
    }

    @Test
    void testAddStatsForFlex() {
        assertTrue(runningBack.addStat("rush", 122));
        assertTrue(runningBack.addStat("fumble", 1));
        assertTrue(runningBack.addStat("rec", 33));
        assertTrue(runningBack.addStat("catch", 4));
        assertTrue(runningBack.addStat("td", 2));
        assertEquals(122+33, runningBack.getTotalYards());
        assertEquals(4, runningBack.getReceptions());
        assertEquals(2, runningBack.getTouchdowns());
        assertEquals(1, runningBack.getFumbles());
        assertEquals(29, runningBack.getPoints());
    }

    @Test
    void testAddStatsForKicker() {
        assertTrue(kicker.addStat("xp", 4));
        assertTrue(kicker.addStat("fg", 51));
        assertEquals(51, kicker.getLongest());
    }

    @Test
    void testAddStatsForDefense() {
        assertTrue(defense.addStat("sack", 2));
        assertTrue(defense.addStat("safety", 0));
        assertTrue(defense.addStat("fumble", 2));
        assertTrue(defense.addStat("int", 2));
        assertTrue(defense.addStat("td", 1));
        assertTrue(defense.addStat("pts", 0));
        assertEquals(0, defense.getPointsAllowed());
        assertEquals(1, defense.getTouchdowns());
        assertEquals(2, defense.getInterceptions());
        assertEquals(2, defense.getForcedFumbles());
        assertEquals(0, defense.getSafeties());
        assertEquals(2, defense.getSacks());
    }

    @Test
    void testGetPlayerPointsOnLargerTeam() {
        myTeam.addPlayer(quarterback);
        myTeam.addPlayer(runningBack);
        myTeam.addPlayer(wideReceiver);
        wideReceiver.addTDs(5);
        assertEquals(0, myTeam.getPlayerPoints("Derrick Henry"));
        assertEquals(30, myTeam.getPlayerPoints("Justin Jefferson"));
    }

    @Test
    void testFalseAddStat() {
        assertFalse(quarterback.addStat("jeff", 3));
        assertFalse(runningBack.addStat("jeff", 3));
        assertFalse(kicker.addStat("jeff", 3));
        assertFalse(defense.addStat("jeff", 3));
    }

    @Test
    void testGetPlayerPointsNotInList() {
        assertEquals(-1, myTeam.getPlayerPoints("jeff"));
    }

    @Test
    void testAddOnePointToAllDifferentPositions() {
        quarterback.addPoints(1);
        assertEquals(1, quarterback.getPoints());
        runningBack.addPoints(1);
        assertEquals(1, runningBack.getPoints());
        kicker.addPoints(1);
        assertEquals(1, kicker.getPoints());
        defense.addPoints(1);
        assertEquals(1, defense.getPoints());
    }

    @Test
    void testAddPointsMultipleTimes() {
        quarterback.addPoints(12);
        assertEquals(12, quarterback.getPoints());
        quarterback.addPoints(10);
        assertEquals(22, quarterback.getPoints());
    }

    @Test
    void testAddDefenseStatsMultipleTimes() {
        defense.addSacks(5);
        assertEquals(5, defense.getPoints());
        defense.addSacks(5);
        assertEquals(10, defense.getPoints());
    }

    @Test
    void testRemovePlayer() {
        myTeam.addPlayer(quarterback);
        myTeam.addPlayer(runningBack);
        assertEquals(2, myTeam.getSize());
        assertEquals(quarterback, myTeam.getPlayerFromIndex(0));
        myTeam.removePlayer();
        assertEquals(1, myTeam.getSize());
        assertEquals(runningBack, myTeam.getPlayerFromIndex(0));
        assertFalse(myTeam.getPlayers().contains(quarterback));

    }




}