package model;

/*
class for kicker, adds specified number of points for field goal and stores longest distance,
and number of extra points
 */
public class Kicker extends Player {

    int longest;

    public Kicker(String name, String team) {
        super(name, team, "Kicker");
    }

    public Kicker() {
        this.longest = 0;

    }

    // REQUIRES: statType is either "fg" or "xp"
    // MODIFIES: this
    // EFFECTS: calls helper below based on statType
    public boolean addStat(String statType, int num) {
        boolean valid = true;
        if (statType.equals("fg")) {
            addFieldGoal(num);
        } else if (statType.equals("xp")) {
            addExtraPoints(num);
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

    // REQUIRES: 100 >= yards >= 0
    // MODIFIES: this
    // EFFECTS: adds 5 points for every field goal over 50 yards, 3 points for every field goal under 50 yards to total,
    // set as longest field goal if longer than previous longest
    public void addFieldGoal(int yards) {
        if (yards > longest) {
            longest = yards;
        }

        if (yards < 50) {
            points += 3;
        } else {
            points += 5;
        }

    }

    // REQUIRES: num >= 0
    // MODIFIES: this
    // EFFECTS: adds number of extra points made to total(1 point per extra point)
    public void addExtraPoints(int num) {
        points += num;
    }

    @Override
    // getters
    public int getPoints() {
        return points;
    }

    public int getLongest() {
        return longest;
    }


}
