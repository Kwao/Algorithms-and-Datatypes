import java.util.*;

public class Prims {
//This part build the structure of the graph
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(final int source, final int destination, final int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
//The heap provides the representation of the vertex and its location
    static class HeapNode {
        int vertex;
        int key;
    }

    static class ResultSet {
        int parent;
        int weight;
    }
//The graph is being presented as an adjacencylist and stuctured as a linkedlist.
    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjacencylist;

        Graph(final int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }
//This routine adds edges and there position to the adjacencylist
        public void addEdge(final int source, final int destination, final int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencylist[destination].addFirst(edge); 
        }
//The prims minimum spanning tree is worked here
        public void primsMST() {

            final boolean[] inPriorityQueue = new boolean[vertices];
            final ResultSet[] resultSet = new ResultSet[vertices];
            final int[] key = new int[vertices]; 

            final HeapNode[] heapNodes = new HeapNode[vertices];
            for (int i = 0; i < vertices; i++) {
                heapNodes[i] = new HeapNode();
                heapNodes[i].vertex = i;
                heapNodes[i].key = Integer.MAX_VALUE;
                resultSet[i] = new ResultSet();
                resultSet[i].parent = -1;
                inPriorityQueue[i] = true;
                key[i] = Integer.MAX_VALUE;
            }

            heapNodes[0].key = 0;

            final PriorityQueue<HeapNode> pq = new PriorityQueue<>(vertices, new Comparator<HeapNode>() {
                @Override
                public int compare(final HeapNode o1, final HeapNode o2) {
                    return o1.key - o2.key;
                }
            });
            for (int i = 0; i < vertices; i++) {
                pq.offer(heapNodes[i]);
            }

            while (!pq.isEmpty()) {
                final HeapNode extractedNode = pq.poll();

                final int extractedVertex = extractedNode.vertex;
                inPriorityQueue[extractedVertex] = false;

                final LinkedList<Edge> list = adjacencylist[extractedVertex];
                for (int i = 0; i < list.size(); i++) {
                    final Edge edge = list.get(i);
                    if (inPriorityQueue[edge.destination]) {
                        final int destination = edge.destination;
                        final int newKey = edge.weight;
                        if (key[destination] > newKey) {
                            decreaseKey(pq, newKey, destination);
                            resultSet[destination].parent = extractedVertex;
                            resultSet[destination].weight = newKey;
                            key[destination] = newKey;
                        }
                    }
                }
            }
            printMST(resultSet);
        }

        public void decreaseKey(final PriorityQueue<HeapNode> pq, final int newKey, final int vertex) {

            final Iterator it = pq.iterator();

            while (it.hasNext()) {
                final HeapNode heapNode = (HeapNode) it.next();
                if (heapNode.vertex == vertex) {
                    pq.remove(heapNode);
                    heapNode.key = newKey;
                    pq.offer(heapNode);
                    break;
                }
            }
        }
// Computing the cost of the minimum spanning tree from the graph
        public void printMST(final ResultSet[] resultSet) {
            int total_min_weight = 0;
            System.out.println("Minimum Spanning Tree: ");
            for (int i = 1; i < vertices; i++) {
                System.out.println("Edge: " + i + " - " + resultSet[i].parent + " key: " + resultSet[i].weight);
                total_min_weight += resultSet[i].weight;
            }
            System.out.println("Total minimum key: " + total_min_weight);
        }
    }

    public static void main(final String[] args) {
        final int vertices = 8;
        System.out.println("The number of vertices in the graph is " +vertices);
        final Graph graph = new Graph(vertices);
            graph.addEdge(0, 2, 4);
            graph.addEdge(0, 1, 5);
            graph.addEdge(1, 2, 2);
            graph.addEdge(2, 4, 4);
            graph.addEdge(1, 3, 3);
            graph.addEdge(4, 5, 1);
            graph.addEdge(4, 3, 2);
            graph.addEdge(3, 6, 6);
            graph.addEdge(5, 6, 8);
            graph.addEdge(6, 7, 2);
            //Prims Minimum Spanning Tree Cost
            graph.primsMST();
        }
}