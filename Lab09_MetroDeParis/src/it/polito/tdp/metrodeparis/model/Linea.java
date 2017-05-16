package it.polito.tdp.metrodeparis.model;

public class Linea {
	
	private int idLinea;
	private String nomeLinea;
	private double velocità;
	private double intervallo;
	private String colore;
	
	
	public Linea(int idLinea) {
		
		this.idLinea = idLinea;
	}


	
	public Linea(int idLinea, String nomeLinea, double velocità, double intervallo, String colore) {
		
		this.idLinea = idLinea;
		this.nomeLinea = nomeLinea;
		this.velocità = velocità;
		this.intervallo = intervallo;
		this.colore = colore;
	}



	/**
	 * @return the idLinea
	 */
	public int getIdLinea() {
		return idLinea;
	}



	/**
	 * @return the nomeLinea
	 */
	public String getNomeLinea() {
		return nomeLinea;
	}



	/**
	 * @return the velocità
	 */
	public double getVelocità() {
		return velocità;
	}



	/**
	 * @return the intervallo
	 */
	public double getIntervallo() {
		return intervallo;
	}



	/**
	 * @return the colore
	 */
	public String getColore() {
		return colore;
	}



	/**
	 * @param idLinea the idLinea to set
	 */
	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}



	/**
	 * @param nomeLinea the nomeLinea to set
	 */
	public void setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
	}



	/**
	 * @param velocità the velocità to set
	 */
	public void setVelocità(double velocità) {
		this.velocità = velocità;
	}



	/**
	 * @param intervallo the intervallo to set
	 */
	public void setIntervallo(double intervallo) {
		this.intervallo = intervallo;
	}



	/**
	 * @param colore the colore to set
	 */
	public void setColore(String colore) {
		this.colore = colore;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLinea;
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s %s", idLinea, nomeLinea);
	}
	
	
	
	
	
	
	

}
