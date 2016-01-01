/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
    HashMap<GeographicPoint, MapNode> graph;
    int numEdges;	
    
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		graph = new HashMap<GeographicPoint, MapNode>();
		numEdges = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return graph.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return graph.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numEdges;
	}
	
	/**
	 * Return neighbors (edges) of node p
	 * @param p the node
	 * @return list of neighbors (edges) 
	 */
	private List<Edge> getNeighbors(MapNode p) {
	    return p.getNeighbors();
	}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if (location == null || graph.containsKey(location))
		    return false;
		
		graph.put(location, new MapNode(location));		
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
	    if (from == null || to == null || roadType == null ||
	            length < 0 || !graph.containsKey(from) || !graph.containsKey(to))
	        throw new IllegalArgumentException();
	    
	    // Add edge to corresponding "from" vertex in hash map
	    graph.get(from).addNeighbor(graph.get(from), graph.get(to), roadName, roadType, length);	
	    numEdges++;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
	    if (start == null || goal == null || !graph.containsKey(start) || !graph.containsKey(goal))
	        return null;

	    // parent map to record parent of each vertex during search
	    HashMap<MapNode, MapNode> parentMap = 
                new HashMap<MapNode, MapNode>();
	    
	    MapNode startNode = graph.get(start);
	    MapNode goalNode = graph.get(goal);
	    
	    boolean found = bfsSearch(startNode, goalNode, parentMap, nodeSearched);	    
	    if (!found)
	        return null;
	  
		return constructPath(startNode, goalNode, parentMap);
	}	
	/**
	 * Private method to construct the path from start to goal using parentMap
	 */
	private List<GeographicPoint> constructPath(MapNode start, MapNode goal, 
	                                                HashMap<MapNode, MapNode> parentMap)   {
	    LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
	    MapNode curr = goal;        
        while (!curr.equals(start))  {
            path.addFirst(curr.getLocation());
            curr = parentMap.get(curr);
        }
        path.addFirst(start.getLocation());
        return Collections.unmodifiableList(path);
	}
	/**
	 * Private method to actually do the searching for breadth first search
	 */
	private boolean bfsSearch(MapNode start, MapNode goal, 
        	        HashMap<MapNode, MapNode> parentMap, 
        	        Consumer<GeographicPoint> nodeSearched)   {
	    Queue<MapNode> toExplore = new LinkedList<MapNode>();
        HashSet<MapNode> visited = new HashSet<MapNode>();        
        
        toExplore.add(start);
        
        while (!toExplore.isEmpty())   {
            MapNode curr = toExplore.remove();
            nodeSearched.accept(curr.getLocation());      // add to visualization tool
            if (curr.equals(goal))  {
                return true;
            }
            for (Edge neighborEdge : getNeighbors(curr))    {
                // Retrieve end point from edge
                MapNode neighborVertex = neighborEdge.getEndPoint();
                if (!visited.contains(neighborVertex)) {
                    visited.add(neighborVertex);
                    toExplore.add(neighborVertex);
                    parentMap.put(neighborVertex, curr);
                }
            }
        }
        return false;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
	    if (start == null || goal == null || !graph.containsKey(start) || !graph.containsKey(goal))
            return null;
	    MapNode startNode = graph.get(start);
	    MapNode goalNode = graph.get(goal);
	    
	    HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
	    boolean found = dijkstraSearch(startNode, goalNode, parentMap, nodeSearched);
	    if (!found)
	        return null;
		
		return constructPath(startNode, goalNode, parentMap);
	}	
	private boolean dijkstraSearch(MapNode startNode, MapNode goalNode, 
	        HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched)   {
	    PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>(MapNode.DIJKSTRA);
        HashSet<MapNode> visited = new HashSet<MapNode>();
        
        // Initialize starting point's distance to be 0, and add start node to queue
        startNode.setDistStart(0);        
        toExplore.add(startNode);
        
        while (!toExplore.isEmpty())   {
            MapNode curr = toExplore.remove();
            if (!visited.contains(curr))   {
                visited.add(curr);
                nodeSearched.accept(curr.getLocation());      // add to visualization tool
                if (curr.equals(goalNode)) {
                    return true;
                }
                for (Edge neighborEdge : curr.getNeighbors())  {
                    // Retrieve end point from edge
                    MapNode neighborVertex = neighborEdge.getEndPoint();
                    // If neighbor is not visited, and distance through curr is smaller than original distance
                    if (!visited.contains(neighborVertex) && 
                            curr.getDistStart() + neighborEdge.getLength() < neighborVertex.getDistStart())    {
                        neighborVertex.setDistStart(curr.getDistStart() + neighborEdge.getLength());
                        parentMap.put(neighborVertex, curr);
                        toExplore.add(neighborVertex);
                    }
                }
            }
        }
	    return false;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
        if (start == null || goal == null || !graph.containsKey(start) || !graph.containsKey(goal))
            return null;
        MapNode startNode = graph.get(start);
        MapNode goalNode = graph.get(goal);
        
        HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
        boolean found = aStarActualSearch(startNode, goalNode, parentMap, nodeSearched);
        if (!found)
            return null;
        
        return constructPath(startNode, goalNode, parentMap);		
	}
	private boolean aStarActualSearch(MapNode startNode, MapNode goalNode, 
        HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched)    {   
        PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>(MapNode.A_STAR);
        HashSet<MapNode> visited = new HashSet<MapNode>();
	        
        // Initialize starting point's distance to be 0, and add start node to queue
        startNode.setDistStart(0); 
        startNode.setDistPredict(0);     
        toExplore.add(startNode);
        
        while (!toExplore.isEmpty())   {
            MapNode curr = toExplore.remove();
            if (!visited.contains(curr))   {
                visited.add(curr);
                nodeSearched.accept(curr.getLocation());      // add to visualization tool
                if (curr.equals(goalNode)) {
                    return true;
                }
                for (Edge neighborEdge : curr.getNeighbors())  {
                    // Retrieve end point from edge
                    MapNode neighborVertex = neighborEdge.getEndPoint();
                    // If neighbor is not visited, and distance through curr is smaller than original distance
                    if (!visited.contains(neighborVertex) && 
                            curr.getDistStart() + neighborEdge.getLength() + neighborVertex.distance(goalNode) 
                            < neighborVertex.getDistPredict())    {
                        neighborVertex.setDistStart(curr.getDistStart() + neighborEdge.getLength());
                        neighborVertex.setDistPredict(curr.getDistStart() + neighborEdge.getLength() 
                                                                                        + neighborVertex.distance(goalNode));
                        parentMap.put(neighborVertex, curr);
                        toExplore.add(neighborVertex);
                    }
                }
            }
        }
        return false;
	    
	}
	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		// Test number of vertices and edges
		System.out.println("Number of vertices: " + theMap.getNumVertices());
		System.out.println("Number of edges: " + theMap.getNumEdges());		
		
		GeographicPoint start = new GeographicPoint(1, 1);
		GeographicPoint goal = new GeographicPoint(8, -1);
		// Test bfs
		System.out.println("BFS path:");
		List<GeographicPoint> bfsPath = theMap.bfs(start, goal);		
		for (GeographicPoint p : bfsPath)
		    System.out.println(p);
		// Test dijkstra
		System.out.println("\nDijkstra's path:");
		List<GeographicPoint> dijkstraPath = theMap.dijkstra(start, goal);		
		for (GeographicPoint p : dijkstraPath)
           System.out.println(p);
		// Test A Star
		System.out.println("\nA Star Search path:");
        List<GeographicPoint> aStarPath = theMap.aStarSearch(start, goal);        
        for (GeographicPoint p : aStarPath)
           System.out.println(p);
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
