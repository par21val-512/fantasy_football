package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

/*
app for team builder, runs in console
 */
public class TeamBuilderApp {

    private Team myTeam;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/team.json";

    // REQUIRES: add player not ran for more than 5 times (code not robust yet)
    // EFFECTS: runs team builder application
    public TeamBuilderApp() {
        runTeamBuilder();
    }

    public void runTeamBuilder() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes team
    public void init() {
        myTeam = new Team();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: prints display menu
    public void displayMenu() {
        System.out.println("\nSelect From:");
        System.out.println("\tp -> Add Player(Maximum of 5)");
        System.out.println("\ta -> Add Statistic");
        System.out.println("\tg -> Get Points");
        System.out.println("\ts -> Save current team");
        System.out.println("\tl -> Load previously saved team");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes command
    public void processCommand(String command) {
        if (command.equals("p")) {
            doAddPlayer();
        } else if (command.equals("a")) {
            doAddStat();
        } else if (command.equals("g")) {
            doGetPoints();
        } else if (command.equals("s")) {
            saveTeam();
        } else if (command.equals("l")) {
            loadTeam();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves team to file
    public void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(myTeam);
            jsonWriter.close();
            System.out.println("Saved your team to " + JSON_STORE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    public void loadTeam() {
        try {
            myTeam = jsonReader.read();
            System.out.println("Loaded previous team from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // REQUIRES: position must be one of those listed
    // MODIFIES: this
    // EFFECTS: creates new player with given name and adds them to team
    public void doAddPlayer() {
        Player player;
        System.out.println("Enter name of player (capitalize first letter of first and last name):");
        String name = input.next();

        System.out.println("Enter team name (capitalize first letter):");
        String team = input.next();

        System.out.println("Enter player position (QB, RB, WR, TE, Kicker, or Defense)");
        String position = input.next();

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

        myTeam.addPlayer(player);
        System.out.println(name + " was added to your team.");
    }

    // REQUIRES: indexNumber must be one currently listed
    // MODIFIES: myTeam
    // EFFECTS: add stats to one player on myTeam
    public void doAddStat() {
        int indexNumber;
        int num;
        String statType;
        System.out.println("Type in index number of player to add stats for:");
        for (int i = 0; i < myTeam.getSize(); i++) {
            System.out.println(i + " -> " + myTeam.getPlayerFromIndex(i).getName());
        }

        indexNumber = input.nextInt();
        Player player = myTeam.getPlayerFromIndex(indexNumber);

        if (player.getPosition().equals("QB")) {
            menuForQB();

        } else if (player.getPosition().equals("Kicker")) {
            menuForKicker();

        } else if (player.getPosition().equals("Defense")) {
            menuForDefense();

        } else {
            menuForFlex();

        }
        statType = input.next().toLowerCase();
        System.out.println("type in amount to add(integer):");
        num = input.nextInt();
        player.addStat(statType, num);
        System.out.println("the statistic has been added!");

    }

    // EFFECTS: displays menu for selecting a QB
    public void menuForQB() {
        System.out.println("\nChoose from the following stat types:");
        System.out.println("\tpy -> passing yards");
        System.out.println("\try -> rushing or receiving yards");
        System.out.println("\tptd -> passing touchdowns(must be positive)");
        System.out.println("\trtd -> rushing or receiving touchdowns(must be positive)");
        System.out.println("\tint -> interception thrown(must be positive)");
        System.out.println("\tfumble -> fumbles committed(must be positive)");
        System.out.println("\ttwo -> 2 point conversion");

    }

    // EFFECTS: displays menu for kicker
    public void menuForKicker() {
        System.out.println("\nChoose from the following stat types:");
        System.out.println("\tfg -> made field goal distance");
        System.out.println("\txp -> extra points made");
    }

    // EFFECTS: displays menu for defense
    public void menuForDefense() {
        System.out.println("\nChoose from the following stat types:");
        System.out.println("\tsack -> number of sacks(must be positive)");
        System.out.println("\tsafety -> safeties(must be positive)");
        System.out.println("\ttd -> defensive touchdowns(must be positive)");
        System.out.println("\tint -> interception made(must be positive)");
        System.out.println("\tfumble -> forced fumbles(must be positive)");
        System.out.println("\tpts -> number of points allowed for entire game");
    }

    // EFFECTS: displays menu for flex player
    public void menuForFlex() {
        System.out.println("\nChoose from the following stat types:");
        System.out.println("\trush -> number of rushing yards");
        System.out.println("\trec -> number of receiving yards");
        System.out.println("\ttd -> touchdowns(must be positive)");
        System.out.println("\tcatch -> receptions made(must be positive)");
        System.out.println("\tfumble -> fumbles committed(must be positive)");
    }

    // EFFECTS: prints points for each player and total points
    public void doGetPoints() {
        for (int i = 0; i < myTeam.getSize(); i++) {
            Player player = myTeam.getPlayerFromIndex(i);
            System.out.println(player.getName() + " has " + player.getPoints() + " points");
        }

        System.out.println("Total points = " + myTeam.getTotalPoints());

    }


}
