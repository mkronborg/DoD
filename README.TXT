CONFIGURATION INSTRUCTIONS:
Keep all files in same folder as supplied

INSTALLATION INSTRUCTIONS:
Compile all java files

USER INSTRUCTIONS:
Compile files, run main method in the GameLogic class, input char to select map type, and play.

COPYRIGHT, LICENSING, AND ACKNOWLEDGEMENTS:
Pathfinding algorithm from https://en.wikipedia.org/wiki/Pathfinding used, "Sample Algorithm"

CONTACT INFORMATION FOR THE DISTRIBUTOR OR PROGRAMMER:
Anonymous

KNOWN BUGS:
If Bot cannot find a path, it will move randomly even if it can see the player

CHANGELOG:
V1.1:
	Features all basic features, but no bot
V2.2:
	Features a bot that moves randomly
	HumanPlayer and Bot now inherit from the Player Class
V3.1:
	Features a bot that follows the player if they are within the distance of the LOOK
	command, otherwise just moves randomly around until the  player is found again.

CURRENT: 3.1

GENERAL COMMENTS
There are no varying difficulties, though I did consider adding them. These would include the bot taking breaks,
the player being told which move the bot made etc.
At the moment, the bot breaks every 10th turn, since if the bot follows right behind the player,
the player cannot pickup gold without losing.