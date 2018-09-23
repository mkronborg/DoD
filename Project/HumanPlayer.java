/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */

import java.io.*;
public class HumanPlayer extends Player {
    BufferedReader reader;
    int gold; 
    // Other instance variables are already declared in superclass
    public HumanPlayer(char[][] mapArray)
    {
        super(mapArray); // Calls superclass constructor
        reader = new BufferedReader(new InputStreamReader(System.in)); // instantiates BufferedReader
    }
    
    /**
     * @return gold owned by player
     */
    public int getGold()
    {
        return gold;
    }
    
    /**
     * @return gold owned by player
     */
    public void addGold()
    {
        gold++;
    }
    
    /**
     * Reads player's input from the console.
     * <p>
     * return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() 
    {
        try 
        {
            //Keep on accepting input from the command-line
            while(true) 
            {
                String command = reader.readLine(); // Reads a new line from input

                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null) 
                {
                    //Exit code 0 for a graceful exit
                    System.exit(0);
                }

                //Otherwise, (attempt to) process the character
                return getNextAction(command);          
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null; // Unreachable, but the compiler demands a return statement.
        }
    }
    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @param command : Input entered by the user.
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction(String command) 
    {
        if (command.length() > 5 && command.substring(0, 4).equalsIgnoreCase("MOVE"))
        {
            return "M " + command.substring(5, 6); // Shortens information to be passed
        }
        else if (command.length() > 4 && command.substring(0, 5).equalsIgnoreCase("HELLO"))
            return "H"; // Shortens information to be passed
        else if (command.length() > 3 && command.substring(0, 4).equalsIgnoreCase("LOOK"))
        {
            return "L"; // Shortens information to be passed
        }
        else if (command.length() > 5 && command.substring(0, 6).equalsIgnoreCase("PICKUP"))
            return "P"; // Shortens information to be passed
        else if (command.length() > 3 && command.substring(0, 4).equalsIgnoreCase("QUIT"))
            return "Q"; // Shortens information to be passed
        else
            return "Invalid";
    }




}