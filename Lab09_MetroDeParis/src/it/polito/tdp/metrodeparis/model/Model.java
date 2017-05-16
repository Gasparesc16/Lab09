package it.polito.tdp.metrodeparis.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private List<Fermata> fermate;
	private UndirectedGraph<Fermata,DefaultWeightedEdge> grafo;
	
	
	public Model() {
		
	}
	
	public List<Fermata> getAllFermate() {
		
		if(this.fermate == null){
			
			MetroDAO dao = new MetroDAO();
			this.fermate = dao.getAllFermate();			
		}
		
		return this.fermate;
		
		
	
	}
	
	public void creGrafo(){
		
		grafo = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.getAllFermate());
		
		//System.out.println(grafo.vertexSet());
		
		
	}
		
		
		
	

	public Fermata getAllFermateArrivo(Fermata partenza) {
		
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
