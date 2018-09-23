
/**
 * Superclass for classes HumanPlayer and Bot, specifies instance variables and general methods
 *
 * 1.1
 */
public class Player
{
    int x; // Instance variables
    int y;
    public Player(char[][] mapArray) // Overloaded constructors
    {
        do
        {
            x = (int)(Math.random() * mapArray[0].length);
            y = (int)(Math.random() * mapArray.length);
        } while (mapArray[y][x] != '.' && mapArray[y][x] != 'E'); // Ensures player is only placed on exits or on regular tiles
    }
    
    public Player(char[][] mapArray, int xCoordinate, int yCoordinate) // For use when another player has already been created, potential for multiplayer, or simply for use with a bot
    {
        do
        {
            x = (int)(Math.random() * mapArray[0].length);
            y = (int)(Math.random() * mapArray.length);
        } while (mapArray[y][x] != '.' && mapArray[y][x] != 'E' && !(xCoordinate == x && yCoordinate == y)); // Prevents player from spawning in specified position in addition to walls and gold
    }
    
    /**
     * @return player xCoordinate
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * @return player yCoordinate
     */
    public int getY()
    {
        return y;
    }
        
    /**
     * @param player's new coordinates
     */
    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
