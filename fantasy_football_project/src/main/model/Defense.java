package model;

/*
Class for team defense, can store statistics for sacks, forced fumbles, interceptions, safeties, defensive
touchdowns, and adds/subtracts fantasy points for points allowed in one game
 */
public class Defense extends Player {

    int sacks;
    int forceFumbles;
    int interceptions;
    int safeties;
    int touchdowns;
    int allowed;
    boolean hasAddedPointsAllowed;

    // REQUIRES: name is "Team Defense"
    // EFFECTS: constructor
    public Defense(String team) {
        super(team + " Defense", team, "Defense");
    }

    public Defense() {
        this.sacks = 0;
        this.forceFumbles = 0;
        this.interceptions = 0;
        this.safeties = 0;
        this.touchdowns = 0;
        this.hasAddedPointsAllowed = false;
    }

    @Override
    // REQUIRES: statType is either "sack", "int", "fumble", "safety", "td", or "pts"
    // MODIFIES: this
    // EFFECTS: calls appropriate function to add stat based on statType
    public boolean addStat(String statType, int num) {
        boolean valid = true;
        if (statType.equals("sack")) {
            addSacks(num);
        } else if (statType.equals("int")) {
            addInterceptions(num);
        } else if (statType.equals("fumble")) {
            addFumbles(num);
        } else if (statType.equals("safety")) {
            addSafeties(num);
        } else if (statType.equals("td")) {
            addTouchdowns(num);
        } else if (statType.equals("pts")) {
            addPointsAllowed(num);
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

    // REQUIRES: sks >= 0
    // MODIFIES: this
    // EFFECTS: adds sks to total sacks
    public void addSacks(int sks) {
        sacks += sks;
    }

    // REQUIRES: picks >= 0
    // MODIFIES: this
    // EFFECTS: adds picks to interceptions
    public void addInterceptions(int picks) {
        interceptions += picks;
    }

    // REQUIRES: fumbles >= 0
    // MODIFIES: this
    // EFFECTS: adds fumbles to total forced fumbles
    public void addFumbles(int fumbles) {
        forceFumbles += fumbles;
    }

    // REQUIRES: sfts >= 0
    // MODIFIES: this
    // EFFECTS: adds sfts to total safeties
    public void addSafeties(int sfts) {
        safeties += sfts;
    }

    // REQUIRES: tds >= 0
    // MODIFIES: this
    // EFFECTS: adds tds to total
    public void addTouchdowns(int tds) {
        touchdowns += tds;
    }

    // REQUIRES: pointsAllowed >= 0 and CANNOT USE METHOD MULTIPLE TIMES
    // MODIFIES: this
    // EFFECTS: adds or deducts points according to points allowed rules
    public void addPointsAllowed(int pointsAllowed) {
        allowed = pointsAllowed;
        if (pointsAllowed == 0) {
            points += 10;
        } else if (pointsAllowed < 7) {
            points += 7;
        } else if (pointsAllowed < 14) {
            points += 4;
        } else if (pointsAllowed < 21) {
            points += 1;
        } else if (pointsAllowed < 28) {
            points += 0;
        } else if (pointsAllowed < 35) {
            points -= 1;
        } else {
            points -= 4;
        }

        hasAddedPointsAllowed = true;
    }

    @Override
    // EFFECTS: returns number of points according to formula
    public int getPoints() {
        int pts = 0;
        pts += sacks + 2 * (interceptions + forceFumbles + safeties) + 6 * touchdowns;
        return points + pts;
    }

    public int getPointsAllowed() {
        return allowed;
    }

    public int getSacks() {
        return sacks;
    }

    public int getTouchdowns() {
        return touchdowns;
    }

    public int getForcedFumbles() {
        return forceFumbles;
    }

    public int getInterceptions() {
        return interceptions;
    }

    public int getSafeties() {
        return safeties;
    }
}
