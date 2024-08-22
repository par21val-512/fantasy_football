# My Personal Project: Fantasy Football Application

## Proposal:

My project will be an application that allows a group of people
to play fantasy football with each other, where there will be a draft
at the start of the season, where the users can each assemble their team for
the season by drafting from a pool of all players in the National Football League(NFL). 
Then, as the season progresses, each user's team is awarded points for the players' accomplishments
during the season(e.g. scoring a touchdown, recording a specific number of passing/rushing yards, making
field goals), and whoever has the most points at the end of the season is declared the winner.
I was inspired to make this project because of being a fan of the NFL for the last few years. 

## User Stories:

- As a user, I want to be able to instantiate a new team
- As a user, I want to be able to instantiate new players with their name, team, and position(QB, RB, WR, TE, Kicker, Team Defense)
- As a user, I want to be able to add a player to my team(drafting)
- As a user, I want to be able to add multiple players to my team
- As a user, I want to be able to add a statistic for my player
- As a user, I want to be able to add multiple statistics to my player
- As a user, I want to be able to choose from a finite number of statistics that differs based on the player's position
- As a user, I want to be able to convert my player's statistics into points based on a pre-determined scoring system
- As a user, I want to be able to keep a running tally of my team's points for that week
- As a user, I want to have the option to save my current team when quitting from the application menu
- As a user, I want to have the option to reload a previous saved team from file

## Instructions for grader:

- You can add multiple players to a team by typing in the player's name and team, and then clicking the appropriate button for the position
you would like to add to (QB, RB, etc.)
- you can remove the first player from the team by clicking the remove first player button, and clicking it multiple times will remove the players
in the order they were added(e.g. clicking it a second time removes second player added)
- You can locate my visual element with the button icons for adding each type of player
- You can save the state by clicking the "save" button
- You can reload the state of my application by clicking the "load" button

## Phase 4: Task 2

Added QB, aa to team
Added Defense, cc to team
Added 40 sack to cc
Removed first player

The events were unable to be logged because an error occurred regarding the WindowAdapter type kept interrupting the code
when I tried to implement an InternalFrameListener, and the program was paused as a result. 

## Phase 4: Task 3
If I had more time with the project, I would have likely made my Team class implement an iterable type. 
This would make it easier to implement adding stats, since I don't have to then code specific methods for the individual
player classes. In addition, I would have added throwing more exceptions to make the code robust. For example, I would
include exceptions for when the team size exceeds max size, as in the current state of the application, there are several
points where the program either crashes or should print an error message but doesn't.
