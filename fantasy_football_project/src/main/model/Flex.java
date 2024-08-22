package model;

/*
class for flex players(RBs, WRs, or TEs), stores stats of type rushing yards, receiving yards, total
receptions, touchdowns, and fumbles committed
 */

public class Flex extends Player {

    int rushYards;
    int recYards;
    int touchdowns;
    int receptions;
    int fumbles;

    public Flex(String name, String team, String pos) {
        super(name, team, pos);
    }

    public Flex() {
        this.rushYards = 0;
        this.recYards = 0;
        this.receptions = 0;
        this.touchdowns = 0;
        this.fumbles = 0;
    }

    // REQUIRES: statType is either "rush", "rec", "catch", "td", or "fumble"
    // MODIFIES: this
    // EFFECTS: runs appropriate helper based on statType to add stat
    public boolean addStat(String statType, int num) {
        boolean valid = true;
        if (statType.equals("rush")) {
            addRushYards(num);
        } else if (statType.equals("rec")) {
            addRecYards(num);
        } else if (statType.equals("catch")) {
            addReceptions(num);
        } else if (statType.equals("td")) {
            addTDs(num);
        } else if (statType.equals("fumble")) {
            addFumbles(num);
        } else {
            valid = false;
            System.out.println("type doesn't exist...");
        }

        if (valid == true) {
            EventLog.getInstance().logEvent(new Event(
                    "Added " + num + " of " + statType + " to " + playerName));
        }

        return valid;
    }

    // MODIFIES: this
    // EFFECTS: yards added to total rushing yards
    public void addRushYards(int yards) {
        rushYards += yards;
    }

    // MODIFIES: this
    // EFFECTS: yards added to total receiving yards
    public void addRecYards(int yards) {
        recYards += yards;
    }

    // REQUIRES: catches >= 0
    // MODIFIES: this
    // EFFECTS: catches added to total receptions
    public void addReceptions(int catches) {
        receptions += catches;
    }

    // REQUIRES: tds >= 0
    // MODIFIES: this
    // EFFECTS: tds added to total touchdowns
    public void addTDs(int tds) {
        touchdowns += tds;
    }

    // REQUIRES: f >= 0
    // MODIFIES: this
    // EFFECTS: f added to fumbles
    public void addFumbles(int f) {
        fumbles += f;
    }

    // getters
    public int getRushYards() {
        return rushYards;
    }

    public int getRecYards() {
        return recYards;
    }

    // EFFECTS: returns total yards from scrimmage
    public int getTotalYards() {
        return rushYards + recYards;
    }

    public int getTouchdowns() {
        return touchdowns;
    }

    public int getReceptions() {
        return receptions;
    }

    public int getFumbles() {
        return fumbles;
    }

    @Override
    // EFFECTS: calculates total points according to formula (rushing and receiving yards are calculated together)
    public int getPoints() {
        return points + (rushYards + recYards) / 10 + receptions + touchdowns * 6 - fumbles * 2;
    }
}
