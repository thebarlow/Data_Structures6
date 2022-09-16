import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

//import src.Edge;
//import src.Vertex;

public class Graph {

  // Keep a fast index to nodes in the map
  private Map<Integer, Vertex> vertexNames;

  /**
   * Construct an empty Graph with a map. The map's key is the name of a vertex
   * and the map's value is the vertex object.
   */
  public Graph() {
    vertexNames = new HashMap<>();
  }

  /**
   * Adds a vertex to the graph. Throws IllegalArgumentException if two vertices
   * with the same name are added.
   * 
   * @param v
   *          (Vertex) vertex to be added to the graph
   */
  public void addVertex(Vertex v) {
    if (vertexNames.containsKey(v.name))
      throw new IllegalArgumentException("Cannot create new vertex with existing name.");
    vertexNames.put(v.name, v);
  }

  /**
   * Gets a collection of all the vertices in the graph
   * 
   * @return (Collection<Vertex>) collection of all the vertices in the graph
   */
  public Collection<Vertex> getVertices() {
    return vertexNames.values();
  }

  /**
   * Gets the vertex object with the given name
   * 
   * @param name
   *          (String) name of the vertex object requested
   * @return (Vertex) vertex object associated with the name
   */
  public Vertex getVertex(String name) {
    return vertexNames.get(name);
  }

  /**
   * Adds a directed edge from vertex u to vertex v
   * 
   * @param nameU
   *          (String) name of vertex u
   * @param nameV
   *          (String) name of vertex v
   * @param cost
   *          (double) cost of the edge between vertex u and v
   */
  public void addEdge(int nameU, int nameV, Double cost) {
    if (!vertexNames.containsKey(nameU))
      throw new IllegalArgumentException(nameU + " does not exist. Cannot create edge.");
    if (!vertexNames.containsKey(nameV))
      throw new IllegalArgumentException(nameV + " does not exist. Cannot create edge.");
    Vertex sourceVertex = vertexNames.get(nameU);
    Vertex targetVertex = vertexNames.get(nameV);
    Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
    sourceVertex.addEdge(newEdge);
  }

  /**
   * Adds an undirected edge between vertex u and vertex v by adding a directed
   * edge from u to v, then a directed edge from v to u
   * 
   * @param name
   *          (String) name of vertex u
   * @param name2
   *          (String) name of vertex v
   * @param cost
   *          (double) cost of the edge between vertex u and v
   */
  public void addUndirectedEdge(int name, int name2, double cost) {
    addEdge(name, name2, cost);
    addEdge(name2, name, cost);
  }


  /**
   * Computes the euclidean distance between two points as described by their
   * coordinates
   * 
   * @param ux
   *          (double) x coordinate of point u
   * @param uy
   *          (double) y coordinate of point u
   * @param vx
   *          (double) x coordinate of point v
   * @param vy
   *          (double) y coordinate of point v
   * @return (double) distance between the two points
   */
  public double computeEuclideanDistance(double ux, double uy, double vx, double vy) {
    return Math.sqrt(Math.pow(ux - vx, 2) + Math.pow(uy - vy, 2));
  }

  /**
   * Computes euclidean distance between two vertices as described by their
   * coordinates
   * 
   * @param u
   *          (Vertex) vertex u
   * @param v
   *          (Vertex) vertex v
   * @return (double) distance between two vertices
   */
  public double computeEuclideanDistance(Vertex u, Vertex v) {
    return computeEuclideanDistance(u.x, u.y, v.x, v.y);
  }

  /**
   * Calculates the euclidean distance for all edges in the map using the
   * computeEuclideanCost method.
   */
  public void computeAllEuclideanDistances() {
    for (Vertex u : getVertices())
      for (Edge uv : u.adjacentEdges) {
        Vertex v = uv.target;
        uv.distance = computeEuclideanDistance(u.x, u.y, v.x, v.y);
      }
  }



  // STUDENT CODE STARTS HERE

  public void generateRandomVertices(int n) {
	try{
		if(n<=0){
			throw new Exception();
		}
	}
	catch(Exception e){
		System.out.println("\n Please don't do that. This program works best with 2+ vertices \n");
	}
	  
    vertexNames = new HashMap<>(); // reset the vertex hashmap
    
    Random rgen = new Random();//initializes new random generator
    for(int i=0;i<n;i++){
	    int my_y=rgen.nextInt(101);//values of x and y are within the range [0,100], because the generator is exclusive
	    int my_x=rgen.nextInt(101);//of its upper bound (101)
	    Vertex my_vert= new Vertex(i, my_x, my_y);
	    vertexNames.put(i, my_vert);
    }
    Collection<Vertex> allVert=getVertices();
    for(Vertex v : allVert){
    	for(Vertex k: allVert){
    		if(!v.equals(k)){
    			addEdge(v.name,k.name,1.0);
    		}
    	}
    }
    
    computeAllEuclideanDistances(); // compute distances
  }
  public List<Edge> nearestNeighborTsp() { 
	  /*1. Start on arbitrary vertex as current vertex - I choose the first vertex in the list getVertices()
	   * 2. find shortest edge connecting current vertex and some unvisited vertex V
	   * 3. set current vertex to V
	   * 4. mark V as visited
	   * 5. If all the vertices in domain are visited, terminate
	   * 6. Go to step 2
	   */
	  Collection<Vertex> allVertex=getVertices();
	  List<Vertex> list = new ArrayList<Vertex>(allVertex);
	  int start_index=0;
	  List<Edge> finResult=new ArrayList<Edge>();
	  for(Vertex x: allVertex){
		  x.known=false; // make sure everything starts as unknown, this makes it so the program doesn't crash with subsequent runs
	  }
	  try{
		  if(allVertex.size()<=1){
			  throw new Exception();
		  }
	  }
	  catch(Exception e){
		  System.out.println("\n NOT COOL DUDE, I DON'T TRY TO BREAK YOUR STUFF >.> \n TO BE CLEAR, THIS WAS CLOSED INTENTIONALLY \n");
		  
		  System.exit(0);
	  }
	  Vertex current=list.get(start_index);
	  Vertex original=current; //we keep track of the original vertex to make sure we come back to it at the end for a full loop
	  

	  double unKnown=allVertex.size();
	  while(unKnown>0){ //so long as there are vertexes we have yet to visit...
		  Edge tempEdge=null;
		  List<Edge> nearMe=current.adjacentEdges;
		  for(Edge e:nearMe){
			  Vertex target = e.target;
			  if(!target.known){
				  if(tempEdge==null){
					  tempEdge=e;
				  }
				  else if(e.distance<tempEdge.distance){
					  tempEdge=e;
				  }
			  }
		  }
		  if(tempEdge==null){
			  for(Edge e:current.adjacentEdges){
				  if(e.target.equals(original)){
					  tempEdge=e;
				  }
			  }
		  }
		  Vertex nextVert=tempEdge.target;
		  finResult.add(tempEdge);
		  current.known=true;
		  current=nextVert;
		  unKnown--;
	  }
	  return finResult;
 }
  /*Credit to:
   * https://www.programcreek.com/2013/02/leetcode-permutations-java/
   * for permutation method
   */
  public ArrayList<ArrayList<Vertex>> permutations(List<Vertex> myList){
	  ArrayList<ArrayList<Vertex>> result = new ArrayList<ArrayList<Vertex>>();
	  result.add(new ArrayList<Vertex>());
	  for(int i=0; i<myList.size();i++){
		  ArrayList<ArrayList<Vertex>> aList = new ArrayList<ArrayList<Vertex>>();
		  for(ArrayList<Vertex> curr: result){
			  for( int k=0;k<curr.size()+1; k++){
				  curr.add(k,myList.get(i));
				  ArrayList<Vertex> thing = new ArrayList<Vertex>(curr);
				  aList.add(thing);
				  curr.remove(k);
			  }
		  }
		  result= new ArrayList<ArrayList<Vertex>>(aList);
	  }
	  
	  return result;
  }
  /*Picks a random start vertex
   * Creates ever possible permutation of the vertices, then convert that list to edge
   * Only edges which start with our starting vertex are considered. At the end of the run, we add our starting vertex to 
   * the end of the list, then compute the which list of edges has the lowest weight. 
   */
  public List<Edge> bruteForceTsp() {
	  List<Vertex> myList = new ArrayList<Vertex>(getVertices());
	  ArrayList<ArrayList<Vertex>> allPermutations=permutations(myList);
	  ArrayList<List<Edge>> allEdges = new ArrayList<List<Edge>>();
	  try{
		  if(myList.size()<=1){
			  throw new Exception();
		  }
	  }
	  catch(Exception e){
		  System.exit(0);
		  System.out.println("\n NOT COOL DUDE, I DON'T TRY TO BREAK YOUR STUFF >.> \n TO BE CLEAR, THIS WAS CLOSED INTENTIONALLY \n");
	  }
	  for(ArrayList<Vertex> first: allPermutations){
		  List<Edge> tempEdge = new ArrayList<Edge>();
		  for(int i=0;i<first.size()-1;i++){
			  for(Edge e: first.get(i).adjacentEdges){
				  if(e.target.equals(first.get(i+1))){
					  tempEdge.add(e);
				  }
			  }
		  }
		  allEdges.add(tempEdge);
	  }
	  
	  
	  int shortest_distance=-1;
	  List<Edge> shortest_path= null;
	  for(List<Edge> le: allEdges){
		  Vertex start_vertex=le.get(0).source;
		  Vertex last_visited= le.get(le.size()-1).target;
		  for(Edge doIt: last_visited.adjacentEdges){
			  if(doIt.target.equals(start_vertex)){
				  le.add(doIt);
			  }
		  }
		  int total_distance=0;
		  for(Edge e: le){
			  total_distance+=e.distance;
		  }
		  if(total_distance<shortest_distance || shortest_distance==-1){
			  shortest_distance=total_distance;
			  shortest_path=le;
		  }
	  }
	  return shortest_path;
  }

  // STUDENT CODE ENDS HERE



  /**
   * Prints out the adjacency list of the graph for debugging
   */
  public void printAdjacencyList() {
    for (int u : vertexNames.keySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(u);
      sb.append(" -> [ ");
      for (Edge e : vertexNames.get(u).adjacentEdges) {
        sb.append(e.target.name);
        sb.append("(");
        sb.append(e.distance);
        sb.append(") ");
      }
      sb.append("]");
      System.out.println(sb.toString());
    }
  }
}
