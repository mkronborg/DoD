
/**
 * Describes a point on the map, but including a counter variable, and functionality to create a weighted map
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class CountedPoint
{
    int x, y, c;
    ArrayList <CountedPoint> nearby;
    /**
     * Constructor for objects of class countedPoint
     */
    public CountedPoint(int x, int y, int c)
    {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public boolean alreadyInQueue(ArrayList<CountedPoint> q)
    {
        for (int count = 0; count < q.size(); count++)
        {
            if (present(q.get(count)))
                return true;
        }
        return false;
    }

    public boolean present(CountedPoint p)
    {
        if (x == p.getX() && y == p.getY() && c > p.getC())
            return true;
        else
            return false;
    }

    public CountedPoint getNearby(int i)
    {
        CountedPoint p;
        if ( i == 1)
        {
            p = new CountedPoint(x + 1, y, c + 1);
        }
        else if ( i == 2)
        {
            p = new CountedPoint(x - 1, y, c + 1);
        }
        else if ( i == 3)
        {
            p = new CountedPoint(x, y + 1, c + 1);
        }
        else
            p = new CountedPoint(x, y - 1, c + 1);

        return p;
    }

    public CountedPoint getBestMove(ArrayList<CountedPoint> weights)
    {
        CountedPoint best = null; // best point to move to to be found
        boolean first = true;
        fillNearby(); // Fills array with nearby points
        for (int i = 0; i < weights.size(); i++)
        {
            for (int k = 0; k < 4; k++) // There will only be four points in nearby
            {
                if (theSame(weights.get(i), nearby.get(k)))
                    if (first) // assigns the first nearby point in weights to be the best point
                    {
                        best = weights.get(i);
                        first = false;
                    }
                    else if (best.getC() > weights.get(i).getC())
                        best = weights.get(i);
            }
        }

        return best;
    }

    public boolean theSame(CountedPoint a, CountedPoint b)
    {
        if (a.getX() == b.getX() && a.getY() == b.getY())
            return true;
        else
            return false;
    }

    public ArrayList<CountedPoint> fillNearby() // fills instance variables with four nearest points, but also returns that instance variable as method is also used in the bot class
    {
        nearby = new ArrayList();
        nearby.add(getNearby(1));
        nearby.add(getNearby(2));
        nearby.add(getNearby(3));
        nearby.add(getNearby(4));
        return nearby;
    }

    public boolean compareCoordinates(CountedPoint p)
    {
        if (x == p.getX() && y == p.getY())
            return true;
        else
            return false;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getC()
    {
        return c;
    }

    public void setC(int c)
    {
        this.c = c;
    }
}
