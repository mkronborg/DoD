/**
 * Reads and contains in memory the map of the game.
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
public class Map {

    /* Representation of the map */
    private char[][] map;

    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
    public Map() {
        
            mapName = "Very small Labyrinth of Doom";
            goldRequired = 2;
            map = new char[][]{
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
            };
        System.out.println("You are playing on: " + mapName);
    }

    /**
     * Constructor that accepts a map to read in from.
     *
     * @param : The filename of the map file.
     */
    public Map(String fileName) 
    {
        readMap(fileName); 
    }

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /**
     * Changes a given coordinate on the map, used to remove gold from map
     * 
     * @param coordinates of point, and char to place there
     */
    protected void setChar(int x, int y, char c)
    {
        map[y][x] = c;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap()
    {
        return map;
    }

    /**
     * @return : The name of the current map.
     */
    protected String getMapName()
    {
        return mapName;
    }

    /**
     * Reads the map from file.
     *
     * @param : Name of the map's file.
     */
    protected void readMap(String fileName) 
    {
        try {
            File file = new File(fileName); // Attempts to find file
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String s;
            do
            {
                s = reader.readLine();
            }
            while  (s.length() > 3 && !s.substring(0, 4).equals("name")); // If map name is not the first line it loops until it reaches that line
            mapName = s.substring (5, s.length()); //Reads map name

            do
            {
                s = reader.readLine();
            }
            while  (s.length() > 2 && !s.substring(0, 3).equals("win")); // If win condition is the next line it loops until it reaches that line
            int i = 5;
            StringBuilder win = new StringBuilder();
            win.append(s.substring(4, i));
            while(i < s.length() && Character.isDigit(s.charAt(i)))
            {
                win.append(Character.toString(s.charAt(i))); // Reads all digits to 
            }
            goldRequired = Integer.parseInt(win.toString()); // Parses string of digits to gold required to win
            char c;
            int x = 0;
            int y = 0;
            ArrayList<String> read = new ArrayList(); // Used due to dynamic size;
            s = reader.readLine();
            while(s != null) // Reads each line in the file and places it in an ArrayList due to its dynamic sizing, so the dimensions of the map are known
            {
                read.add(s);
                s = reader.readLine();
            }
            
            map = new char [read.size()][read.get(0).length()];
            for (int k = 0; k < read.size(); k++) // Reads ArrayList into 2d array
            {
                for (int a = 0; a < read.get(k).length(); a++)
                {
                    map[k][a] = read.get(k).charAt(a);
                }
            }
            System.out.println("\"example_map.txt\" found and scannned.");
        }
        catch (IOException ioe) // If file cannot be found, standard map will be used instead
        {
            System.out.println("File not found, standard map used instead");
            mapName = "Very small Labyrinth of Doom";
            goldRequired = 2;
            map = new char[][]{
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
            };
        }
    }

}
