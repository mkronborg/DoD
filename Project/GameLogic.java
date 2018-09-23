/**
 * Contains the main logic part of the game, as it processes.
 * 
 * 
 *
 */
import java.util.Scanner;
public class GameLogic {

    private Map map; //
    private HumanPlayer player;
    private Bot bot; // Bot object
    private boolean lose = false; // If player loses, this boolean is set to true
    private boolean win = false;

    public GameLogic()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("Would you like to use the standard map (S), use a file named \"example_map.txt\" (E), or enter your own mapName (N)"); // Allows user to select standard map, example_map file, or a different file
        char answer = s.next().charAt(0); // Read input
        if (Character.toUpperCase(answer) == 'E')
            map = new Map("example_map.txt"); // attempts to read example_map file
        else if (Character.toUpperCase(answer) == 'N') // Allows user to enter own map name
        {
            System.out.print("Please enter your map file name: ");
            String mapName = s.next();
            map = new Map(mapName);
        }
        else 
            map = new Map(); // Standard map

        player = new HumanPlayer(map.getMap());
        bot = new Bot(map.getMap(), player.getX(), player.getY()); // uses second instructor for Player superclass, to prevent bot from spawning on top of player
        System.out.println("Welcome to Dungeons of Doom...\nAvailable Commands\nLOOK, HELLO, PICKUP, QUIT\nMOVE N, MOVE S, MOVE E, MOVE W"); // Informs user of commands

        runGame(); // Calls the rungame method, which assigns a turn to the player and then the bot
    }

    /**
     * @Param input read from user
     * 
     * @return whether action was valid, invalid, or look
     */
    public String processCommand(String input)
    {
        if (input.equals("Invalid")) // Invalid input allows another turn
        {
            System.out.println("FAIL");
            return "Invalid";
        }
        boolean look = false; // Look doesn't count as a turn
        switch (input.charAt(0)) // Shortened input from HumanPlayer class, calls appropriate method
        {
            case 'M':
            System.out.println(move(input.charAt(2)));
            break;

            case 'H':
            System.out.println(hello());
            break;

            case 'L':
            System.out.println(look());
            look = true;
            break;

            case 'P':
            System.out.println(pickup());
            break;

            case 'Q':
            quitGame();
            break;
        }
        if (look)
            return "Look";
        else
            return "Valid";
    }

    /**
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        if (lose) // Prints appropriate message if player has lost or won
        {
            System.out.println("You have lost!");
            return false;
        }
        if (win)
        {
            System.out.println("You have won!");
            return false;
        }
        return true;
    }

    /**
     * @return : Returns back gold player requires to exit the Dungeon.
     */
    protected String hello() {
        return "Gold to win: <" + (map.getGoldRequired() - player.getGold()) + ">"; // Informs player of gold left to collect
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param instruction : The direction of the movement
     * @return : Protocol if success or not.
     */
    protected String move(char direction) {
        int x = player.getX(); 
        int y = player.getY();
        char mapArray [][] = map.getMap();
        if (direction == 'N' || direction == 'n')
        {
            y--;
        }
        else if (direction == 'S' || direction == 's')
            y++;
        else if (direction == 'E' || direction == 'e')
            x++;
        else if (direction == 'W' || direction == 'w')
            x--;
        if ((x < 0 || y < 0) || y >= mapArray.length || x >= mapArray[0].length || mapArray[y][x] == '#') // player cannot move outside the map or into a wall
            return "FAIL";

        if (y == bot.getY() && x == bot.getX()) // If player moves onto bot then the player loses
            lose = true;
        else if (mapArray[y][x] == 'E' && player.getGold() >= map.getGoldRequired()) // if the player reaches an exit with enough gold then the  player has won
            win = true;
        else
            player.setPos(x, y); // otherwise, simply update player position

        return "SUCCESS";
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param instruction : The direction of the movement, plus an indication of whether the player or the bot is making this move
     *                      This is done because the starter code only uses one s
     * @return : Protocol if success or not.
     */
    protected void botMove(char direction) {
        int x = bot.getX();
        int y = bot.getY();
        char mapArray [][] = map.getMap();
        if (direction == 'N')
        {
            y--;
        }
        else if (direction == 'S')
            y++;
        else if (direction == 'E')
            x++;
        else if (direction == 'W')
            x--;

        if (y == player.getY() && x == player.getX()) // If bot cathes player, player loses
            lose = true;
        else if ((x < 0 || y < 0) || y >= mapArray.length || x >= mapArray[0].length || mapArray[y][x] == '#') // Bot also cannot leave map, but wastes a turn if attempting it, which player doesn't, only actually applicable on a map without walls
            return;
        else
            bot.setPos(x, y);

    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map.
     */
    protected String look() 
    {
        StringBuilder sMap = new StringBuilder(); // StringBuilder avoids constructing several immutable string objects that are discarded
        char mapArray [][] = map.getMap(); // fetches map
        int x = player.getX();
        int y = player.getY();
        for (int row = y - 2; row <= y + 2; row++)
        {
            for (int column = x - 2; column <= x + 2; column++)
            {
                if (column == player.getX() && row == player.getY())
                    sMap.append("P"); // Adds player position to map
                else if (column == bot.getX() && row == bot.getY())
                    sMap.append("B"); // Adds bot position to map
                else
                    try{
                        sMap.append(Character.toString(mapArray[row][column])); // Adds map character to StringBuilder
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        sMap.append("#"); // If positions outside walls need printing, then they are represented as walls
                    }
            }
            sMap.append("\n"); // Newline character after every line
        }
        return sMap.toString(); // Changes StringBuilder to String
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        char mapArray [][] = map.getMap();
        int x = player.getX();
        int y = player.getY();
        if (mapArray[y][x] == 'G') // If there is gold,then it is picked up
        {
            player.addGold(); 
            map.setChar(x, y, '.'); // Removes gold from map
            return "SUCESS. Gold owned: <" + player.getGold() + ">";
        }

        return "FAIL";
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() 
    {
        System.out.println("GoodBye!");
        System.exit(0);
    }

    /**
     * Runs the game, alterating between bot and player turns while gameRunning() returns true
     */
    public void runGame()
    {
        String action; // validity of action chosen
        while (gameRunning())
        {
            do 
            {
                action = processCommand(player.getInputFromConsole()); // gets and processes player input
                if (action.equals("Invalid"))
                    System.out.println("Invalid command, try again:");
            } while (!action.equals("Valid")); // Invalid or look actions doesn't take a turn
            botMove(bot.act(player.getX(), player.getY())); // bots turn
        }
    }

    /**
     * Creates game object
     */
    public static void main(String[] args) 
    {
        GameLogic game = new GameLogic();
        System.exit(0);
    }
}