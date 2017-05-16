package it.polito.tdp.metrodeparis.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;

public class FermataTraversalListener implements TraversalListener<Fermata, DefaultWeightedEdge> {
	
	
	private Graph<Fermata, DefaultWeightedEdge> grafo ;
	private Map<Fermata,Fermata> map ;
	

	public FermataTraversalListener(Graph<Fermata, DefaultWeightedEdge> grafo, java.util.Map<Fermata,Fermata> map) {
		
		this.grafo = grafo;
		this.map = map;
	}
	
	
	

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<Fermata, DefaultWeightedEdge> evento) {
		// TODO Auto-generated method stub
		
		Fermata f1 = grafo.getEdgeSource(evento.getEdge()) ;
		Fermata f2 = grafo.getEdgeTarget(evento.getEdge()) ;
		
		if(map.containsKey(f1) && map.containsKey(f2))
			return ;
		
		if( !map.containsKey(f1) ) {
			
			map.put(f1,  f2) ;
		} 
		
		else {
			
			map.put(f2,  f1) ;
		}
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Fermata> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Fermata> arg0) {
		// TODO Auto-generated method stub

	}

}
