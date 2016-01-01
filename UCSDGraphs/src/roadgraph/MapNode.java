package roadgraph;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode   {
    public static final Comparator<MapNode> DIJKSTRA = new dijkstraLessThan();
    public static final Comparator<MapNode> A_STAR = new aStarLessThan();
    
    private GeographicPoint location;
    private List<Edge> neighbors;
    // variable to keep track of current distance to start point during search (Dijkstra's Algorithm)
    private double distStart = Double.POSITIVE_INFINITY;
    // variable to keep track of current distance to start point, 
    // plus predicted distance to end point(AStar Search)
    private double distPredict = Double.POSITIVE_INFINITY;

    /**
     * Constructor
     * @param location GeographicPoint
     */
    public MapNode(GeographicPoint location)    {
        this.location = location;
        this.neighbors = new LinkedList<Edge>();
    }
    
    /**
     * Return location of node
     * @return location of node
     */
    public GeographicPoint getLocation()    {
        return location;
    }
    
    /**
     * Calculates the geographic distance in km between this point and 
     * the other point. 
     * @param other
     * @return The distance between this lat, lon point and the other point
     */
    public double distance(MapNode other)
    {
        return this.getLocation().distance(other.getLocation());
    }
    
    /**
     * Add directed edge to neighbor list
     */
    public void addNeighbor(MapNode from, MapNode to, String roadName,
                                  String roadType, double length) {
        neighbors.add(new Edge(from, to, roadName, roadType, length));
    }
    
    /**
     * Return neighbors of node as a list
     * @return list of neighbors
     */
    public List<Edge> getNeighbors()    {
        return Collections.unmodifiableList(neighbors);
    }
    
    /**
     * Set current distance to start point during search (Dijkstra's Algorithm)
     * @param dist distance
     */
    public void setDistStart(double dist)  {
        if (dist < 0)
            throw new IllegalArgumentException();
        distStart = dist;
    }
    
    /**
     * Get current distance to start point during search (Dijkstra's Algorithm)
     * @return distance
     */
    public double getDistStart()    {
        return distStart;
    }
    
    /**
     * Set current distance to start point during search (Dijkstra's Algorithm)
     * @param dist distance
     */
    public void setDistPredict(double dist)  {
        if (dist < 0)
            throw new IllegalArgumentException();
        distPredict = dist;
    }
    
    /**
     * Get current distance to start point during search (Dijkstra's Algorithm)
     * @return distance
     */
    public double getDistPredict()    {
        return distPredict;
    }
    
    public int hashCode()   {
        return location.hashCode();
    }
    
    public boolean equals(Object that) {
        if (that == null || that.getClass() != this.getClass())
            return false;
        if (that == this)
            return true;
        
        MapNode thatNode = (MapNode) that;        
        return this.location.equals(thatNode.location);
    }
    
    public String toString()    {
        return this.location.toString();
    }
    
    private static class dijkstraLessThan implements Comparator<MapNode>  {
        @Override
        public int compare(MapNode o1, MapNode o2) {
            return (int)((o1.distStart - o2.distStart) * 100000);
        }        
    }
    
    private static class aStarLessThan implements Comparator<MapNode>  {
        @Override
        public int compare(MapNode o1, MapNode o2) {
            return (int)((o1.distPredict - o2.distPredict) * 100000);
        }        
    }

}