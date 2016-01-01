package roadgraph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode    {
    private GeographicPoint location;
    private List<Edge> neighbors;
    
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
     * Add directed edge to neighbor list
     */
    public void addNeighbor(GeographicPoint from, GeographicPoint to, String roadName,
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
    
}