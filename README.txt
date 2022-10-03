README

Included files:
* Display.java
* Edge.java
* Graph.java
* Vertex.java
* Writen.pdf
* README.txt

To run the program:
Compile Display.java with:
javac ÐXlint Display.java
Then run it with:
java Display


PROMPT 
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Generating a Random Complete Graph - in Graph.java, implement the method generateRandomVertices(int n). This method should add vertices and edges to the Graph instance. After running the method, the instance should represent a complete graph with nvertices with the IDs/names 0,1,...,n-1 at random (x,y) coordinates. x and y can range between 0 and 100. Use java.util.Random to create random numbers. If the method is implemented correctly, clicking on "Generate Random Graph" in the GUI will display the graph.
Nearest Neighbor - Implement the method nearestNeighborTsp(), which should use the nearest neighbor algorithm to find an estimate of the shortest simple cycle that visits each vertex. The method should return a list of Edges on the cycle. Make sure to add the correct distance to each edge. If the method is implemented correctly, clicking on "Nearest Neighbor" in the GUI will display the nearest neighbor tour as an overlay. The GUI will also display the sum of edge costs, as well as the time required to compute the tour. This should not take longer than a few milliseconds for a graph of size 8.
Brute Force - Implement the method bruteForceTsp(), which should compute the actualshortest simple cycle visiting each vertex. The method returns this cycle in the same format as nearestNeighborTsp(). You can run the method by clicking the "Brute Force" button, but it is not advisable to try this for graphs with n>8. 
