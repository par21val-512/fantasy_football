package model;

import org.json.JSONObject;

/*
one quarterback on team, storing stats for passing yards, rushing/receiving yards, passing TDs,
rushing/receiving TDs, interceptions thrown, fumbles lost, and two point conversions
 */
public class QB extends Player {

    int passYards;
    int rushAndRecYards;
    int passTouchdowns;
    int rushAndRecTouchdowns;
    int ints;
    int fumblesLost;
    int conversions;

    public QB(String name, String team) {
        super(name, team, "QB");
    }

    public QB() {
        this.passYards = 0;
        this.passTouchdowns = 0;
        this.rushAndRecYards = 0;
        this.rushAndRecTouchdowns = 0;
        this.ints = 0;
        this.fumblesLost = 0;
        this.conversions = 0;

    }

    @Override
    // REQUIRES: statType must be one of "py", "ry", "ptd", "rtd", "int", "fumble", or "two"
    // MODIFIES: this
    // EFFECTS: adds type of statistic based on statType input, helper function for app
    public boolean addStat(String statType, int num) {
        boolean valid = true;
        if (statType.equals("py")) {
            addPassingYards(num);
        } else if (statType.equals("ry")) {
            addRushAndRecYards(num);
        } else if (statType.equals("ptd")) {
            addPassingTouchdowns(num);
        } else if (statType.equals("rtd")) {
            addRushAndRecTouchdowns(num);
        } else if (statType.equals("int")) {
            addInts(num);
        } else if (statType.equals("fumble")) {
            addFumbles(num);
        } else if (statType.equals("two")) {
            addTwoPointConversion(num);
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
    // EFFECTS: adds number of passing yards to total
    public void addPassingYards(int yards) {
        passYards += yards;
    }

    // MODIFIES: this
    // EFFECTS: adds number of combined rushing and receiving yards to total
    public void addRushAndRecYards(int yards) {
        rushAndRecYards += yards;
    }

    // REQUIRES: tds >= 0
    // MODIFIES: this
    // EFFECTS: adds tds to total number of passing TDs
    public void addPassingTouchdowns(int tds) {
        passTouchdowns += tds;
    }

    // REQUIRES: tds >= 0
    // MODIFIES: this
    // EFFECTS: adds tds to total number of rushing & receiving TDs
    public void addRushAndRecTouchdowns(int tds) {
        rushAndRecTouchdowns += tds;
    }

    public void addInts(int picks) {
        ints += picks;
    }

    public void addFumbles(int fumbles) {
        fumblesLost += fumbles;
    }

    public void addTwoPointConversion(int twoPoint) {
        conversions += twoPoint;
    }


    // getters
    public int getPassYards() {
        return passYards;
    }

    public int getRushAndRecYards() {
        return rushAndRecYards;
    }

    public int getTotalYards() {
        return passYards + rushAndRecYards;
    }

    public int getPassTouchdowns() {
        return passTouchdowns;
    }

    public int getRushAndRecTouchdowns() {
        return rushAndRecTouchdowns;
    }

    public int getInts() {
        return ints;
    }

    public int getFumblesLost() {
        return fumblesLost;
    }

    public int getConversions() {
        return conversions;
    }

    @Override
    // EFFECTS: returns total number of points calculated according to scoring system
    // (1 point for every 20 passing yards, every 10 rushing/receiving yards, 4 points for every passing TD, 6 points
    // for every rushing/receiving TD, -2 points for every turnover(interceptions + fumbles) and 2 points for every 2pt)
    public int getPoints() {
        int pts = 0;
        pts += passYards / 20;
        pts += rushAndRecYards / 10;
        pts += passTouchdowns * 4;
        pts += rushAndRecTouchdowns * 6;
        pts -= ints * 2;
        pts -= fumblesLost * 2;
        pts += conversions * 2;
        return pts + points;
    }

}
