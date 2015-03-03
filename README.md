# Overview

Texas-holdem is a Java console application allows you to play texas holdem against X number of robots. The robots will act fairly randomly, and
you can choose your moves as the game goes along. The game will output to console when it is your turn with a list of options. It will also
print out the current status of the game (pot size, bet amounts, etc).


### Running

This application requires Java 8.

To run the application you simply need to run the `Main.java` class. When the application starts up, it will ask you
how many players to include in the game. One of those players will be a human player that you get to control.

It will also ask you the number of starting chips you want each player to begin with.

### Behavior

As soon as the number of players and starting chip amounts are set, the game will begin (you will need to have added at
least 2 players).

The game will continue to run in tournament style (although it does not support raising the blinds on a schedule just yet).
As soon as only one player is left with chips, the tournament is over, and a winner is declared.

The application lumps the concept of `BETs` into `RAISEs`. So at any given point you can simply `RAISE` X number of chips.
If you tell the application to raise `80 chips`, it will simply add `80` to your own previous bet. Some other applications
will consider raising 80 to mean adding 80 to the previous player's bet; however, since we've lumped betting in with raising
this is how this application is implemented.

When a player is all in, we indicate that the only action available to the player is a `CHECK`. Any time there is only
one action available, and that action is a `CHECK` the application will handle performing that automatically for the
human player as well.

When it is the human's turn the application will show something along the lines of:

     Robot #1 posts small blind of 10 chips
     Human Player posts big blind of 20 chips
     Robot #1 calls 10 chips
     My turn!
     My Cards: [9h, Jc]
     (180 chips, Current Bet: 20, My Bet: 20, Total Pot: 40)
     (1)FOLD (2)CHECK (3)RAISE`

The human can then enter in 1, 2, or 3 into the console to process their action. For `RAISES` an amount must be supplied,
which you can simply do by entering in `3 40` in the example above if you want to raise 40 chips.

If there is an error with your input the application should spit out a message indicating the error, and re-prompt you
to enter in your input again.

If a player is facing a bet that is larger than the amount he currently has, the application manages this as a `CALL` action,
even though it is for less than the bet. `ALL INs` in general pose some interesting behavior.

The application does not currently support fraction bets. Assume you are betting chips, and they must be bet whole.

At the moment the big blind and small blind are hardcoded to be 10 and 20.

### Testing

Unit tests were written using `JUNIT`. You can find all the tests in `src/test/*`.

