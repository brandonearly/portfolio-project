package cs2321;

import net.datastructures.*;
import cs2321util.HashMap;
import cs2321util.DoublyLinkedList;

/*
 * Implement Graph interface. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 * 
 * @author CS2321 Instructor
 */
public class AdjacencyGraph<V, E> implements Graph<V, E> {

	private static class myVertex<V,E> implements Vertex<V> {

		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing;
		private Map<Vertex<V>, Edge<E>> incoming;
		
		//constructor to make vertex object
		public myVertex(V element, boolean directed) {
			this.element = element;
			outgoing = new HashMap<>();
			if(directed) {
				incoming = new HashMap<>();
			}
			else {
				incoming = outgoing;
			}
		}
		
		@Override
		public V getElement() {
			return element;
		}
		
		//positionally aware methods
		public void setPosition(Position<Vertex<V>> p) {
			pos = p;
		}
		
		public Position<Vertex<V>> getPosition(){
			return pos;
		}
		
		public Map<Vertex<V>, Edge<E>> getOutgoing(){
			return outgoing;
		}
		
		public Map<Vertex<V>, Edge<E>> getIncoming(){
			return incoming;
		}
	}
	
	private static class myEdge<V,E> implements Edge<E> {
		
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V> [] endpoints;
		
		//constructor to create an edge object
		public myEdge(Vertex<V> u, Vertex<V> v, E element) {
			this.element = element;
			endpoints = (Vertex<V> []) new Vertex[] {u,v};
		}
		
		@Override
		public E getElement() {
			return element;
		}
		
		//positionally aware methods
		public Vertex<V> [] getEndpoints(){
			return endpoints;
		}
		
		public void setPosition(Position<Edge<E>> p) {
			pos = p;
		}
		
		public Position<Edge<E>> getPosition() {
			return pos;
		}
	}
	
	//track the vertices/edges and if the graph is directed or not
	private boolean isDirected;
	private PositionalList<Vertex<V>> vertices = new DoublyLinkedList<>();
	private PositionalList<Edge<E>> edges = new DoublyLinkedList<>();
	
	public AdjacencyGraph(boolean directed) {
		isDirected = directed;
	}

	public AdjacencyGraph() {
		isDirected = false; 
	}
	
	//ensure that a given vertex is actually in the graph, and then return it if so
	private myVertex<V,E> validateVertex(Vertex<V> v) throws IllegalArgumentException{
		for(Position<Vertex<V>> pos : vertices.positions()) {
			if(pos.getElement().equals(v)) {
				return (myVertex<V,E>) pos.getElement();
			}
		}
		
		throw new IllegalArgumentException("Vertex not in graph");
	}
	
	//ensure that a given edge is actually in the graph and then return it if so
	public myEdge<V,E> validateEdge(Edge<E> e) throws IllegalArgumentException{
		for (Position<Edge<E>> pos : edges.positions()) {
            if (pos.getElement().equals(e)) {
                return (myEdge<V,E>) pos.getElement();
            }
        }
        
        throw new IllegalArgumentException("Edge is not in the graph");
	}
	
	
	/* (non-Javadoc)
	 * @see net.datastructures.Graph#edges()
	 * returns a list of all edges in the graph
	 */
	@TimeComplexity("O(1)")
	public Iterable<Edge<E>> edges() {
		/*
		 * TCJ: O(1) operation to return a list
		 */
		return edges;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
	 * returns a list of all the vertices an edge ends at
	 */
	@TimeComplexity("O(m)")
	public Vertex[] endVertices(Edge<E> e) throws IllegalArgumentException {
		/*
		 * TCJ: the validate method checks the list of all edges, so
		 * if m is the number of edges in the graph, the worst case 
		 * is O(m)
		 */
		myEdge<V,E> edge = validateEdge(e);
		return edge.getEndpoints();
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
	 * add a new edge into the graph between 2 vertices with a specific name
	 */
	@TimeComplexityExpected("O(m + n)")
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
			throws IllegalArgumentException {
		/*
		 * TCJ: since both validate and getEdge run in O(n) time
		 * the average time is O(n) despite the majority of the method
		 * running in O(1) time
		 */
		if(getEdge(u,v) == null) {
			myEdge<V,E> edge = new myEdge<>(u,v,o);
			edge.setPosition(edges.addLast(edge));
			myVertex<V,E> orgin = validateVertex(u);
			myVertex<V,E> destination = validateVertex(v);
			orgin.getOutgoing().put(v, edge);
			destination.getIncoming().put(u, edge);
			return edge;
		}
		else {
			throw new IllegalArgumentException("Edge from u to v exists");
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertVertex(java.lang.Object)
	 * add a new vertex into the graph
	 */
	@TimeComplexity("O(1)")
	public Vertex<V> insertVertex(V o) {
		/*
		 * TCJ:O(1) because the linked list performs addLast in O(1) time
		 */
		myVertex<V,E> vertex = new myVertex<>(o, isDirected);
		vertex.setPosition(vertices.addLast(vertex));
		return vertex;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numEdges()
	 * return the number of edges on the graph
	 */
	@TimeComplexity("O(1)")
	public int numEdges() {
		/*
		 * TCJ: O(1) operation to check the size of a linked list
		 */
		return edges.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numVertices()
	 * return the number of vertices on the graph
	 */
	public int numVertices() {
		return vertices.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
	 * returns the vertex on the otherside of the given edge
	 */
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
			throws IllegalArgumentException {
		myEdge<V,E> edge = validateEdge(e);
		Vertex<V> [] endpoints = edge.getEndpoints();
		if(endpoints[0] == v) {
			return endpoints[1];
		}
		else if(endpoints[1] == v) {
			return endpoints[0];
		}
		else {
			throw new IllegalArgumentException("v is not incident to this edge");
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 * removes an edge from the graph and updates the pointers in the vertex
	 */
	@TimeComplexityExpected("O(m)")
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		/*
		 * TCJ: Validate method takes O(m) time based on the number
		 * of edges, every other operation is O(1)
		 */
		myEdge<V,E> edge = validateEdge(e);
		Vertex<V> [] endpoints = edge.getEndpoints();
		myVertex<V,E> u = (myVertex<V,E>) endpoints[0];
		myVertex<V,E> v = (myVertex<V,E>) endpoints[1];
		
		u.getOutgoing().remove(v);
		v.getIncoming().remove(u);
		
		edges.remove(edge.getPosition());
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
	 * removes a vertex from the graph and updates the edges
	 */
	@TimeComplexityExpected("O(n + m)")
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		/*
		 * TCJ: Since the validate method takes O(n) time and 
		 * the removeEdge method takes O(m) time, the average
		 * time is O(n+m)
		 */
		myVertex<V,E> vertex = validateVertex(v);
		for(Edge<E> e : vertex.getOutgoing().values()) {
			removeEdge(e);
		}
		for(Edge<E> e : vertex.getIncoming().values()) {
			removeEdge(e);
		}
		vertices.remove(vertex.getPosition());
	}

	/* 
     * replace the element in edge object, return the old element
     */
	public E replace(Edge<E> e, E o) throws IllegalArgumentException {
		myEdge<V,E> edge = validateEdge(e);
		E oldElement = edge.getElement();
		edge.element = o;
		return oldElement;
	}

    /* 
     * replace the element in vertex object, return the old element
     */
	public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		myVertex<V,E> vertex = validateVertex(v);
		V oldElement = vertex.getElement();
		vertex.element = o;
		return oldElement;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#vertices()
	 * return a list of all the vertices in the graph
	 */
	@TimeComplexity("O(1)")
	public Iterable<Vertex<V>> vertices() {
		return vertices;
	}

	/*
	 * returns the number of edges leaving a certain vertex
	 */
	@Override
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		if(v == null) {
			throw new IllegalArgumentException("Vertex cannot be null");
		}
		myVertex<V,E> vertex = validateVertex(v);
		return vertex.getOutgoing().size();
	}

	/*
	 * returns the number of edges coming into a certain vertex
	 */
	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		if(v == null) {
			throw new IllegalArgumentException("Vertex cannot be null");
		}
		myVertex<V,E> vertex = validateVertex(v);
		return vertex.getIncoming().size();
	}

	/*
	 * returns a list of all outgoing edges on a vertex
	 */
	@Override
	@TimeComplexity("O(n + deg+(n)")
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ: The validate method takes O(n) time, but then
		 * we also return the outgoing edges or deg+(v) which would
		 * take O(deg(v)) time
		 */
		if(v == null) {
			throw new IllegalArgumentException("Vertex cannot be null");
		}
		myVertex<V,E> vertex = validateVertex(v);
		return vertex.getOutgoing().values();
	}

	/*
	 * returns a list of all incoming edges on a vertex
	 */
	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		if(v == null) {
			throw new IllegalArgumentException("Vertex cannot be null");
		}
		myVertex<V,E> vertex = validateVertex(v);
		return vertex.getIncoming().values();
	}

	/*
	 * returns an edge that connects two vertices if it exists
	 */
	@Override
	@TimeComplexityExpected("O(n)")
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ: Because the validate method uses a linear search, the expected time is O(n)
		 * despite the best case being O(1)
		 */
		if(u == null || v == null) {
			throw new IllegalArgumentException("Vertex cannot be null");
		}
		myVertex<V,E> orgin = validateVertex(u);
		return orgin.getOutgoing().get(v);
	}
	
}
