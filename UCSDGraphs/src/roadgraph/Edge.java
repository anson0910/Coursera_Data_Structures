package roadgraph;

import geography.GeographicPoint;

/**
 * 
 * @author anson
 * 
 * A class which represents an edge between two vertices in the class MapGraph
 *
 */

public class Edge   {
    private MapNode startPoint;
    private MapNode endPoint;
    private String roadName;
    private String roadType;
    private double length;
    private int hash = 0;
    
    /**
     * Constructor
     * @param endPoint ending point of edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length The length of the road, in km
     */
    public Edge(MapNode startPoint, MapNode endPoint, 
                        String roadName, String roadType, double length)  {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }
    
    /**
     * Return ending point of edge
     * @return ending point of edge
     */
    public MapNode getEndPoint()    {
        return endPoint;
    }
    
    /**
     * Return length (weight) of edge
     * @return length of edge
     */
    public double getLength()   {
        return length;
    }
    
    public int hashCode()   {
        if (hash != 0)
            return hash;
         for (int i = 0; i < roadName.length(); i++)
             hash = hash * 31 + roadName.charAt(i);
         return hash;
    }
    
    /**
     * Is this edge equal to another one?
     */
    public boolean equals(Object that)    {
        if (that == null || this.getClass() != that.getClass())
            return false;
        if (this == that)
            return true;
        
        Edge thatEdge = (Edge) that;
        if (!startPoint.equals(thatEdge.startPoint))     return false;
        if (!endPoint.equals(thatEdge.endPoint))     return false;
        if (!roadName.equals(thatEdge.roadName)) return false;
        if (!roadType.equals(thatEdge.roadType))    return false;
        if (length != thatEdge.length)  return false;
        
        return true;
    } 
    
}