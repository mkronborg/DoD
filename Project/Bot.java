
/**
 * Inherits from class player. Describes a bot for the game Dungeons of Doom
 *
 * @version 3.0
 */
import java.util.ArrayList; // Imported  to create a list of weighted points
import java.util.Random; // Allows picking random direction
public class Bot extends Player
{
    final char[][] map; // Anything that changes on the map is irrelevant to the bot, so it can continue
    // use the original scan of the map
    char direction; // Direction the bot is currently moving, used when moving randomly
    int pX; // Player's coordinates
    int pY;
    int yDiff; // X and Y distance from bot to player
    int xDiff;
    ArrayList<CountedPoint> weights = new ArrayList(); // ArrayList holding CountePoint objects that allow for finding a path to the player
    int moveCount; // Counts number of moves made by the  bot
    // Other instance variables are already declared in superclass
    public Bot(char[][] mapArray, int pX, int pY) // Receives current player coordinates
    {
        super(mapArray, pX, pY); // Calls superclass constructor
        map = mapArray;
        direction = 'N'; // Default direction is 'N'
        update(pX, pY); // Updates xDiff and yDiff, as well as pX and pY
    }

    public void update(int pX, int pY) // Given player's coordinates, updates instance variables to track player
    {
        this.pX = pX; 
        this.pY = pY;
        xDiff = x - pX;
        yDiff = y - pY;
    }

    public char act(int pX, int pY) // Determines the action taken by the bot
    {
        update(pX, pY); // Updates tracking of player
        char c;
        if (moveCount % 10 == 0) // every 10th turn, the bot waits to give the player a chance to get away if the bot is right behind the player
            return ' '; 
        else if ((Math.abs(xDiff) == 1 && yDiff == 0) || (Math.abs(yDiff) == 1 && xDiff == 0)) // If the bot is right next to the player, it moves to the players position
        {
            CountedPoint p = new CountedPoint(pX, pY, 0); // Weight of player coordinate doesn't matter
            return getTo(p);
        }
        else if (Math.abs(xDiff) < 3 && Math.abs(yDiff) < 3) // Mimics the result of a look action
        {
            c = findPath();
            if (c == ' ') // If there is an error, then a random move is made instead
            {
                return randomMove();
            }
            else
                return c; // 
        }
        else // If player is outside range of sight, the bot moves randomly
        {
            do {
                c = randomMove();
            } while (c == 'r'); // 'r' for repeat 
            return c;
        }
    }

    public char findPath()
    {
        CountedPoint b = new CountedPoint(x, y, 0); // Weight of bot's coordinate doesn't matter
        ArrayList<CountedPoint> weights = weighMap(); // Creates list of weighted points
        CountedPoint b2 = b.getBestMove(weights); // Finds best move from the bots position
        if (b2 == null) // if an error occurs, a random move is made by the bot
            return ' ';
        else
            return getTo(b2); // returns necessary direction to get to point
    }

    /**
     * Based on algorithm described on wikipedia page "Pathfinding"
     * 
     * @ return ArrayList of weighted points based on moves to get to player
     */
    public ArrayList<CountedPoint> weighMap() // For all points that can map the path from the bot to the player, a weight is assigned so the fastest path can be found
    {
        ArrayList<CountedPoint> weights = new ArrayList();
        CountedPoint p = new CountedPoint(pX, pY, 0); // weight of starting point is 0, and the getNearby() method in the CountedPoint class automaticallly increments the weight

        CountedPoint considered = p; // Player position is first to be considered
        ArrayList<CountedPoint> nearby; // An ArrayList of four nearby points to a given one
        int count = 1; // element 0 in ArrayList has already been assigned
        do
        {
            nearby = considered.fillNearby(); // Gets a list of the four nearby points
            for (int i = 0; i < nearby.size(); i++)
            {
                if (map[nearby.get(i).getY()][nearby.get(i).getX()] == '#') // Point is ignored if it is a wall
                {
                    break;
                }
                else
                {
                    for (int k = 0; k < weights.size(); k++)
                    {
                        if (nearby.get(i).compareCoordinates(weights.get(k))) // If points are the same then the new one must have a lower weight to be places in ArrayList
                        {
                            if (nearby.get(i).getC() < weights.get(k).getC()) // If two coordinates are the same but one has a lower wight then that one is used
                            {
                                weights.remove(k); // Removes "inferior" point
                                weights.add(nearby.get(i)); // adds new point
                            }
                        }
                    }
                    weights.add(nearby.get(i));
                }
            }
            considered = weights.get(count);
            count++; // Increments counter variable
        } while (considered.getX() != x && considered.getY() != y); // Searches points until a path is found to the player
        return weights;
    }
    
    /**
     * @param point to move to
     * 
     * @return necessary direction to move in
     */
    public char getTo(CountedPoint p) // Provided a point known to be only one coordinate point away from the bot, the appropriate moves is found to get there
    {
        if (p.getX() - x == -1)
        {
            System.out.println("Move W");
            return 'W';
        }
        else if (p.getX() - x == 1)
        {
            System.out.println("Move E");
            return 'E';
        }
        else if (p.getY() - y == -1)
        {
            System.out.println("Move N");
            return 'N';
        }
        else 
        {
            System.out.println("Move S");
            return 'S';
        }

    }

    /**
     * Picks a random direction to move in
     * 
     * @return 'r' to indicate new direction assignment is necessary
     */
    public char randomDirection()
    {
        char [] directions = {'N', 'S', 'E', 'W'};
        Random rand = new Random();
        direction = directions[rand.nextInt(4)];
        System.out.println("Direction is now: "  + direction);
        return 'r';
    }

    /**
     * Makes the bot move around the map, changing directions when it hits a wall
     * 
     * @return new direction to move in
     */
    public char randomMove() 
    {
        if (direction == 'N' && map[y-1][x] == '#')
            if (map[y + 1][x] != '#') // Picks new direction
                direction = 'S';
            else // If alternative direction is into a wall, a random one is picked, and statement is reevaluated
            {
                direction = randomDirection();
                return 'r';
            }
        else if (direction == 'S' && map[y+1][x] == '#' && map[y][x + 1] != '#') //
            if (map[y][x + 1] != '#')    // Picks new direction
                direction = 'E';
            else// If alternative direction is into a wall, a random one is picked, and statement is reevaluated
            {
                direction = randomDirection();
                return 'r';
            }
        else if (direction == 'E' && map[y][x+1] == '#' && map[y][x - 1] != '#')
            if (map[y][x - 1] != '#')    // Picks new direction
                direction = 'W';
            else// If alternative direction is into a wall, a random one is picked, and statement is reevaluated
            {
                direction = randomDirection();
                return 'r';
            }
        else if (direction == 'W' && map[y][x-1] == '#' && map[y - 1][x] != '#')
            if (map[y - 1][x] != '#')    // Picks new direction
                direction = 'N';
            else// If alternative direction is into a wall, a random one is picked, and statement is reevaluated
            {
                direction = randomDirection();
                return 'r';
            }
        return direction;
    }
}
