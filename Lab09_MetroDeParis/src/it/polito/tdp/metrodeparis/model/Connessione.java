package it.polito.tdp.metrodeparis.model;

public class Connessione {
	
	private int idConnessione;
	private Linea linea;
	private Fermata stazionePartenza;
	private Fermata stazioneArrivo;
	
	
	
	public Connessione(int idConnessione) {
		
		this.idConnessione = idConnessione;
	}

	
	public Connessione(int idConnessione, Linea linea, Fermata stazionePartenza, Fermata stazioneArrivo) {
		
		this.idConnessione = idConnessione;
		this.linea = linea;
		this.stazionePartenza = stazionePartenza;
		this.stazioneArrivo = stazioneArrivo;
	}

	/**
	 * @return the idConnessione
	 */
	public int getIdConnessione() {
		return idConnessione;
	}

	/**
	 * @return the linea
	 */
	public Linea getLinea() {
		return linea;
	}

	/**
	 * @return the stazionePartenza
	 */
	public Fermata getStazionePartenza() {
		return stazionePartenza;
	}

	/**
	 * @return the stazioneArrivo
	 */
	public Fermata getStazioneArrivo() {
		return stazioneArrivo;
	}
	
	
	
	
	
}