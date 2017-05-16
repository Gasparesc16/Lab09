package it.polito.tdp.metrodeparis.model;


import java.util.List;
//import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private MetroDAO dao;
	
	private SimpleWeightedGraph<Fermata,DefaultWeightedEdge> grafo = null;
	
	private List<Fermata> fermate;
	private List<Connessione> connessioni;
	private List<Linea> linee;
	
	private List<DefaultWeightedEdge> pathEdgeList = null;
	private double pathTempoTotale = 0;
	
	
	public Model() {
		
		dao = new MetroDAO();
		
	}
	
	public List<Fermata> getAllFermate() {
		
		if(this.fermate == null){
			this.fermate = dao.getAllFermate();			
		//	throw new RuntimeException("Lista delle stazioni non disponibile!");
		}
		
		return this.fermate;
		
		
	
	}
	
	public void creaGrafo(){
		
		
		this.fermate = dao.getAllFermate();
		this.linee = dao.getAllLinee();
		this.connessioni = dao.getAllConnessioni(fermate,linee);
		
		grafo = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// Aggiungo un nodo per ogni fermata
		Graphs.addAllVertices(grafo, this.getAllFermate());
		
		
		for(Connessione c: connessioni){
			
			// IMPORTANTE:
			// Usando un grafo semplice non prendo in considerazione il caso
			// in cui due fermate siano collegate da più linee.
			
			double velocita = c.getLinea().getVelocità();
			double distanza = LatLngTool.distance(c.getStazionePartenza().getCoords(), c.getStazioneArrivo().getCoords(), LengthUnit.KILOMETER);
			double tempo = (distanza / velocita) * 60 * 60;

			// Aggiungoun un arco pesato tra le due fermate
			Graphs.addEdge(grafo,c.getStazionePartenza(), c.getStazioneArrivo(), tempo);
		}
		
		
	}
	
	public WeightedGraph<Fermata,DefaultWeightedEdge> getGrafo(){
		
		if(this.grafo == null)
			this.creaGrafo();
		
		return this.grafo;
	}
	
	
	public void calcolaPercorso(Fermata partenza,Fermata arrivo) {
		
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, partenza, arrivo);

		pathEdgeList = dijkstra.getPathEdgeList();
		pathTempoTotale = dijkstra.getPathLength();

		// Nel calcolo del tempo non tengo conto della prima e dell'ultima
		if (pathEdgeList.size() - 1 > 0) 
			pathTempoTotale += (pathEdgeList.size() - 1) * 30;
		
		
		
//		GraphPath<Fermata,DefaultWeightedEdge> path = dijkstra.getPath();
//		
//		if(path == null)
//			return null;
//		
//		return Graphs.getPathVertexList(path);
		
	
	}
	
	public String getPercorsoEdgeList() {

		if (pathEdgeList == null)
			throw new RuntimeException("Non è stato creato un percorso.");

		StringBuilder risultato = new StringBuilder();
		risultato.append("Percorso: [ ");

		for (DefaultWeightedEdge edge : pathEdgeList) {
			risultato.append(grafo.getEdgeTarget(edge).getNome());
			risultato.append(", ");
		}
		risultato.setLength(risultato.length()-2);
		risultato.append("]");

		return risultato.toString();
	}

	public double getPercorsoTempoTotale() {

		if (pathEdgeList == null)
			throw new RuntimeException("Non è stato creato un percorso.");

		return pathTempoTotale;
	}
	
	
	
	
	
	
	
	
	
	
	

}
