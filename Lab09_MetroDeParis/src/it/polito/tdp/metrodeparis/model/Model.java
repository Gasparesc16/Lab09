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
	
	//private DefaultDirectedWeightedGraph<FermataSuLinea, DefaultWeightedEdge> grafo = null;
	//private List<FermataSuLinea> fermateSuLinea;
	
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
		
		//fermateSuLinea = dao.getAllFermateSuLinea(fermate, linee);
		
		grafo = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//grafo = new DefaultDirectedWeightedGraph<FermataSuLinea, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// Aggiungo un nodo per ogni fermata
		Graphs.addAllVertices(grafo, this.getAllFermate());
		//Graphs.addAllVertices(grafo, fermateSuLinea);
		
		for(Connessione c: connessioni){
			
			// IMPORTANTE:
			// Usando un grafo semplice non prendo in considerazione il caso
			// in cui due fermate siano collegate da più linee.
			
			double velocita = c.getLinea().getVelocità();
			double distanza = LatLngTool.distance(c.getStazionePartenza().getCoords(), c.getStazioneArrivo().getCoords(), LengthUnit.KILOMETER);
			double tempo = (distanza / velocita) * 60 * 60;
			
			// Cerco la fermataSuLinea corretta all'interno della lista delle fermate
//				FermataSuLinea fslPartenza = fermateSuLinea.get(fermateSuLinea.indexOf(new FermataSuLinea(c.getStazionePartenza(), c.getLinea())));
//				FermataSuLinea fslArrivo = fermateSuLinea.get(fermateSuLinea.indexOf(new FermataSuLinea(c.getStazioneArrivo(), c.getLinea())));
//			

			// Aggiungoun un arco pesato tra le due fermate
			Graphs.addEdge(grafo,c.getStazionePartenza(), c.getStazioneArrivo(), tempo);
			//Graphs.addEdge(grafo, fslPartenza, fslArrivo, tempo);
		}
		
		// -----   Esercizio  2 ------
		
//		// Aggiungo tutte le connessioni tra tutte le fermateSuLinee della stessa Fermata
//				for (Fermata fermata : fermate) {
//					
//					FermataSuLinea metaFermataP = fermateSuLinea.get(fermateSuLinea.indexOf(new FermataSuLinea(fermata, virtInizioCorsa)));
//					FermataSuLinea metaFermataA = fermateSuLinea.get(fermateSuLinea.indexOf(new FermataSuLinea(fermata, virtFineCorsa)));
//					
//					for (FermataSuLinea ft : fermata.getFermateSuLinea()) {
//						if (debug) System.err.println("Pseudo arco da " + metaFermataP + " a " + ft);
//						Graphs.addEdge(grafo, metaFermataP, ft, 0);
//						if (debug) System.err.println("Pseudo arco da " + ft + " a " + metaFermataA);
//						Graphs.addEdge(grafo, ft, metaFermataA, 0);
//					}
//
//					for (FermataSuLinea fslP : fermata.getFermateSuLinea()) {
//						for (FermataSuLinea fslA : fermata.getFermateSuLinea()) {
//							if (!fslP.equals(fslA)) {
//								Graphs.addEdge(grafo, fslP, fslA, fslA.getLinea().getIntervallo() * 60 / 2);
//							}
//						}
//					}
//				}
//		
	
		
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
		
		
		
//		FermataSuLinea fslP = fermateSuLinea.get(fermateSuLinea.indexOf(new FermataSuLinea(partenza, virtInizioCorsa)));
//		FermataSuLinea fslA = fermateSuLinea.get(fermateSuLinea.indexOf(new FermataSuLinea(arrivo, virtFineCorsa)));
//
//		System.out.println("Calcolo percorso da: " + fslP + " a " + fslA);
//
//		DijkstraShortestPath<FermataSuLinea, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<FermataSuLinea, DefaultWeightedEdge>(grafo, fslP, fslA);
//
//		pathEdgeList = dijkstra.getPathEdgeList();
//		pathTempoTotale = dijkstra.getPathLength();
//
//		if (pathEdgeList == null)
//			throw new RuntimeException("Non è stato creato un percorso.");
		
	
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
		
		
		
		
//		if (pathEdgeList == null)
//			throw new RuntimeException("Non è stato creato un percorso.");
//
//		StringBuilder risultato = new StringBuilder();
//		risultato.append("Percorso:");
//
//		Linea ultimaLinea = null;
//		for (DefaultWeightedEdge edge : pathEdgeList) {
//			if (!grafo.getEdgeTarget(edge).getLinea().equals(virtFineCorsa)) {
//				if (!grafo.getEdgeTarget(edge).getLinea().equals(ultimaLinea)) {
//					ultimaLinea = grafo.getEdgeTarget(edge).getLinea();
//					risultato.append("\n\nPrendere Linea: " + ultimaLinea.getNome() + "\n");
//				} else {
//					risultato.append(", ");
//				}
//				risultato.append(grafo.getEdgeTarget(edge).getNome());
//			}
//		}
//
//		return risultato.toString();
	}

	public double getPercorsoTempoTotale() {

		if (pathEdgeList == null)
			throw new RuntimeException("Non è stato creato un percorso.");

		return pathTempoTotale;
	}
	
	
	
	
	
	
	
	
	
	
	

}
